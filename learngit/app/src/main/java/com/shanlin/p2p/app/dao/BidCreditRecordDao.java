package com.shanlin.p2p.app.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.LockModeType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import com.shanlin.framework.persistence.BaseDao;
import com.shanlin.p2p.app.model.BidCreditRecord;
import com.shanlin.p2p.app.model.enums.BidStatus;

public interface BidCreditRecordDao extends BaseDao<BidCreditRecord, Long>{
	
	/**
	 * 使用用户id获取持有债权金额
	 * @param userId
	 * @return
	 */
	@Query("select sum(u.holdPrice) from BidCreditRecord u where u.userAccount.id=?1")
	BigDecimal findHoldPriceByUserId(Long userId);
	
	@Query("select u.id, u.creditNumber, u.bid.title from BidCreditRecord u where u.bid.status in(?1) and u.userAccount.id=?2")
	Page<Object[]> findSimpleByBidStatusAndUserAccount(List<BidStatus> bidStatus, Long userId, Pageable pageable);
	
	@Query("select u.id, u.creditNumber, u.bid.title from BidCreditRecord u where u.bid.status in(?1) and u.userAccount.id=?2 and u.holdPrice > 0")
	Page<Object[]> findSimpleByBidStatusAndUserAccountAndHoldPrice(List<BidStatus> bidStatus, Long userId, Pageable pageable);

	@Query("select u from BidCreditRecord u join fetch u.bid where u.id=?1 and u.userAccount.id=?2")
	BidCreditRecord findByIdAndUserAccountId(Long id, Long userId);
	
	
	/** 可转让的债权 */
	@Query("select b.id,b.creditNumber,b.bid.title from BidCreditRecord b where b.userAccount.id=?1 and b.bid.status='HKZ' and b.isMakeOverNow='F' "
			+ "and b.bid.extend.overdue='F' and ?2 < b.bid.extend.nextRepayDate "
			+ "and b.holdPrice > 0 and ?3 >= b.createDate and b.bid.isSxbao='F'")
	Page<Object[]> findZqkzcList(Long uId,Date date,Date date2, Pageable pageable);
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select u from BidCreditRecord u where u.id=?1")
	BidCreditRecord lockById(Long id);
	
	@Query("select count(u.id) from BidCreditRecord u where u.investRecordId=?1")
	int countByInvestRecordId(Long investRecordId);
		
	//回收中的债权
	@Query("select u from BidCreditRecord u where u.bid.status in(?1) and u.userAccount.id=?2 and u.holdPrice > 0")
	Page<BidCreditRecord> findSimpleByBidStatusAndUserAccountAndHoldPriceV2(List<BidStatus> bidStatus, Long userId, Pageable pageable);
	
	//已结清的债权
	@Query("select u from BidCreditRecord u where u.bid.status in(?1) and u.userAccount.id=?2")
	Page<BidCreditRecord> findSimpleByBidStatusAndUserAccountV2(List<BidStatus> bidStatus, Long userId, Pageable pageable);
	
	/** 可转让的债权V2 */
	@Query("select b from BidCreditRecord b where b.userAccount.id=?1 and b.bid.status='HKZ' and b.isMakeOverNow='F' "
			+ "and b.bid.extend.overdue='F' and ?2 < b.bid.extend.nextRepayDate "
			+ "and b.holdPrice > 0 and ?3 >= b.createDate and b.bid.isSxbao='F'")
	Page<BidCreditRecord> findZqkzcListV2(Long uId,Date date,Date date2, Pageable pageable);
	
	// 回收中的债权购买金额合计
	@Query("select sum(u.buyingPrice) from BidCreditRecord u where u.bid.status in(?1) and u.userAccount.id=?2 and u.holdPrice > 0")
	BigDecimal findBuyingPriceByBidStatusAndUserAccountAndHoldPriceV2(List<BidStatus> bidStatus, Long userId);
	
	// 获取债权转让盈亏
	@Query("SELECT sum(a.zrProfit) from CreditAssignmentStatistics a where a.id=?1")
	BigDecimal findAllZrProfit(Long userId);
	
	@Query("select u from BidCreditRecord u where u.creditNumber=?1")
	BidCreditRecord findByCreditNumber(String creditNumber);
}
