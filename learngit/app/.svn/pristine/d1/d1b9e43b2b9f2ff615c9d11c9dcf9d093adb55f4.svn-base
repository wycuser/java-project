package com.shanlin.p2p.rotu.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.util.MultiValueMap;

import com.shanlin.framework.config.PropertyFileConfigurer;
import com.shanlin.framework.controller.BaseAction;
import com.shanlin.p2p.rotu.util.RotuMainUtil;

/**
 * 融途主Action类
 * 
 * @author ice
 * 
 */
public class RotuMainAction extends BaseAction {

	public static final Boolean isCreateHtml = true;// 是否创建Html

	public static final String env = PropertyFileConfigurer.getContextProperty("environment");// 环境变量(Local,Test,Server)
	public static final String charset = "UTF-8";// 编码
	public static final String debugType = "0";// 调试类型(0:不显示调试信息,1:显示关键调试信息,2:显示全部调试信息)

	public static int danganId = getPlId();// 融途网档案Id(平台Id)
	public static String shNo = getShNo();// 商户编号(融途不需要)
	public static String shKey = getShKey();// 商户密钥(融途不需要)

	public static String urlQzSecYmd = getUrlQzSecYmd();// 前置链接
	public static String serverQzWeb = getQzWeb();

	// 获取平台Id
	public static int getPlId() {
		if (env.equals("Test")) {
			return 1783;
		} else if (env.equals("Server")) {
			return 1783;
		}
		return 0;
	}

	// 获取web链接前置
	public static String getQzWeb() {
		if (env.equals("Test")) {
			urlQzSecYmd = "http://58.250.171.53:8080";// 前置链接
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
			urlQzSecYmd = "http://shuju.erongtu.com/api/test";// 前置链接
			return urlQzSecYmd;
		} else if (env.equals("Server")) {
			urlQzSecYmd = "http://shuju.erongtu.com/api/borrow";// 前置链接
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