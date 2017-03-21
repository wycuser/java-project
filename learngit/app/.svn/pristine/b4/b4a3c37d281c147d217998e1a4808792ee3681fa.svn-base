package com.shanlin.p2p.app.dao;

import java.util.List;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import com.shanlin.framework.persistence.BaseDao;
import com.shanlin.p2p.app.model.ExperienceBid;
import com.shanlin.p2p.app.model.enums.BidStatus;

public interface ExperienceBidDao extends BaseDao<ExperienceBid, Long> {

	ExperienceBid findFirstByStatusInOrderByStatusAscCreateTimeDesc(List<BidStatus> status);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select e from ExperienceBid e where e.id=?1")
	ExperienceBid lockById(Long id);

}
