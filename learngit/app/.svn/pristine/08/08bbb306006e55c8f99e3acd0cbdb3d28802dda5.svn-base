package com.shanlin.p2p.jint.util;

import java.security.MessageDigest;

import org.springframework.util.Assert;

/**
 * MD5加密工具类
 * 
 * @author ice
 * 
 */
public class MD5Encrypt {

	private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	/**
	 * MD5加密主方法(返回全小写)
	 * 
	 * @param str
	 *            待加密字符串
	 * @param charset
	 *            编码模式
	 * @return 返回全小写的加密后的字符串
	 */
	public static String MD5(String str, String charset) {
		// 参数检查
		Assert.notNull(str);
		Assert.hasText(charset);

		String resultString = null;
		try {
			resultString = new String(str);
			MessageDigest md = MessageDigest.getInstance("MD5");
			if (charset == null || "".equals(charset)) {
				resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
			} else {
				resultString = byteArrayToHexString(md.digest(resultString.getBytes(charset)));
			}
		} catch (Exception e) {
			throw new RuntimeException("MD5加密主方法(返回全小写)异常", e);
		}
		return resultString;
	}

	/**
	 * MD5加密方法(加密byte[],返回全小写)
	 * 
	 * @param byArr
	 *            byte[]数组
	 * @return
	 */
	public static String MD5(byte[] byArr) {
		// 参数检查
		Assert.notNull(byArr);

		// 16进制字符
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] strTemp = byArr;
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] by = mdTemp.digest();
			int j = by.length;
			char str[] = new char[j * 2];
			int k = 0;
			// 移位 输出字符串
			for (int i = 0; i < j; i++) {
				byte byte0 = by[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			throw new RuntimeException("MD5加密方法(加密byte[],返回全小写)异常", e);
		}
	}

	// 将byte[]数组转化为16进制字符串
	private static String byteArrayToHexString(byte[] byArr) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < byArr.length; i++) {
			sb.append(byteToHexString(byArr[i]));
		}

		return sb.toString();
	}

	// 将byte转化为16进制字符串
	private static String byteToHexString(byte by) {
		int n = by;
		if (n < 0) {
			n += 256;
		}
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}
}