package com.shanlin.p2p.csai.service;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Pageable;

/**
 * 获取P2p服务接口
 * 
 * @author ice
 *
 */
public interface LqGetP2pService {

	// 获取P2p
	public Map<String, Object> getP2p(Long bidId);

	// 获取P2p用户(由希财过来的用户)列表
	public Map<String, Object> getP2pUserList(Date startDate, Date endDate, Pageable pageable, HttpServletRequest request);

	// 获取P2p用户(由希财过来的用户)投资统计列表
	public Map<String, Object> getP2pUserInveList(Date startDate, Date endDate, Pageable pageable);
}