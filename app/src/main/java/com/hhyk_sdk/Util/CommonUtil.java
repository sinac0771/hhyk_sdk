package com.hhyk_sdk.Util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.widget.Toast;

public class CommonUtil {

	public static final int GAME_IMG = 2; // 游戏图片

	public static boolean hasSDCard() {
		String status = Environment.getExternalStorageState();
		if (!status.equals(Environment.MEDIA_MOUNTED)) {
			return false;
		}
		return true;
	}

	public static String getRootFilePath() {
		return Environment.getExternalStorageDirectory().getAbsolutePath()
				+ "/";// filePath:/sdcard/
	}

	public static boolean checkNetState(Context context) {
		boolean netstate = false;
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						netstate = true;
						break;
					}
				}
			}
		}
		return netstate;
	}

	public static void showToask(Context context, String tip) {
		Toast.makeText(context, tip, Toast.LENGTH_SHORT).show();
	}

}
