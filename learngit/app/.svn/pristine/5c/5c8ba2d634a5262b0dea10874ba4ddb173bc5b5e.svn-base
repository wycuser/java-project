package com.shanlin.p2p.app.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.LockModeType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.shanlin.framework.persistence.BaseDao;
import com.shanlin.p2p.app.model.DeductionRecord;

public interface DeductionRecordDao extends BaseDao<DeductionRecord, Long>{

	@Modifying
	@Query(value="UPDATE DeductionRecord c SET c.state = ?1, c.bid = ?2,tzid=?3,c.updateTime=?4 WHERE c.id=?5")
	int updateRecord(int state,Long bid,Long tzid, Date currentTime,Long id);
	
	@Modifying
	@Query(value="UPDATE DeductionRecord c SET c.state = ?1,c.updateTime=?2 WHERE c.id=?3")
	int updateRecord(int state, Date currentTime,Long id);
	
	@Modifying
	@Query(value="UPDATE DeductionRecord c SET c.state = ?1,c.updateTime=?2 WHERE c.bid=?3")
	int updRecordByBid(int state, Date currentTime,Long bid);
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query(value="SELECT c FROM DeductionRecord c WHERE c.id=?1")
	DeductionRecord lockById(Long id);
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query(value="SELECT c FROM DeductionRecord c WHERE c.bid=?1 and c.state=2")
	List<DeductionRecord> findRecordByBid(Long bid);
	
	@Query(value="SELECT c FROM DeductionRecord c WHERE c.userId=?1 and c.state=?2")
	Page<DeductionRecord> findDkList(Long userId,int state, Pageable pageable);
	
	Page<DeductionRecord> findByUserIdAndStateIn(Long userId, List<Integer> state, Pageable pageable);
	
	@Query(value="SELECT c FROM DeductionRecord c WHERE c.userId=?1 and c.state in (2,3)")
	Page<DeductionRecord> findDkList2(Long userId, Pageable pageable);
	
	@Query(value="SELECT c FROM DeductionRecord c WHERE c.userId=?1 and c.state in (1,5)")
	Page<DeductionRecord> findDkList3(Long userId, Pageable pageable);
	
	@Query(value="SELECT c FROM DeductionRecord c WHERE c.state=1 and c.userId=?1 and c.endTime>=?2 and c.ranges<=?3 ORDER BY c.endTime ASC,c.amount DESC,c.useActivity ASC")
	List<DeductionRecord> findDkList(Long userId, Date sysDate,BigDecimal ranges);
	
	@Query(value="SELECT c FROM DeductionRecord c WHERE c.state=1 and c.userId=?1 and c.endTime>=?2 and c.ranges<=?3 and c.useActivity=?4 "
			+ "ORDER BY c.endTime ASC,c.amount DESC,c.useActivity ASC")
	List<DeductionRecord> findDkList(Long userId, Date sysDate,BigDecimal ranges,int useActivity);
	
	@Query(value="select userId from S10.deduction_record where childId=?1 limit 1", nativeQuery=true)
	Long findParentId(Long childId);
	
	@Modifying
	@Query(value="UPDATE S10.deduction_record SET state=1,updateTime=?1 WHERE childId =?2 and state=5", nativeQuery=true)
	void activation(Date currentTime,Long childId);
	
	@Query(value="select amount from S10.deduction_record WHERE id=?1 and state=1 and curdate()<=endTime", nativeQuery=true)
	BigDecimal getKyDeDuAmount(long dkId);
}
