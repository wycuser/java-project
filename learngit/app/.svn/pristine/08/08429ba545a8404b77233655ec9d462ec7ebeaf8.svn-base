package com.shanlin.p2p.app.dao;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import com.shanlin.framework.persistence.BaseDao;
import com.shanlin.p2p.app.model.BidExtend;

public interface BidExtendDao extends BaseDao<BidExtend, Long> {
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select b from BidExtend b where b.id=?1")
	BidExtend LockById(Long id);
	
}
