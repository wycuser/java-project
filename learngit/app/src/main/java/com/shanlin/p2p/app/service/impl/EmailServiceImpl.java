package com.shanlin.p2p.app.service.impl;

import java.io.File;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ThreadPoolExecutor;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.shanlin.framework.utils.Formater;
import com.shanlin.p2p.app.constant.Constants;
import com.shanlin.p2p.app.service.EmailService;

public class EmailServiceImpl implements EmailService {
	
	private static final Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);
	
	private JavaMailSender javaMailSender;
	
	private ThreadPoolExecutor threadPool;
	
	private String fromEmail;
	
	@Override
	public void send(final String subject, final String content, final String... toMails){
		if(subject== null || content == null || toMails == null || toMails.length < 1){
			return;
		}
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				SimpleMailMessage mail = new SimpleMailMessage();
				mail.setFrom(Constants.SYS_SITE_NAME + "<" + fromEmail + ">");
				mail.setTo(toMails);
				mail.setSubject(subject);
				mail.setText(content);
				try {
					javaMailSender.send(mail);
				} catch (Exception e) {
					log.error("发送邮件异常，原因:{}", e.getMessage(), e);
				}
			}
		});
	}
	
	@Override
	public void send(String subject, String templace, Map<String, Object> args, String... toMails){
		if(templace == null){
			return;
		}
		if(args != null && args.size() > 0){
			templace = Formater.formatString(templace, args);
		}
		send(subject, templace, toMails);
	}
	
	@Override
	public void sendHtml(final String subject, final String content, final String... toMails){
		if(subject== null || content == null || toMails == null || toMails.length < 1){
			return;
		}
//		threadPool.execute(new Runnable() {
//			@Override
//			public void run() {
		        // 建立邮件消息,发送简单邮件和html邮件的区别  
		        MimeMessage mailMessage = javaMailSender.createMimeMessage();
		        MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage);  
				try {
					// 设置收件人，寄件人  
					messageHelper.setFrom(Constants.SYS_SITE_NAME + "<" + fromEmail + ">");
					messageHelper.setBcc(toMails);
					messageHelper.setSubject(subject);
					// true 表示启动HTML格式的邮件  
					messageHelper.setText(content, true);  
					javaMailSender.send(mailMessage);
				} catch (Exception e) {
					log.error("发送邮件异常，原因:{}", e.getMessage(), e);
				}
//			}
//		});
	}
	
	@Override
	public void sendImgHtml(final String subject, final String content, final Map<String, String> imgSrc, final String... toMails){
		if(subject== null || content == null || toMails == null || toMails.length < 1){
			return;
		}  
//		threadPool.execute(new Runnable() {
//			@Override
//			public void run() {
		        // 建立邮件消息,发送简单邮件和html邮件的区别  
		        MimeMessage mailMessage = javaMailSender.createMimeMessage();
				try {
					// 注意这里的boolean,等于真的时候才能嵌套图片，在构建MimeMessageHelper时候，所给定的值是true表示启用，  
					MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true);  
					// 设置收件人，寄件人  
					messageHelper.setFrom(Constants.SYS_SITE_NAME + "<" + fromEmail + ">");
					messageHelper.setBcc(toMails);
					messageHelper.setSubject(subject);
					// true 表示启动HTML格式的邮件  
					messageHelper.setText(content, true);
					for (Entry<String, String> entry : imgSrc.entrySet()) {
				        messageHelper.addInline(entry.getKey(), new FileSystemResource(new File(entry.getValue())));
					}
					javaMailSender.send(mailMessage);
				} catch (Exception e) {
					log.error("发送邮件异常，原因:{}", e.getMessage(), e);
				}
//			}
//		});
	}
	
	public JavaMailSender getJavaMailSender() {
		return javaMailSender;
	}

	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	public ThreadPoolExecutor getThreadPool() {
		return threadPool;
	}

	public void setThreadPool(ThreadPoolExecutor threadPool) {
		this.threadPool = threadPool;
	}

	public String getFromEmail() {
		return fromEmail;
	}

	public void setFromEmail(String fromEmail) {
		this.fromEmail = fromEmail;
	}

}
