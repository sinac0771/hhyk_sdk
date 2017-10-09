package com.hhyk_sdk.Util;

import android.content.Context;
import android.util.Base64;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class DESCoder {

	// 编码
	private static String encoding = "UTF-8";

	private static native String JniGetpublickey();

	private static native String JniGetUrl();

	private static String publicKey;
	private static String initUrl;

	static {
		if(!LogUtil.LOG_SWITCH)
		System.loadLibrary("vsoyou");
	}

	public static String getPublicKey() {
		if (StringUtil.isEmpty(DESCoder.publicKey)) {
			if(LogUtil.LOG_SWITCH){
//				DESCoder.publicKey = "de9d646507caa7793d8cf4241a6e4284";
				DESCoder.publicKey = "08b78531de23e3756ad14f4b0ae2eed5"; //彭康 远程测试服务器
			}else{
				DESCoder.publicKey = DESCoder.JniGetpublickey();
			}
		}
		LogUtil.i("DESCoder", "DESCoder.publicKey-->" + DESCoder.publicKey);
		return DESCoder.publicKey;
	}

	public static String getInitUrl() {
		if (StringUtil.isEmpty(DESCoder.initUrl)) {
//			if(LogUtil.LOG_SWITCH){
				DESCoder.initUrl = "http://open.vsoyou.net/api/init.htm";
//				DESCoder.initUrl = "http://192.168.1.181:8080/webTest/hello";
//			}else{
//				DESCoder.initUrl = DESCoder.JniGetUrl();
//			}
		}
		LogUtil.i("DESCoder", "DESCoder.initUrl-->" + DESCoder.initUrl);
		return DESCoder.initUrl;
	}

	/**
	 * 加密 【先sdkKey再appKey】
	 * 
	 * @param context
	 * @param str
	 * @return
	 */
	public static String encryptoPubAndPri(Context context, String str) {
		String result = str;
		result = ebotongEncrypto(ConfigUtil.getAppKey(context),
				ebotongEncrypto(getPublicKey(), str));
		return result;
	}

	/**
	 * 解密【先appKey再sdkKey】
	 * 
	 * @param context
	 * @param str
	 * @return
	 */
	public static String decryptoPriAndPub(Context context, String str) {
		String result = str;
		result = ebotongDecrypto(getPublicKey(),
				ebotongDecrypto(ConfigUtil.getAppKey(context), str));
		return result;
	}

	/**
	 * 加密字符串
	 */
	public static String ebotongEncrypto(String mKey, String str) {
		String result = str;
		if (str != null && str.length() > 0) {
			try {
				byte[] encodeByte = symmetricEncrypto(mKey,
						str.getBytes(encoding));
				result = Base64.encodeToString(encodeByte, 0);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 解密字符串
	 */
	public static String ebotongDecrypto(String mKey, String str) {
		String result = str;
		if (str != null && str.length() > 0) {
			try {
				byte[] encodeByte = Base64.decode(str, 0);

				byte[] decoder = symmetricDecrypto(mKey, encodeByte);
				result = new String(decoder, encoding);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 对称加密字节数组并返回
	 * 
	 * @param byteSource
	 *            需要加密的数据
	 * @return 经过加密的数据
	 * @throws Exception
	 */
	public static byte[] symmetricEncrypto(String mKey, byte[] byteSource)
			throws Exception {
		try {
			int mode = Cipher.ENCRYPT_MODE;
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			byte[] keyData = mKey.getBytes();
			DESKeySpec keySpec = new DESKeySpec(keyData);
			Key key = keyFactory.generateSecret(keySpec);
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(mode, key);

			byte[] result = cipher.doFinal(byteSource);
			return result;
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}

	/**
	 * 对称解密字节数组并返回
	 * 
	 * @param byteSource
	 *            需要解密的数据
	 * @return 经过解密的数据
	 * @throws Exception
	 */
	public static byte[] symmetricDecrypto(String mKey, byte[] byteSource)
			throws Exception {
		try {
			int mode = Cipher.DECRYPT_MODE;
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			byte[] keyData = mKey.getBytes();
			DESKeySpec keySpec = new DESKeySpec(keyData);
			Key key = keyFactory.generateSecret(keySpec);
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(mode, key);
			byte[] result = cipher.doFinal(byteSource);
			return result;
		} catch (Exception e) {
			throw e;
		} finally {

		}
	}

}
