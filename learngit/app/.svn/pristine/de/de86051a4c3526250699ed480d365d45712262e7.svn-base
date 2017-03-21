package com.shanlin.p2p.app.dao;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import com.shanlin.framework.persistence.BaseDao;
import com.shanlin.p2p.app.model.ExperienceAccount;

public interface ExperienceAccountDao extends BaseDao<ExperienceAccount, Long> {

	ExperienceAccount findByUserId(Long userId);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select a from ExperienceAccount a where a.userId=?1")
	ExperienceAccount lockByUserId(Long userId);

}
