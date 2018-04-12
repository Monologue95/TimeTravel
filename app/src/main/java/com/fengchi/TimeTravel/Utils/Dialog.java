package com.fengchi.TimeTravel.Utils;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by stormbaron on 17-5-24.
 */

public class Dialog {
    public Context mContext;
    public ProgressDialog mProgressDialog;

    public Dialog(Context context) {
        mContext = context;
    }

    public void createProgressDialog(String message) {
        // 创建ProgressDialog对象
        mProgressDialog = new ProgressDialog(mContext);
        // 设置进度条风格，风格为圆形，旋转的
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // 设置ProgressDialog 提示信息
        mProgressDialog.setMessage(message);
        // 设置ProgressDialog 的进度条是否不明确
        mProgressDialog.setIndeterminate(false);
        // 设置ProgressDialog 是否可以按退回按键取消
        mProgressDialog.setCancelable(true);
    }

    public void showProgressDialog() {
        if(mProgressDialog!=null){
            mProgressDialog.show();
        }

    }

    public void hideProgressDialog() {
        if(mProgressDialog!=null){
            mProgressDialog.hide();
        }

    }

    public void deleteProgressDialog() {
        mContext=null;
    }
}
