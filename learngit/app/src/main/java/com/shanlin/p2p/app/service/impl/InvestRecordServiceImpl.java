package com.shanlin.p2p.app.service.impl;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shanlin.p2p.app.dao.InvestRecordDao;
import com.shanlin.p2p.app.model.InvestRecord;
import com.shanlin.p2p.app.service.InvestRecordService;

@Service
@Transactional(readOnly=true)
public class InvestRecordServiceImpl implements InvestRecordService {
	
	@Resource
	private InvestRecordDao investRecordDao;
	
	@Override
	public InvestRecord findById(Long investRecordId) {
		return investRecordDao.findOne(investRecordId);
	}

	@Override
	public BigDecimal findAllCreditPriceByUser(Long userId) {
		return investRecordDao.findAllCreditPriceByUser(userId);
	}

}
