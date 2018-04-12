package com.fengchi.TimeTravel.Splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.fengchi.TimeTravel.Login.View.LoginActivity;
import com.fengchi.TimeTravel.MainActivity;
import com.fengchi.TimeTravel.R;
import com.fengchi.TimeTravel.Utils.Config;
import com.fengchi.TimeTravel.Utils.InitUtils;
import com.fengchi.TimeTravel.Utils.SPUtils;

public class SplashActivity extends AppCompatActivity {
    boolean isLogin =false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        InitUtils.init(getApplication());
        SPUtils.getInstance(Config.SP_SETTING_NAME);
        //mSP = this.getSharedPreferences(Config.SP_SETTING_NAME, Context.MODE_WORLD_READABLE);
        //isLogin =mSP.getBoolean(Config.ISLOGIN_SHARED_PREFS,isLogin);
        isLogin =SPUtils.getInstance().getBoolean(Config.ISLOGIN_SHARED_PREFS,isLogin);
        autoLogin();
    }

    private void autoLogin() {
        if (isLogin) {
            Handler x = new Handler();
            x.postDelayed(new MainHandler(), 2000);
        }else {
            Handler x = new Handler();
            x.postDelayed(new LoginHandler(), 2000);
        }
    }

    class MainHandler implements Runnable {
        public void run() {
            startActivity(new Intent(getApplication(), MainActivity.class));
            SplashActivity.this.finish();
        }
    }

    class LoginHandler implements Runnable {
        public void run() {
            startActivity(new Intent(getApplication(), LoginActivity.class));
            SplashActivity.this.finish();
        }
    }
}
