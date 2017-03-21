package com.shanlin.p2p.app.service;

import java.math.BigDecimal;
import java.util.List;

import com.shanlin.p2p.app.model.enums.BidStatus;

/**
 * 标的债券记录业务
 * @author zheng xin
 * @createDate 2015年1月28日
 */
public interface BidCreditRecordService {

	/**
	 * 获取用户持有债权总额
	 * @param userId
	 * @return
	 */
	BigDecimal findHoldPriceByUserId(Long userId);
	
	
	// 回收中的债权购买金额合计 
	BigDecimal findBuyingPriceByBidStatusAndUserAccountAndHoldPriceV2(List<BidStatus> bidStatus,Long userId);
	
	// 获取债权转让盈亏
	BigDecimal findAllZrProfit(Long userId);
}