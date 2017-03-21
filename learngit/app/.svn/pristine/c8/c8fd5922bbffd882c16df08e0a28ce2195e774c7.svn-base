package com.shanlin.p2p.app.dao;

import org.springframework.data.jpa.repository.Query;

import com.shanlin.framework.persistence.BaseDao;
import com.shanlin.p2p.app.model.RepaymentStatus;

public interface RepaymentStatusDao extends BaseDao<RepaymentStatus, Long> {

	@Query("select a from RepaymentStatus a where a.bid=?1 and a.num=?2")
	RepaymentStatus findByBidAndNum(Long bid,int num);
}
