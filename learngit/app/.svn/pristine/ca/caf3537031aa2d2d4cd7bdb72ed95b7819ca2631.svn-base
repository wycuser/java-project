package com.shanlin.p2p.duoz.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shanlin.p2p.duoz.service.DuozP2pService;

/**
 * 多赚P2p的Action
 * 
 * @author ice
 *
 */
@Controller
@RequestMapping("/duoz")
public class DuozP2pAction extends DuozMainAction {

	@Resource
	private DuozP2pService duozP2pService;

	// 获取多赚P2p信息
	// http://www.myshanxing.com:8888/app/duoz/getP2pBids.action?page=1&pageSize=20
	// http://58.250.171.53:8200/app/duoz/getP2pBids.action?page=1&pageSize=20
	@RequestMapping("/getP2pBids")
	@ResponseBody
	public Map<String, Object> getP2pBids(@RequestParam int page, @RequestParam int pageSize, HttpServletRequest request) {
		if (page == 0) {
			page = 1;
		}
		if (pageSize == 0) {
			pageSize = 20;
		}
		Map<String, Object> mapRe = duozP2pService.getP2pBids(page, pageSize);
		request.setAttribute("isWrapJson", false);
		return mapRe;
	}

	// 获取多赚P2p信息
	// http://58.250.171.53:8200/app/duoz/getP2pInves.action?projectId=200
	@RequestMapping("/getP2pInves")
	@ResponseBody
	public LinkedHashMap<String, Object> getP2pInves(@RequestParam long projectId, HttpServletRequest request) {
		LinkedHashMap<String, Object> mapRe = duozP2pService.getP2pInves(projectId);
		request.setAttribute("isWrapJson", false);
		return mapRe;
	}
}