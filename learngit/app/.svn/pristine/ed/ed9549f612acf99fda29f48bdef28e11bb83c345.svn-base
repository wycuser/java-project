package com.shanlin.p2p.app.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import com.shanlin.framework.persistence.BaseDao;
import com.shanlin.p2p.app.model.CreditAssignmentRecord;

/**
 * 债权转让记录
 * @author yangjh
 *
 */
public interface CreditAssignmentRecordDao extends BaseDao<CreditAssignmentRecord, Long>{
	
	/** 已转出的债权 */
	@Query(value="SELECT c.creditAssignmentApply.id,c.creditAssignmentApply.creditRecord.creditNumber,c.creditAssignmentApply.creditRecord.bid.title "
			+ "FROM CreditAssignmentRecord c where c.creditAssignmentApply.creditRecord.userAccount.id=?1 ")
	Page<Object[]> findZqyzcList(Long uId, Pageable pageable);
	
	/** 已转入的债权 */
	@Query(value="SELECT c.creditAssignmentApply.id,c.creditAssignmentApply.creditRecord.creditNumber,c.creditAssignmentApply.creditRecord.bid.title "
			+ "FROM CreditAssignmentRecord c where c.userAccount.id=?1 ")
	Page<Object[]> findZqyzrList(Long uId, Pageable pageable);
	
	CreditAssignmentRecord findByCreditAssignmentApplyId(Long id);
	
	/** 已转出的债权V2 */
	@Query(value="SELECT c "
			+ "FROM CreditAssignmentRecord c where c.creditAssignmentApply.creditRecord.userAccount.id=?1 ")
	Page<CreditAssignmentRecord> findZqyzcListV2(Long uId, Pageable pageable);
	
	/** 已转入的债权V2 */
	@Query(value="SELECT c "
			+ "FROM CreditAssignmentRecord c where c.userAccount.id=?1 ")
	Page<CreditAssignmentRecord> findZqyzrListV2(Long uId, Pageable pageable);
}
