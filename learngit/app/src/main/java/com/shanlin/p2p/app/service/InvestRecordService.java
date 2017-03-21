package com.shanlin.p2p.app.service;

import java.math.BigDecimal;

import com.shanlin.p2p.app.model.InvestRecord;

public interface InvestRecordService {

	InvestRecord findById(Long investRecordId);
	
	BigDecimal findAllCreditPriceByUser(Long userId);

}
