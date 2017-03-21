package com.shanlin.p2p.app.service;

import java.math.BigDecimal;

/**
 * 
 * @author zheng xin
 * @createDate 2015年1月28日
 */
public interface BidRepaymentRecordService {
	
	/**
	 * 获取用户未还借款金额
	 * @param userId
	 * @return
	 */
	BigDecimal findShouldPriceByUserId(Long userId);

	/**
	 * 查询总投资金额
	 */
	BigDecimal findAllInvestByUserId(Long userId);

	/**
	 * 查询未回收金额
	 */
	BigDecimal findWaitRecyclePrice(Long id, Long userId);
	
	/**
	 * 查询已赚金额
	 * @param id
	 * @param userId
	 * @return
	 */
	BigDecimal findObtainPrice (Long id, Long userId);

	/**
	 * 查询所有未回收本金
	 * @param userId
	 * @return
	 */
	BigDecimal findAllWaitRecycleSourcePrice(Long userId);
	
	/**
	 * 查询所有未回收利息
	 * @param userId
	 * @return
	 */
	BigDecimal findAllWaitRecycleInterestPrice(Long userId);
	
	/**
	 * 查询所有未回收利息V2
	 * @param userId
	 * @return
	 */
	BigDecimal findAllWaitRecycleInterestPriceV2(Long userId);

}
