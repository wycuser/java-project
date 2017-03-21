package com.shanlin.framework.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shanlin.p2p.app.constant.Constants;
import com.shanlin.p2p.app.vo.huichao.TranDataModel;

public class FunctionClientUtil {

	private static Logger logger = LoggerFactory.getLogger(FunctionClientUtil.class);

	private static String createXMLStart() {
		String xml = "<?xml version='1.0' encoding='UTF-8' standalone='yes'?><yimadai>";
		return xml;
	}

	private static String createXMLEnd() {
		String end = "</yimadai>";
		return end;
	}

	public static String getTranDataXml(TranDataModel tranDataModel) {
		String dataXml = createXMLStart();
		dataXml = dataXml + tranDataModel.createTranXML();
		dataXml = dataXml + createXMLEnd();
		String base64 = null;
		try {
			base64 = new String(new Base64().encode(dataXml.getBytes("utf-8")),
					"utf-8");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return base64;
	}

	public static Map<String, Object> sendTrade(TranDataModel tranDataModel)  {
		String xml = FunctionClientUtil.getTranDataXml(tranDataModel);
		return sendHttp(xml);
	}
	
	private static Map<String, Object> sendHttp(String xml) {
		Map<String, String> params=new HashMap<String, String>();
		params.put("transData", xml);
		return HttpUtil.create().post(Constants.PAY_URL,params).execute2Map();
	}
	
	
	
}
