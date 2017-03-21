package com.shanlin.p2p.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shanlin.p2p.app.model.Area;
	
public interface AreaDao extends JpaRepository<Area, Long> {

	@Query("select t from Area t where t.level='SHENG'")
	List<Area> findProvinceList();
	
	@Query("select t from Area t where t.provinceId=?1 and t.level='SHI'")
	List<Area> findCityList(Long provinceId);
	
	@Query("select t from Area t where t.provinceId=?1 and t.cityId=?2 and t.level='XIAN'")
	List<Area> findCountyList(Long provinceId,Long cityId);
}
