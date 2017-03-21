package com.shanlin.p2p.app.dao;

import java.math.BigDecimal;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import com.shanlin.p2p.app.model.FundAccount;
import com.shanlin.p2p.app.model.enums.FundAccountType;

public interface FundAccountDao extends JpaRepository<FundAccount, Long>{
	
	FundAccount findByUserIdAndType(Long userId, FundAccountType type);
	
	@Query("select f.balance from FundAccount f where f.userId=?1 and f.type=?2")
	BigDecimal findBalanceByUserIdAndType(Long userId, FundAccountType type);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select f from FundAccount f where f.userId=?1 and f.type=?2")
	FundAccount lockFundAccountByUserIdAndType(Long userId, FundAccountType wlzh);
}
