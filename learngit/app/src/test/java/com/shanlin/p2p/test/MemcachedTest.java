package com.shanlin.p2p.test;

import java.security.SecureRandom;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.shanlin.framework.cache.SlMemcachedClient;
import com.shanlin.p2p.app.constant.MemcachedKeyConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:spring/applicationContext*.xml"})
@ActiveProfiles("development")
public class MemcachedTest {
	
	@Resource
	private SlMemcachedClient memcachedClient;
	
	@Test
	public void test(){
//		memcachedClient.set("test.memcached", 60, "haha");
		System.out.println(memcachedClient.get(MemcachedKeyConfig.LOGIN_TOKEN.getPrefix() + 69));
	}
	private final SecureRandom random = new SecureRandom();
	
	@Test
	public void test1(){
		System.out.println(getCode());
		System.out.println(getCode());
		System.out.println(getCode());
		System.out.println(getCode());
		System.out.println((char) 48);
	}
	
	private String getCode(){
		int length = 6;
		char[] value = new char[length];
		for (int i = 0; i < length; i++) {
		  value[i] = ((char)(this.random.nextInt(10) + 48));
		}
		return new String(value);
	}
}
