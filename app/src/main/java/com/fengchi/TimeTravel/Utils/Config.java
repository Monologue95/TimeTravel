package com.fengchi.TimeTravel.Utils;

/**
 * Created by baron on 2017/2/17.
 */

public class Config {
    public static String UserNick;

    /**
     * 配置类存储名
     */

    public final static String SP_SETTING_NAME="sp_setting";
    public final static String SP_SETTING_IP="sp_setting_ip";
    public final static String SP_SETTING_IPADDRESS="SP_SETTING_IPADDRESS";


    public final static String KEY_IS_KEEP_INFO = "ISCHECK";
    public final static String KEY_KEEP_NUMBER = "USER_NUMBER_FROM";
    public final static String KEY_KEEP_PASSWORD = "USER_PASSWORD_FROM";
    //判断是否登录
    public static final String ISLOGIN_SHARED_PREFS = "ISLOGIN_SHARED_PREFS";
    //判断是否自动登录的开关
    public static final String ISLOGINSWITCH_SHARED_PREFS = "ISLOGINSWITCH_SHARED_PREFS";
    //判断是否刷新，会导致oom
    public static final String ISREFRESH_SHARED_PREFS ="ISREFRESH_SHARED_PREFS";
    //判断账号登陆人
    public static final String LOGINACCOUNT ="LOGINACCOUNT";
}
