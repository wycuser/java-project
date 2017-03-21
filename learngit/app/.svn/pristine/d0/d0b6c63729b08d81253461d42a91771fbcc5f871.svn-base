package com.shanlin.p2p.app.service.impl;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.shanlin.framework.controller.InterfaceResultCode;
import com.shanlin.framework.exception.ServiceException;
import com.shanlin.p2p.app.dao.AreaDao;
import com.shanlin.p2p.app.model.Area;
import com.shanlin.p2p.app.model.enums.AreaLevel;
import com.shanlin.p2p.app.service.AreaService;

@Service
public class AreaServiceImpl implements AreaService {

	@Resource
	private AreaDao areaDao;
	
	@Override
	public List<Area> findNextLevel(Long id) {
		if(id.intValue()==0)
			return areaDao.findProvinceList();
		Area vo=areaDao.findOne(id);
		if(vo!=null && vo.getLevel()==AreaLevel.SHENG)
			return areaDao.findCityList(vo.getProvinceId());
		if(vo!=null && vo.getLevel()==AreaLevel.SHI)
			return areaDao.findCountyList(vo.getProvinceId(), vo.getCityId());
		throw new ServiceException(InterfaceResultCode.ILLEGAL_REQUEST);
	}

	@Override
	public Map<String,Object> getAreaList() {
		List<Area> provinceList=areaDao.findProvinceList();
		List<Area> cityList=null;
		List<Area> countyList=null;
		
		List<String> pList=new LinkedList<String>();
		List<String> cList=null;
		List<Object> aList=null;
		
		Map<String,Object> returnMap=new LinkedHashMap<String,Object>();
		Map<String, Object> cMap=new LinkedHashMap<String, Object>();
		Map<String, Object> aMap=new LinkedHashMap<String, Object>();
		
		Map<String, Object> a_map=null;
		for (Area province : provinceList) {
			pList.add(province.getName());
			cityList=areaDao.findCityList(province.getProvinceId());
			cList=new LinkedList<String>();
			for (Area city : cityList) {
				cList.add(city.getName());
				countyList=areaDao.findCountyList(city.getProvinceId(),city.getCityId());
				aList=new LinkedList<Object>();
				for (Area county : countyList) {
					a_map=new LinkedHashMap<String, Object>();
					a_map.put("id", county.getId());
					a_map.put("name", county.getName());
					aList.add(a_map);
				}
				aMap.put(province.getName()+"-"+city.getName(), aList);
			}
			cMap.put(province.getName(), cList);
		}
		returnMap.put("p", pList);
		returnMap.put("c", cMap);
		returnMap.put("a", aMap);
		return returnMap;
	}

	@Override
	public Area findById(Long id) {
		return areaDao.findOne(id);
	}
}
