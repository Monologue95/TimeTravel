package com.fengchi.TimeTravel.Http;

import android.content.Context;
import android.net.Uri;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fengchi.TimeTravel.Utils.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by baron on 2017/2/17.
 */

public class HttpUtilImpl implements HttpUtil {
    Context mContext;
    RequestQueue mQueue;
    CallBack mCallBack;
    String TAG="HttpUtilImpl";
    public HttpUtilImpl(Context context) {
        mContext = context;
        mQueue = Volley.newRequestQueue(mContext);
    }

    @Override
    public void getDataByGET(String url, CallBack callBack) {
        mCallBack = callBack;
        //创建StringRequest
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            public void onResponse(String response) {
                if (mCallBack != null) {
                    mCallBack.onSuccess(response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (mCallBack != null) {
                    mCallBack.onError(error);
                }
            }
        }){
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String parsed;
                try {
                    parsed = new String(response.data, "UTF-8");
                } catch (UnsupportedEncodingException var4) {
                    parsed = new String(response.data);
                }

                return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
            }
        };
        LogUtil.error("TAG",url);
        mQueue.add(request);
    }

    private String appendParameter(String url, Map<String, String> params) {
        Uri uri = Uri.parse(url);
        Uri.Builder builder = uri.buildUpon();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            builder.appendQueryParameter(entry.getKey(), entry.getValue());
        }
        return builder.build().getQuery();
    }

    @Override
    public void getDataByPost_Key(String url, JSONObject Object, CallBack callBack) {
        LogUtil.error("HttpUtilImpl", Object.toString());
        Map<String, String> params = new HashMap<String, String>();
        params.put("data", Object.toString());
        final String mRequestBody = appendParameter(url, params);
        mCallBack = callBack;
        //创建StringRequest
        JsonRequest<JSONObject> request = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject response) {
                if (response.has("status")) {
                    try {
                        if ("200".equals(response.getString("status"))) {
                            if (mCallBack != null) {
                                mCallBack.onSuccess(response);
                            }
                        }
                        if ("400".equals(response.getString("status"))) {
                            if (mCallBack != null) {
                                mCallBack.onFail();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                LogUtil.error("TAG", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (mCallBack != null) {
                    mCallBack.onError(error);
                }
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=" + getParamsEncoding();
            }

            @Override
            public byte[] getBody() {
                try {
                    return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                            mRequestBody, "utf-8");
                    return null;
                }
            }
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                try {
                    String je = new String(response.data, "UTF-8");
                    return Response.success(new JSONObject(je), HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException var3) {
                    return Response.error(new ParseError(var3));
                } catch (JSONException var4) {
                    return Response.error(new ParseError(var4));
                }
            }
        };
        //LogUtil.error("TAG", "request"+ request.getBody().toString());
        mQueue.add(request);
    }
    @Override
    public void getDataByPost_Body(String url, JSONObject Object, CallBack callBack) {
        LogUtil.error("HttpUtilImpl", Object.toString());
        mCallBack = callBack;
        //创建StringRequest
        JsonRequest<JSONObject> request = new JsonObjectRequest(Request.Method.POST, url, Object, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject response) {
                LogUtil.error(TAG,"respppp"+response.toString());
                if (response.has("status")) {
                    try {
                        if ("200".equals(response.getString("status"))) {
                            if (mCallBack != null) {
                                mCallBack.onSuccess(response);
                            }
                        }
                        if ("400".equals(response.getString("status"))) {
                            if (mCallBack != null) {
                                mCallBack.onFail();
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                LogUtil.error("TAG", response.toString());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (mCallBack != null) {
                    mCallBack.onError(error);
                }
            }
        }){
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                try {
                    String je = new String(response.data, "UTF-8");
                    return Response.success(new JSONObject(je), HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException var3) {
                    return Response.error(new ParseError(var3));
                } catch (JSONException var4) {
                    return Response.error(new ParseError(var4));
                }
            }
        };
        //LogUtil.error("TAG", "request"+ request.getBody().toString());
        mQueue.add(request);
    }
   /* @Override
    public void getDataByPost_StringQuest(String url , final JSONObject Object, CallBack callBack) {
        LogUtil.error("HttpUtilImpl", Object.toString());
        mCallBack=callBack;
        //创建StringRequest
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            public void onResponse(String response) {
                if(mCallBack!=null){
                    mCallBack.onSuccess(response);
                }
                LogUtil.error("HttpUtilImpl","response:"+ response.toString()+response.length()+response.substring(20));
            }
        } ,new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                LogUtil.error("HttpUtilImpl","VolleyError:"+error.getMessage());
                if(mCallBack!=null){
                    mCallBack.onError(error);
                }
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap<String, String>();
                //使用map添加参数
                hashMap.put("data", Object.toString());
                //返回map
                return hashMap;
            }

        };
        try {
            LogUtil.error("HttpUtilImpl","请求参数:"+request.getUrl()+";"+request.getBody().toString()+";"+request.toString());
        } catch (AuthFailureError authFailureError) {
            authFailureError.printStackTrace();
        }
        mQueue.add(request);
    }*/

    public interface CallBack {
        void onSuccess(Object obj);

        void onFail();

        void onError(Exception e);
    }
}
