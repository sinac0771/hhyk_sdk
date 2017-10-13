package sdk.hhyk.com.libhhyk_sdk.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import java.net.URLEncoder;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;

import sdk.hhyk.com.libhhyk_sdk.Constants;
import sdk.hhyk.com.libhhyk_sdk.entity.OrderModel;


/**
 * 对话框显示
 * 
 * @author smallwei
 * 
 */
public class DtDialog extends Dialog {
	private Context mContext;
	private int width;
	private int height;
	private OrderModel orderModel;

	public DtDialog(Context context , OrderModel orderModel) {
		super(context);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.orderModel=orderModel;
		WindowManager windowManager=(WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics metrics=new DisplayMetrics();
	   windowManager.getDefaultDisplay().getMetrics(metrics);
		width=metrics.widthPixels;
		height=metrics.heightPixels;
		init(context,  width, height);
	}

	public DtDialog(Context context, int theme,OrderModel orderModel) {
		super(context, theme);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.orderModel=orderModel;
		WindowManager windowManager=(WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics metrics=new DisplayMetrics();
		windowManager.getDefaultDisplay().getMetrics(metrics);
		width=metrics.widthPixels;
		height=metrics.heightPixels;
		init(context,  width, height);
	}

	private void init(Context context, int width, int height) {
		setCancelable(true);
		setCanceledOnTouchOutside(false);
		WindowManager.LayoutParams layoutParams=new WindowManager.LayoutParams();
		layoutParams.width=width;
		layoutParams.height=height*2/3;
		this.
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		getWindow().getAttributes().windowAnimations = 0;
		
		if (!(context instanceof Activity)) {
			getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		}
		
		if (android.os.Build.VERSION.SDK_INT >= 14) {// 4.0 需打开硬件加速
			getWindow().setFlags(0x1000000, 0x1000000);
		}
		this.mContext = context;
		setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {

			}
		});
		final DWebView dWebView=new DWebView(context,width,height );
		try {
			String sign=privateKeySign(orderModel.toString());

			String param="transdata="+URLEncoder.encode(orderModel.toString(),"UTF-8")+"&sign="+sign+"&signtype=RSA";
			dWebView.postUrl("https://cp.halocash.hk/halocash/v1/createtrans",param.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("transdata==printSt",e.getMessage());
		}




		setContentView(dWebView,layoutParams);

	}
	private String  privateKeySign(String transdata) throws Exception{
//	 byte[] keyBytes =  Base64.decode(Constants.HHYK_S_KEY);
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Constants.HHYK_S_KEY.getBytes("ISO-8859-1"));
		KeyFactory keyFactory = KeyFactory.getInstance("RSA", "BC");
		PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
		Signature signature = Signature.getInstance("MD5withRSA");
//初始化私钥
		signature.initSign(privateKey);
//传入签名内容
		signature.update(transdata.getBytes());
//生成签名
		byte[] result = signature.sign();

		return result.toString();
	}
//	/**
//	 * Transform the specified byte into a Hex String form.
//	 */
//	public static final String bytesToHexStr(byte[] bcd) {
//		StringBuffer s = new StringBuffer(bcd.length * 2);
//
//		for (int i = 0; i < bcd.length; i++) {
//			s.append(bcdLookup[(bcd[i] >>> 4) & 0x0f]);
//			s.append(bcdLookup[bcd[i] & 0x0f]);
//		}
//
//		return s.toString();
//	}

//	/**
//	 * Transform the specified Hex String into a byte array.
//	 */
//	public static final byte[] hexStrToBytes(String s) {
//		byte[] bytes;
//
//		bytes = new byte[s.length() / 2];
//
//		for (int i = 0; i < bytes.length; i++) {
//			bytes[i] = (byte) Integer.parseInt(s.substring(2 * i, 2 * i + 2),
//					16);
//		}
//
//		return bytes;
//	}
//
//	private static final char[] bcdLookup = { '0', '1', '2', '3', '4', '5',
//			'6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
}


