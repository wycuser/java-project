package com.shanlin.p2p.app.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shanlin.framework.controller.BaseAction;
import com.shanlin.framework.utils.StringHelper;
import com.shanlin.p2p.app.model.Banner;
import com.shanlin.p2p.app.model.Bid;
import com.shanlin.p2p.app.model.CreditAssignmentApply;
import com.shanlin.p2p.app.model.ExperienceBid;
import com.shanlin.p2p.app.service.BidService;
import com.shanlin.p2p.app.service.CreditAssignmentApplyService;
import com.shanlin.p2p.app.service.ExperienceBidService;
import com.shanlin.p2p.app.service.HomeService;

@Controller
@RequestMapping("/home")
public class HomeAction extends BaseAction{
	
	@Resource
	private HomeService homeService;
	
	@Resource
	private BidService bidService;
	
	@Resource
	private CreditAssignmentApplyService applyService;
	
	@Resource
	private ExperienceBidService experienceBidService; 
	/**
	 * 首页轮播图
	 * @param size banner个数
	 * @return
	 */
	@RequestMapping("/banner")
	@ResponseBody
	public List<Banner> banner(){
		return homeService.findAllBanner();
	}
	
	/**
	 * 首页呈现的标的信息
	 * 体验标1个
	 * 标的4个
	 * 转让标1个
	 */
	@RequestMapping("/bid")
	@ResponseBody
	public List<Map<String, Object>> bid(){
		ExperienceBid experienceBid = experienceBidService.findFirstExperienceBid();
//		CreditAssignmentApply creditAssignmentApply = null;
		CreditAssignmentApply creditAssignmentApply = applyService.findFirstZrzAssignment();
		List<Bid> bidList = bidService.findBidByType(null, getPageable(1, creditAssignmentApply == null? 5 : 4,
				new Sort(new Order("status"), new Order(Direction.DESC, "publishTime"))));
		List<Map<String, Object>> list = new ArrayList<>(6);
		if(experienceBid != null){
			list.add(experienceBid.toMap());
		}
		for (Bid bid : bidList) {
			list.add(bid.toMap());
		}
		if(creditAssignmentApply != null){
			list.add(creditAssignmentApply.toMap());
		}
		return list;
	}
	
	/**
	 * 网站公告
	 * @return
	 */
	@RequestMapping("/wzgg")
	public String wzgg(@RequestParam Long id, Model model, HttpServletRequest request){
		Object[][] wzgg = homeService.findWzggById(id);
		if(wzgg == null || wzgg.length == 0)
			throw new IllegalArgumentException("不存在的公告");
		model.addAttribute("type", wzgg[0][0]);
		model.addAttribute("title", wzgg[0][1]);
		StringBuilder sb = new StringBuilder();
		try {
			StringHelper.format(sb , (String)wzgg[0][2]);
		} catch (IOException e) {
			throw new IllegalArgumentException("异常");
		}
		model.addAttribute("content", sb.toString());
		model.addAttribute("lastUpdateTime", wzgg[0][3]);
		model.addAttribute("description", wzgg[0][4]);
		model.addAttribute("requestUrl", request.getRequestURL().toString());
		return "template";
	}
	/**
	 * 媒体报道
	 * @return
	 */
	@RequestMapping("/mtbd")
	public String mtbd(@RequestParam Long id, Model model, HttpServletRequest request){
		Object[][] mtbd = homeService.findMtbdById(id);
		if(mtbd == null || mtbd.length == 0)
			throw new IllegalArgumentException("不存在的媒体报道");
		model.addAttribute("title", mtbd[0][0]);
		StringBuilder sb = new StringBuilder();
		try {
			StringHelper.format(sb , (String)mtbd[0][1]);
		} catch (IOException e) {
			throw new IllegalArgumentException("异常");
		}
		model.addAttribute("content", sb.toString());
		model.addAttribute("lastUpdateTime", mtbd[0][2]);
		model.addAttribute("description", mtbd[0][3]);
		model.addAttribute("requestUrl", request.getRequestURL().toString());
		return "template";
	}
	/**
	 * 行业资讯
	 * @return
	 */
	@RequestMapping("/wdhyzx")
	public String wdhyzx(@RequestParam Long id, Model model, HttpServletRequest request){
		Object[][] wdhyzx = homeService.findWdhyzxById(id);
		if(wdhyzx == null || wdhyzx.length == 0)
			throw new IllegalArgumentException("不存在的行业资讯");
		model.addAttribute("title", wdhyzx[0][0]);
		StringBuilder sb = new StringBuilder();
		try {
			StringHelper.format(sb , (String)wdhyzx[0][1]);
		} catch (IOException e) {
			throw new IllegalArgumentException("异常");
		}
		model.addAttribute("content", sb.toString());
		model.addAttribute("lastUpdateTime", wdhyzx[0][2]);
		model.addAttribute("description", wdhyzx[0][3]);
		model.addAttribute("requestUrl", request.getRequestURL().toString());
		return "template";
	}
}
