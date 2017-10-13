package sdk.hhyk.com.libhhyk_sdk.Util;

import android.util.Log;

/**
 * 日志工具类
 * @author lmy 
 *  2013-02-18
 */
public class LogUtil {
	
	public static boolean LOG_SWITCH = true;
	
	public static void i(String tag, String msg){
		if(LOG_SWITCH){
			Log.i(tag, msg);
		}
	}
	
	public static void e(String tag, String msg){
		if(LOG_SWITCH){
			Log.e(tag, msg);
		}
	}

}
