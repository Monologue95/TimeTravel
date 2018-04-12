package com.fengchi.TimeTravel.Login.Biz;

/**
 * Created by fc on 2017/8/10.
 */

public interface UserLoginBiz {
    public void login(String userName , String password,OnLoginListener loginListener);
}
