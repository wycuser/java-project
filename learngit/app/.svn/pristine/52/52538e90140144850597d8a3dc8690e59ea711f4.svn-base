package com.shanlin.p2p.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.shanlin.p2p.app.service.EmailService;
import com.shanlin.p2p.app.service.SmsService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:spring/applicationContext*.xml"})
@ActiveProfiles("development")
public class MessageTest {

	@Resource
	private EmailService emailService;
	
	@Resource
	private SmsService smsService;
	
	@Test
	public void testEmail() throws InterruptedException{
		emailService.send("注册成功", "恭喜您在善行创投注册成功", "362117444@qq.com");
		Thread.sleep(5000l);
	}
	
}
