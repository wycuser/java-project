package com.shanlin.p2p.app.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shanlin.p2p.app.dao.BidCreditRecordDao;
import com.shanlin.p2p.app.model.enums.BidStatus;
import com.shanlin.p2p.app.service.BidCreditRecordService;

@Service
@Transactional(readOnly = true)
public class BidCreditRecordServiceImpl implements BidCreditRecordService {
	
	@Resource
	private BidCreditRecordDao bidCreditRecordDao;
	
	@Override
	public BigDecimal findHoldPriceByUserId(Long userId) {
		return bidCreditRecordDao.findHoldPriceByUserId(userId);
	}
	
	@Override
	public BigDecimal findBuyingPriceByBidStatusAndUserAccountAndHoldPriceV2(List<BidStatus> bidStatus,Long userId) {
		return bidCreditRecordDao.findBuyingPriceByBidStatusAndUserAccountAndHoldPriceV2(bidStatus, userId);
	}
	
	// 获取债权转让盈亏
	@Override
	public BigDecimal findAllZrProfit(Long userId) {
		return bidCreditRecordDao.findAllZrProfit(userId);
	}

}
