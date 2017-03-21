package com.shanlin.p2p.csai.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shanlin.framework.cache.SlMemcachedClient;
import com.shanlin.framework.utils.DateHelper;
import com.shanlin.framework.utils.MD5;
import com.shanlin.framework.utils.StringHelper;
import com.shanlin.p2p.csai.service.LqGetP2pService;
import com.shanlin.p2p.csai.util.CsaiZhtUtil;

/**
 * 拉取P2p数据业务逻辑类
 * 
 * @author ice
 *
 */
@Controller
@RequestMapping("/csai")
public class LqGetP2pAction extends CsaiMainAction {

	@Resource
	private LqGetP2pService lqGetP2pService;

	@Resource
	protected SlMemcachedClient memcachedClient;
	
	private static final Logger log = LoggerFactory.getLogger(LqGetP2pAction.class);
	
	/**
	 * 获取P2p
	 * 
	 * @return
	 */
	@RequestMapping("/getP2p")
	@ResponseBody
	// 如:http://127.0.0.1:8100/app/csai/getP2p.action?pid=60
	public Map<String, Object> getP2p(@RequestParam Long pid, HttpServletRequest request) {
		Map<String, Object> map = lqGetP2pService.getP2p(pid);
		if (map != null && map.size() > 0) {
			map.put("code", "0");
		}
		request.setAttribute("isWrapJson", false);
		return map;
	}

	/**
	 * 获取P2p返回(这个是在希财返回,(如用户在希财那边点击标的,返回地址事先通过link_website参数已指定)
	 * 
	 * @return
	 */
	@RequestMapping("/getP2pRe")
	public String getP2pRe(@RequestParam Long id, HttpServletRequest request, HttpServletResponse response) {

		log.info("(希财)->获取P2p返回->写入会员登录Cookie,其中过期时间为30天");
		
		// 写入会员登录Cookie
		Cookie cookie = new Cookie("part3Cookie", "csai");
		cookie.setDomain("58.250.171.53:8088");
		cookie.setPath("/");
		cookie.setMaxAge(30 * 24 * 60 * 60);// 30天的秒数
		response.addCookie(cookie);
		
		log.info("(希财)P2p返回"+CsaiMainAction.serverQzWeb + "/financing/sbtz/bdxq/" + id + ".html?partId=csai"+"("+CsaiZhtUtil.getTime()+")");
		return "redirect:" + CsaiMainAction.serverQzWeb + "/financing/sbtz/bdxq/" + id + ".html?partId=csai";
	}

	/**
	 * 获取P2p用户(由希财过来的用户)列表
	 * http://58.250.171.53:8200/app/csai/getP2pUserList.action?t=1444635597&token=d0c5fd2413fe888b8ab51da65a7ec123&page=1&startdate=&enddate=
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getP2pUserList")
	public Map<String, Object> getP2pUserList(@RequestParam int page, @RequestParam(required=false, defaultValue="10") Integer pagesize, @RequestParam String startdate, @RequestParam String enddate, @RequestParam String t, @RequestParam String token, HttpServletRequest request, HttpServletResponse response) {
		
		// 定义,初始化
		Sort sort = new Sort(Direction.DESC, "registerTime");
		Map<String, Object> map = new HashMap<String, Object>();
		String tokenP2p = MD5.getMD5ofStr(MD5.getMD5ofStr(StringHelper.getSafeAndTrim(t)) + shKey);
		Date endDate = DateHelper.getDateForCsai(enddate);
		Date startDate = DateHelper.getDateForCsai(startdate);

		// 若签名合法
		if (tokenP2p.equals(token)) {
			map = lqGetP2pService.getP2pUserList(startDate, endDate, getPageable(page, pagesize, sort), request);
			map.put("code", 0);
			
			log.info("(希财)->获取P2p用户成功"+"("+CsaiZhtUtil.getTime()+")");
		} else {
			map.put("code", 1);
			map.put("msg", "没有权限访问");
			
			log.info("(希财)->获取P2p用户失败"+"("+CsaiZhtUtil.getTime()+")");
		}
		request.setAttribute("isWrapJson", false);
		return map;
	}

	/**
	 * 获取P2p用户(由希财过来的用户)投资统计列表
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getP2pUserInveList")
	public Map<String, Object> getP2pUserInveList(@RequestParam int page, @RequestParam(required=false, defaultValue="10") Integer pagesize, @RequestParam String startdate, @RequestParam String enddate, @RequestParam String t, @RequestParam String token, HttpServletRequest request, HttpServletResponse response) {
		Sort sort = new Sort(Direction.DESC, "investTime");
		Map<String, Object> map = new HashMap<String, Object>();
		String tokenP2p = MD5.getMD5ofStr(MD5.getMD5ofStr(t) + shKey);
		Date endDate = DateHelper.getDateForCsai(enddate);
		Date startDate = DateHelper.getDateForCsai(startdate);

		// 若签名合法
		if (tokenP2p.equals(token)) {
			map = lqGetP2pService.getP2pUserInveList(startDate, endDate, getPageable(page, pagesize, sort));
			map.put("code", 0);
			
			log.info("(希财)->获取P2p用户投资统计列表成功"+"("+CsaiZhtUtil.getTime()+")");
		} else {
			map.put("code", 1);
			map.put("msg", "没有权限访问");
			
			log.info("(希财)->获取P2p用户投资统计列表失败"+"("+CsaiZhtUtil.getTime()+")");
		}
		request.setAttribute("isWrapJson", false);
		return map;
	}
}