package com.shanlin.p2p.app.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.shanlin.p2p.app.model.InvestRecord;
import com.shanlin.p2p.app.model.RedPacketRecord;

public interface RedPacketRecordService {

	/**
	 * 发放红包
	 * @param userId 用户Id
	 * @param source 发放类型 1.手动发放 2.注册
	 * @param type 红本类型 1.投资红包 2.现金红包
	 */
	void sendRedPacket(Long userId,int source, int type);

	/**
	 * 使用红包
	 * @param userId 用户Id
	 * @param bid 标的Id
	 * @param tzid 投资Id
	 * @param id 红包Id
	 * @throws Throwable
	 */
	void useRedPacket(Long userId,Long bid, InvestRecord investRecord, Long id);

	/**
	 * 红包列表
	 * @param userId 用户Id
	 * @param state 红包状态
	 * @param page 页数
	 * @param size 每页条数
	 * @return
	 */
	Map<String, Object> findHbList(Long userId, int state, int page, int size);
	
	/**
	 * 红包列表
	 * @param userId 用户Id
	 * @param state 红包状态
	 * @param page 页数
	 * @param size 每页条数
	 * @return
	 */
	Map<String, Object> findHbList2(Long userId, int state, int page, int size);
	
	/**
	 * 红包详情
	 * @param id 红包记录Id
	 * @return
	 */
	RedPacketRecord findHbRecord(Long id);
	
	/**
	 * 投资时可使用红包列表
	 * @param userId 用户Id
	 * @param ranges 投资下线
	 * @param useActivity 是否用于活动 1.是 2.否
	 * @return
	 */
	List<RedPacketRecord> findHbList(Long userId,BigDecimal ranges,int useActivity);
}
