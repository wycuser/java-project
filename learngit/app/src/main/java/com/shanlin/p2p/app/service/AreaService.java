package com.shanlin.p2p.app.service;

import java.util.List;
import java.util.Map;

import com.shanlin.p2p.app.model.Area;

/**
 * 区域业务接口
 * @author yangjh
 *
 */
public interface AreaService {

	/**
	 * 查找下级区域
	 * @param id
	 * @return
	 */
	List<Area> findNextLevel(Long id);
	
	Map<String,Object> getAreaList();
	
	/**
	 * 查询区域信息
	 * @param id
	 * @return
	 */
	Area findById(Long id);
}
