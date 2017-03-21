package com.shanlin.p2p.app.service;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.shanlin.p2p.app.model.Letter;
import com.shanlin.p2p.app.model.LetterContent;

/**
 * 站内信业务
 * @author zheng xin
 * @createDate 2015年1月28日
 */
public interface LetterService {
	
	/**
	 * 发送站内信
	 * @param userId 用户id
	 * @param title 标题
	 * @param content 站内信内容
	 */
	void sendLetter(Long userId, String title, String content);
	
	/**
	 * 发送站内信
	 * @param userId 用户id
	 * @param title 标题
	 * @param letterKey 站内信模版key
	 * @param args 模版参数
	 */
	void sendLetter(Long userId, String title, String letterKey, Map<String, Object> args);

	Page<Letter> findAll(Long userId, Pageable page);

	LetterContent findContentById(Long id);
	
	void updateLetter(Letter letter);
	
	Integer getUnReadCount(Long userId);
}
