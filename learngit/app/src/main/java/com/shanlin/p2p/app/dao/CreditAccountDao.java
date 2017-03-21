package com.shanlin.p2p.app.dao;

import java.math.BigDecimal;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.shanlin.framework.persistence.BaseDao;
import com.shanlin.p2p.app.model.CreditAccount;

public interface CreditAccountDao extends BaseDao<CreditAccount, Long>{
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query(value="SELECT c FROM CreditAccount c WHERE c.id=?1")
	CreditAccount lockById(Long id);

	@Modifying
	@Query(value="INSERT INTO S61.T6117 SET F02=?1,F03=?2,F04=CURRENT_TIMESTAMP(),F05=?3,F07=?4,F08=?5",nativeQuery=true)
	void saveRecord(Long userId,int feeCode,BigDecimal inAmount,BigDecimal balance,String remark);
}
