package com.shanlin.p2p.rotu.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shanlin.p2p.rotu.service.RotuP2pService;

/**
 * 融途P2p的Action
 * 
 * @author ice
 *
 */
@Controller
@RequestMapping("/rotu")
public class RotuP2pAction extends RotuMainAction {

	@Resource
	private RotuP2pService rotuP2pService;

	/**
	 * Post到融途
	 * 
	 * @return
	 */
	// http://58.250.171.53:8200/app/rotu/postToRotu.action?bidId=135
	@RequestMapping("/postToRotu")
	@ResponseBody
	public Map<String, Object> postToRotu(@RequestParam Long bidId, HttpServletRequest request) {
		Boolean is = rotuP2pService.postP2pToRotu(bidId);
		Map<String, Object> map = new HashMap<String, Object>();
		if (is) {
			map.put("code", "0");
		} else {
			map.put("code", "-1");
		}
		request.setAttribute("isWrapJson", false);
		return map;
	}
}