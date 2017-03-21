package com.shanlin.p2p.peye.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shanlin.p2p.peye.service.PeyeP2pService;

/**
 * 天眼P2p的Action
 * 
 * @出口Ip:联通112.95.141.70,电信183.62.139.241
 * 
 * @测试地址:http://datatest.p2peye.com/data/debug.html
 * 
 * @author ice
 *
 */
@Controller
@RequestMapping("/peye")
public class PeyeP2pAction extends PeyeMainAction {

	@Resource
	private PeyeP2pService peyeP2pService;

	// 获取token(尚未实现)
	// http://www.myshanxing.com:8888/app/peye/getToken.action?username=peye&password=60cef9f29e18477b8e3ebfb347d38b8c
	// http://58.250.171.53:8200/app/peye/getToken.action?username=peye&password=60cef9f29e18477b8e3ebfb347d38b8c
	@RequestMapping("/getToken")
	@ResponseBody
	public Map<String, Object> getToken(@RequestParam(required = true) String username, @RequestParam(required = true) String password, HttpServletRequest request) {
		return null;// 
	}

	// 获取借款列表
	// http://www.myshanxing.com:8888/app/peye/getP2pInfors.action?page=1&pageSize=20
	// http://58.250.171.53:8200/app/peye/getP2pInfors.action?page=1&pageSize=20&status=1&time_from=2015-12-04 14:43:58&time_to=2015-12-05 14:43:58
	@RequestMapping("/getP2pInfors")
	@ResponseBody
	public Map<String, Object> getP2pInfors(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer pageSize, @RequestParam(required = false) Integer status, @RequestParam(required = false) String time_from, @RequestParam(required = false) String time_to, HttpServletRequest request) {
		if (page == null || page == 0) {
			page = 1;
		}
		if (pageSize == null || pageSize == 0) {
			pageSize = 20;
		}

		Map<String, Object> mapRe = peyeP2pService.getP2pInfors(page, pageSize, status, time_from, time_to);
		request.setAttribute("isWrapJson", false);
		return mapRe;
	}

	// 获取借款列表
	// http://www.myshanxing.com:8888/app/peye/getP2pInves.action?page=1&pageSize=20&id=321
	// http://58.250.171.53:8200/app/peye/getP2pInves.action?page=1&pageSize=20&id=321
	@RequestMapping("/getP2pInves")
	@ResponseBody
	public Map<String, Object> getP2pInves(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer pageSize, @RequestParam Integer id, HttpServletRequest request) {
		if (page == null || page == 0) {
			page = 1;
		}
		if (pageSize == null || pageSize == 0) {
			pageSize = 20;
		}
		Map<String, Object> mapRe = peyeP2pService.getP2pInves(page, pageSize, id == null ? null : Long.parseLong(id.toString()));
		request.setAttribute("isWrapJson", false);
		return mapRe;
	}
}