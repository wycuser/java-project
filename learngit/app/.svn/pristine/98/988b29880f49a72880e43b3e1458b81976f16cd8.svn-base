package com.shanlin.p2p.app.dao;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import com.shanlin.framework.persistence.BaseDao;
import com.shanlin.p2p.app.model.ExperienceBidRecord;

public interface ExperienceBidRecordDao extends BaseDao<ExperienceBidRecord, Long> {

	@Query("select r.userAccount.loginName, r.investPrice, r.investTime from ExperienceBidRecord r where r.bidPk=?1")
	Page<Object[]> findSimpleListByBidPk(Long id, Pageable pageable);

	@Query("select sum(r.investPrice) from ExperienceBidRecord r where r.userAccount.id=?1")
	BigDecimal findInvestExperienceMoney(Long userId);

	Page<ExperienceBidRecord> findByUserAccountId(Long userId, Pageable pageable);

}
