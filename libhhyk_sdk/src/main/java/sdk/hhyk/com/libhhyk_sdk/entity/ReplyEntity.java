package sdk.hhyk.com.libhhyk_sdk.entity;

import org.json.JSONObject;

import java.io.Serializable;

public class ReplyEntity implements Serializable {

	private static final long serialVersionUID = -2127468856972774481L;
	
	public int rType;
	public String sKey = "";
	public String startKey = "";
	public String endKey = "";
	
	public long time;

	@Override
	public String toString() {
		return "ReplyEntity [rType=" + rType + ", sKey=" + sKey + ", startKey="
				+ startKey + ", endKey=" + endKey + ", time=" + time + "]";
	}
	
	public String getJsonStr(){
		String str = "";
		try {
			JSONObject json = new JSONObject();
			json.put("rType", rType);
			json.put("sKey", sKey);
			json.put("startKey", startKey);
			json.put("endKey", endKey);
			str = json.toString();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return str;
	}
	
}
