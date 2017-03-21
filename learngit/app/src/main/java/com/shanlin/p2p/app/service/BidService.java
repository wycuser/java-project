package com.shanlin.p2p.app.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.shanlin.p2p.app.model.Bid;
import com.shanlin.p2p.app.model.enums.UserType;

public interface BidService {

	List<Bid> findBidByType(UserType userType, Pageable pageable);
	
	/**
	 * 是否预约人
	 * @param bid,investName
	 * @return
	 */
	boolean isSubscribers(Long bid,String investName);
	
	/**
	 * 查询借款统计信息 
	 * @param userId
	 * @return
	 */
	Map<String, Object> getMyLoanCount(Long userId);
	
	/**
	 * 还款中的借款列表
	 * @param userId
	 * @param page
	 * @param size
	 * @return
	 */
	Map<String, Object> getHkzJk(Long userId,int page, int size);
	
	/**
	 * 已还清列表
	 * @param userId
	 * @param page
	 * @param size
	 * @return
	 */
	Map<String, Object> getYhqJk(Long userId,int page, int size);
	
	/**
	 * 还款详情
	 * @param bid
	 * @param userId
	 * @return
	 */
	Map<String, Object> loanDetail(Long bid,Long userId);
	
	/**
	 * 还款操作
	 * @param Id
	 * @param bid
	 * @param num
	 */
	void repayment(Long id,Long bid,int num);
	
	void addRepaymentStatus(Long bid,int num);
}
