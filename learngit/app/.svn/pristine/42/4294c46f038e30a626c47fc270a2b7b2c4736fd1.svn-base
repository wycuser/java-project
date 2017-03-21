package com.shanlin.p2p.wangd.controller;

import com.shanlin.framework.config.PropertyFileConfigurer;
import com.shanlin.framework.controller.BaseAction;

/**
 * 网贷主Action类
 * 
 * @author ice
 * 
 */
public class WangdMainAction extends BaseAction {

	public static final String env = PropertyFileConfigurer.getContextProperty("environment");// 环境变量(Local,Test,Server)
	public static final String charset = "UTF-8";// 编码
	public static final String debugType = "0";// 调试类型(0:不显示调试信息,1:显示关键调试信息,2:显示全部调试信息)

	public static String serverQzWeb = getQzWeb();

	// 获取web链接前置
	public static String getQzWeb() {
		if (env.equals("Test")) {
			return "http://58.250.171.53:8080";
		} else if (env.equals("Server")) {
			return "http://www.myshanxing.com";
		}
		return null;
	}
}