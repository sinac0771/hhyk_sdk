package sdk.hhyk.com.libhhyk_sdk.entity;

import java.io.Serializable;

public class ProductEntity implements Serializable {

	private static final long serialVersionUID = -6234204655232320161L;
	
	public int id;
	
	public String appName;
	
	public int price;
	
	public int appId;
	
	public int status;
	
	public String titleName;
	
	public String addTime;

	@Override
	public String toString() {
		return "ProductInfoParser [id=" + id + ", appName=" + appName
				+ ", price=" + price + ", appId=" + appId + ", status="
				+ status + ", titleName=" + titleName + ", addTime=" + addTime
				+ "]";
	}

}
