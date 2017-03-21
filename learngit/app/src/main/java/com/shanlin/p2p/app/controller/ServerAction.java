package com.shanlin.p2p.app.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shanlin.p2p.app.constant.Constants;
import com.shanlin.p2p.app.dao.AppVersionDao;
import com.shanlin.p2p.app.dao.SysConstantDao;
import com.shanlin.p2p.app.model.AppVersion;
import com.shanlin.p2p.app.service.AreaService;

/**
 * 
 * @author zheng xin
 * @createDate 2015年1月12日
 */
@Controller
@RequestMapping("/server")
public class ServerAction {
	
	@Resource
	private AppVersionDao appVersionDao;
	
	@Resource
	private AreaService areaService;
	
	@Resource
	private SysConstantDao sysConstantDao;
	
	/**
	 * 获取后台时间
	 * @return 当前时间戳
	 */
	@RequestMapping("time")
	public @ResponseBody long time(){
		return System.currentTimeMillis();
	}
	
	/**
	 * 获取安卓版本
	 * @return
	 */
	@RequestMapping("androidVersion")
	@ResponseBody
	public AppVersion androidVersion(){
		AppVersion appVersion = appVersionDao.findFirstByTypeOrderByVersionCodeDesc("android");
		return appVersion;
	}
	
	/**
	 * 获取APP版本
	 * @return
	 */
	@RequestMapping("appVersion")
	@ResponseBody
	public AppVersion appVersion(@RequestParam(defaultValue="android") String type){
		AppVersion appVersion = appVersionDao.findFirstByTypeOrderByVersionCodeDesc(type);
		return appVersion;
	}
	
	/**
	 * 获取APP启动图片
	 * @return
	 */
	@RequestMapping("startupImg")
	@ResponseBody
	public Map<String, Object> startupImg(){
		Map<String, Object> map = new HashMap<>();
		map.put("url", Constants.SYS_BANNER_URL_SUFFIX + "/default20160126.png");
		map.put("showTime", 1000);
		map.put("version", "20160126");
		return map;
	}
	
	/**
	 * 获取区域版本
	 * @return
	 */
	@RequestMapping("areaVersion")
	@ResponseBody
	public Map<String, Object> areaVersion(){
		Map<String, Object> map = new HashMap<>();
		map.put("version", "20160205");
		return map;
	}
	
	/**
	 * 区域列表
	 * @return
	 */
	@RequestMapping("/areaList")
	@ResponseBody
	public Map<String,Object> areaList() {
		return areaService.getAreaList();
	}
	
	/**
	 * 提现规则
	 * @return
	 */
	@RequestMapping("/getWithdrawRule")
	@ResponseBody
	public Map<String, Object> getWithdrawRule(){
		BigDecimal min = new BigDecimal(sysConstantDao.findValueById(Constants.WITHDRAW_MIN_FUNDS));
		BigDecimal max = new BigDecimal(sysConstantDao.findValueById(Constants.WITHDRAW_MAX_FUNDS));
		Map<String, Object> returnMap=new HashMap<String, Object>();
		returnMap.put("WITHDRAW_MIN", min);
		returnMap.put("WITHDRAW_MAX", max);
		return returnMap;
	}
	
	/**
	 * 获取客服电话
	 * @return
	 */
	@RequestMapping("servicePhone")
	@ResponseBody
	public String servicePhone(){
		return Constants.SYS_SERVICE_PHONE;
	}
}
