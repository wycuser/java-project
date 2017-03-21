package com.shanlin.p2p.app.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shanlin.p2p.app.model.BankCard;
import com.shanlin.p2p.app.model.enums.UseStatus;

public interface BankCardDao extends JpaRepository<BankCard, Long> {

	List<BankCard> findByUserIdAndStatus(Long userId, UseStatus status);
	
	@Query("select t from BankCard t where t.userId=?1 and t.status=?2")
	Page<BankCard> findBankCardPage(Long userId, UseStatus status, Pageable pageable);
	
	@Query("select t from BankCard t where t.cipherNumber=?1 and t.status=?2")
	BankCard findByCipherNumber(String cipherNumber,UseStatus status);
	
	BankCard findFirstByUserIdAndStatus(Long userId,UseStatus status);
	
	BankCard findByIdAndStatus(Long id,UseStatus status);
}
