package com.fengchi.TimeTravel.Mine;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.hardware.fingerprint.FingerprintManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v4.os.CancellationSignal;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fengchi.TimeTravel.MainActivity;
import com.fengchi.TimeTravel.Mine.Activity.PersonActivity;
import com.fengchi.TimeTravel.R;
import com.fengchi.TimeTravel.Utils.CheckPermissionUtils;
import com.fengchi.TimeTravel.Utils.CleanMessageUtil;
import com.fengchi.TimeTravel.Utils.ImageUtil;
import com.fengchi.TimeTravel.Utils.OnSeekbarChangeListener;
import com.fengchi.TimeTravel.Utils.Seekbar;
import com.fengchi.TimeTravel.Utils.SnackBarUtil;
import com.fengchi.TimeTravel.Utils.ToastUtils;
import com.fengchi.TimeTravel.Utils.UpdateDialog;
import com.jingewenku.abrahamcaijin.commonutil.AppDateMgr;
import com.jingewenku.abrahamcaijin.commonutil.AppToastMgr;
import com.jingewenku.abrahamcaijin.commonutil.DensityUtils;
import com.jingewenku.abrahamcaijin.commonutil.ToolAnimation;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

import static com.fengchi.TimeTravel.R.style.dialog;

public class MineFragment extends Fragment implements EasyPermissions.PermissionCallbacks {
    View view;
    @Bind(R.id.mine_set)
    ImageView mineSet;
    @Bind(R.id.serverAddress)
    RelativeLayout serverAddress;
    @Bind(R.id.checkUpdate)
    RelativeLayout checkUpdate;
    @Bind(R.id.deleteCache)
    RelativeLayout deleteCache;
    @Bind(R.id.userName)
    TextView userName;
    @Bind(R.id.tv_deleteCache)
    TextView tvDeleteCache;
    /**
     * 扫描跳转Activity RequestCode
     */
    public static final int REQUEST_CODE = 111;
    /**
     * 请求CAMERA权限码
     */
    public static final int REQUEST_CAMERA_PERM = 101;

    /**
     * 选择系统图片Request Code
     */
    public static final int REQUEST_IMAGE = 112;
    @Bind(R.id.mine_bg)
    RelativeLayout mineBg;
    private PopupWindow popupWindow;
    private TextView identifyResult;
    private FingerprintManagerCompat fingerprintManager = null;
    private MyAuthCallback myAuthCallback = null;
    private CancellationSignal cancellationSignal = null;

    private Handler handler = null;
    public static final int MSG_AUTH_SUCCESS = 100;
    public static final int MSG_AUTH_FAILED = 101;
    public static final int MSG_AUTH_ERROR = 102;
    public static final int MSG_AUTH_HELP = 103;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mine, container, false);
        ButterKnife.bind(this, view);
        initView();
        //初始化权限
        initPermission();
        fingerprintManager = FingerprintManagerCompat.from(getActivity());
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Log.d("finger", "msg: " + msg.what + " ,arg1: " + msg.arg1);
                switch (msg.what) {
                    case MSG_AUTH_SUCCESS:
                        new Thread() {
                            @Override
                            public void run() {//在run()方法实现业务逻辑；

                                //更新UI操作；
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        identifyResult.setText("验证中...");
                                        try {
                                            Thread.sleep(2000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });

                            }
                        }.start();

                        setResultInfo(R.string.fingerprint_success);
                        //cancellationSignal = null;
                        break;
                    case MSG_AUTH_FAILED:
                        setResultInfo(R.string.fingerprint_not_recognized);
                        //cancellationSignal = null;
                        break;
                    case MSG_AUTH_ERROR:
                        handleErrorCode(msg.arg1);
                        break;
                    case MSG_AUTH_HELP:
                        handleHelpCode(msg.arg1);
                        break;
                }
            }
        };
        if (!fingerprintManager.isHardwareDetected()) {
            // no fingerprint sensor is detected, show dialog to tell user.
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.no_sensor_dialog_title);
            builder.setMessage(R.string.no_sensor_dialog_message);
            builder.setIcon(android.R.drawable.stat_sys_warning);
            builder.setCancelable(false);
            builder.setNegativeButton(R.string.cancel_btn_dialog, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    getActivity().finish();
                }
            });
            // show this dialog.
            builder.create().show();
        } else if (!fingerprintManager.hasEnrolledFingerprints()) {
            // no fingerprint image has been enrolled.
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(R.string.no_fingerprint_enrolled_dialog_title);
            builder.setMessage(R.string.no_fingerprint_enrolled_dialog_message);
            builder.setIcon(android.R.drawable.stat_sys_warning);
            builder.setCancelable(false);
            builder.setNegativeButton(R.string.cancel_btn_dialog, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    getActivity().finish();
                }
            });
            // show this dialog
            builder.create().show();
        } else {
            try {
                myAuthCallback = new MyAuthCallback(handler);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return view;
    }
    private void initIdentify() {
        try {
            CryptoObjectHelper cryptoObjectHelper = new CryptoObjectHelper();
            if (cancellationSignal == null) {
                cancellationSignal = new CancellationSignal();
            }
            fingerprintManager.authenticate(cryptoObjectHelper.buildCryptoObject(), 0,
                    cancellationSignal, myAuthCallback, null);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Fingerprint init failed! Try again!", Toast.LENGTH_SHORT).show();
        }
    }

    private void setResultInfo(int stringId) {
        if (identifyResult != null) {
            if (stringId == R.string.fingerprint_success) {
                Toast.makeText(getActivity(),"验证成功",Toast.LENGTH_LONG).show();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                identifyResult.setText("验证中");
                                try {
                                   Thread.sleep(2000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                identifyResult.setTextColor(getActivity().getColor(R.color.blue_text));
                identifyResult.setText(stringId);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
//                popupWindow.dismiss();
//                showUpdateDialog();
            } else {
                identifyResult.setTextColor(getActivity().getColor(R.color.update_color));
                identifyResult.setText(stringId);
            }

        }
    }

    private void handleHelpCode(int code) {
        switch (code) {
            case FingerprintManager.FINGERPRINT_ACQUIRED_GOOD:
                setResultInfo(R.string.AcquiredGood_warning);
                break;
            case FingerprintManager.FINGERPRINT_ACQUIRED_IMAGER_DIRTY:
                setResultInfo(R.string.AcquiredImageDirty_warning);
                break;
            case FingerprintManager.FINGERPRINT_ACQUIRED_INSUFFICIENT:
                setResultInfo(R.string.AcquiredInsufficient_warning);
                break;
            case FingerprintManager.FINGERPRINT_ACQUIRED_PARTIAL:
                setResultInfo(R.string.AcquiredPartial_warning);
                break;
            case FingerprintManager.FINGERPRINT_ACQUIRED_TOO_FAST:
                setResultInfo(R.string.AcquiredTooFast_warning);
                break;
            case FingerprintManager.FINGERPRINT_ACQUIRED_TOO_SLOW:
                setResultInfo(R.string.AcquiredToSlow_warning);
                break;
        }
    }

    private void handleErrorCode(int code) {
        switch (code) {
            case FingerprintManager.FINGERPRINT_ERROR_CANCELED:
                setResultInfo(R.string.ErrorCanceled_warning);
                break;
            case FingerprintManager.FINGERPRINT_ERROR_HW_UNAVAILABLE:
                setResultInfo(R.string.ErrorHwUnavailable_warning);
                break;
            case FingerprintManager.FINGERPRINT_ERROR_LOCKOUT:
                setResultInfo(R.string.ErrorLockout_warning);
                break;
            case FingerprintManager.FINGERPRINT_ERROR_NO_SPACE:
                setResultInfo(R.string.ErrorNoSpace_warning);
                break;
            case FingerprintManager.FINGERPRINT_ERROR_TIMEOUT:
                setResultInfo(R.string.ErrorTimeout_warning);
                break;
            case FingerprintManager.FINGERPRINT_ERROR_UNABLE_TO_PROCESS:
                setResultInfo(R.string.ErrorUnableToProcess_warning);
                break;
        }
    }

    private void showFingerIdentifyPopupWindow(View view) {
        //rlBackgroundGray.setVisibility(View.VISIBLE);
        final View contentView = LayoutInflater.from(getActivity()).inflate(
                R.layout.popupwindow_fingerprint, null);
        popupWindow = new PopupWindow(contentView, DensityUtils.dp2px(getActivity(),280), DensityUtils.dp2px(getActivity(),250), true);
        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //cancellationSignal.cancel();
                //rlBackgroundGray.setVisibility(View.GONE);
            }
        });
        identifyResult = ((TextView) contentView.findViewById(R.id.identifyResult));
        //popupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }

    /**
     * 初始化权限事件
     */
    private void initPermission() {
        //检查权限
        String[] permissions = CheckPermissionUtils.checkPermission(getActivity());
        if (permissions.length == 0) {
            //权限都申请了
            //是否登录
        } else {
            //申请权限
            ActivityCompat.requestPermissions(getActivity(), permissions, 100);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        /**
         * 处理二维码扫描结果
         */
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    Toast.makeText(getActivity(), "解析成功   解析结果为:" + result, Toast.LENGTH_LONG).show();
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(getActivity(), "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }

        /**
         * 选择系统图片并解析
         */
        else if (requestCode == REQUEST_IMAGE) {
            if (data != null) {
                Uri uri = data.getData();
                try {
                    CodeUtils.analyzeBitmap(ImageUtil.getImageAbsolutePath(getActivity(), uri), new CodeUtils.AnalyzeCallback() {
                        @Override
                        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
                            Toast.makeText(getActivity(), "解析结果:" + result, Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onAnalyzeFailed() {
                            Toast.makeText(getActivity(), "解析二维码失败", Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == REQUEST_CAMERA_PERM) {
            Toast.makeText(getActivity(), "从设置页面返回...", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    private void initView() {
        try {
            tvDeleteCache.setText(CleanMessageUtil.getTotalCacheSize(getActivity().getApplicationContext()));
        } catch (Exception e) {

        }
        final Seekbar rangeSeekbar = (Seekbar) view.findViewById(R.id.rangeSeekbar4);
        final TextView tvreal = (TextView) view.findViewById(R.id.text5);
        rangeSeekbar
                .setCornerRadius(10f)
                .setBarColor(Color.parseColor("#93F9B5"))
                .setBarHighlightColor(Color.parseColor("#16E059"))
                .setMinValue(0)
                .setMaxValue(100)
                .setSteps(2)
                .setLeftThumbDrawable(R.drawable.seek)
                .setLeftThumbHighlightDrawable(R.drawable.seek)
                .setDataType(Seekbar.DataType.INTEGER)
                .apply();

        // set listener
        rangeSeekbar.setOnSeekbarChangeListener(new OnSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue) {
                tvreal.setText(String.valueOf(minValue));
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.mine_set, R.id.serverAddress, R.id.checkUpdate, R.id.deleteCache})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mine_set:
//                LinearLayout.LayoutParams param1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                SnackbarUtils.addView(view, param1);
                Intent intent = new Intent(getActivity().getApplicationContext(), PersonActivity.class);
                startActivity(intent);
                break;
            case R.id.serverAddress:
                Intent intent1 = new Intent(getActivity(), CaptureActivity.class);
                startActivityForResult(intent1, REQUEST_CODE);
                break;
            case R.id.checkUpdate:
                initIdentify();
                showFingerIdentifyPopupWindow(view);
                break;
            case R.id.deleteCache:
                Date date=new Date();
                AppToastMgr.ToastShortCenter(getContext(), AppDateMgr.getWeekNumber(date)+"");
                ToolAnimation.addTouchLight(mineBg, true);
                SnackBarUtil.showSnackBar(view, "清除缓存成功");
                //SnackBarUtil.showSnackBar(v, "该功能暂不开放");
                CleanMessageUtil.clearAllCache(getActivity().getApplicationContext());
                try {
                    tvDeleteCache.setText(CleanMessageUtil.getTotalCacheSize(getActivity().getApplicationContext()));
                } catch (Exception e) {
                }
                break;
        }
    }



    private void showUpdateDialog() {
        final UpdateDialog updateDialog = new UpdateDialog(getActivity(), dialog, "优化处理逻辑,增加适配机型", new UpdateDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm) {
                ToastUtils.showToast_Now(getActivity(), "更新完成");
                dialog.dismiss();
            }

            @Override
            public void onHoldClick(Dialog dialog) {
                dialog.dismiss();
            }
        });
        updateDialog.show();
        setDialogWindowAttr(updateDialog, getActivity().getApplicationContext());
    }

    public void setDialogWindowAttr(Dialog dlg, Context ctx) {
//        Window window = dlg.getWindow();
//        WindowManager.LayoutParams lp = window.getAttributes();
        Window dialogWindow = dlg.getWindow();
        WindowManager m = getActivity().getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高度
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        p.gravity = Gravity.CENTER;
        p.height = (int) (d.getHeight() * 0.5); // 高度设置为屏幕的0.6，根据实际情况调整
        p.width = (int) (d.getWidth() * 0.65); // 宽度设置为屏幕的0.65，根据实际情况调整
        dlg.getWindow().setAttributes(p);
    }


    /**
     * EsayPermissions接管权限处理逻辑
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    @AfterPermissionGranted(REQUEST_CAMERA_PERM)
    public void cameraTask(int viewId) {
        if (EasyPermissions.hasPermissions(getActivity(), Manifest.permission.CAMERA)) {
            // Have permission, do the thing!
            //onClick(viewId);
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(this, "需要请求camera权限",
                    REQUEST_CAMERA_PERM, Manifest.permission.CAMERA);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        Toast.makeText(getActivity(), "执行onPermissionsGranted()...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Toast.makeText(getActivity(), "执行onPermissionsDenied()...", Toast.LENGTH_SHORT).show();
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this, "当前App需要申请camera权限,需要打开设置页面么?")
                    .setTitle("权限申请")
                    .setPositiveButton("确认")
                    .setNegativeButton("取消", null /* click listener */)
                    .setRequestCode(REQUEST_CAMERA_PERM)
                    .build()
                    .show();
        }
    }

}
