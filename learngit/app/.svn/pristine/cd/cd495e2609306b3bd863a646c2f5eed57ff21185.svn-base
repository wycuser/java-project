package com.shanlin.p2p.app.service.impl;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shanlin.p2p.app.dao.ExperienceBidRecordDao;
import com.shanlin.p2p.app.model.ExperienceBidRecord;
import com.shanlin.p2p.app.service.ExperienceBidRecordService;

@Service
@Transactional(readOnly=true)
public class ExperienceBidRecordServiceImpl implements ExperienceBidRecordService {

	@Resource
	private ExperienceBidRecordDao experienceBidRecordDao;
	
	@Override
	public BigDecimal findInvestExperienceMoney(Long userId) {
		BigDecimal money = experienceBidRecordDao.findInvestExperienceMoney(userId);
		return money == null ? BigDecimal.ZERO : money;
	}

	@Override
	public Page<ExperienceBidRecord> findByUserId(Long userId, Pageable pageable) {
		return experienceBidRecordDao.findByUserAccountId(userId, pageable);
	}

	@Override
	public Page<Object[]> findByBid(Long id, Pageable pageable) {
		return experienceBidRecordDao.findSimpleListByBidPk(id, pageable);
	}

}
