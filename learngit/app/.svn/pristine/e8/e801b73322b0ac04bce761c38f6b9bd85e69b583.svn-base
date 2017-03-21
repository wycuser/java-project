package com.shanlin.p2p.rotu.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.binary.Base64;

import com.shanlin.p2p.app.model.enums.BidMode;
import com.shanlin.p2p.app.model.enums.BidPayMode;

/**
 * 融途P2p工具类
 * 
 * @author ice
 *
 */
public class RotuMainUtil {

	// 是否为空
	public static boolean isBlank(String str) {
		int strLen;
		if ((str == null) || ((strLen = str.length()) == 0)) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	// 获取转码后的64位字符串
	public static String getBase64(String str) {
		String base64 = null;
		try {
			base64 = new String(new Base64().encode(str.getBytes("utf-8")), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return base64;
	}

	// 获取安全字符
	public static String getSafeString(Object obj) {
		if (obj == null) {
			return "";
		} else {
			return obj.toString();
		}
	}

	// 读取文件信息(将读取的文件信息返回)
	public static String readFile(String filePath) {
		String line = "";
		BufferedReader br = null;
		StringBuffer sb = new StringBuffer();
		InputStreamReader inputStreamReader = null;
		try {
			File file = new File(filePath);
			inputStreamReader = new InputStreamReader(new FileInputStream(file), "UTF-8");
			br = new BufferedReader(inputStreamReader);
			while ((line = br.readLine()) != null) {
				sb.append(line + "\r\n");
			}
			if (!isBlank(sb.toString())) {
				sb.substring(0, sb.lastIndexOf("\r\n"));
			}
		} catch (Exception e) {
			throw new RuntimeException("读取文件信息(将读取的文件信息返回)异常", e);
		} finally {
			try {
				if (br != null) {
					br.close();
				}
				if (inputStreamReader != null) {
					inputStreamReader.close();
				}
			} catch (Exception e) {
				throw new RuntimeException("读取文件信息(将读取的文件信息返回)异常", e);
			}
		}
		return sb.toString();
	}

	/**
	 * 将指定中文转化为16进制ASCII码
	 * 
	 * @param str
	 *            待添加字符串数组
	 * @return
	 */
	public static String changeChineseToAscii(String str) {
		// 定义,初始化
		String result = "";
		char[] chars = str.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if ((chars[i] >= 0x4e00) && (chars[i] <= 0x9fbb)) {
				result += "\\u" + Integer.toHexString((int) chars[i]).toUpperCase();
			} else {
				result += chars[i];
			}
		}
		return result;
	}
	
	/**
	 * 获取安全Integer
	 * 
	 * @param o
	 *            对象
	 * @return
	 */
	public static Integer getSafeInteger(Object o) {
		if (o == null || isBlank(o.toString())) {
			return 0;
		}
		return Integer.parseInt(o.toString());
	}

	// 获取接口所需整型标还款方式
	public static Integer getBidMode(BidMode mode, BidPayMode payMode) {
		// 借款还款类型（0:等额本息,1:到期本息，3:月息期本，由于平台的自定义类型特别多，目前就是这三种类型，如果有其他类型的选择和以上三种类型最接近的一个类型返回
		// 等额本息
		if (mode == BidMode.DEBJ) {
			// 若为按月
			if (payMode == BidPayMode.ZRY) {
				return 0;
			} else if (payMode == BidPayMode.GDR) {
				return 0;// 设置为其他,因为接口这里没有可选的选项
			}
		}
		// 每月付息,到期还本
		else if (mode == BidMode.MYFX) {
			return 3;
		}
		// 本息到期一次付清
		else if (mode == BidMode.YCFQ) {
			return 1;
		}
		// 等额本金
		else if (mode == BidMode.DEBJ) {
			return 0;// 选择的最接近的
		}
		return 0;// 选择的最接近的
	}

	public static void main(String[] args) {
		List<String> list = getBefore30Days();
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
	}

	//  获取今天之前30的日期存入list(只含MM-dd)
	public static List<String> getBefore30Days() {
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
		Calendar cal = Calendar.getInstance();// 获得当前的时间
		cal.add(Calendar.DATE, -1);// 
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < 30; i++) {
			list.add(sdf.format(cal.getTime()));
			cal.add(Calendar.DATE, -1);
		}
		return list;
	}

	/**
	 * 字符获取:获取安全字符并安全剪断两边
	 * 
	 * @param obj
	 * @return
	 */
	public static String getSafeAndTrim(Object obj) {
		if (obj == null) {
			return "";
		} else {
			return obj.toString().trim();
		}
	}
	
	/**
	 * 获取当前时间(yyyy-MM-dd HH:mm:ss)
	 * 
	 * @return
	 */
	public static String getTime() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}
}