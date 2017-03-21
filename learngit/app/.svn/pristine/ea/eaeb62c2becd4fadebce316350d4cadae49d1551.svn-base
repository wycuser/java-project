package com.shanlin.p2p.app.service;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.shanlin.p2p.app.model.FundAccountFlow;

public interface FundAccountFlowService {
	
	/**
	 * 获取用户总收益
	 * @param id
	 * @return
	 */
	BigDecimal findLucreByAccountId(Long id);

	/**
	 * 获取用户当天收益
	 * @param id
	 * @return
	 */
	BigDecimal findCurrentDateLucreByAccountId(Long id);

	Page<FundAccountFlow> findWlzhFlowByUserId(Long userId, Pageable pageable);

	FundAccountFlow findById(Long id);

}
