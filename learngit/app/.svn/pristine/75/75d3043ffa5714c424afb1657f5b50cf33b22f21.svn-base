package com.shanlin.p2p.app.dao;

import org.springframework.data.jpa.repository.Query;

import com.shanlin.framework.persistence.BaseDao;
import com.shanlin.p2p.app.model.Deduction;

public interface DeductionDao extends BaseDao<Deduction, Long>{

	@Query("select c from Deduction c where c.source=?1 and c.type=?2 and c.state=1")
	Deduction[] getList(int source,int type);
}
