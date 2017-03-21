package com.shanlin.p2p.app.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shanlin.framework.controller.BaseAction;
import com.shanlin.p2p.app.service.PushService;

/**
 * @author ice
 * @createDate 2015年8月04日
 *
 */
@Controller
@RequestMapping("/push")
public class PushAction extends BaseAction {

	@Resource
	private PushService pushService;

	/**
	 * 极光推送
	 * 
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping("/getPushList")
	@ResponseBody
	public Map<String, Object> getPushList(@RequestParam int page, @RequestParam int size) {
		Sort sort = new Sort(Direction.DESC, "createTime");
		return pushService.getPushList(getPageable(page, size, sort));
	}
	
	/**
	 * 极光推送获取内容详情
	 * 
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping("/getPushContent")
	@ResponseBody
	public Map<String, Object> getPushContent(@RequestParam Long id) {
		return pushService.getPushContent(id);
	}
}