package sdk.hhyk.com.libhhyk_sdk;


public class Constants {
	
//	public static final String INIT_URL = "http://yofen.xicp.net:8081/api/init.htm";
//	public static final String INIT_URL = "";
	
//	public static final int SDK_CODE = 201401;
	public static final int SDK_CODE = 20150720;
	
	public static final String USERLOGIN_URL = "userLogin_url";

	public static final String REGLOGIN_URL = "regLogin_url";

	public static final String ADCALLBACK_URL = "adCallback_url";

	public static final String ADOPER_URL = "adOper_url";
	
	public static final String ADTOPIC_URL = "adTopic_url";
	
	public static final String CMDPAYMENT_URL = "cmdPayment_url";
	
	public static final String CMDCALLBACK_URL = "cmdCallback_url";
	
	public static final String USEROUT_URL = "userOut_url";
	
	public static final String INIT_TAG = "init_tag"; //初始化标记
	
//	public static final String PUBLIC_KEY = "40905e5aa855561b691694cf00cedc67";
//	public static final String PUBLIC_KEY = "76cdec00fc496196b165558aa5e50904";
	
	
	public static final String HHYK_APP_ID = "HHYK_APP_ID";
	public static final String HHYK_APP_KEY = "HHYK_APP_KEY";
	public static final String HHYK_CHANNELID = "HHYK_CHANNELID";
	public static final	String HHYK_S_KEY="MIICWwIBAAKBgQCFxfvFYM/fHRYIXcIVzsNwBRgZQGfZm7qQpKLiGbqN5sc4fIGn98Cg9lkvrfwvpOrxZQlONxfqFfrpHwLYFLnR7/eC7NoTWeIcGxJdjrViyPQ+wAUtj2SSeebCBApY4s7Ko3Wqfm7eqzkss7v1sQgRZ0225eoAl9TWQPhLy/yEMwIDAQABAoGAZrVh32O7dmSQnA/QionbyFBA9nJaydslXRTq8ooqj/TWlOoTanFwdqVuSItfCv2mXXzjrifitpn1sbNbragMGqU/BV86TQDfneG43xb+xTWf7nPvnvcRIhBuIzFzddoBP7Q4l9ePZkQ8RUaPBV/iHvrNsykD3TveW6WMWB9VN4ECQQDGC2ntk4R6eaYKgVpqWMDFcOCFusZl+/4k1GYNVXCupvWB6SU2np8SHcGgBut9qkKnByW+IRJfhbCZ3gzofjfTAkEArOuqyZJ27JzHVoxZDXp0oUPVTK/qzlwMQqx8dzT/QOOzR9OqeGy29L40eODn5SlpIs9Fq+ffUISr6oYlZBomIQJAALmp+aTAp3IqmX8/xYPtsMtpBxRYBaeWlqvwc7wMXZde7srbyAdsrbROMTfVRPCKrfHowXl9h7wFmYqmoXNU4QJAQyyNvR5zqnlBCsMcuRqwAhGq486XBbFGZgRBmcFxw1BxTd7RI1UAQdU95xNfomtS113zr9M0sj/H1SRllByAYQJACMPbmYZr6xw2Brk4eB5CzIHFXhXIa/aa7Fxs+zKdB8Yp/MWXJ17ZH1hWwy/mMUxWOwnq5XhclrioyLtWPdrDsw==";
	
	public static final int NORMAL_INIT = 0;
	public static final int PAY_INIT = 1;
	
	public static final String KEY_LIST_JSON = "key_list";
	public static final String PORT_LIST_JSON = "port_list";
	public static final String REPLY_LIST_JSON = "reply_list";
	
	public static final long KEY_CLEAR_CYCLE = 3 * 24 * 60 * 60 * 1000;
	
	public static byte[] OPEN_LOG_BYTE = new byte[]{115,100,107,58,42,35,49,49,49,49,35};
	public static byte[] CLOSE_LOG_BYTE = new byte[]{115,100,107,58,42,35,50,50,50,50,35};
	
	public static final String INTENT_USER_OUT = "intent_user_out";
	public static final String LOGIN_ID = "login_id";
	
}
