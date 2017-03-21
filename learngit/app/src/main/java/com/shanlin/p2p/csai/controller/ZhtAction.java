package com.shanlin.p2p.csai.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shanlin.p2p.csai.service.ZhtService;
import com.shanlin.p2p.csai.util.CsaiZhtUtil;

/**
 * 账户通业务逻辑类
 * 
 * @author ice
 *
 */
@Controller
@RequestMapping("/csai")
public class ZhtAction extends CsaiMainAction {

	@Resource
	private ZhtService zhtService;

	private static final Logger log = LoggerFactory.getLogger(ZhtAction.class);
	
	/**
	 * 登录
	 * 
	 * @return
	 */
	@RequestMapping("/csaiLogin")
	// 如:http://127.0.0.1:8100/app/csai/login.action
	// http://58.250.171.53:8200/app/csai/csaiLogin.action?sign=sCaFi5UUIzL%2ba95nQH6r2EcTVOoLSKUEockYRh6J5tvRAM2%2bxmyB5WqC8TfTlnWuIymtTQbXAHc%3d
	public String csaiLogin(@RequestParam String sign, HttpServletRequest request) {
		String t = "";
		String pid = "";
		String name = "";
		String phone = "";
		//		long expirTime = 180 * 1000;// 秒

		boolean isCsAi = false;

		// 格式phone=13800138000&name=test&pid=0&t=123456789
		String str = CsaiZhtUtil.decrypt(sign, CsaiZhtUtil.getZhtKey());
		String[] strArr = str.split("&");
		if (strArr != null && strArr.length > 0) {
			for (int i = 0; i < strArr.length; i++) {
				phone = strArr[0].substring(strArr[0].indexOf("=") + 1, strArr[0].length());// 用户在希财网注册的手机号
				name = strArr[1].substring(strArr[1].indexOf("=") + 1, strArr[1].length());// 用户在希财网注册的用户名
				pid = strArr[2].substring(strArr[2].indexOf("=") + 1, strArr[2].length());// pid(产品Id也即标Id)
				t = strArr[3].substring(strArr[3].indexOf("=") + 1, strArr[3].length());// 时间戳(1970-1-1到现在的总秒数)
				isCsAi = true;
			}
		}

		String csai = "";
		if (isCsAi) {
			csai = "&partId=csai";
		}

		Boolean is = zhtService.isRegistered(phone, name, pid, t, urlQzSecYmd);

		log.info("(希财)账户通->登录->自动生成账号完成->执行绑定回调接口"+"("+CsaiZhtUtil.getTime()+")"+"("+is+")");

		// 若用户已注册过
		if (is) {
			return "redirect:" + CsaiMainAction.serverQzWeb + "/user/login.html?accountName=" + name + "&mobilephone=" + phone + csai;
		}

		return "redirect:" + CsaiMainAction.serverQzWeb + "/register.html?accountName=" + name + "&mobilephone=" + phone + csai;
	}
}