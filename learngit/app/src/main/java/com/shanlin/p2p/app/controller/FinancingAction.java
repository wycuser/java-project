package com.shanlin.p2p.app.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shanlin.framework.controller.BaseAction;
import com.shanlin.framework.utils.Formater;
import com.shanlin.p2p.app.constant.Constants;
import com.shanlin.p2p.app.constant.MessageConstants;
import com.shanlin.p2p.app.model.Bid;
import com.shanlin.p2p.app.model.ExperienceAccount;
import com.shanlin.p2p.app.model.ExperienceBid;
import com.shanlin.p2p.app.model.FundAccount;
import com.shanlin.p2p.app.model.SxbaoConfig;
import com.shanlin.p2p.app.model.SxbaoType;
import com.shanlin.p2p.app.model.UserAccount;
import com.shanlin.p2p.app.model.enums.BidStatus;
import com.shanlin.p2p.app.model.enums.FundAccountType;
import com.shanlin.p2p.app.model.enums.Judge;
import com.shanlin.p2p.app.model.enums.SurpriseStatus;
import com.shanlin.p2p.app.service.BidService;
import com.shanlin.p2p.app.service.CreditAssignmentApplyService;
import com.shanlin.p2p.app.service.ExperienceAccountService;
import com.shanlin.p2p.app.service.ExperienceBidRecordService;
import com.shanlin.p2p.app.service.ExperienceBidService;
import com.shanlin.p2p.app.service.FinancingService;
import com.shanlin.p2p.app.service.FundAccountService;
import com.shanlin.p2p.app.service.LetterService;
import com.shanlin.p2p.app.service.SmsService;
import com.shanlin.p2p.app.service.UserAccountService;

@Controller
@RequestMapping("/financing")
public class FinancingAction extends BaseAction{
	
	@Resource
	private FinancingService financingService;
	
	@Resource
	private FundAccountService fundAccountService;
	
	@Resource
	private UserAccountService userAccountService;
	
	@Resource
	private ExperienceBidService experienceBidService;
	
	@Resource
	private ExperienceAccountService experienceAccountService;
	
	@Resource
	private ExperienceBidRecordService experienceBidRecordService;
	
	@Resource
	private LetterService letterService;
	
	@Resource
	private SmsService smsService;
	
	@Resource
	private BidService bidService;
	
	
	@Resource
	private CreditAssignmentApplyService applyService;
	
	/**
	 * 标的列表
	 * @param type
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping("/bidList")
	@ResponseBody
	public Map<String, Object> bidList(@RequestParam int type, @RequestParam int page, @RequestParam int size){
		if(type == 4){
			return financingService.findSxbaoTypes(getPageable(page, size));
		}
		return financingService.findBidOrAssignmentByType(type, page, size);
	}
	
	/**
	 * 标的详情
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/bid")
	public String bid(@RequestParam Long id, Model model){
		Bid bid = financingService.findOneBid(id);
		model.addAttribute(bid);
		DateTime publishTime = new DateTime(bid.getPublishTime());
		if(bid.getExtend().getIsByDay() == Judge.S){
			model.addAttribute("endTime", publishTime.plusDays(bid.getExtend().getLoanPeriod()).toDate());
			model.addAttribute("loanPeriodType", "天");
		} else {
			model.addAttribute("endTime", publishTime.plusMonths(bid.getLoanPeriod()).toDate());
			model.addAttribute("loanPeriodType", "个月");
		}
		String loanAccount = bid.getUserAccount().getLoginName();
		loanAccount = loanAccount.substring(0, 1) + "***" + loanAccount.substring(loanAccount.length() - 1);
		model.addAttribute("loanAccount", loanAccount);
		model.addAttribute("findEndTime", publishTime.plusDays(bid.getFindPeriod()).toDate());
		model.addAttribute(financingService.findBidExtendById(id));
		model.addAttribute("countInvest", financingService.countByBid(id));
		model.addAttribute("totalInvest", financingService.findAllBuyingPriceByBid(bid));
		model.addAttribute("surpriseLucreList", financingService.findSurpriseLucreByBid(id));
		model.addAttribute("surpriseConfigList", financingService.findSurpriseConfigByBid(id));
		List<Object[]> repaymentPlanList = financingService.findRepaymentPlanByBid(id);
		if(repaymentPlanList != null && repaymentPlanList.size() > 0){
			model.addAttribute("repaymentPlanList", repaymentPlanList);
		}
		return "financing/bid";
	}
	
	@RequestMapping("/investRecord")
	@ResponseBody
	public Map<String, Object> investRecord(@RequestParam Long id, @RequestParam int page, @RequestParam int size){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Map<String, Object> map = new HashMap<>();
		Page<Object[]> investRecordList = financingService.findInvestRecordBy(id, getPageable(page, size, new Sort(Direction.ASC, "investTime")));
		String loginName = null;
		for (Object[] record : investRecordList.getContent()) {
			loginName = ((String)record[0]);
			record[0] = loginName.substring(0, 1) + "***" + loginName.substring(loginName.length() - 1);
			record[1] = Formater.formatAmount(((BigDecimal)record[1])); 
			record[2] = format.format((Date)record[2]);
			record[3] = (record[3]== Judge.S?"手机端":"web端");
			record[4] = Formater.formatAmount(((BigDecimal)record[4])); 
			
		}
		map.put("content", investRecordList.getContent());
		map.put("hasNext", investRecordList.hasNext());
		map.put("number", investRecordList.getNumber());
		map.put("size", investRecordList.getSize());
		return map;
	}
	
	/**
	 * 投资详情页
	 * @param id
	 * @param userId
	 */
	@RequestMapping("/investDetails")
	@ResponseBody
	public Map<String, Object> investDetails(@RequestParam Long id, @RequestParam Long userId){
		Bid bid = financingService.findOneBid(id);
		FundAccount fundAccount = fundAccountService.findWlzhByUserId(userId);
		Map<String, Object> map = new HashMap<>();
		if(fundAccount == null)
			throw new IllegalArgumentException("资金账户不存在");
		if(bid != null){
			map.put("id", bid.getId());
			map.put("title", bid.getTitle());
			map.put("loanPeriod", bid.getExtend().getIsByDay() == Judge.S?
					bid.getExtend().getLoanPeriod().toString() + "天" : bid.getLoanPeriod().toString() + "个月");
			map.put("rate", Formater.formatRate(bid.getRate(), false));
			map.put("bonus", bid.getBonus());
			map.put("surprise", bid.getSurpriseStatus() != SurpriseStatus.WPZ? 1 : 0);
			map.put("residueAmount", bid.getResidueAmount());
			map.put("balance", fundAccount.getBalance());
			map.put("investName", bid.getInvestName());
			map.put("isParty", userAccountService.isRegisterHuiChaoPay(userId)?1:0);
		}
		return map;
	}
	
	/**
	 * 投标
	 * @param id
	 * @param userId
	 */
	// http://127.0.0.1:8200/app/financing/invest.action?id=524&money=5500&userId=22
	@RequestMapping("/invest")
	@ResponseBody
	public void invest(@RequestParam Long id, @RequestParam Long userId, @RequestParam BigDecimal money,@RequestParam(required=false,defaultValue="0") Long hbId,@RequestParam(required=false,defaultValue="0") Long dkId){
		if(money.compareTo(new BigDecimal(Constants.SYS_MIN_INVEST_AMOUNT)) < 0)
			throw new IllegalArgumentException("投资金额必须大于等于" + Constants.SYS_MIN_INVEST_AMOUNT);
		if(money.stripTrailingZeros().toPlainString().indexOf(".") > -1)
			throw new IllegalArgumentException("投资金额必须为整数");
		if(!userAccountService.isSafeByUserId(userId))
			throw new IllegalArgumentException("请先安全认证");
		if(!userAccountService.isRegisterHuiChaoPay(userId))
			throw new IllegalArgumentException("您需要到电脑端进行第三方托管平台注册，才可申请投标！");
		UserAccount userAccount = userAccountService.findOne(userId);
		if(!bidService.isSubscribers(id, userAccount.getLoginName()))
			throw new IllegalArgumentException("该标的仅预约户可以投资，请选择其他标的投资");
		//投标
		financingService.invest(id, userAccount, money,hbId, dkId);
		Map<String, Object> args = new HashMap<>();
		args.put("title", financingService.findTitleByBid(id));
		String message = Formater.formatString(MessageConstants.SMS_LETTER_INVEST, args);
		//发送站内信
		letterService.sendLetter(userId, "投标成功", message);
		//发送短信
//		smsService.send(message, userAccount.getMobilePhone());
	}
	
	/**
	 * 体验标详情
	 * @param id
	 * @param model
	 */
	@RequestMapping("/experienceBid")
	public String experienceBid(@RequestParam Long id, Model model){
		ExperienceBid experienceBid = experienceBidService.findById(id);
		if(experienceBid == null)
			throw new IllegalArgumentException("无效的id");
		model.addAttribute(experienceBid);
		DateTime publishTime = new DateTime(experienceBid.getPublishTime());
		model.addAttribute("findEndTime", publishTime.plusDays(experienceBid.getFindPeriod()).toDate());
		model.addAttribute("endTime", publishTime.plusDays(experienceBid.getLoanPeriod()).toDate());
		Page<Object[]> bidRecordList = experienceBidRecordService.findByBid(id, getPageable(1, 10, new Sort(Direction.ASC, "investTime")));
		model.addAttribute("bidRecordList", bidRecordList);
		return "financing/experienceBid";
	}
	
	@RequestMapping("/experienceInvestRecord")
	@ResponseBody
	public Map<String, Object> experienceInvestRecord(@RequestParam Long id, @RequestParam int page, @RequestParam int size){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Map<String, Object> map = new HashMap<>();
		Page<Object[]> bidRecordList = experienceBidRecordService.findByBid(id, getPageable(page, size, new Sort(Direction.ASC, "investTime")));
		for (Object[] record : bidRecordList.getContent()) {
			record[0] = ((String)record[0]).substring(0, 4)+"***";
			record[1] = Formater.formatAmount(((BigDecimal)record[1])); 
			record[2] = format.format((Date)record[2]);
		}
		map.put("content", bidRecordList.getContent());
		map.put("hasNext", bidRecordList.hasNext());
		map.put("number", bidRecordList.getNumber());
		map.put("size", bidRecordList.getSize());
		return map;
	}
	
	/**
	 * 体验标投资详情页
	 * @param id
	 * @param userId
	 */
	@RequestMapping("/experienceInvestDetails")
	@ResponseBody
	public Map<String, Object> experienceInvestDetails(@RequestParam Long id, @RequestParam Long userId){
		Map<String, Object> map = new HashMap<>();
		ExperienceBid experienceBid = experienceBidService.findById(id);
		ExperienceAccount experienceAccount = experienceAccountService.findOrAddByUserId(userId);
		map.put("id", experienceBid.getId());
		map.put("title", experienceBid.getTitle());
		map.put("loanPeriod", experienceBid.getLoanPeriod().toString() + "天");
		map.put("rate", Formater.formatRate(experienceBid.getRate().divide(new BigDecimal(100)), false));
		map.put("residueAmount", experienceBid.getResidueAmount());
		map.put("balance", experienceAccount.getBalance());
		return map;
	}
	
	/**
	 * 体验标投资
	 * @param id
	 * @param userId
	 * @param money
	 */
	@RequestMapping("/experienceInvest")
	@ResponseBody
	public void experienceInvest(@RequestParam Long id, @RequestParam Long userId, @RequestParam BigDecimal money){
		if(money.compareTo(new BigDecimal(Constants.SYS_MIN_INVEST_AMOUNT)) < 0)
			throw new IllegalArgumentException("投资金额必须大于等于" + Constants.SYS_MIN_INVEST_AMOUNT);
		if(money.stripTrailingZeros().toPlainString().indexOf(".") > -1)
			throw new IllegalArgumentException("投资金额必须为整数");
		if(!userAccountService.isSafeByUserId(userId))
			throw new IllegalArgumentException("请先安全认证");
		if(!userAccountService.isRegisterHuiChaoPay(userId))
			throw new IllegalArgumentException("您需要到电脑端进行第三方托管平台注册，才可申请投标！");
		experienceBidService.invest(id, userId, money);
	}
	
	/**
	 * 债权转让详情
	 * @param zqId
	 * @return
	 */
	@RequestMapping("/creditorAssignmentDetail")
	@ResponseBody
	public Map<String, Object> creditorAssignmentDetail(@RequestParam Long zqApplyId){
		return applyService.findById(zqApplyId);
	}
	
	/**
	 * 债权转让投资详情
	 * @param zqId
	 * @param userId
	 * @return
	 */
	@RequestMapping("/creditorAssignmentInvestDetail")
	@ResponseBody
	public Map<String, Object> creditorAssignmentInvestDetail(@RequestParam Long zqApplyId,@RequestParam Long userId){
		Map<String, Object> map=applyService.findById(zqApplyId);
		FundAccount fundAccount = fundAccountService.findWlzhByUserId(userId);
		if(fundAccount == null)
			throw new IllegalArgumentException("资金账户不存在");
		map.put("balance", fundAccount.getBalance());
		return map;
	}
	
	/**
	 * 债权转让投资
	 * @param zqId
	 * @param userId
	 * @return
	 */
	@RequestMapping("/creditorAssignmentInvest")
	@ResponseBody
	public void creditorAssignmentInvest(@RequestParam Long zqApplyId,@RequestParam Long userId){
		if(zqApplyId<=0)
			throw new IllegalArgumentException("线上债权转让申请不存在");
		if(!userAccountService.isSafeByUserId(userId))
			throw new IllegalArgumentException("请先安全认证");
		if(!userAccountService.isRegisterHuiChaoPay(userId))
			throw new IllegalArgumentException("您需要到电脑端进行第三方托管平台注册，才可申请投标！");
		applyService.purchase(zqApplyId, userId);
	}
	
	/**
	 * 善行宝详情
	 * @param typeId
	 * @return
	 */
	@RequestMapping("/sxbaoDetails")
	@ResponseBody
	public Map<String, Object> sxbaoDetails(@RequestParam Long typeId, Long userId){
		Map<String, Object> map = new HashMap<>();
		SxbaoType sxbaoType = financingService.findOneSxbaoType(typeId);
		if(sxbaoType == null)
			throw new IllegalArgumentException("无效的id");
		map.put("status", sxbaoType.getSellStatus() == Judge.S? "立即投资" : "已停售");
		map.put("configs", financingService.findSxbaoByTypeId(typeId));
		if(userId != null){
			map.put("balance", fundAccountService.findBalanceByUserIdAndType(userId, FundAccountType.WLZH));
		}
		return map;
	}
	
	/**
	 * 善行宝投资
	 * @param id
	 * @param userId
	 * @param money
	 */
	@RequestMapping("/sxbaoInvest")
	@ResponseBody
	public void sxbaoInvest(@RequestParam Long id, @RequestParam Long userId, @RequestParam BigDecimal money){
		SxbaoConfig config = financingService.findSxbaoConfigById(id);
		if(config == null || config.getBid() == null)
			throw new IllegalArgumentException("非法请求");
		if(money.compareTo(config.getInvestFloor()) < 0)
			throw new IllegalArgumentException("投资金额必须大于等于" + config.getInvestFloor());
		if(money.stripTrailingZeros().toPlainString().indexOf(".") > -1)
			throw new IllegalArgumentException("投资金额必须为整数");
		if(!userAccountService.isSafeByUserId(userId))
			throw new IllegalArgumentException("请先安全认证");
		if(!userAccountService.isRegisterHuiChaoPay(userId))
			throw new IllegalArgumentException("您需要到电脑端进行第三方托管平台注册，才可申请投标！");
		UserAccount userAccount = userAccountService.findOne(userId);
		//投标
		financingService.invest(config.getBid().getId(), userAccount, money);
		Map<String, Object> args = new HashMap<>();
		args.put("title", config.getBid().getTitle());
		String message = Formater.formatString(MessageConstants.SMS_LETTER_INVEST, args);
		//发送站内信
		letterService.sendLetter(userId, "投标成功", message);
		//发送短信
		smsService.send(message, userAccount.getMobilePhone());
	}
	
	/**
	 * 标的状态
	 * @param id
	 */
	@RequestMapping("/bidStatus")
	@ResponseBody
	public String bidStatus(@RequestParam Long id, HttpServletRequest request){
		Bid bid = financingService.findOneBid(id);
		if(bid == null)
			throw new IllegalArgumentException("无效的id");
		BidStatus status = bid.getStatus();
		String statusButtonTemp = null;
		if(status == BidStatus.TBZ || status == BidStatus.DFK){
			if(new DateTime(bid.getPublishTime()).plusDays(bid.getFindPeriod()).getMillis() - System.currentTimeMillis() <= 0){
				statusButtonTemp = "已完结";
			}else if(status == BidStatus.DFK){
				statusButtonTemp = status.getChineseName();
			}else{
				statusButtonTemp = "立即投资";
			}
		}else{
			statusButtonTemp = status.getChineseName();
		}
		request.setAttribute("isWrapJson", Boolean.FALSE);
		return statusButtonTemp;
	}
	
	/**
	 * 体验标的状态
	 * @param id
	 */
	@RequestMapping("/experienceBidStatus")
	@ResponseBody
	public String experienceBidStatus(@RequestParam Long id, HttpServletRequest request){
		ExperienceBid experienceBid = experienceBidService.findById(id);
		if(experienceBid == null)
			throw new IllegalArgumentException("无效的id");
		BidStatus status = experienceBid.getStatus();
		String statusButtonTemp = null;
		if(status == BidStatus.TBZ || status == BidStatus.DFK){
			if(new DateTime(experienceBid.getPublishTime()).plusDays(experienceBid.getFindPeriod()).getMillis() - System.currentTimeMillis() <= 0){
				statusButtonTemp = "已完结";
			}else if(status == BidStatus.DFK){
				statusButtonTemp = status.getChineseName();
			}else{
				statusButtonTemp = "立即投资";
			}
		}else{
			statusButtonTemp = status.getChineseName();
		}
		request.setAttribute("isWrapJson", Boolean.FALSE);
		return statusButtonTemp;
	}
	
}
