package sdk.hhyk.com.libhhyk_sdk.Util;

import android.content.Context;
import android.content.SharedPreferences;

public class LocalStorage {

    public static final String STORAGE_NAME = "VsoYouSdk";

    private SharedPreferences sharedPreferences;

    private LocalStorage(Context context) {
        sharedPreferences = context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE);
    }

    public static LocalStorage getInstance(Context context) {
        return new LocalStorage(context);
    }

    public boolean hasKey(String key) {
        return !StringUtil.isEmpty(key)
                && sharedPreferences.contains(key);
    }

    public void putString(String key, String value) {

        if (StringUtil.isEmpty(key)
                || StringUtil.isEmpty(value))
            return;
        sharedPreferences.edit().putString(key, value).commit();

    }
    
    public String getString(String key, String... defaultValues) {
        if (StringUtil.isEmpty(key)) return null;
        String defaultValue =
                defaultValues.length >= 1 ?
                        defaultValues[0] : "";
        return sharedPreferences.getString(key, defaultValue);

    }
    
    public void putInt(String key, int value) {
        sharedPreferences.edit().putInt(key, value).commit();
    }
    
    public int getInt(String key, int... defaultValues) {
        if (StringUtil.isEmpty(key)) return 0;
        int defaultValue =
                defaultValues.length >= 1 ?
                        defaultValues[0] : 0;
        return sharedPreferences.getInt(key, defaultValue);

    }
    
    public void putLong(String key, long value) {
        sharedPreferences.edit().putLong(key, value).commit();
    }
    
    public long getLong(String key, long... defaultValues) {
        if (StringUtil.isEmpty(key)) return 0;
        long defaultValue =
                defaultValues.length >= 1 ?
                        defaultValues[0] : 0;
        return sharedPreferences.getLong(key, defaultValue);

    }
    
    

}
