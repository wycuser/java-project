package com.shanlin.p2p.app.service;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 提現申請業務接口
 * 
 * @author yangjh
 *
 */
public interface WithdrawApplyService {
	
	/**
	 * 提現申請操作
	 * @param withdrawApply
	 */
	Map<String, Object> withdrawIndex(Long userId,Long bankCardId);
	
	/**
	 * 提現申請操作
	 * @param withdrawApply
	 */
	void opWithdrawApply(Long userId,Long bankCardId,BigDecimal amount,String withdrawPsd);
	
	/**
	 * 计算提现手续费
	 * @param withdrawApply
	 */
	 Map<String, Object> withdrawUserLines(Long userId,BigDecimal amount);
}
