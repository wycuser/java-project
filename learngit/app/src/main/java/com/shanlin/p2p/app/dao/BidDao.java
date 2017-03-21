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
import com.shanlin.p2p.app.model.Bid;
import com.shanlin.p2p.app.model.InvestRecord;
import com.shanlin.p2p.app.model.enums.BidStatus;
import com.shanlin.p2p.app.model.enums.UserType;

public interface BidDao extends BaseDao<Bid, Long> {

	@Query("select b from Bid b join fetch b.extend where b.status in(?1) and (b.id >=55 or b.id in(25,50)) and b.isSxbao = 'F' and b.isDfBid = 'F'")
	List<Bid> findSimpleBidByPage(List<BidStatus> statusList, Pageable pageable);

	@Query("select b from Bid b join fetch b.extend where b.status in(?1) and (b.id >=55 or b.id in(25,50)) and b.userAccount.userType=?2 and b.isSxbao = 'F' and b.isDfBid = 'F'")
	List<Bid> findSimpleBidByPageAndType(List<BidStatus> statusList, UserType userType, Pageable pageable);

	@Query("select b from Bid b where b.status in(?1) and (b.id >=55 or b.id in(25,50)) and b.isSxbao = 'F' and b.isDfBid = 'F'")
	Page<Bid> findBidByPage(List<BidStatus> statusList, Pageable pageable);

	@Query("select b from Bid b where b.status in(?1) and (b.id >=55 or b.id in(25,50)) and b.userAccount.userType=?2 and b.isSxbao = 'F' and b.isDfBid = 'F'")
	Page<Bid> findBidByPageAndType(List<BidStatus> statusList, UserType userType, Pageable pageable);

	@Query("select b.status from Bid b where b.id=?1")
	BidStatus findBidStatusById(Long id);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select b from Bid b where id=?1")
	Bid lockById(Long id);

	@Query("select b.title from Bid b where b.id=?1")
	String findTitleById(Long id);

	@Query("select b.investName from Bid b where b.id=?1")
	String findInvestNameById(Long id);

	@Query("select b.sxbaoConfig.type.id,  b.sxbaoConfig.type.name, b.sxbaoConfig.type.sellStatus, sum(b.amount-b.residueAmount) from Bid b where b.sxbaoConfig is not null group by b.sxbaoConfig.type.id,  b.sxbaoConfig.type.name, b.sxbaoConfig.type.sellStatus")
	Page<Object[]> findSxbaoType(Pageable pageable);

	// 30天内每天的平均年利率和30天内借款金额分布
	@Query(value = "select date_format(F22,'%m-%d'), sum(F06)*100, count(*), (sum(F06)*100/count(*)), sum(F05) from S62.T6230 where date_sub(curdate(), INTERVAL 31 DAY) <= date(F22) and to_days(F22) < to_days(now()) group by TO_DAYS(F22) order by F22 asc", nativeQuery = true)
	Object[][] findSumEveryDayPublishBid();

	// 平台借款期限分布
	@Query(value = "select IF(a1.F09>0,a1.F09,FLOOR(b2.F22 / 30)) as '借款期限',a1.F05 as '借款金额' from S62.T6230  a1 inner join S62.T6231 as b2 on a1.F01 = b2.F01 where a1.F20 != 'YLB' and a1.F20 !='YZF' and (a1.F01>=55 or a1.F01 in(25,50)) and a1.isSxbao = 'F' and a1.isDfBid = 'F'", nativeQuery = true)
	Object[][] findPlBorrowQxFb();

	// 30天内成交量
	@Query(value = "SELECT date_format(F06, '%m-%d') AS '投标时间',sum(F05) AS '总投标金额' FROM S62.T6250 WHERE date_sub(curdate(), INTERVAL 31 DAY) <= date(F06) AND to_days(F06) < to_days(now()) AND F08 = 'S' GROUP BY TO_DAYS(F06)", nativeQuery = true)
	Object[][] findSumEveryDayChengJiaoAmount();

	// 前一天平均年利率
	@Query(value = "select date_format(F22,'%m-%d'), sum(F06)*100, count(*), (sum(F06)*100/count(*)), sum(F05) from S62.T6230 where date_sub(curdate(), INTERVAL 2 DAY) <= date(F22) and to_days(F22) < to_days(now()) group by TO_DAYS(F22)", nativeQuery = true)
	Object[][] findOneBeforeNlv();

	// 平台总成交量
	@Query(value = "SELECT SUM(F05-F07)-134942 FROM S62.T6230", nativeQuery = true)
	BigDecimal findSumTotalChengJiaoAmount();

	// 平台总待还金额
	@Query(value = "select sum(a.F07) from S62.T6252 a where a.F09='WH'", nativeQuery = true)
	BigDecimal findSumTotalDhkAmount();

//	@Query("select a from Bid a")
//	Page<Bid> findAllBids(Pageable pageable);
	
	@Query("select a from Bid a where a.status='TBZ'")
	Page<Bid> findTbzBids(Pageable pageable);
	
	@Query("select a from Bid a where a.status='DFK'")
	Page<Bid> findDfkBids(Pageable pageable);
	
	@Query("select count(*) from Bid a where a.status='DFK'")
	Integer findCountAmountBids();
	
	@Query("select sum(a.amount) from Bid a where a.status='DFK'")
	BigDecimal findSumAmountBids();
	
	@Query("select b from Bid b where b.status in(?1) and b.publishTime>(?2) and b.publishTime<(?3) and (b.id >=55 or b.id in(25,50)) and b.isSxbao = 'F' and b.isDfBid = 'F'")
	Page<Bid> findPeriodBidByPage(List<BidStatus> statusList, Date startTime, Date endTime, Pageable pageable);
	
	@Query("select b from Bid b where b.bidNumber=?1")
	Bid findBybidNumber(String bidNumber);
	
	//begin
	
	@Query(value="SELECT b FROM InvestRecord b ")
	List<InvestRecord> getBidRate();
	
	@Modifying
	@Query(value="update S62.T6250 u set u.income=?1 where u.F01=?2",nativeQuery=true)
	int updateInRate(BigDecimal income, Long id);
	
	//end
	
	/**还款中的借款*/
	@Query(value="select b from Bid b where b.userAccount.id=?1 and b.status IN ('HKZ','YDF')")
	Page<Bid> getHkzJk(Long userId, Pageable pageable);
	
	/**已还清的借款*/
	@Query(value="select b from Bid b where b.userAccount.id=?1 and b.status='YJQ'")
	Page<Bid> getYhqJk(Long userId, Pageable pageable);

	/**根据标ID 查询还款中下期的还款金额*/
	@Query(value="SELECT IFNULL(SUM(F07),0) FROM S62.T6252 WHERE T6252.F02 = ?1 AND T6252.F09 = 'WH' AND F06 = (SELECT MIN(F06) FROM S62.T6252 WHERE T6252.F02 = ?1 AND T6252.F09 = 'WH')",nativeQuery=true)
	BigDecimal getXqhkje(Long bid);
		
	/**根据标ID 查询已还清的标的还款总额*/
	@Query(value="SELECT IFNULL(SUM(F07),0) FROM S62.T6252 WHERE F09 = 'YH' AND F02 = ?1",nativeQuery=true)
	BigDecimal getHkze(Long bid);
	
}