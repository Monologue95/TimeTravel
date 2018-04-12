package com.fengchi.TimeTravel.Utils;

import android.app.Application;
import android.support.annotation.NonNull;

/**

 *     desc  : Utils初始化相关
 * </pre>
 */
public final class InitUtils {

    private static Application sApplication;

    private InitUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 初始化工具类
     *
     * @param app 应用
     */
    public static void init(@NonNull final Application app) {
        InitUtils.sApplication = app;
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Application getApp() {
        if (sApplication != null) return sApplication;
        throw new NullPointerException("u should init first");
    }
}