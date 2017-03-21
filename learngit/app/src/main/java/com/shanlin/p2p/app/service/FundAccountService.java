package com.shanlin.p2p.app.service;

import java.math.BigDecimal;

import com.shanlin.p2p.app.model.FundAccount;
import com.shanlin.p2p.app.model.enums.FundAccountType;

/**
 * 资产账户业务
 * @author zheng xin
 * @createDate 2015年1月28日
 */
public interface FundAccountService {
	
	/**
	 * 获取用户往来账户
	 * @param userId
	 * @return
	 */
	FundAccount findWlzhByUserId(Long userId);

	BigDecimal findBalanceByUserIdAndType(Long userId, FundAccountType type);

}
