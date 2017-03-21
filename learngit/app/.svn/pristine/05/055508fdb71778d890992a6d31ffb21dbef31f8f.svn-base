package com.shanlin.p2p.csai.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.shanlin.p2p.csai.controller.CsaiMainAction;

/**
 * 账户通工具类
 * 
 * @author ice
 *
 */
public class CsaiZhtUtil {

	// 获取账户通的Key
	public static String getZhtKey() {
		String shKey = CsaiMainAction.getShKey();
		// 获取前8位(和希财的约定)
		return shKey.substring(0, 8);
	}

	// 加密
	public static String encrypt(String data, String key) {
		byte[] bt = encrypt(data.getBytes(), key.getBytes());
		String strs = new BASE64Encoder().encode(bt);
		return strs;
	}

	// 解密
	public static String decrypt(String data, String key) {
		if (data == null)
			return null;
		BASE64Decoder decorder = new BASE64Decoder();
		byte[] buf;
		try {
			buf = decorder.decodeBuffer(data);
			byte[] bt = decrypt(buf, key.getBytes());
			return new String(bt);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 加密方法
	private static byte[] encrypt(byte[] data, byte[] key) {
		try {
			DESKeySpec dks = new DESKeySpec(key);
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey secretkey = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			IvParameterSpec iv = new IvParameterSpec(key);
			cipher.init(Cipher.ENCRYPT_MODE, secretkey, iv);
			return cipher.doFinal(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 解密方法
	private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
		DESKeySpec dks = new DESKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretkey = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		IvParameterSpec iv = new IvParameterSpec(key);
		cipher.init(Cipher.DECRYPT_MODE, secretkey, iv);
		return cipher.doFinal(data);
	}
	
	// 获取当前时间
	public static Date getDate() {
		Calendar cal = Calendar.getInstance();
		return cal.getTime();
	}
	
	// 日期转化为字符串:(yyyy-MM-dd HH:mm:ss)
	public static String getDateStr(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}
	
	// 计算:两个日期的时间差(获取相差的秒数)
	public static Long calcSubSeconds(String time1, String time2) {

		// 定义,初始化
		long t1 = 0L;
		long t2 = 0L;
		long result = 0L;
		long hoursToSeconds = 0L;
		long minutesToSeconds = 0L;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {
			t1 = sdf.parse(time1).getTime();
			t2 = sdf.parse(time2).getTime();

			// 因为t1-t2得到的是毫秒级,所以要除3600000得出小时.算天数或秒同理
			int hours = (int) ((t2 - t1) / 3600000);
			int minutes = (int) (((t2 - t1) / 1000 - hours * 3600) / 60);
			int seconds = (int) ((t2 - t1) / 1000 - hours * 3600 - minutes * 60);

			// 若小时不为0,将小时化为秒
			if (hours != 0) {
				hoursToSeconds = hours * 3600;
			}
			// 若分不为0,将分化为秒
			if (minutes != 0) {
				minutesToSeconds = minutes * 60;
			}

			result = hoursToSeconds + minutesToSeconds + seconds;
		} catch (Exception e) {
			throw new RuntimeException("计算:两个日期的时间差(获取相差的秒数)异常", e);
		}
		return result;
	}
	
	// 获取当前时间(yyyy-MM-dd HH:mm:ss)
	public static String getTime() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}
	
	// 获取安全Integer
	public static Integer getSafeInteger(Object o) {
		if (o == null || isBlank(o.toString())) {
			return 0;
		}
		return Integer.parseInt(o.toString());
	}
	
	// 字符检查:是否字符串为空
	public static Boolean isBlank(String str) {
		int strLen;
		if ((str == null) || ((strLen = str.length()) == 0)) {
			return true;
		}

		// 若为null字符串也表示为空
		if (str.trim().equalsIgnoreCase("null")) {
			return true;
		}

		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	
	// 字符获取:获取安全字符并安全剪断两边
	public static String getSafeAndTrim(Object obj) {
		if (obj == null) {
			return "";
		} else {
			return obj.toString().trim();
		}
	}
	
	// 取出一个指定长度大小的随机正整数
	public static int getRandom(int length) {
		int num = 1;
		double random = Math.random();
		if (random < 0.1) {
			random = random + 0.1;
		}
		for (int i = 0; i < length; i++) {
			num = num * 10;
		}
		return (int) ((random * num));
	}
}