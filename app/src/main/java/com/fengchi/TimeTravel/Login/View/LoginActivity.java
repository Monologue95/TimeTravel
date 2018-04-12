package com.fengchi.TimeTravel.Login.View;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fengchi.TimeTravel.Login.Presenter.UserLoginPresenter;
import com.fengchi.TimeTravel.MainActivity;
import com.fengchi.TimeTravel.R;
import com.fengchi.TimeTravel.Utils.ClearWriteEditText;
import com.fengchi.TimeTravel.Utils.Dialog;
import com.fengchi.TimeTravel.Utils.SnackBarUtil;
import com.rey.material.widget.CheckBox;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements ILoginView {
    @Bind(R.id.de_img_backgroud)
    ImageView deImgBackgroud;
    @Bind(R.id.de_frm_backgroud)
    FrameLayout deFrmBackgroud;
    @Bind(R.id.tv_set)
    TextView tvSet;
    @Bind(R.id.de_login_logo)
    ImageView deLoginLogo;
    @Bind(R.id.app_username_et)
    ClearWriteEditText appUsernameEt;
    @Bind(R.id.fr_username_delete)
    FrameLayout frUsernameDelete;
    @Bind(R.id.liner1)
    RelativeLayout liner1;
    @Bind(R.id.fr_pass_delete)
    FrameLayout frPassDelete;
    @Bind(R.id.app_password_et)
    ClearWriteEditText appPasswordEt;
    @Bind(R.id.liner2)
    RelativeLayout liner2;
    @Bind(R.id.cb_mima)
    CheckBox cbMima;
    @Bind(R.id.app_sign_in_bt)
    Button appSignInBt;
    private Context mcontext = this;
    Dialog mDialog;
    private final String TAG = "LoginActivity";
    UserLoginPresenter userLoginPresenter =new UserLoginPresenter(this,mcontext);
    View view1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initView();
        initConfig();

    }

    private void initConfig() {

    }

    private void initView() {
        mDialog = new Dialog(mcontext);
        mDialog.createProgressDialog("正在登录...");
    }

    @OnClick({R.id.tv_set, R.id.app_sign_in_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_set:
//                SnackBarUtil.showSnackBarAction(getWindow().getDecorView(), "1", "知道了", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        ToastUtils.showCustomLong(v);
//                    }
//                });
                userLoginPresenter.toServerSettingActivity();
                break;
            case R.id.app_sign_in_bt:
                userLoginPresenter.login();
                break;
        }
    }

    @Override
    public String getUserName() {
        return appUsernameEt.getText().toString().trim();
    }

    @Override
    public String getPassword() {
        return appPasswordEt.getText().toString().trim();
    }

    @Override
    public boolean rememberPassword() {
        return cbMima.isChecked();
    }


    @Override
    public void showLoading() {
        mDialog.showProgressDialog();
    }

    @Override
    public void hideLoading() {
       mDialog.hideProgressDialog();
    }

    @Override
    public void toMainActivity() {
        Intent intent =new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void toServerSettingActivity() {
        Intent intent =new Intent(LoginActivity.this,ServerSetActivity.class);
        startActivity(intent);
    }

    @Override
    public void showFailure() {
        SnackBarUtil.showSnackBar(getWindow().getDecorView(),"网络连接超时,请重新登录");
    }

    @Override
    public void showSuccess() {
        SnackBarUtil.showSnackBar(getWindow().getDecorView(),"登录成功");
    }

    @Override
    public void showVoid() {
        SnackBarUtil.showSnackBar(getWindow().getDecorView(),"账号或密码不能为空");
    }

    @Override
    public void showFailError() {
        SnackBarUtil.showSnackBar(getWindow().getDecorView(),"账号或密码错误");
    }
}
