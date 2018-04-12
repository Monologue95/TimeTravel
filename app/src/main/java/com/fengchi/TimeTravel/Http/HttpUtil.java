package com.fengchi.TimeTravel.Http;

import org.json.JSONObject;

/**
 * Created by baron on 2017/2/17.
 */

public interface HttpUtil {
      public void getDataByGET(String url, HttpUtilImpl.CallBack callBack);
      public void getDataByPost_Key(String url, JSONObject object, HttpUtilImpl.CallBack callBack);
      public void getDataByPost_Body(String url, JSONObject object, HttpUtilImpl.CallBack callBack);

}
