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
import com.shanlin.p2p.app.model.BidRepaymentRecord;
import com.shanlin.p2p.app.model.enums.BidRepaymentStatus;

public interface BidRepaymentRecordDao extends BaseDao<BidRepaymentRecord, Long>{
	
	@Query("select sum(r.amount) from BidRepaymentRecord r where r.repaymentUserId=?1 and r.status='WH'")
	BigDecimal findShouldPriceByUserId(Long id);
	
	/**
	 * 查询用户逾期未还记录条数
	 * @param id
	 * @return
	 */
	@Query("select count(r) from BidRepaymentRecord r where r.repaymentUserId=?1 and r.shouldDate < CURRENT_DATE and r.status='WH'")
	Integer findTimeLimitRecordCount(Long id);

	@Query("select sum(r.amount) from BidRepaymentRecord r where r.gatherUserId=?1")
	BigDecimal findAllInvestByUserId(Long userId);

	@Query("select sum(r.amount) from BidRepaymentRecord r where r.creditId=?1 and r.gatherUserId=?2 and r.status in(?3) and r.feeCode in(?4)")
	BigDecimal findWaitRecyclePrice(Long id, Long userId, List<BidRepaymentStatus> statusList, List<Integer> feeCodeList);

	@Query("select sum(r.amount) from BidRepaymentRecord r where r.gatherUserId=?1 and r.status in(?2) and r.feeCode in(?3)")
	BigDecimal findAllWaitRecyclePrice(Long userId, List<BidRepaymentStatus> newArrayList, List<Integer> feeCodeList);
	
	/**
	 * 获取标的还款计划
	 * @param bid
	 * @return
	 */
	@Query("select sum(r.amount), r.shouldDate, r.status, r.repaymentTime, r.feeCode, r.issue from BidRepaymentRecord r where r.bid.id = ?1 group by r.shouldDate, r.feeCode")
	List<Object[]> findRepaymentPlanByBid(Long bid);
	
	/**获取待收本息*/
	@Query(value="SELECT IFNULL(SUM(F07),0) FROM S62.T6252 WHERE F11 = ?1 AND T6252.F05 IN ('7002','7001') AND T6252.F09 = 'WH'",nativeQuery=true)
	BigDecimal findDsbxById(Long zqId);
	
	/**根据债权ID查询逾期记录条数*/
	@Query("select count(r) from BidRepaymentRecord r where r.creditId=?1 and r.shouldDate < CURRENT_DATE and r.status='WH'")
	Integer findRecordCountByCreditId(Long id);
	
	/**根据债权ID查询标还款记录还款日期*/
	@Query(value="SELECT DISTINCT F08 FROM S62.T6252 WHERE T6252.F08 >= CURRENT_DATE() AND T6252.F09 = 'WH' AND T6252.F11 = ? ORDER BY F06 ASC limit 1 ",nativeQuery=true)
	Date findEndDateByCreditId(Long id);
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select b from BidRepaymentRecord b where b.creditId=?1")
	List<BidRepaymentRecord> LockByCreditId(Long id);
	
	/**修改还款计划*/
	@Modifying
	@Query(value="UPDATE S62.T6252 SET F04 = ?1, F11 = ?2 WHERE F01 in (?3) ",nativeQuery=true)
	void updGatherUserIdAndCreditIdByIds(Long gatherUserId,Long creditId,List<Long> ids);
	
	/**获取回帐查询列表**/
	@Query("select a.bid.id, a.bid.title, a.shouldDate, sum(a.amount), a.id, a.creditId, a.issue from BidRepaymentRecord a where a.gatherUserId=?1 and a.feeCode in (?2) and a.status=?3 group by a.bid.id,a.issue,a.creditId")
	Page<Object[]> findBackAccountListByType(Long gatherUserId, List<Integer> listFeeCodes, BidRepaymentStatus bidRepaymentStatus, Pageable pageable);
	
	/**获取单条回帐查询详情**/
	@Query("select a.bid.id, a.bid.title, a.issue, a.shouldDate, sum(a.amount), a.id, a.creditId, a.repaymentUserId, a.bid.status from BidRepaymentRecord a where a.gatherUserId=?1 and a.bid.id=?2 and a.creditId=?3 and a.issue=?4 and a.feeCode in (?5) and a.status=?6 group by a.bid.id,a.issue,a.creditId")
	Object[][] findSingleBackAccountDetail(Long userId, Long bidId, Long creditId, int issue, List<Integer> listFeeCodes, BidRepaymentStatus bidRepaymentStatus);
	
	@Query("select sum(r.amount) from BidRepaymentRecord r where r.creditId=?1 and r.status in(?2) and r.feeCode in(?3)")
	BigDecimal findDsbxPrice(Long id, List<BidRepaymentStatus> statusList, List<Integer> feeCodeList);
	
	/**成功借款金额*/
	@Query(value="SELECT IFNULL(SUM(F05-F07),0) FROM S62.T6230 WHERE F02 = ? AND F20 IN ('HKZ','YDF','YJQ')",nativeQuery=true)
	BigDecimal getJkje(Long userId);
	
	/**近30天待还*/
	@Query(value="SELECT IFNULL(SUM(F07),0) FROM S62.T6252 WHERE F03 = ? AND F09 = 'WH' AND F05 IN (7001,7002,7004,7005) AND F08>=CURRENT_DATE() AND F08<=date_add(CURRENT_DATE(), interval 30 day)",nativeQuery=true)
	BigDecimal get30dh(Long userId);
	
	/**逾期金额*/
	@Query(value="SELECT IFNULL(SUM(F07),0) FROM S62.T6252 WHERE F03 = ? AND DATEDIFF(CURRENT_DATE(),F08)>0 AND F09 = 'WH' AND F05 IN (7001,7002,7004,7005)",nativeQuery=true)
	BigDecimal getYqje(Long userId);
	
	/**未还金额*/
	@Query(value="SELECT IFNULL(SUM(F07),0) FROM S62.T6252 WHERE F03 = ? AND F09 = 'WH' AND F05 IN (7001,7002,7004,7005)",nativeQuery=true)
	BigDecimal getWhje(Long userId);
	
	/**还款详情列表*/
	@Query(value="SELECT F06,IFNULL(SUM(F07),0),F08 FROM S62.T6252 WHERE F02 = ?1 AND F03=?2 GROUP BY F06",nativeQuery=true)
	Object[][] loanDetail(Long bid,Long userId);
	
	/**查询逾期天数*/
	@Query(value="SELECT IFNULL(MAX(DATEDIFF(CURDATE(),F08)),0) FROM S62.T6252 WHERE F02=?1 AND F06=?2 AND F09='WH'",nativeQuery=true)
	int getOverduePay(Long bid,int num);
	
	/**查询之前期数是否还请*/
	@Query(value="SELECT COUNT(1) FROM S62.T6252 WHERE F02=?1 AND F06<?2 AND F09='WH'",nativeQuery=true)
	int repaymentRecord(Long bid,int num);
	
	/**未还记录*/
	@Query(value="SELECT F01 FROM S62.T6252 WHERE F02 = ?1 AND F06 = ?2 AND F09 = 'WH' ORDER BY F05 DESC",nativeQuery=true)
	Long[] getWHRecordIds(Long bid,int num);
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select b from BidRepaymentRecord b where b.id=?1")
	BidRepaymentRecord LockById(Long id);
	
	/**查询本期是否还完,更新下次还款日期*/
	@Query(value="SELECT COUNT(*) FROM S62.T6252 WHERE F02 = ?1 AND F06 = ?2 AND (F09 = 'WH' OR F09 = 'HKZ')",nativeQuery=true)
	int whCount(Long bid,int num);
	
	/**下个还款日*/
	@Query(value="SELECT F08 FROM S62.T6252 WHERE F02 = ? AND F06 = ? LIMIT 1",nativeQuery=true)
	Date nextDate(Long bid,int num);
	
	/**查询标剩余期数*/
	@Query(value="SELECT COUNT(*) FROM S62.T6252 WHERE F02 = ? AND (F09 = 'WH' OR F09 = 'HKZ')",nativeQuery=true)
	int remainNum(Long bid);
	
	/**判断标是否逾期*/
	@Query(value="SELECT count(1) FROM S62.T6252 WHERE (T6252.F05 = 7001 OR T6252.F05=7002) AND T6252.F08 < CURDATE()  AND T6252.F02 = ?  AND (T6252.F09 = 'WH' OR T6252.F09='HKZ') LIMIT 1",nativeQuery=true)
	int overdueCount(Long bid);
}
