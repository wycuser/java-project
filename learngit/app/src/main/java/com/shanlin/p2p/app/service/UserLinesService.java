package com.shanlin.p2p.app.service;

import java.math.BigDecimal;
import java.util.Map;


/**
 * 处理体现手续费Service
 * 
 * @author ice
 *
 */
public interface UserLinesService {

	// 处理体现手续费
	Map<String,Object> handleUserLines(Long userId, BigDecimal money);
	
	//计算提现手续费
	public Map<String, Object> withdrawleUserLines(Long userId,BigDecimal amount,String _proportion); 

}
