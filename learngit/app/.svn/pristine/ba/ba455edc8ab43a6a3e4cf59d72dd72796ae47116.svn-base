package com.shanlin.framework.controller;

import java.security.SecureRandom;

import javax.annotation.Resource;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.shanlin.framework.cache.SlMemcachedClient;

public class BaseAction {
	
	private static final SecureRandom random = new SecureRandom();
	
	@Resource
	protected SlMemcachedClient memcachedClient;
	
	/**
	 * 
	 * @param page
	 * @param size
	 * @return
	 */
	protected Pageable getPageable(int page, int size){
		return getPageable(page, size, null);
	}
	
	/**
	 * 
	 * @param page
	 * @param size
	 * @param sort
	 * @return
	 */
	protected Pageable getPageable(int page, int size, Sort sort){
		return new PageRequest(page - 1, size, sort);
	}
	
	/**
	 * 获取随机6位数
	 * @return
	 */
	protected String getRandomNumber(){
		char[] value = new char[6];
		for (int i = 0; i < 6; i++) {
		  value[i] = ((char)(random.nextInt(10) + 48));
		}
		return new String(value);
	}
	
}
