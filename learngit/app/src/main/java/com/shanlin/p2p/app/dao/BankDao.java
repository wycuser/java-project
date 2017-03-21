package com.shanlin.p2p.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shanlin.p2p.app.model.Bank;
import com.shanlin.p2p.app.model.enums.UseStatus;

public interface BankDao extends JpaRepository<Bank, Long> {

	List<Bank> findByStatus(UseStatus status);

}
