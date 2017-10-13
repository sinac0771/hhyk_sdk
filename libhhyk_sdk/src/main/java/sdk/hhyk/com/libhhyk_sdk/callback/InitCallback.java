package sdk.hhyk.com.libhhyk_sdk.callback;


import sdk.hhyk.com.libhhyk_sdk.entity.UrlEntity;

public interface InitCallback {
	
	public void onInitSuccess(int type, UrlEntity urlEntity);
	
	public void onInitFailure(int type, int errorCode, String errorMsg);

}
