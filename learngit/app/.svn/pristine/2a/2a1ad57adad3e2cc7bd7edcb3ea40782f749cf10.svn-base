package com.shanlin.p2p.app.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import com.shanlin.framework.persistence.BaseDao;
import com.shanlin.p2p.app.model.SxbaoType;

public interface SxbaoTypeDao extends BaseDao<SxbaoType, Long> {
	
	@Query("select t.id, t.name, t.sellStatus, sum(b.amount - b.residueAmount) from SxbaoType t left join t.configs c left join c.bids b group by t.id, t.name, t.sellStatus")
	Page<Object[]> findTypes(Pageable pageable);
}
