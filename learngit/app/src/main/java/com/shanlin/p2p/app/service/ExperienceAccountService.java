package com.shanlin.p2p.app.service;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.shanlin.p2p.app.model.ExperienceAccount;
import com.shanlin.p2p.app.model.ExperienceAccountFlow;
import com.shanlin.p2p.app.model.enums.ExperienceSource;

public interface ExperienceAccountService {

	ExperienceAccount findByUserId(Long userId);

	ExperienceAccount save(ExperienceAccount experienceAccount);
	
	ExperienceAccount findOrAddByUserId(Long userId);
	
	void addExperienceMoney(Long uid, BigDecimal money, ExperienceSource status);
	
	void addExperienceMoneyBySMRZ(Long uid);

	Page<ExperienceAccountFlow> findSourceByUserId(Long userId, Pageable pageable);
}
