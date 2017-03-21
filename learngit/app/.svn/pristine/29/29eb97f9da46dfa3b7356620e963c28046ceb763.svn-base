package com.shanlin.p2p.wangd.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shanlin.p2p.wangd.service.WangdP2pService;

/**
 * 网贷P2p的Action
 * 
 * @author ice
 *
 */
@Controller
@RequestMapping("/wangd")
public class WangdP2pAction extends WangdMainAction {

	@Resource
	private WangdP2pService wangdP2pService;

	// 获取网袋P2p信息
	// http://www.myshanxing.com:8888/app/wangd/getP2pInfors.action?page=1&pageSize=20
	// http://58.250.171.53:8200/app/wangd/getP2pInfors.action?page=1&pageSize=20
	@RequestMapping("/getP2pInfors")
	@ResponseBody
	public Map<String, Object> getP2pInfors(@RequestParam int page, @RequestParam int pageSize, HttpServletRequest request) {
		if (page == 0) {
			page = 1;
		}
		if (pageSize == 0) {
			pageSize = 20;
		}
		Map<String, Object> mapRe = wangdP2pService.getP2pInfors(page, pageSize);
		request.setAttribute("isWrapJson", false);
		return mapRe;
	}
}