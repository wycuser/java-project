package com.shanlin.p2p.app.dao;

import java.util.List;

import com.shanlin.framework.persistence.BaseDao;
import com.shanlin.p2p.app.model.SurpriseConfig;

public interface SurpriseConfigDao extends BaseDao<SurpriseConfig, Long>{

	List<SurpriseConfig> findByBidOrderByNameAsc(Long id);

}
