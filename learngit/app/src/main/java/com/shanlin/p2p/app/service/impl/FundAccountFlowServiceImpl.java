package com.shanlin.p2p.app.service.impl;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shanlin.p2p.app.dao.FundAccountFlowDao;
import com.shanlin.p2p.app.model.FundAccountFlow;
import com.shanlin.p2p.app.model.enums.FundAccountType;
import com.shanlin.p2p.app.service.FundAccountFlowService;

@Service
@Transactional(readOnly = true)
public class FundAccountFlowServiceImpl implements FundAccountFlowService {
	
	@Resource
	private FundAccountFlowDao fundAccountFlowDao;
	
	/**
	 * 获取用户收益
	 * 利息,逾期罚息,提前还款违约金,有效推广
	 * @param id 往来帐号id
	 * @return
	 */
	@Override
	public BigDecimal findLucreByAccountId(Long id) {
		return fundAccountFlowDao.findLucreByAccountId(id);
	}
	
	/**
	 * 获取用户当天收益
	 * 利息,逾期罚息,提前还款违约金,有效推广
	 * @param id 往来帐号id
	 * @return
	 */
	@Override
	public BigDecimal findCurrentDateLucreByAccountId(Long id) {
		return fundAccountFlowDao.findCurrentDateLucreByAccountId(id);
	}

	@Override
	public Page<FundAccountFlow> findWlzhFlowByUserId(Long userId, Pageable pageable) {
		return fundAccountFlowDao.findByUserIdAndFundAccountType(userId, FundAccountType.WLZH, pageable);
	}

	@Override
	public FundAccountFlow findById(Long id) {
		return fundAccountFlowDao.findOne(id);
	}

}
