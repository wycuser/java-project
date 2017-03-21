package com.shanlin.framework.utils;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

public class FunctionUtil {
	public static String getOutTradeNo(int type, Long outTradeNo,Date date){
		String str = "";
		if(date==null)
			date=new Date();
		String dt = DateParser.format(date, "yyyyMMddHHmmss");
		if (0 == type) {
			str = "jy_";
		} else if (1 == type) {
			str = "ch_";
		} else if (2 == type) {
			str = "tx_";
		} else if (3 == type) {
			str = "txfee_";
		} else if (4 == type) {
			str = "hk_";
		} else if (5 == type) {
			str = "hkfee_";
		} else if (6 == type) {
			str = "jyfee_";
		} else if (7 == type) {
			str = "zq_";
		} else if (8 == type) {
			str = "zqfee_";
		} else if (9 == type) {
			str = "chgtgfee_";
		} else if (10 == type) {
			str = "tztgfee_";
		} else if (11 == type) {
			str = "df_";
		}else if (12 == type) {
			str = "redp_";
		}
		str = str + outTradeNo + "_" + dt;
		if (str.length() > 20) {
			str = str.substring(0, 19);
		}
		return str;
	}
	
	/**
	 * 转字符串参数模式
	 * 如：say=hello&name=tom
	 * @param paramMap
	 * @return
	 */
	public static String map2StrParm(Map<String,Object> paramMap) {
        String  buffer=new String();
        for (String key : paramMap.keySet()) {
            Object value=paramMap.get(key);
            if(value!=null&&!"".equalsIgnoreCase(value.toString())){
                buffer+=(key+"="+value);
                buffer+=("&");
            }
		}
        if(buffer.length()>0){
            buffer=buffer.substring(0,buffer.length()-1);
        }
        return buffer;
    }
	
	/**
	 * 提交表单
	 * @param response
	 * @param param 参数
	 * @param linUrl 请求地址
	 * @throws IOException
	 */
	public static void submitForm(HttpServletResponse response, Map<String, Object> param,String linUrl) throws IOException{
		StringBuilder builder = new StringBuilder();
		builder.append("<form action=\"");
		builder.append(linUrl);
		builder.append("\" method=\"post\">");
		for (String key : param.keySet()) {
			builder.append("<input type=\"hidden\" name=\"" + key
					+ "\" value=\"");
			builder.append(param.get(key));
			builder.append("\" />");
		}
		builder.append("</form>");
		builder.append("<script type=\"text/javascript\">");
		builder.append("document.forms[0].submit();");
		builder.append("</script>");
		String formUrl = builder.toString();
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(formUrl);
		response.getWriter().close();
	}
}
