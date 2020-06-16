package com.alanpaine.sphtech.help.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


import com.alanpaine.sphtech.R;
import com.alanpaine.sphtech.SphtechApp;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class FcfrtNetHelper {

	  public static boolean IsHaveInternet(final Context context) {
	        try {
	            ConnectivityManager manger =(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
	            if (manger==null) {
					return false;
				}

	            NetworkInfo info = manger.getActiveNetworkInfo();
	            if (info==null||!info.isAvailable()) {
					return false;
				}
	            return true;

	        } catch (Exception e) {
	            return false;
	        }

	    }

	public static boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}

		return false;
	}

	//处理网络异常
	public static <T> String handleNetworkException(T throwable) {
		int stringId = -1;
		if (throwable instanceof UnknownHostException) {
			if (!isNetworkConnected(SphtechApp.instance)) {
				stringId = R.string.network_error;
			} else {
				stringId = R.string.notify_no_network;
			}
		} else if (throwable instanceof SocketTimeoutException) {
			stringId = R.string.time_out_please_try_again_later;
		} else if (throwable instanceof ConnectException) {
			stringId = R.string.esky_service_exception;
		}
		return stringId == -1 ? null : SphtechApp.instance.getString(stringId);
	}


}
