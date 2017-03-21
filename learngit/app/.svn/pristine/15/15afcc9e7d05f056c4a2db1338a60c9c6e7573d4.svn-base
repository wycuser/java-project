package com.shanlin.p2p.duoz.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.util.MultiValueMap;

import com.shanlin.framework.config.PropertyFileConfigurer;
import com.shanlin.framework.controller.BaseAction;
import com.shanlin.p2p.rotu.util.RotuMainUtil;

/**
 * 多赚主Action类
 * 
 * @author ice
 * 
 */
public class DuozMainAction extends BaseAction {

	public static final String env = PropertyFileConfigurer.getContextProperty("environment");// 环境变量(Local,Test,Server)
	public static final String charset = "UTF-8";// 编码
	public static final String debugType = "0";// 调试类型(0:不显示调试信息,1:显示关键调试信息,2:显示全部调试信息)

	public static String serverQzApp = getQzApp();
	public static String serverQzWeb = getQzWeb();

	// 获取app链接前置
	public static String getQzApp() {
		if (env.equals("Test")) {
			return "http://58.250.171.53:8200";
		} else if (env.equals("Server")) {
			return "http://www.myshanxing.com:8888";
		}
		return null;
	}

	// 获取web链接前置
	public static String getQzWeb() {
		if (env.equals("Test")) {
			return "http://58.250.171.53:8080";
		} else if (env.equals("Server")) {
			return "http://www.myshanxing.com";
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