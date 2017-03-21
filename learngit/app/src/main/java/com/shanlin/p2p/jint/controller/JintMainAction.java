package com.shanlin.p2p.jint.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.util.MultiValueMap;

import com.shanlin.framework.config.PropertyFileConfigurer;
import com.shanlin.framework.controller.BaseAction;
import com.shanlin.p2p.jint.util.MD5Encrypt;
import com.shanlin.p2p.rotu.util.RotuMainUtil;

/**
 * 金投主Action类
 * 
 * @author ice
 * 
 */
public class JintMainAction extends BaseAction {

	public static final Boolean isCreateHtml = true;// 是否创建Html

	public static final String env = PropertyFileConfigurer.getContextProperty("environment");// 环境变量(Local,Test,Server)
	public static final String charset = "UTF-8";// 编码
	public static final String debugType = "0";// 调试类型(0:不显示调试信息,1:显示关键调试信息,2:显示全部调试信息)

	public static int plId = getPlId();// 平台Id
	public static String jqAccount = getJqAccount();// 鉴权账号
	public static String jqPassword = getJqPassword();// 鉴权密码
	public static String shNo = getShNo();// 商户编号(金投不需要)
	public static String shKey = getShKey();// 商户密钥(金投不需要)

	public static String urlQzSecYmd = getUrlQzSecYmd();// 前置链接
	public static String serverQzWeb = getQzWeb();

	// 获取平台Id
	public static int getPlId() {
		if (env.equals("Test")) {
			return 282;
		} else if (env.equals("Server")) {
			return 333;
		}
		return 0;
	}

	// 获取鉴权账号
	public static String getJqAccount() {
		if (env.equals("Test")) {
			return "test@p2p";
		} else if (env.equals("Server")) {
			return "shanxingchuangtou";
		}
		return "";
	}
	
	// 获取鉴权密码
	public static String getJqPassword() {
		if (env.equals("Test")) {
			return "1d430d9213ccdabbfd9b0d13edb15438";
		} else if (env.equals("Server")) {
			return "ea9e8069cd09c8c5582598f52ab936e3";
		}
		return "";
	}

	// 获取鉴权签名
	public static String getJqSign(long timestamp) {
		// MD5(username + ":" + password + ":" + timestamp)
		return MD5Encrypt.MD5(getJqAccount() + ":" + getJqPassword() + ":" + timestamp, charset);
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
			urlQzSecYmd = "http://115.236.174.163:5190/api/v1/p2p/public/product";// 前置链接
			return urlQzSecYmd;
		} else if (env.equals("Server")) {
			urlQzSecYmd = "http://p2p.cngold.org/api/v1/p2p/public/product";// 前置链接
			return urlQzSecYmd;
		}
		return null;
	}

	// 获取商户编号
	public static String getShNo() {
		if (env.equals("Test")) {
			shNo = "";// 商户编号
			return shNo;
		} else if (env.equals("Server")) {
			shNo = "";// 商户编号
			return shNo;
		}
		return null;
	}

	// 获取商户密钥
	public static String getShKey() {
		if (env.equals("Test")) {
			shKey = "";// 商户密钥
			return shKey;
		} else if (env.equals("Server")) {
			shKey = "";// 商户密钥
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
				String value = RotuMainUtil.getSafeString(mapKeyAndValues.get(paramName));
				sb.append("  <input type=\"hidden\" name=\"" + paramName + "\" id=\"" + paramName + "\" value=\"" + value + "\" />\n");
			}
			sb.append("</form>\n");
			sb.append("<script language=\"javascript\">window.onload=function(){document.sl_form.submit();}</script>");
		}
		return sb.toString();
	}
}