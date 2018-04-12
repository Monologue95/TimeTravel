package com.fengchi.TimeTravel.Login.View;

/**
 * Created by fc on 2017/8/10.
 */

public interface ILoginView {
   String getUserName();

   String getPassword();

   boolean rememberPassword();

   void showLoading();

   void hideLoading();

   void toMainActivity();

   void toServerSettingActivity();

   void showFailure();

   void showSuccess();

   void showVoid();

   void showFailError();






}
