package sdk.hhyk.com.libhhyk_sdk.http;

import android.content.Context;

public interface ResponseParser<T> {

    public T getResponse(Context context, String response);

}
