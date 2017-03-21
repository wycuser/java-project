package com.shanlin.p2p.csai.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.springframework.util.MultiValueMap;

import com.shanlin.framework.config.PropertyFileConfigurer;
import com.shanlin.framework.controller.BaseAction;

/**
 * 希财主Action类
 * 
 * @author ice
 * 
 */
public class CsaiMainAction extends BaseAction {

	public static final Boolean isCreateHtml = true;// 是否创建Html

	public static final String env = PropertyFileConfigurer.getContextProperty("environment");// 环境变量(Local,Test,Server)
	public static final String charset = "UTF-8";// 编码
	public static final String debugType = "0";// 调试类型(0:不显示调试信息,1:显示关键调试信息,2:显示全部调试信息)

	public static String shNo = getShNo();// 商户编号
	public static String shKey = getShKey();// 商户密钥
	public static String urlQzSecYmd = getUrlQzSecYmd();// 前置链接
	public static String serverQzApp = getQzApp();
	public static String serverQzWeb = getQzWeb();

	// 获取app链接前置
	public static String getQzApp() {
		if (env.equals("Test")) {
			urlQzSecYmd = "http://58.250.171.53:8200";// 前置链接
			return urlQzSecYmd;
		} else if (env.equals("Server")) {
			urlQzSecYmd = "http://www.myshanxing.com:8888";// 前置链接
			return urlQzSecYmd;
		}
		return null;
	}

	// 获取web链接前置
	public static String getQzWeb() {
		if (env.equals("Test")) {
			urlQzSecYmd = "http://58.250.171.53:8088";// 前置链接
			return urlQzSecYmd;
		} else if (env.equals("Server")) {
			urlQzSecYmd = "http://www.myshanxing.com";// 前置链接
			return urlQzSecYmd;
		}
		return null;
	}

	// 获取前置链接
	public static String getUrlQzSecYmd() {
		if (env.equals("Test")) {
			urlQzSecYmd = "http://api.csai.cn/api";// 前置链接
			return urlQzSecYmd;
		} else if (env.equals("Server")) {
			urlQzSecYmd = "http://api.csai.cn/api";// 前置链接
			return urlQzSecYmd;
		}
		return null;
	}

	// 获取商户编号
	public static String getShNo() {
		if (env.equals("Test")) {
			shNo = "b2becfd8f8384124afc1a7075ff735d2";// 商户编号
			return shNo;
		} else if (env.equals("Server")) {
			shNo = "b2becfd8f8384124afc1a7075ff735d2";// 商户编号
			return shNo;
		}
		return null;
	}

	// 获取商户密钥
	public static String getShKey() {
		if (env.equals("Test")) {
			shKey = "32825ddd1c5d4a8d9d0534be19cd222a";// 商户密钥
			return shKey;
		} else if (env.equals("Server")) {
			shKey = "32825ddd1c5d4a8d9d0534be19cd222a";// 商户密钥
			return shKey;
		}
		return null;
	}

	/**
	 * 获取表单提交型参数(根据Url和MultiValueMap)
	 * 
	 * @param url
	 * @param multiValueMap
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String getFormSubmitParam(String url, MultiValueMap<String, String> multiValueMap) {
		StringBuffer sb = new StringBuffer();
		if (multiValueMap != null) {
			Map mapKeyAndValues = multiValueMap.toSingleValueMap();
			List<String> listKeys = new ArrayList<String>(multiValueMap.keySet());
			sb.append("<form id=\"sl_form\" name=\"sl_form\" action=\"" + url + "\" method=\"post\" target=\"_self\">\n");
			for (String paramName : listKeys) {
				String value = getSafeString(mapKeyAndValues.get(paramName));
				sb.append("  <input type=\"hidden\" name=\"" + paramName + "\" id=\"" + paramName + "\" value=\"" + value + "\" />\n");
			}
			sb.append("</form>\n");
			sb.append("<script language=\"javascript\">window.onload=function(){document.sl_form.submit();}</script>");
		}
		return sb.toString();
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
}