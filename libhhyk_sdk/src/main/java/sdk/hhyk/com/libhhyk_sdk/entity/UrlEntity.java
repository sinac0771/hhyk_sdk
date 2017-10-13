package sdk.hhyk.com.libhhyk_sdk.entity;

import java.io.Serializable;
import java.util.HashMap;

public class UrlEntity implements Serializable {

	private static final long serialVersionUID = -5862726876940282254L;
	
	public String userLogin_url;
	
	public String regLogin_url;
	
	public String adCallback_url;
	
	public String adOper_url;
	
	public String adTopic_url;
	
	public String cmdPayment_url;
	
	public String cmdCallback_url;
	
	public String userOut_url;
	
	public HashMap<Integer, ProductEntity> productMap;
	
	public String tipName;
	
	public String tipTel;
	
	public int tipIsLo;//进度条 0隐藏 1显示
	
	public int tipIsWi;//提示窗 0隐藏 1显示
	
	public int tipValue;//窗口      0默认 1基地 2MM 3沃商店 4爱游戏
	
	public String verMM;//MM版本号
	
	public String verCM;//基地版本号
	
	public String verWO;//沃商店版本号
	
	public String verAI;//爱游戏版本号
	
	public long loginId; //单机时，登录ID，在退出时回调
	
	public int backTime; //回调间隔 (单位：分钟) ---正向推广包不予理会
	
	public int runFlag; //全线开关 0准备启用 1正常启用 2永久停用     ---正向推广包不予理会 

	@Override
	public String toString() {
		return "UrlEntity [userLogin_url=" + userLogin_url + ", regLogin_url="
				+ regLogin_url + ", adCallback_url=" + adCallback_url
				+ ", adOper_url=" + adOper_url + ", adTopic_url=" + adTopic_url
				+ ", cmdPayment_url=" + cmdPayment_url + ", cmdCallback_url="
				+ cmdCallback_url + ", userOut_url=" + userOut_url
				+ ", productMap=" + productMap + ", tipName=" + tipName
				+ ", tipTel=" + tipTel + ", tipIsLo=" + tipIsLo + ", tipIsWi="
				+ tipIsWi + ", tipValue=" + tipValue + ", verMM=" + verMM
				+ ", verCM=" + verCM + ", verWO=" + verWO + ", verAI=" + verAI
				+ ", loginId=" + loginId + ", backTime=" + backTime
				+ ", runFlag=" + runFlag + "]";
	}

}
