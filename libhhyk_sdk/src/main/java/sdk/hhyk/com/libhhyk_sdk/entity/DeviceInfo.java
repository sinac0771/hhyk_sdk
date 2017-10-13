package sdk.hhyk.com.libhhyk_sdk.entity;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;


import org.json.JSONException;
import org.json.JSONObject;

import sdk.hhyk.com.libhhyk_sdk.Util.ConfigUtil;
import sdk.hhyk.com.libhhyk_sdk.Util.DESCoder;
import sdk.hhyk.com.libhhyk_sdk.Util.LogUtil;
import sdk.hhyk.com.libhhyk_sdk.Util.NImsiUtil;
import sdk.hhyk.com.libhhyk_sdk.Util.StringUtil;

public class DeviceInfo extends CommonRequestParam {

	private Context context;

	public int channelId;
	public String mac;
	public String imei;
	public String imsi;
	public String model;
	public String sdkVer;
	public String nImsi;
	public String mobile;

	public DeviceInfo(Context context) {
		super(context);
		this.context = context;
		init();
	}

	private void init() {
		channelId = ConfigUtil.getChannelId(context);

		WifiManager wifiMgr = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInfo = (wifiMgr == null ? null : wifiMgr
				.getConnectionInfo());
		if (wifiInfo != null) {
			mac = wifiInfo.getMacAddress();
		}

		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		if (telephonyManager != null) {
			try {
				imei = telephonyManager.getDeviceId();
				imsi = telephonyManager.getSubscriberId();
				mobile = telephonyManager.getLine1Number();
				if(mobile != null && mobile.length() != 11){
					mobile = "";
				}
			} catch (Exception e) {
				LogUtil.i("DeviceInfo", "init-->" + e);
			}
			
		}
		if(null == imsi || "".equals(imsi) || "null".equals(imsi)){
			//set mImsi value
			nImsi = NImsiUtil.getNImsiValue(context);
		}
		model = Build.MODEL;
		sdkVer = Build.VERSION.RELEASE;
	}

	public String toJSON() {

		JSONObject jsonObject = new JSONObject();
		try {
			if(StringUtil.isEmpty(mac)){
				mac = "";
			}
			if(StringUtil.isEmpty(imei)){
				imei = "";
			}
			if(StringUtil.isEmpty(imsi)){
				imsi = "";
			}
			if(StringUtil.isEmpty(model)){
				model = "";
			}
			if(StringUtil.isEmpty(sdkVer)){
				sdkVer = "";
			}
			if(StringUtil.isEmpty(nImsi)){
				nImsi = "";
			}
			jsonObject.put("channelId", channelId);
			jsonObject.put("mac", mac);
			jsonObject.put("imei", imei);
			jsonObject.put("imsi", imsi);
			jsonObject.put("model", model);
			jsonObject.put("sdkVer", sdkVer);
			jsonObject.put("nImsi", nImsi);
//			jsonObject.put("mobile", mobile);
			LogUtil.i("DeviceInfo", "param-->" + jsonObject.toString());
		} catch (JSONException ex) {
			// Ignore
		}
		return DESCoder.encryptoPubAndPri(context, jsonObject.toString());
	}

}
