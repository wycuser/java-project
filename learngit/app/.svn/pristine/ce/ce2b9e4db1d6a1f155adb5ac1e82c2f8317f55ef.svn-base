package com.shanlin.p2p.jint.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shanlin.p2p.jint.service.JintP2pService;

/**
 * 金投P2p的Action
 * @金投的出口Ip:112.95.141.70
 * 
 * @author ice
 *
 */
@Controller
@RequestMapping("/jint")
public class JintP2pAction extends JintMainAction {

	@Resource
	private JintP2pService jintP2pService;

	/**
	 * Post到金投
	 * 
	 * @return
	 */
	// http://58.250.171.53:8200/app/jint/postToJint.action?bidId=135
	@RequestMapping("/postToJint")
	@ResponseBody
	public Map<String, Object> postToJint(@RequestParam Long bidId, HttpServletRequest request) {
		// 金投佣金:@月标: 1.5%*成功投资项目的实际月份÷12个月    @天标: 1.5%*成功投资项目的实际天数÷365天
		Boolean is = jintP2pService.postP2pToJint(bidId);
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