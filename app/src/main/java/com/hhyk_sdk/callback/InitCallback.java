package com.hhyk_sdk.callback;


import com.hhyk_sdk.entity.UrlEntity;

public interface InitCallback {
	
	public void onInitSuccess(int type, UrlEntity urlEntity);
	
	public void onInitFailure(int type, int errorCode, String errorMsg);

}
