package sdk.hhyk.com.libhhyk_sdk.callback;

public interface HttpCallback<T> {

    public void onSuccess(T object);

    public void onFailure(int errorCode, String errorMessage);

}
