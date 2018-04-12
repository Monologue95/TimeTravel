package com.fengchi.TimeTravel.Application;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.crashreport.CrashReport;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

/**
 * Created by fc on 2017/11/10.
 */

public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        CrashReport.initCrashReport(getApplicationContext(), "23f7cd96cc", true);
        ZXingLibrary.initDisplayOpinion(this);
        //CrashReport.testJavaCrash();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        // Normal app init code...
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
