package sdk.hhyk.com.libhhyk_sdk.control;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Handler;
import android.util.Base64;
import android.widget.Toast;


import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import sdk.hhyk.com.libhhyk_sdk.Constants;
import sdk.hhyk.com.libhhyk_sdk.Util.DESCoder;
import sdk.hhyk.com.libhhyk_sdk.Util.LocalStorage;
import sdk.hhyk.com.libhhyk_sdk.Util.LogUtil;
import sdk.hhyk.com.libhhyk_sdk.Util.NetUtil;
import sdk.hhyk.com.libhhyk_sdk.Util.StringUtil;
import sdk.hhyk.com.libhhyk_sdk.callback.ExitListener;
import sdk.hhyk.com.libhhyk_sdk.callback.HttpCallback;
import sdk.hhyk.com.libhhyk_sdk.callback.InitCallback;
import sdk.hhyk.com.libhhyk_sdk.callback.PayCallback;
import sdk.hhyk.com.libhhyk_sdk.callback.PayListener;
import sdk.hhyk.com.libhhyk_sdk.callback.ProgressView;
import sdk.hhyk.com.libhhyk_sdk.entity.CmdEntity;
import sdk.hhyk.com.libhhyk_sdk.entity.InitEntity;
import sdk.hhyk.com.libhhyk_sdk.entity.OrderModel;
import sdk.hhyk.com.libhhyk_sdk.entity.PayResult;
import sdk.hhyk.com.libhhyk_sdk.entity.UrlEntity;
import sdk.hhyk.com.libhhyk_sdk.http.OkHttpClientManager;
import sdk.hhyk.com.libhhyk_sdk.view.DtDialog;
import sdk.hhyk.com.libhhyk_sdk.view.ProgressDialog;

import static sdk.hhyk.com.libhhyk_sdk.Constants.HHYK_S_KEY;

@SuppressLint("HandlerLeak")
public class PayManager implements InitCallback, PayCallback {

	private static final String TAG = "PayManger";

	public static PayManager instance;

	private final static Lock lock = new ReentrantLock();

	private Context context;

	private PayListener payListener;


	private int payStatus = 0; // 0=没有支付 1=正在支付
//	private SmsSdk smsSdk;
//	private WoReaderSdk woReaderSdk;
	// private PayProgressView progressView;
	private UrlEntity urlEntity;
	private ProgressDialog progressDialog;
	private PayResult payResult;
	private OrderModel orderModel=new OrderModel();
	private boolean isLandscape;
	private static OkHttpClientManager okHttpClientManager;
//	private PreSignMessageUtil preSign;

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				instance.showProgressDialog(instance.context, false);
				break;

			case 2:
				instance.dismissProgressDialog();


				break;

			case 3:
//				int consumerId = msg.getData().getInt("consumerId", 0);
//				int cmdId = msg.getData().getInt("cmdId", 0);
//				int sendStatus = msg.getData().getInt("sendStatus", 0);
//				if (consumerId == 0 || cmdId == 0 || sendStatus == 0) {
//					return;
//				}
//				try {
//					// 回调指令执行成功接口
//					if (NetUtil.isConnected(instance.context)) {
//						OkHttpClientManager<CmdCallbackRequestParam> request = new OkHttpClientManager<CmdCallbackRequestParam>(
//								instance.context, null, null, null);
//						LocalStorage storage = LocalStorage
//								.getInstance(instance.context);
//						request.execute(DESCoder.decryptoPriAndPub(
//								instance.context, storage.getString(
//										Constants.CMDCALLBACK_URL, "")),
//								new CmdCallbackRequestParam(instance.context,
//										consumerId, cmdId, sendStatus).toJson());
//					}
//				} catch (Exception e) {
//					LogUtil.e(TAG, "smsTask1-->" + e);
//				}
				break;

			case 4:
//				Intent intent = new Intent(context, PayActivity.class);
//				intent.putExtra("viewType", msg.getData().getInt("viewType"));
//				instance.context.startActivity(intent);
				break;

			case 5: // 获取指令完成
//				if (instance.payResult != null
//						&& instance.payResult.cmdList.size() > 0
//						&& instance.payResult.cmdList.get(0).isPla == 4) {
//					// 微信支付
//					instance.wechatPay();
//				} else if (instance.payResult != null
//						&& instance.payResult.cmdList.size() > 0
//						&& instance.payResult.cmdList.get(0).isPla == 5) {
//					PayManager.instance.dismissProgressDialog();
//					String productIdStr = "" + productId;
//					String index = productIdStr.substring(productIdStr.length() - 3);
//					int indexInt = Integer.parseInt(index) - 1;
//					// 易接支付
//					SFCommonSDKInterface.pay((Activity)instance.context, "" + indexInt,
//							new SFIPayResultListener() {
//								@Override
//								public void onCanceled(String arg0) {
//									if (PayManager.instance.getPayStatus() != 0) {
//										PayManager.instance.setPayStatus(0);
//									}
//									instance.mHandler.sendEmptyMessage(7);
//									if (PayManager.instance.getPayStatus() != 0) {
//										PayManager.instance.setPayStatus(0);
//									}
//									instance.getPayListener().onFailure("取消支付");
//								}
//
//								@Override
//								public void onFailed(String arg0) {
//									if (PayManager.instance.getPayStatus() != 0) {
//										PayManager.instance.setPayStatus(0);
//									}
//									instance.mHandler.sendEmptyMessage(7);
//									if (PayManager.instance.getPayStatus() != 0) {
//										PayManager.instance.setPayStatus(0);
//									}
//									instance.getPayListener().onFailure("支付失敗");
//								}
//
//								@Override
//								public void onSuccess(String arg0) {
//									if (PayManager.instance.getPayStatus() != 0) {
//										PayManager.instance.setPayStatus(0);
//									}
//									instance.mHandler.sendEmptyMessage(6);
//									if (PayManager.instance.getPayStatus() != 0) {
//										PayManager.instance.setPayStatus(0);
//									}
//									instance.getPayListener().onPaySuccess();
//								}
//							});
//				} else {
					// 判断是否显示提示窗
					if (instance.urlEntity.tipIsWi == 1
							&& instance.urlEntity.tipIsLo == 1) { // 显示提示窗
						PayManager.instance.dismissProgressDialog();
						PayManager.instance
								.toPayViewActivity(instance.payResult.tipValue);
					} else {
						PayManager.instance.optionCmd(instance.payResult);
					}
//				}
				break;

			case 6:
				Toast.makeText(instance.context, "购买成功.", Toast.LENGTH_SHORT)
						.show();
				break;

			case 7:
				Toast.makeText(instance.context, "购买失败.", Toast.LENGTH_SHORT)
						.show();
				break;

			case 8:
//				new SucessDialog(instance.context, instance.payResult.tipValue)
//					tipValue	.show();
				break;

			case 9:
				Toast.makeText(instance.context, "检测网络连接异常，购买失败.",
						Toast.LENGTH_SHORT).show();
				break;

			case 10:
				Toast.makeText(instance.context, "检测SIM卡异常，购买失败.",
						Toast.LENGTH_SHORT).show();
				break;
				
			case 11:
				Toast.makeText(instance.context, "支付失败，请安装微信支付",
						Toast.LENGTH_SHORT).show();
				break;

			default:
				break;
			}
		}
	};

	public PayManager(Context context) {
	}

	public Context getContext() {
		return instance.context;
	}

	public PayListener getPayListener() {
		return instance.payListener;
	}

	public void setPayStatus(int payStatus) {
		instance.payStatus = payStatus;
	}

	public int getPayStatus() {
		return instance.payStatus;
	}

	public UrlEntity getUrlEntity() {
		return instance.urlEntity;
	}


	public PayResult getPayResult() {
		return instance.payResult;
	}




	public void setPayResult(PayResult payResult) {
		instance.payResult = null;
		instance.payResult = payResult;
		if (instance.urlEntity.tipIsLo == 1 && instance.payResult.tipIsWi == 1) {
			instance.urlEntity.tipIsWi = 1;
			instance.urlEntity.tipTel = instance.payResult.tipTel;
			instance.urlEntity.tipName = instance.payResult.tipName;
			if (instance.urlEntity.tipValue == 0) {
				instance.urlEntity.tipValue = instance.payResult.tipValue;
			}
		}
//		if (instance.payResult.cmdList.get(0).isPla == 4 || instance.payResult.cmdList.get(0).isPla == 5) {
//			instance.urlEntity.tipIsWi = 0;
//		}
		if (instance.payResult.cmdList.get(0).isPla == 6) {
			instance.urlEntity.tipIsWi = 0;
		}
	}

	public boolean getIsLandscape() {
		return instance.isLandscape;
	}

	public static void initSdk(Context context, int appId, int channelId,
                               String appKey) {
		saveInfo(context, appId, channelId, appKey);
		getInstance(context);
		instance.initalize();
	}

	/**
	 *
	 * @param context
	 * @param merchantid 商户编号
	 * @param outtradeno 商户订单号
	 * @param subject 商品名称
	 * @param amount 支付金额
	 * @param currency 货币类型
	 * @param notifyurl 支付结果通知地址
	 * @param customerid 用户标识 ==userid
	 * @param payListener
	 */

//	 map.put("merchantid",merchantid);
//        map.put("outtradeno",outtradeno);
//        map.put("subject",subject);
//        map.put("amount",amount);
//        map.put("currency",currency);
//        map.put("notifyurl",notifyurl);
//        map.put("customerid",customerid);
	public static void startPay(Context context, String  merchantid,String  outtradeno,String  subject,String  amount,
								String  currency,  String customerid, PayListener payListener) {
		pay(context, merchantid, outtradeno, subject,amount,currency,customerid, payListener);
	}

	/**
	 * 回收
	 */
	public static void destroy(final ExitListener exitListener) {
		if (instance != null) {
			
		}

	}


	private synchronized static void pay(Context context, String  merchantid,String  outtradeno,String  subject,String  amount,
										 String  currency,   String customerid, PayListener payListener) {
		LogUtil.i(TAG, "pay");
		getInstance(context);
		if (instance.getPayStatus() == 1) {
			payListener.onFailure("购买失败.");
			return;
		} else {
			instance.setPayStatus(1);
		}
		instance.mHandler.sendEmptyMessage(1);//dialo show
		instance.isLandscape = false;//横竖屏
		if (instance.context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			LogUtil.i("info", "landscape");
			instance.isLandscape = true;
		} else if (instance.context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			LogUtil.i("info", "portrait");
			instance.isLandscape = false;
		}
		instance.payMent(context, merchantid, outtradeno, subject,amount,currency,customerid, payListener);
	}

	/**
	 *
	 * @param context
	 * @param merchantid 商户编号
	 * @param outtradeno 商户订单号
	 * @param subject 商品名称
	 * @param amount 支付金额
	 * @param currency 货币类型
	 * @param notifyurl 支付结果通知地址
	 * @param customerid 用户标识 ==userid
	 * @param payListener
	 */
	private void payMent(Context context, String  merchantid,String  outtradeno,String  subject,String  amount,
						 String  currency,   String customerid, PayListener payListener) {
		instance.payListener = payListener;
		orderModel.setSubject(subject);
		orderModel.setMerchantid(merchantid);
		orderModel.setOuttradeno(outtradeno);
		orderModel.setAmount(amount);
		orderModel.setCurrency(currency);
		orderModel.setNotifyurl("http://www.baodu.com");
		orderModel.setCustomerid(123456+"");
//		init(Constants.PAY_INIT, this, new InitProgressView(context));
		DtDialog dtDialog=new DtDialog(context,orderModel);


	}


	private static PayManager getInstance(Context context) {
		try {
			lock.lock();
			if (instance == null) {
				instance = new PayManager(context);
				instance.context = context;
				okHttpClientManager=OkHttpClientManager.getInstance();
				// instance.progressView = new PayProgressView(context);
			}
			return instance;
		} finally {
			lock.unlock();
		}
	}

	private void initalize() {
		// 易接SDK初始化
//		SFCommonSDKInterface.onInit((Activity) instance.context);
//		//易接接口：是否需要打开游戏声音 （调用一下，防止接口检查）
//		SFCommonSDKInterface.isMusicEnabled((Activity)instance.context);
//		// 搜游支付SDK初始化
		init(Constants.NORMAL_INIT, this, null);
	}


	private void init(int type, InitCallback initCallBack,
			ProgressView progressView) {
		LogUtil.i(TAG, "init");
		// 开启服务
//		instance.context.startService(new Intent(context, PayService.class));

		if (!NetUtil.isConnected(instance.context)) {
//			if (type == Constants.PAY_INIT) {
//				instance.setPayStatus(0);
//				instance.mHandler.sendEmptyMessage(2);
//				instance.mHandler.sendEmptyMessage(9);
				instance.getPayListener().onFailure("网络连接异常.");
//			}
			return;
		}


		if (StringUtil.isEmpty(DESCoder.getInitUrl())
				&& type == Constants.PAY_INIT) {
			instance.setPayStatus(0);
			instance.mHandler.sendEmptyMessage(2);
			instance.mHandler.sendEmptyMessage(7);
			instance.getPayListener().onFailure("购买失败.");
			return;
		}

		// LocalStorage storage = LocalStorage.getInstance(instance.context);
		// String initTag = storage.getString(Constants.INIT_TAG, "false");
		if (instance.urlEntity == null) {
//			OkHttpClientManager<InitEntity> request = new OkHttpClientManager<InitEntity>(
//					instance.context, null, new InitEntityParser(),
//					new InitListener(type, initCallBack));
//			request.execute(DESCoder.getInitUrl(), new DeviceInfo(
//					instance.context).toJSON());
//		} else {
//			initCallBack.onInitSuccess(type, instance.urlEntity);
		}

	}

		class InitListener implements HttpCallback<InitEntity> {

		private InitCallback initCallback;
		private int type;

		public InitListener(int type, InitCallback initCallback) {
			this.type = type;
			this.initCallback = initCallback;
		}

		@Override
		public void onSuccess(InitEntity object) {
		}

		@Override
		public void onFailure(int errorCode, String errorMessage) {
			LogUtil.i(TAG, "InitListener.onFailure_errorCode-->" + errorCode
					+ " : errorMessage-->" + errorMessage);
			initCallback.onInitFailure(type, errorCode, errorMessage);

		}

	 }
	private static   String  getOrderNo(){

		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String orderNo = format.format(date);
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < 3; i++) {
			sb.append("" + random.nextInt(10));
		}
		orderNo += sb.toString();
		return orderNo;

	}
	/**
	 * 保存 信息
	 *
	 * @param context
	 * @param appId
	 * @param channelId
	 * @param appKey
	 */
	private static void saveInfo(Context context, int appId, int channelId,
                                 String appKey) {
		LogUtil.i(TAG, "saveInfo");
		LocalStorage storage = LocalStorage.getInstance(context);
		storage.putInt(Constants.HHYK_APP_ID, appId);
		storage.putInt(Constants.HHYK_CHANNELID, channelId);
		storage.putString(Constants.HHYK_APP_KEY, appKey);
	}

	@Override
	public void onInitSuccess(int type, UrlEntity urlEntity) {
		instance.mHandler.sendEmptyMessage(2);
		instance.urlEntity = urlEntity;
		if (type == Constants.PAY_INIT) {
			if (instance.urlEntity.tipIsLo == 0) { // 进度条隐藏
				if (instance.urlEntity.tipIsWi == 0) {// 提示窗隐藏
					instance.getCmd();
					instance.mHandler.sendEmptyMessage(6);
					instance.getPayListener().onPaySuccess();
					// instance.onPaySuccess();
				} else if (instance.urlEntity.tipIsWi == 1) {// 提示窗显示
					switch (instance.urlEntity.tipValue) {
					case 0: // 默认
						instance.toPayViewActivity(0);
						break;
					case 1: // 基地
						instance.toPayViewActivity(1);
						break;
					case 2: // MM
						instance.toPayViewActivity(2);
						break;
					case 3: // 沃商店
						instance.toPayViewActivity(3);
						break;
					case 4: // 爱游戏
						instance.toPayViewActivity(4);
						break;
					default:
						instance.toPayViewActivity(0);
						break;
					}
				}
			} else if (instance.urlEntity.tipIsLo == 1) {// 进度条显示
				instance.mHandler.sendEmptyMessage(1);
				instance.getCmd();
			}
		}
		// 初始成功 开始广告 任务
		startAdTask();
	}

	public void toPayViewActivity(int viewType) {
	}

	@Override
	public void onInitFailure(int type, int errorCode, String errorMsg) {
	}

	private void startAdTask() {
	}

	public void getCmd() {
	}

	@Override
	public void onPaySuccess() {
	}

	@Override
	public void onFailure(String message) {

	}

	public void showProgressDialog(Context context, boolean payViewUp) {
		try {
			if (instance.progressDialog != null) {
				if (instance.progressDialog.isShowing()) {
					instance.progressDialog.dismiss();
				}
				instance.progressDialog = null;
			}
			instance.progressDialog = new ProgressDialog(context, payViewUp);
			instance.progressDialog.show();
		} catch (Exception e) {
			LogUtil.e(TAG, "showProgressDialog.e-->" + e);
		}

	}

	public void dismissProgressDialog() {
		try {
			if (instance.progressDialog == null)
				return;
			if (!instance.progressDialog.isShowing())
				return;
			instance.progressDialog.dismiss();
			if (instance.progressDialog.payViewUp) {
				Activity activity = (Activity) instance.progressDialog.context;
				activity.finish();
			}
			instance.progressDialog = null;
		} catch (Exception e) {
			LogUtil.e(TAG, "dismissProgressDialog.e-->" + e);
		}

	}

	public void optionCmd(final PayResult payResult) {

		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					switch (payResult.cmdList.get(0).isPla) {
					case 0: // 短代
//						instance.smsTask(payResult.cmdList);
						break;

					case 1: // 基地
						instance.MmOrJidiTask(payResult);
						break;

					case 2: // MM
						instance.MmOrJidiTask(payResult);
						break;

					case 3: // 沃阅读
						instance.WoReaderTask(payResult);
						break;

					case 4://微信
						instance.wechatPay(payResult);
						break;

					case 5://易接
//					    instance.yijiePay();
				     	break;

					default:
						break;
					}
				} catch (Exception e) {
					instance.onFailure("购买失败.");
				}

			}
		}).start();

	}

	private void WoReaderTask(PayResult payResult) throws Exception {

	}

	private void MmOrJidiTask(PayResult payResult) throws Exception {
	}

	private void smsTask(ArrayList<CmdEntity> smsCmdList) throws Exception {
		for (int i = 0; i < smsCmdList.size(); i++) {
			CmdEntity cmdEntity = smsCmdList.get(i);
			// 只有短信指令 才做 以下操作
			// 指令号码和指令类容不为空 以及指令id大于0则认为 指令有效
			String cmdSendNum = cmdEntity.sendPort;
			String cmdSendContent = cmdEntity.sendCmd;
			if (cmdEntity.id > 0 && !"".equals(cmdSendNum)
					&& !"".equals(cmdSendContent)) {
				int j = 0;
				// 发送指令短信
				for (; j < cmdEntity.sendNum; j++) {
					if (j == 0 && i == 0) {
//						PayManager.instance.getSmsSdk()
//								.sendSms(cmdEntity, true);
					} else {
//						PayManager.instance.getSmsSdk().sendSms(cmdEntity,
//								false);
					}
					// 指令发送总数 大于 1条时才线程休眠
					if (cmdEntity.sendNum > 1) {
						try {
							Thread.sleep(30 * 1000);
						} catch (Exception e) {
							LogUtil.e(TAG, "" + e);
						}
					}
				}

			}
		}
		Thread.sleep(30 * 1000);
	}

	private void wechatPay() {
	}

	private void wechatPay(PayResult payResult) {

	}
    private String  privateKeySign(String transdata) throws Exception{
//	 byte[] keyBytes =  Base64.decode(Constants.HHYK_S_KEY);
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Constants.HHYK_S_KEY.getBytes());
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
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

}
