package com.hhyk_sdk.Util;

import android.content.Context;

import com.hhyk_sdk.Constants;


public class ConfigUtil {
	
	public static int getAppID(Context context){
		LocalStorage storage = LocalStorage.getInstance(context);
		return storage.getInt(Constants.VSOYOU_APP_ID, 0);
	}
	
	public static String getAppKey(Context context){
		LocalStorage storage = LocalStorage.getInstance(context);
		return storage.getString(Constants.VSOYOU_APP_KEY, "");
	}
	
	public static int getChannelId(Context context){
		LocalStorage storage = LocalStorage.getInstance(context);
		return storage.getInt(Constants.VSOYOU_CHANNELID, 0);
	}
	

}
