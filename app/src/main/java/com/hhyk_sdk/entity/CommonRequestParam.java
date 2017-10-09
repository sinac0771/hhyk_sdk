package com.hhyk_sdk.entity;

import android.content.Context;

import com.hhyk_sdk.Constants;
import com.hhyk_sdk.Util.ConfigUtil;


public class CommonRequestParam {
	
	public int sdkCode;
	public int appId;
	
	public CommonRequestParam(Context context) {
		sdkCode = Constants.SDK_CODE;
		appId = ConfigUtil.getAppID(context);
	}

}
