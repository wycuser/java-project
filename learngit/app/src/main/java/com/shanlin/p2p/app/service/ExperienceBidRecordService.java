package com.shanlin.p2p.app.service;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.shanlin.p2p.app.model.ExperienceBidRecord;

public interface ExperienceBidRecordService {

	BigDecimal findInvestExperienceMoney(Long userId);

	Page<ExperienceBidRecord> findByUserId(Long userId, Pageable pageable);

	Page<Object[]> findByBid(Long id, Pageable pageable);

}
