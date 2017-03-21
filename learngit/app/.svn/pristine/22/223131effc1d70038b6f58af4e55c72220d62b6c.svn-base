package com.shanlin.p2p.app.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shanlin.framework.communication.sms.util.SlSmsSender;
import com.shanlin.p2p.app.constant.MessageConstants;
import com.shanlin.p2p.app.service.SmsService;

public class SmsServiceImpl implements SmsService {
	
	private static final Logger log = LoggerFactory.getLogger(SmsServiceImpl.class);
	
	private SlSmsSender sender;
	
	private ThreadPoolExecutor threadPool;
	
	public SmsServiceImpl(String serverURL, String sn, String pwd){
		try {
			sender = new SlSmsSender(serverURL, sn, pwd);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	public void send(final String message, final String... phones) {
		if (message == null || message.trim().length() == 0 || phones == null
				|| phones.length <= 0) {
			return;
		}
		//使用线程发送短信
		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				StringBuilder buff = new StringBuilder();
				for (String phone : phones) {
					buff.append(phone).append(",");
				}
				buff.deleteCharAt(buff.length() - 1);
				log.info("手机号码:{}", buff.toString());
				log.info("短信内容:{}", message);
				if(MessageConstants.SMS_DEBUG){
					return;
				}
				try{
					String returnCode = sender.mdsmssend(buff.toString(), URLEncoder.encode(message, "UTF-8"));
					log.info("返回码:{}", returnCode);
				} catch(Exception e){
					log.error("发送短信异常，原因:{}", e.getMessage(), e);
				}
			}
		});
		
	}

	public ThreadPoolExecutor getThreadPool() {
		return threadPool;
	}

	public void setThreadPool(ThreadPoolExecutor threadPool) {
		this.threadPool = threadPool;
	}
	
}
