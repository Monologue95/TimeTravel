package com.fengchi.TimeTravel.Utils;

import android.app.Application;

/**
 * Created by stormbaron on 17-4-17.
 */

public class MApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        /*new ALog.Builder(this)
                .setLogSwitch(true)// 设置log总开关，默认开
                .setGlobalTag("Log")// 设置log全局标签，默认为空
                // 当全局标签不为空时，我们输出的log全部为该tag，
                // 为空时，如果传入的tag为空那就显示类名，否则显示tag
                .setLogHeadSwitch(true)// 设置log头部是否显示，默认显示
                .setLog2FileSwitch(true)// 打印log时是否存到文件的开关，默认关
                .setBorderSwitch(true)// 输出日志是否带边框开关，默认开
                .setLogFilter(ALog.V);// log过滤器，和logcat过滤器同理，默认Verbose*/
    }
}
