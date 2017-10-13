package sdk.hhyk.com.libhhyk_sdk.Util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetUtil {
	
	/**
	 * 获取网络连接状态。
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isConnected(Context context) {
		if (null != context) {
			ConnectivityManager conMgr = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (null != conMgr) {
				NetworkInfo info = conMgr.getActiveNetworkInfo();
				if (null != info && info.isConnected()) {
					if (NetworkInfo.State.CONNECTED == info.getState()) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public static String doGet(String url, int timeout){
		StringBuffer stringBuffer = null;
//		HttpParams httpParameters1 = new BasicHttpParams();
//		// 超时设置
//		HttpConnectionParams.setConnectionTimeout(httpParameters1, timeout * 1000);
//		HttpConnectionParams.setSoTimeout(httpParameters1, timeout * 1000);
//		DefaultHttpClient client = new DefaultHttpClient(httpParameters1);
//		HttpGet httpGet = new HttpGet(url);
//		HttpResponse httpResponse = null;
//		try {
//			httpResponse = client.execute(httpGet);
//			int statusCode = httpResponse.getStatusLine().getStatusCode();
//			Log.d("NetUtil", "statuscode = " + statusCode);
//			if (statusCode == 200) {
//				HttpEntity httpEntity = httpResponse.getEntity();
//				int length = (int) httpEntity.getContentLength();
//				if (length < 0)
//					length = 10000;
//				stringBuffer = new StringBuffer(length);
//				InputStreamReader inputStreamReader = new InputStreamReader(
//						httpEntity.getContent(), HTTP.UTF_8);
//				char buffer[] = new char[length];
//				int count;
//				while ((count = inputStreamReader.read(buffer, 0, length - 1)) > 0) {
//					stringBuffer.append(buffer, 0, count);
//				}
//				inputStreamReader.close();
//			} else {
//				// 做其他状态的解析
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		return stringBuffer.toString();
	}

}
