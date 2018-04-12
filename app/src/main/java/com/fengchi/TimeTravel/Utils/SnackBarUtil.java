package com.fengchi.TimeTravel.Utils;

import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by fc on 2017/7/18.
 */

public class SnackBarUtil {

    public static void showSnackBar(View v,String message){
        final Snackbar snackBar = Snackbar.make(v, message, Snackbar.LENGTH_LONG);
        View mView = snackBar.getView();
        TextView tvSnackbarText = (TextView) mView.findViewById(android.support.design.R.id.snackbar_text);
        tvSnackbarText.setTextSize(15);
        tvSnackbarText.setGravity(Gravity.CENTER_HORIZONTAL);
        snackBar.show();
    }


    public static void showSnackBarAction(View v,String message,String actionMessage,final View.OnClickListener listener){
        final Snackbar snackBar = Snackbar.make(v, message, Snackbar.LENGTH_LONG);
        View mView = snackBar.getView();
        TextView tvSnackbarText = (TextView) mView.findViewById(android.support.design.R.id.snackbar_text);
        tvSnackbarText.setTextSize(15);
        tvSnackbarText.setGravity(Gravity.CENTER_HORIZONTAL);
//        snackBar.setAction(actionMessage, new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                snackBar.dismiss();
//            }
//        });
        snackBar.setAction(actionMessage,listener);
        snackBar.show();
    }
}
