package com.shanlin.p2p.app.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.shanlin.framework.persistence.BaseDao;
import com.shanlin.p2p.app.model.ExperienceAccountFlow;
import com.shanlin.p2p.app.model.enums.ExperienceSource;

public interface ExperienceAccountFlowDao extends BaseDao<ExperienceAccountFlow, Long> {

	Page<ExperienceAccountFlow> findByUserIdAndSourceNot(Long userId, ExperienceSource source, Pageable pageable);

	ExperienceAccountFlow findByUserIdAndSource(Long uid, ExperienceSource source);

}
