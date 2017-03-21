package com.shanlin.p2p.app.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import com.shanlin.framework.persistence.BaseDao;
import com.shanlin.p2p.app.model.Bid;
import com.shanlin.p2p.app.model.InvestRecord;
import com.shanlin.p2p.app.model.enums.BidStatus;
import com.shanlin.p2p.app.model.enums.Judge;

public interface InvestRecordDao extends BaseDao<InvestRecord, Long> {

	@Query("select i.userAccount.loginName, i.buyingPrice, i.investTime, i.isMobileInvest,i.income from InvestRecord i where i.bid.id=?1 and i.isCancel=?2")
	Page<Object[]> findByBidAndIsCancel(Long bid, Judge isCancel, Pageable pageable);

	//获取标的的总投资次数
	Long countByBidIdAndIsCancel(Long bid, Judge isCancel);

	@Query("select sum(i.buyingPrice) from InvestRecord i where i.bid=?1 and i.isCancel=?2")
	BigDecimal findAllBuyingPriceByBid(Bid bid, Judge isCancel);

	//	@Query("select i from InvestRecord i where i.bid.status in(?1) and i.userAccount.id=?2")
	//	Page<InvestRecord> findByBidStatusAndUserAccount(List<BidStatus> bidStatus, Long userId, Pageable pageable);

	@Query("select i.id, i.bid.bidNumber, i.bid.title from InvestRecord i where i.bid.status in(?1) and i.userAccount.id=?2")
	Page<Object[]> findSimpleByBidStatusAndUserAccount(List<BidStatus> bidStatus, Long userId, Pageable pageable);

	//投标中的债权
	@Query("select a from InvestRecord a where a.bid.status in(?1) and a.userAccount.id=?2")
	Page<InvestRecord> findSimpleByBidStatusAndUserAccountV2(List<BidStatus> bidStatus, Long userId, Pageable pageable);

	// 累计投资
	@Query("select sum(i.creditPrice) from InvestRecord i where i.userAccount.id=?1")
	BigDecimal findAllCreditPriceByUser(Long userId);

	// 统计投资人数
	@Query(value = "select count(*) from (select count(*) from S62.T6250 a where a.F02=?1 group by a.F03) SYMBOLHACK", nativeQuery = true)
	Integer findCountInvestRecordUsers(Long bidId);

	// 获取P2p用户(由希财过来的用户)投资统计列表
	@Query("select a from InvestRecord a where a.userAccount.part3Bs=?1")
	Page<InvestRecord> findCsaiInvestRecords(String part3Bs, Pageable pageable);

	// 获取P2p用户(由希财过来的用户)投资统计列表
	@Query("select a from InvestRecord a where a.userAccount.part3Bs=?1 and ?2 >= a.investTime")
	Page<InvestRecord> findCsaiInvestRecordsByEndDate(String part3Bs, Date endDate, Pageable pageable);

	// 获取P2p用户(由希财过来的用户)投资统计列表
	@Query("select a from InvestRecord a where a.userAccount.part3Bs=?1 and a.investTime >= ?2")
	Page<InvestRecord> findCsaiInvestRecordsByStartDate(String part3Bs, Date startDate, Pageable pageable);

	// 获取P2p用户(由希财过来的用户)投资统计列表
	@Query("select a from InvestRecord a where a.userAccount.part3Bs=?1 and ?3 >= a.investTime and a.investTime >= ?2")
	Page<InvestRecord> findCsaiInvestRecordsByBetweenDate(String part3Bs, Date startDate, Date endDate, Pageable pageable);
	
	// 获取P2p(单个标所有投资记录信息)
	@Query("select i.userAccount.loginName, i.buyingPrice, i.investTime from InvestRecord i where i.bid.id=?1 and i.isCancel=?2")
	List<Object[]> findAllByBidAndIsCancel(Long bid, Judge isCancel);
	
	@Query("select i.id, i.userAccount.loginName, i.userAccount.id, i.buyingPrice, i.creditPrice, i.investTime from InvestRecord i where i.bid.id=?1 and i.isCancel=?2")
	Page<Object[]> findMoreByBidAndIsCancel(Long bid, Judge isCancel, Pageable pageable);
	
	// 获取用户在标的的投资总额
	@Query("select sum(i.creditPrice) from InvestRecord i where i.bid.id=?1 and i.userAccount.id=?2")
	BigDecimal findPriceByUserAndBid(Long bidId, Long userId);

	@Query(value="select F01 from S62.T6250 where F03=?1 and F07='F' LIMIT 1", nativeQuery=true)
	Long findInvestIdByUserId(Long userId);
	
	@Query(value="select F01 from S62.T6251 where F04=? LIMIT 1", nativeQuery=true)
	Long findZqzrIdByUserId(Long userId);
	
	@Query(value="SELECT count(1) FROM S62.T6250 WHERE F02<>? AND F03=? LIMIT 1", nativeQuery=true)
	int isInvest(Long bId,Long userId);
	
	@Query(value="SELECT IFNULL(sum(F04),0) FROM S62.T6250 WHERE F02=? AND F03=? LIMIT 1", nativeQuery=true)
	BigDecimal investSum(Long bId,Long userId);
	
	@Query(value="SELECT count(1) FROM S62.vip_user A INNER JOIN S61.T6110 B ON A.name=B.F02 WHERE B.F01=?", nativeQuery=true)
	int isVipUser(Long userId);
}
