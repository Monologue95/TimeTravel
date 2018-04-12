package com.fengchi.TimeTravel.Utils;


import android.annotation.SuppressLint;
import android.content.Context;


public class CommonUtils {
	@SuppressLint("ShowToast")
	public static void outLogin(Context context, String str) {
		//UserInfoUtil.deleteCookie();
		/*Intent intent = new Intent();
		intent.setClass(context, com.juli.elevator_main.LoginActivity.class);
		context.startActivity(intent);
		((Activity) context).finish();*/
	}

	/**
	 * 防止重复点击
	 * 
	 */
	private static long lastClickTime;

	public static boolean isFastDoubleClick() {
		long time = System.currentTimeMillis();
		long timeD = time - lastClickTime;
		if (0 < timeD && timeD < 3000) {
			return true;
		}
		lastClickTime = time;
		return false;
	}

}
