package com.shanlin.p2p.app.service.impl;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.shanlin.p2p.app.constant.FeeCode;
import com.shanlin.p2p.app.dao.BidRepaymentRecordDao;
import com.shanlin.p2p.app.model.enums.BidRepaymentStatus;
import com.shanlin.p2p.app.service.BidRepaymentRecordService;

@Service
@Transactional(readOnly = true)
public class BidRepaymentRecordServiceImpl implements BidRepaymentRecordService {
	
	@Resource
	private BidRepaymentRecordDao bidRepaymentRecordDao;
	
	@Override
	public BigDecimal findShouldPriceByUserId(Long userId) {
		BigDecimal price = bidRepaymentRecordDao.findShouldPriceByUserId(userId);
		return price == null ? BigDecimal.ZERO : price;
	}

	/**
	 * 查询总投资金额
	 */
	@Override
	public BigDecimal findAllInvestByUserId(Long userId) {
		BigDecimal price = bidRepaymentRecordDao.findAllInvestByUserId(userId);
		return price == null ? BigDecimal.ZERO : price;
	}

	/**
	 * 查询未回收金额
	 */
	@Override
	public BigDecimal findWaitRecyclePrice(Long id, Long userId) {
		BigDecimal price = bidRepaymentRecordDao.findWaitRecyclePrice(id, userId,
				Lists.newArrayList(BidRepaymentStatus.WH),
				Lists.newArrayList(FeeCode.TZ_BJ, FeeCode.TZ_LX));
		return price == null ? BigDecimal.ZERO : price;
	}
	
	/**
	 * 查询已赚金额
	 * @param id
	 * @param userId
	 * @return
	 */
	@Override
	public BigDecimal findObtainPrice (Long id, Long userId){
		BigDecimal price = bidRepaymentRecordDao.findWaitRecyclePrice(id, userId,
				Lists.newArrayList(BidRepaymentStatus.YH),
				Lists.newArrayList(FeeCode.TZ_WYJ, FeeCode.TZ_LX, FeeCode.TZ_FX));
		return price == null ? BigDecimal.ZERO : price;
	}

	@Override
	public BigDecimal findAllWaitRecycleSourcePrice(Long userId) {
		BigDecimal price = bidRepaymentRecordDao.findAllWaitRecyclePrice(userId,
				Lists.newArrayList(BidRepaymentStatus.WH),
				Lists.newArrayList(FeeCode.TZ_BJ));
		return price == null ? BigDecimal.ZERO : price;
	}
	@Override
	public BigDecimal findAllWaitRecycleInterestPrice(Long userId) {
		BigDecimal price = bidRepaymentRecordDao.findAllWaitRecyclePrice(userId,
				Lists.newArrayList(BidRepaymentStatus.WH),
				Lists.newArrayList(FeeCode.TZ_LX));
		return price == null ? BigDecimal.ZERO : price;
	}
	
	@Override
	public BigDecimal findAllWaitRecycleInterestPriceV2(Long userId) {
		BigDecimal price = bidRepaymentRecordDao.findAllWaitRecyclePrice(userId,
				Lists.newArrayList(BidRepaymentStatus.WH),
				Lists.newArrayList(FeeCode.TZ_LX,FeeCode.TZ_FX));
		return price == null ? BigDecimal.ZERO : price;
	}
}
