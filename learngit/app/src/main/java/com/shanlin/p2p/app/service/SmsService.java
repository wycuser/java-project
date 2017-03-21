package com.shanlin.p2p.app.service;

/**
 * 短信发送业务
 * @author zheng xin
 * @createDate 2015年1月30日
 */
public interface SmsService {
	
	/**
	 * 发送短信
	 * @param message 短信内容
	 * @param phones 多个手机号码
	 */
	void send(final String message, final String... phones);
}
