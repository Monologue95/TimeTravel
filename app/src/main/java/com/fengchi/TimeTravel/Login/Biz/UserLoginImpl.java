package com.fengchi.TimeTravel.Login.Biz;

import android.content.Context;

import com.fengchi.TimeTravel.Entity.UserInfo;
import com.fengchi.TimeTravel.Http.API;
import com.fengchi.TimeTravel.Http.HttpUtil;
import com.fengchi.TimeTravel.Http.HttpUtilImpl;
import com.fengchi.TimeTravel.Utils.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by fc on 2017/8/10.
 */

public class UserLoginImpl implements UserLoginBiz {
    private HttpUtil mHttpUtilImpl;
    private Context mContext ;
    boolean isLogin =false;


    public UserLoginImpl(Context context) {
        mContext =context;
        //mHttpUtilImpl= new HttpUtilImpl(mContext);
    }
    @Override
    public void login(String userName, String password, final OnLoginListener loginListener) {
        LogUtil.error("username",userName);
        LogUtil.error("password",password);
        LogUtil.error("Url",API.BaseUrl + "cmd=login&user=" + userName + "&pwd=" + password);
        mHttpUtilImpl =new HttpUtilImpl(mContext);
        mHttpUtilImpl.getDataByGET(API.BaseUrl + "cmd=login&user=" + userName + "&pwd=" + password, new HttpUtilImpl.CallBack() {
            @Override
            public void onSuccess(Object obj) {
                analysis(obj.toString(), loginListener);
            }

            @Override
            public void onFail() {
                loginListener.loginFailed();
            }

            @Override
            public void onError(Exception e) {
                loginListener.loginFailed();
            }
        });
    }

    private void analysis(String obj,OnLoginListener loginListener) {
        if (obj != null) {
            try {
                JSONObject object = new JSONObject(obj);
                if (object.has("status")) {
                    if (200 == object.getInt("status")) {
                        isLogin=true;
                        UserInfo.sid = object.getString("sid");
                        String accountName = object.getString("full_name");
                        LogUtil.error("full name",accountName);
                        loginListener.loginSuccess();
                        //mSP.edit().putBoolean(Config.ISLOGIN_SHARED_PREFS, isLogin).commit();
                    } else {
                        loginListener.loginFailederror();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
