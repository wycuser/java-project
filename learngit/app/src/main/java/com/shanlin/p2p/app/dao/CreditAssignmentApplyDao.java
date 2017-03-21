package com.shanlin.p2p.app.dao;

import javax.persistence.LockModeType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import com.shanlin.framework.persistence.BaseDao;
import com.shanlin.p2p.app.model.CreditAssignmentApply;
import com.shanlin.p2p.app.model.enums.CreditAssignmentStatus;
import com.shanlin.p2p.app.model.enums.Judge;

public interface CreditAssignmentApplyDao extends BaseDao<CreditAssignmentApply, Long>{

	@Query("select a from CreditAssignmentApply a where a.status=?1 and a.creditRecord.isMakeOverNow=?2 and a.creditRecord.holdPrice > 0")
	Page<CreditAssignmentApply> findByStatusAndCreditRecordIsMakeOverNow(CreditAssignmentStatus status, Judge isMakeOverNow, Pageable pageable);
	
	@Query(value="SELECT * FROM S62.T6260 a LEFT JOIN S62.T6251 b ON a.F02 = b.F01 WHERE a.F07=?1 AND b.F08=?2 AND b.F07 > 0 ORDER BY a.F05 DESC LIMIT 1", nativeQuery=true)
	CreditAssignmentApply findFirstByStatusAndCreditRecordIsMakeOverNow(CreditAssignmentStatus status, Judge isMakeOverNow);
	
	CreditAssignmentApply findByCreditRecordId(Long id);
	
	/** 转让中的债权 */
	@Query("select id,c.creditRecord.creditNumber,c.creditRecord.bid.title from CreditAssignmentApply c where c.status in('ZRZ','DSH') and c.creditRecord.userAccount.id=?1 ")
	Page<Object[]> findZqzrzList(Long uId, Pageable pageable);
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select c from CreditAssignmentApply c where c.id=?1")
	CreditAssignmentApply lockById(Long id);
	
	/** 转让中的债权 */
	@Query("select c from CreditAssignmentApply c where c.status in('ZRZ','DSH') and c.creditRecord.userAccount.id=?1 ")
	Page<CreditAssignmentApply> findZqzrzListV2(Long uId, Pageable pageable);
}
