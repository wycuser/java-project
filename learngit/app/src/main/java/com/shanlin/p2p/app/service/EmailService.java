package com.shanlin.p2p.app.service;

import java.util.Map;

/**
 * 发送邮件业务层
 * @author zheng xin
 * @createDate 2015年1月30日
 */
public interface EmailService {
	
	/**
	 * 发送邮件
	 * @param subject 邮件主题
	 * @param content 内容
	 * @param toMails 收件人
	 */
	void send(String subject, String content, String... toMails);
	
	/**
	 * 发送邮件
	 * @param subject 邮件主题
	 * @param templace 模版
	 * @param args 参数
	 * @param toMails 收件人
	 */
	void send(String subject, String templace, Map<String, Object> args, String... toMails);
	
	/**
	 * 发送HTML邮件
	 * @param subject
	 * @param content
	 * @param toMails
	 */
	void sendHtml(String subject, String content, String... toMails);
	
	/**
	 * 发送带图片的HTML邮件
	 * @param subject
	 * @param content
	 * @param imgSrc
	 * @param toMails
	 */
	void sendImgHtml(String subject, String content, Map<String, String> imgSrc, String... toMails);
	
}
