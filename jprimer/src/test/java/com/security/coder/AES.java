package com.security.coder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;


/*******************************************************************************
 * AES加解密算法
 * 
 * @author arix04
 * 
 */
public class AES {
	// 加密
	public static String Encrypt(String sSrc, String sKey) throws Exception {
		if(! validateKey(sKey)){
			return null;
		}
		
		byte[] raw = sKey.getBytes();
		Cipher cipher = initEncry(raw,true);
		byte[] encrypted = cipher.doFinal(sSrc.getBytes());

		return Base64.getEncoder().encodeToString(encrypted);// 此处使用BASE64做转码功能，同时能起到2次加密的作用。
	}

	// 解密
	public static String Decrypt(String sSrc, String sKey) throws Exception {
		if(! validateKey(sKey)){
			return null;
		}
		try {
			byte[] raw = sKey.getBytes("ASCII");

			Cipher cipher = initEncry(raw,false);
			byte[] encrypted1 = Base64.getDecoder().decode(sSrc);// 先用base64解密
			try {
				byte[] original = cipher.doFinal(encrypted1);
				String originalString = new String(original);
				return originalString;
			} catch (Exception e) {
				System.out.println(e.toString());
				return null;
			}
		} catch (Exception ex) {
			System.out.println(ex.toString());
			return null;
		}
	}

	private static Boolean validateKey(String sKey) {
		// 判断Key是否正确
		if (sKey == null) {
			System.out.print("Key为空null");
			return false;
		}
		// 判断Key是否为16位
		if (sKey.length() != 16) {
			System.out.print("Key长度不是16位");
			return false;
		}
		return true;
	}

	private static Cipher initEncry(byte[] raw,boolean isEncrypt) throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		// "算法/模式/补码方式"
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		
		// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
		IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
		cipher.init(isEncrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, skeySpec, iv);
		return cipher;
	}

	public static void main(String[] args) throws Exception {
		/*
		 * 加密用的Key 可以用26个字母和数字组成，最好不要用保留字符，虽然不会错，至于怎么裁决，个人看情况而定
		 * 此处使用AES-128-CBC加密模式，key需要为16位。
		 */
		String cKey = "1234567890123456";
		// 需要加密的字串
		String cSrc = "Email : arix04@xxx.com";
		System.out.println(cSrc);
		// 加密
		long lStart = System.currentTimeMillis();
		String enString = AES.Encrypt(cSrc, cKey);
		System.out.println("加密后的字串是：" + enString);

		long lUseTime = System.currentTimeMillis() - lStart;
		System.out.println("加密耗时：" + lUseTime + "毫秒");
		// 解密
		lStart = System.currentTimeMillis();
		String DeString = AES.Decrypt(enString, cKey);
		System.out.println("解密后的字串是：" + DeString);
		lUseTime = System.currentTimeMillis() - lStart;
		System.out.println("解密耗时：" + lUseTime + "毫秒");
	}
}
