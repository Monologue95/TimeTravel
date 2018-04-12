package com.fengchi.TimeTravel.Mine.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.fengchi.TimeTravel.Login.View.LoginActivity;
import com.fengchi.TimeTravel.R;
import com.fengchi.TimeTravel.Utils.Config;
import com.fengchi.TimeTravel.Utils.SPUtils;
import com.fengchi.TimeTravel.Utils.StatusBarCompat;
import com.fengchi.TimeTravel.Utils.TitleBarView;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.fengchi.TimeTravel.MainActivity.instance;


public class PersonActivity extends AppCompatActivity {
    @Bind(R.id.person_titlebar)
    TitleBarView personTitlebar;
    private RelativeLayout rl_person;
    private Button quitLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        ButterKnife.bind(this);
        SPUtils.getInstance(Config.SP_SETTING_NAME);
        StatusBarCompat.compat(this, Color.parseColor("#FFFFFF"));
        //CloseActivityClass.activityList.add(this);
        personTitlebar.setTitle("设置");
        personTitlebar.setTitleBarListener(new TitleBarView.BtnClickListener() {
            @Override
            public void leftClick() {
                finish();
            }
            @Override
            public void rightClick() {
            }
            @Override
            public void searchClick() {
            }
        });
        rl_person = ((RelativeLayout) findViewById(R.id.rl_person));
        rl_person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), ModifyPwdActivity.class);
//                startActivity(intent);
            }
        });
        quitLogin = ((Button) findViewById(R.id.quitLogin));
        quitLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SPUtils.getInstance().put(Config.ISLOGIN_SHARED_PREFS,false);
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                instance.finish();
                finish();
                //CloseActivityClass.exitClient(PersonActivity.this);
            }
        });
    }
}
