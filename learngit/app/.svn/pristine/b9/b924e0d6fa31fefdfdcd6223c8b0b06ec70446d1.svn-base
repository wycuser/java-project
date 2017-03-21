package com.shanlin.p2p.app.controller;



import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shanlin.p2p.app.service.UserAccountService;

@Controller
@RequestMapping("/api")
public class ApiAction {
	
	@Resource
	private UserAccountService userAccountService;
	
	@RequestMapping
	@ResponseBody
	public Map<String, Object> index(@RequestParam String mod, @RequestParam String time, @RequestParam String token, @RequestParam int arg, HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		if(!checkRequest(mod, time, token)){
			map.put("status", 0);
			map.put("re", "非法请求");
			return map;
//			throw new IllegalArgumentException("非法请求");
		}
		switch (arg) {
		case 1:
			String loginName = request.getParameter("user");
			if(loginName == null || loginName.trim().length() == 0){
				map.put("status", 0);
				map.put("re", "用户名不能为空");
				return map;
//				throw new IllegalArgumentException("用户名不能为空");
			}
			int isSafe = userAccountService.isSafeByLoginName(loginName)?1:0;
			map.put("status", 1);
			map.put("isSafe", isSafe);
		default:
			map.put("status", 0);
			map.put("re", "非法请求");
			return map;
//			throw new ServiceException(InterfaceResultCode.ILLEGAL_REQUEST, "非法请求");
		}
	}
	
	private static final String SYS_MD5_KEY = "sadfddsfsdafx";
	
	public boolean checkRequest(String mod, String time, String token){
		if(!"shanlin".equals(mod))
			return false;
		return DigestUtils.md5Hex(time + SYS_MD5_KEY).equals(token.toLowerCase());
	}
	public static void main(String[] args) {
		System.out.println("mod=shanlin&time=11&token="+DigestUtils.md5Hex("11" + SYS_MD5_KEY)+"&arg=1&user=zhengxin");
	}
}
