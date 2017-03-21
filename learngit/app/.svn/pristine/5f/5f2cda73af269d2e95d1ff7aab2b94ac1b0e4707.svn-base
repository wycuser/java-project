package com.shanlin.p2p.csai.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.shanlin.framework.utils.StringHelper;
import com.shanlin.p2p.csai.service.LqGetP2pService;

/**
 * 拉取PushP2p业务逻辑类
 * 
 * @author ice
 *
 */
@Controller
@RequestMapping("/csai")
public class LqPushP2pAction extends CsaiMainAction {

	@Resource
	private LqGetP2pService lqGetP2pService;

	/**
	 * 先push给希财,然后希财再拉取->用户点击连接返回标的信息->30天内登录注册统计用户来源
	 * @注意:该接口目前由JfResource实现(在标在后台发布后就执行推送到希财)
	 * 
	 * pushP2p
	 * 
	 * @return
	 */
	@RequestMapping("/pushP2p")
	@ResponseBody
	// 如:http://127.0.0.1:8100/app/csai/pushP2p.action?bidId=60
	public String pushP2p(@RequestParam Long bidId,HttpServletRequest request) {
		// 定义,初始化
		String jcHz = "push_p2p";
		String url = urlQzSecYmd + "/" + jcHz;
		MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<String, Object>();
		multiValueMap.add("pid", bidId);
		multiValueMap.add("client_id", CsaiMainAction.shNo);
		multiValueMap.add("client_secret", CsaiMainAction.shKey);

		// 定义,初始化
		String result = "{code=1,msg=\"推送失败\"}";
		RestTemplate restTemplate = new RestTemplate();
		try {
			Object obj = restTemplate.postForObject(url, multiValueMap, String.class);
			result = StringHelper.getSafeAndTrim(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		request.setAttribute("isWrapJson", false);
		return result;
	}
}