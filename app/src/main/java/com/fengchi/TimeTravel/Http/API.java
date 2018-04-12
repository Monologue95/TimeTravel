package com.fengchi.TimeTravel.Http;

/**
 * Created by baron on 2017/2/21.
 */

public class API { //  10.1.2.207:10010  erp.joylive.com:10020
    public static String Ip="http://erp.joylive.com:10030";
    public static String BaseUrl=Ip+"/service.json?";
    public static String PushFileBaseUrl=Ip+"/service/upload/apps?name=";
    public static String GETFileBaseUrl=Ip+"/service/download/apps?name=";
    public static final String UpDateUrl = "http://iot.joylive.com:50021/JuLiRes/jl/version_install.xml";   //获取自动更新配置文件的路径
}
