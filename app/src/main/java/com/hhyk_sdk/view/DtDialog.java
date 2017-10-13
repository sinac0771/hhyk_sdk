package com.hhyk_sdk.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.BoringLayout;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;

import com.hhyk_sdk.entity.OrderModel;


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
	private  OrderModel orderModel;

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
		layoutParams.height=height;
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

String param="transdata="+orderModel.toString()+"&sign="+"25d13a5da"+"&signtype=RSA";
		dWebView.postUrl("https://cp.halocash.hk/halocash/v1/createtrans",param.getBytes());


		setContentView(dWebView,layoutParams);

	}

}
