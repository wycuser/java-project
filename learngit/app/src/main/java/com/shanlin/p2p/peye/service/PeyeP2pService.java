package com.shanlin.p2p.peye.service;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 天眼P2p服务接口
 * 
 * @author ice
 *
 */
public interface PeyeP2pService {

	Map<String, Object> getP2pInfors(Integer page, Integer pageSize, Integer statusForm, String startTime, String endTime);
	LinkedHashMap<String, Object> getP2pInves(Integer page, Integer pageSize, Long id);
}