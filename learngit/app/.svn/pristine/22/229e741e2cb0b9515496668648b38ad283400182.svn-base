package com.shanlin.p2p.app.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.shanlin.p2p.app.model.DeductionRecord;
import com.shanlin.p2p.app.model.InvestRecord;

public interface DeductionRecordService {

	/**
	 * 使用抵扣券
	 * @param userId 用户Id
	 * @param bid 标的Id
	 * @param tzid 投资Id
	 * @param id 抵扣券Id
	 * @throws Throwable
	 */
	void useDeduction(Long userId,Long bid, InvestRecord investRecord, Long id);

	/**
	 * 抵扣券列表
	 * @param userId 用户Id
	 * @param state 红包状态
	 * @param page 页数
	 * @param size 每页条数
	 * @return
	 */
	Map<String, Object> findDkList(Long userId, int state, int page, int size);
	
	/**
	 * 抵扣券列表
	 * @param userId 用户Id
	 * @param state 红包状态
	 * @param page 页数
	 * @param size 每页条数
	 * @return
	 */
	Map<String, Object> findDkList2(Long userId, int state, int page, int size);
	
	/**
	 * 抵扣券详情
	 * @param id 抵扣券记录Id
	 * @return
	 */
	DeductionRecord findDkRecord(Long id);
	
	/**
	 * 投资时可使用抵扣券列表
	 * @param userId 用户Id
	 * @param ranges 投资下线
	 * @param useActivity 是否用于活动 1.是 2.否
	 * @return
	 */
	List<DeductionRecord> findDkList(Long userId,BigDecimal ranges,int useActivity);
}
