package com.shanlin.p2p.wangd.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.shanlin.p2p.app.model.enums.BidMode;
import com.shanlin.p2p.app.model.enums.BidPayMode;

/**
 * 网贷P2p工具类
 * 
 * @author ice
 *
 */
public class WangdMainUtil {

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

	// 获取安全字符
	public static String getSafeString(Object obj) {
		if (obj == null) {
			return "";
		} else {
			return obj.toString();
		}
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
		// 1：到期还本息(到期还本付息，一次性还本付息，按日计息到期还本,一次性付款、秒还)(ok)
		// 2：每月等额本息(按月分期，按月等额本息)(ok)
		// 3：每季分期（按季分期，按季等额本息）
		// 5：每月付息到期还本(先息后本)(ok)
		// 6：等额本金(按月等额本金)(ok)
		// 7：每季付息到期还本（按季付息到期还本）
		// 8：每月付息分期还本
		// 9：先付息到期还本

		// 等额本息
		if (mode == BidMode.DEBJ) {
			// 若为按月
			if (payMode == BidPayMode.ZRY) {
				return 2;
			} else if (payMode == BidPayMode.GDR) {
				return 2;// 设置为最相近的
			}
		}
		// 每月付息,到期还本
		else if (mode == BidMode.MYFX) {
			return 5;
		}
		// 本息到期一次付清
		else if (mode == BidMode.YCFQ) {
			return 1;
		}
		// 等额本金
		else if (mode == BidMode.DEBJ) {
			return 6;
		}
		return 1;// 设置为默认
	}

	/**
	 * 日期转化为字符串:(yyyy-MM-dd HH:mm:ss)
	 * 
	 * @param date
	 *            传入日期
	 * @return
	 */
	public static String getDateStr(Date date) {
		if (date == null) {
			return null;
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
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
}