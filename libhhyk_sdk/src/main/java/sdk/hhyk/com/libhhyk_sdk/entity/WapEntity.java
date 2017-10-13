package sdk.hhyk.com.libhhyk_sdk.entity;

import java.io.Serializable;

public class WapEntity implements Serializable {

	private static final long serialVersionUID = 4924247430069901137L;
	
	public int wType; //0浏览 1获取 2订购
	public String wapUrl;
	public String sKey;
	public String startKey;
	public String endKey;
	
	
	@Override
	public String toString() {
		return "WapEntity [wType=" + wType + ", wapUrl=" + wapUrl + ", sKey="
				+ sKey + ", startKey=" + startKey + ", endKey=" + endKey + "]";
	}
	
}
