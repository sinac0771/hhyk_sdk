package sdk.hhyk.com.libhhyk_sdk.entity;

import android.content.Context;

import sdk.hhyk.com.libhhyk_sdk.Constants;
import sdk.hhyk.com.libhhyk_sdk.Util.ConfigUtil;


public class CommonRequestParam {
	
	public int sdkCode;
	public int appId;
	
	public CommonRequestParam(Context context) {
		sdkCode = Constants.SDK_CODE;
		appId = ConfigUtil.getAppID(context);
	}

}
