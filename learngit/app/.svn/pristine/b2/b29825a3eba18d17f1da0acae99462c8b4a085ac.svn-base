package com.shanlin.p2p.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.shanlin.framework.persistence.BaseDao;
import com.shanlin.p2p.app.model.SurpriseLucre;

public interface SurpriseLucreDao extends BaseDao<SurpriseLucre, Long> {

	@Query("select s from SurpriseLucre s join fetch s.config where s.config.bid=?1")
	List<SurpriseLucre> findByConfigBid(Long id);

}
