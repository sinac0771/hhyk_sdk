package sdk.hhyk.com.libhhyk_sdk.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class CmdEntity implements Serializable {

	private static final long serialVersionUID = 283142161820282066L;
	
	public int id;
	public int cType; //0短信 1WAP链接
	public String sendPort;
	public String sendCmd;
	public int sendNum;
	public int price;
	public ArrayList<WapEntity> wapList = new ArrayList<WapEntity>();
	
	public int consumerId; //消费ID
	public int isPla; //0短代 1基地 2MM 3沃阅读
	public String cfParam; //参数组合，当沃阅读时方使用
	public String apiUrl2; //发送第1条短信后，直接访问获取指令(格式见下文)
	public int slTime; //秒数，访问apiUrl2之前间隔时间
	public int isBase64; //0不需解密，1需要Base64解密短信内容
	
	@Override
	public String toString() {
		return "CmdEntity [id=" + id + ", cType=" + cType + ", sendPort="
				+ sendPort + ", sendCmd=" + sendCmd + ", sendNum=" + sendNum
				+ ", price=" + price + ", wapList=" + wapList + "]";
	}

}
