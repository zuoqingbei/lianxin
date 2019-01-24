package com.hailian.util.encrypt;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.net.URLDecoder;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by han on 2017/4/14.
 */

public class AES {

	private static final String default_charset = "UTF-8";

	/**
	 * 加密
	 *
	 * @param content
	 *            需要加密的内容
	 * @param key
	 *            加密密码
	 * @param md5Key
	 *            是否对key进行md5加密
	 * @param iv
	 *            加密向量
	 * @return 加密后的字节数据
	 */
	public static byte[] encrypt(byte[] content, byte[] key, boolean md5Key, byte[] iv) {
		try {
			if (md5Key) {
				MessageDigest md = MessageDigest.getInstance("MD5");
				key = md.digest(key);
			}
			SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/ISO10126Padding"); // "算法/模式/补码方式"
			IvParameterSpec ivps = new IvParameterSpec(iv);// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivps);
			return cipher.doFinal(content);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException ex) {
			// HLog.e(ex.getLocalizedMessage());
		}
		return null;
	}

	public static byte[] decrypt(byte[] content, byte[] key, boolean md5Key, byte[] iv) {
		try {
			if (md5Key) {
				MessageDigest md = MessageDigest.getInstance("MD5");
				key = md.digest(key);
			}
			SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/ISO10126Padding"); // "算法/模式/补码方式"
			IvParameterSpec ivps = new IvParameterSpec(iv);// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivps);
			return cipher.doFinal(content);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
				| InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException ex) {
			// HLog.e(ex.getLocalizedMessage());
		}
		return null;
	}

	/*
	 * 加密数据 encrypt
	 * 
	 * @param data 需要加密的源数据
	 * 
	 * @param key 用来加密数据的秘钥
	 */
	public static final String encrypt(String data, String key) {
		if (StringUtils.isEmpty(data)) {
			return "";
		}
		if (StringUtils.isEmpty(key)) {
			return "";
		}
		if (key.length() != 16) {
			return "";
		}

		byte[] binary = encrypt(data.getBytes(), key.getBytes(), false, key.getBytes());
		if (binary == null) {
			return "";
		}
		String value = Base64Util.encode(binary);

		// URL解码
		value = URLDecoder.decode(value);

		return value;
	}

	/*
	 * 解密数据 decrypt
	 * 
	 * @param data 需要解密的源数据
	 * 
	 * @param key 用来解密数据的秘钥
	 */
	public static final String decrypt(String data, String key) {
		if (StringUtils.isEmpty(data)) {
			return "";
		}
		if (StringUtils.isEmpty(key)) {
			return "";
		}
		if (key.length() != 16) {
			return "";
		}

		data = URLDecoder.decode(data);

		byte[] binary = Base64Util.decode(data);
		if (binary == null) {
			return "";
		}
		byte[] valueByte = decrypt(binary, key.getBytes(), false, key.getBytes());
		if (valueByte == null) {
			return "";
		}
		String value = "";
		try {
			value = new String(valueByte, default_charset);
		} catch (Exception e) {
			value = "";
		}

		return value;
	}

    public static void main(String[] args) throws Exception {
        /*
         * 此处使用AES-128-ECB加密模式，key需要为16位。
         */
        String cKey = "zwd1497943361999";
        // 需要加密的字串
        String cSrc = "Hello Word-@#￥……*, hello,(中国){4}。";
        System.out.println(cSrc);
        // 加密
        String enString = AES.encrypt(cSrc, cKey);
        System.out.println("加密后的字串是：" + enString);

        // 解密
        String DeString = AES.decrypt("g13%2F4ZXnrWNPn5FhlcTmaPd7%2FkxrLc6FrnFQp0my3kR2gR6Evlg6zaM%2FQ2Xk+wpaJ", cKey);
        System.out.println("解密后的字串是：" + DeString);
    }
    
}
