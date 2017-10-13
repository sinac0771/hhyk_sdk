package sdk.hhyk.com.libhhyk_sdk.entity;

import java.io.Serializable;

public class InitEntity implements Serializable {

	private static final long serialVersionUID = 3515979509926402696L;
	
	public boolean success;
	
	public String msg;
	
	public UrlEntity urlEntity;

	@Override
	public String toString() {
		return "InitEntity [success=" + success + ", msg=" + msg
				+ ", urlEntity=" + urlEntity + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}


}
