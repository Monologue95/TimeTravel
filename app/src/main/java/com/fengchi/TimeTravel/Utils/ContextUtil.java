package com.fengchi.TimeTravel.Utils;

import android.content.Context;

public class ContextUtil extends MApplication {
    private static Context context;

    public static Context getcontext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        //LeakCanary.install(this);
        //腾讯Bugly接入
        // CrashReport.initCrashReport(getApplicationContext(), "4f577c0c27", true);
        //  Bugtags.start("b74999570d1b7d8e9da1c980ed3eac11", this, Bugtags.BTGInvocationEventBubble);
    }
}
