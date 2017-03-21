package com.shanlin.p2p.app.service;

import java.math.BigDecimal;
import java.util.Map;

public interface ChargeService {
	
	/**
	 * 注册第三方托管
	 * @param userId
	 * @return
	 */
	Map<String, Object> openAccount(Long userId,String type);
	
	/**
	 * 充值首页
	 * @param userId
	 * @return
	 */
	Map<String, Object> chargeIndex(Long userId);
	
	/**
	 * 充值请求
	 * @param userId
	 * @param amount
	 * @return
	 */
	Map<String, Object> opChargeApply(Long userId,BigDecimal amount,String retType);
}
