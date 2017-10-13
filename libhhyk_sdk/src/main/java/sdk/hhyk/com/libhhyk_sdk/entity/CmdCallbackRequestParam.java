package sdk.hhyk.com.libhhyk_sdk.entity;

import android.content.Context;


import org.json.JSONObject;

import sdk.hhyk.com.libhhyk_sdk.Util.DESCoder;
import sdk.hhyk.com.libhhyk_sdk.Util.LogUtil;

public class CmdCallbackRequestParam extends DeviceInfo{
	
	private Context context;
	private int consumerId;
	private int cmdId;//通道ID
	private int sendStatus;//发送状态 0待发送 1已发送 2已到达

	public CmdCallbackRequestParam(Context context, int consumerId, int cmdId, int sendStatus) {
		super(context);
		this.context = context;
		this.consumerId = consumerId;
		this.cmdId = cmdId;
		this.sendStatus = sendStatus;
	}
	
	public String toJson(){
		JSONObject json = new JSONObject();
		try {
			json.put("channelId", channelId);
			json.put("mac", mac);
			json.put("imei", imei);
			json.put("imsi", imsi);
			json.put("model", model);
			json.put("sdkVer", sdkVer);
			json.put("consumerId", consumerId);
			json.put("cmdId", cmdId);
			json.put("sendStatus", sendStatus);
			json.put("nImsi", nImsi);
		} catch (Exception e) {
			LogUtil.e("CmdCallbackRequestParam.toJson", "" + e);
		}
		return DESCoder.encryptoPubAndPri(context, json.toString());
	}

}
