package com.fengchi.TimeTravel.Login.Presenter;

import android.content.Context;
import android.os.Handler;

import com.fengchi.TimeTravel.Login.Biz.OnLoginListener;
import com.fengchi.TimeTravel.Login.Biz.UserLoginBiz;
import com.fengchi.TimeTravel.Login.Biz.UserLoginImpl;
import com.fengchi.TimeTravel.Login.View.ILoginView;
import com.fengchi.TimeTravel.Utils.Config;
import com.fengchi.TimeTravel.Utils.LogUtil;
import com.fengchi.TimeTravel.Utils.SPUtils;

/**
 * Created by fc on 2017/8/10.
 */

public class UserLoginPresenter {
    private UserLoginBiz userLoginBiz;
    private ILoginView loginView;
    private Handler mHandler = new Handler();
    //private SharedPreferences mSP;
    private Context mContext;
    boolean isLogin =false;

    public UserLoginPresenter(ILoginView loginView, Context mcontext) {
        mContext =mcontext;
        this.userLoginBiz = new UserLoginImpl(mcontext);
        this.loginView = loginView;
    }

//    public UserLoginPresenter(Context context) {
//        this.userLoginBiz = new UserLoginImpl(context);
//    }

    public void toServerSettingActivity() {
        loginView.toServerSettingActivity();
    }

    public void login() {
        SPUtils.getInstance(Config.SP_SETTING_NAME);
        //mSP = mContext.getSharedPreferences(Config.SP_SETTING_NAME, Context.MODE_WORLD_READABLE);
        loginView.showLoading();
        LogUtil.error("1","1");
        if(!loginView.getUserName().equals("")&& !loginView.getPassword().equals("")) {
            userLoginBiz.login(loginView.getUserName(), loginView.getPassword(), new OnLoginListener() {
                @Override
                public void loginSuccess() {
                    isLogin =true;
                    SPUtils.getInstance().put(Config.ISLOGIN_SHARED_PREFS, isLogin);
                    SPUtils.getInstance().put(Config.KEY_KEEP_NUMBER, loginView.getUserName());
                    SPUtils.getInstance().put(Config.KEY_KEEP_PASSWORD, loginView.getPassword());
//                    mSP.edit().putBoolean(Config.ISLOGIN_SHARED_PREFS, isLogin).commit();
//                    mSP.edit().putString(Config.KEY_KEEP_NUMBER, loginView.getUserName()).commit();
//                    mSP.edit().putString(Config.KEY_KEEP_PASSWORD, loginView.getPassword()).commit();
                    //需要在UI线程执行
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            loginView.hideLoading();
                            loginView.showSuccess();
                            loginView.toMainActivity();
                        }
                    });
                }
                @Override
                public void loginFailed() {
                    loginView.hideLoading();
                    loginView.showFailure();
                }

                @Override
                public void loginFailederror() {
                    loginView.hideLoading();
                    loginView.showFailError();
                }
            });
        }else {
            loginView.hideLoading();
            loginView.showVoid();
        }
    }
}
