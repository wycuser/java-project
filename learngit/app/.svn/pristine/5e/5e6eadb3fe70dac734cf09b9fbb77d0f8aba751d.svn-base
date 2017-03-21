package com.shanlin.p2p.app.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.shanlin.p2p.app.model.Bid;
import com.shanlin.p2p.app.model.BidExtend;
import com.shanlin.p2p.app.model.SurpriseConfig;
import com.shanlin.p2p.app.model.SurpriseLucre;
import com.shanlin.p2p.app.model.SxbaoConfig;
import com.shanlin.p2p.app.model.SxbaoType;
import com.shanlin.p2p.app.model.UserAccount;
import com.shanlin.p2p.app.model.enums.BidStatus;

public interface FinancingService {
	
	Map<String, Object> findBidOrAssignmentByType(int type, int page, int size);
	
	Bid findOneBid(Long id);

	BidExtend findBidExtendById(Long id);

	BidStatus findBidStatusById(Long id);

	Long invest(Long id, UserAccount user, BigDecimal money,Long hbId, Long dkId);
	
	Long invest(Long id, UserAccount user, BigDecimal money);

	Page<Object[]> findInvestRecordBy(Long bid, Pageable pageable);

	Long countByBid(Long bid);
	
	BigDecimal findAllBuyingPriceByBid(Bid bid);
	
	List<SurpriseLucre> findSurpriseLucreByBid(Long id);

	String findTitleByBid(Long id);

	List<Object[]> findRepaymentPlanByBid(Long id);

	List<SurpriseConfig> findSurpriseConfigByBid(Long id);

	Map<String, Object> findSxbaoTypes(Pageable pageable);

	List<Map<String, Object>> findSxbaoByTypeId(Long typeId);
	
	SxbaoConfig findSxbaoConfigById(Long id);

	SxbaoType findOneSxbaoType(Long id);
}
