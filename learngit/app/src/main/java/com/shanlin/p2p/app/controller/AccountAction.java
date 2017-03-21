package com.shanlin.p2p.app.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shanlin.framework.controller.BaseAction;
import com.shanlin.framework.controller.InterfaceResultCode;
import com.shanlin.framework.exception.ServiceException;
import com.shanlin.framework.mapper.BeanMapper;
import com.shanlin.framework.utils.Formater;
import com.shanlin.framework.utils.FunctionUtil;
import com.shanlin.framework.utils.StringHelper;
import com.shanlin.p2p.app.constant.Constants;
import com.shanlin.p2p.app.constant.MemcachedKeyConfig;
import com.shanlin.p2p.app.dao.BidDao;
import com.shanlin.p2p.app.dao.UserSafeStatusDao;
import com.shanlin.p2p.app.model.AuthAccount;
import com.shanlin.p2p.app.model.Bank;
import com.shanlin.p2p.app.model.BankCard;
import com.shanlin.p2p.app.model.Bid;
import com.shanlin.p2p.app.model.BidCreditRecord;
import com.shanlin.p2p.app.model.DeductionRecord;
import com.shanlin.p2p.app.model.ExperienceAccount;
import com.shanlin.p2p.app.model.ExperienceAccountFlow;
import com.shanlin.p2p.app.model.ExperienceBid;
import com.shanlin.p2p.app.model.ExperienceBidRecord;
import com.shanlin.p2p.app.model.FundAccount;
import com.shanlin.p2p.app.model.InvestRecord;
import com.shanlin.p2p.app.model.RedPacketRecord;
import com.shanlin.p2p.app.model.UserSafeStatus;
import com.shanlin.p2p.app.model.enums.BidStatus;
import com.shanlin.p2p.app.model.enums.FundAccountType;
import com.shanlin.p2p.app.model.enums.Judge;
import com.shanlin.p2p.app.model.enums.RealAuthSet;
import com.shanlin.p2p.app.service.AccountService;
import com.shanlin.p2p.app.service.AreaService;
import com.shanlin.p2p.app.service.AuthAccountService;
import com.shanlin.p2p.app.service.BankCardService;
import com.shanlin.p2p.app.service.BidCreditRecordService;
import com.shanlin.p2p.app.service.BidRepaymentRecordService;
import com.shanlin.p2p.app.service.ChargeService;
import com.shanlin.p2p.app.service.CreditAssignmentApplyService;
import com.shanlin.p2p.app.service.DeductionRecordService;
import com.shanlin.p2p.app.service.ExperienceAccountService;
import com.shanlin.p2p.app.service.ExperienceBidRecordService;
import com.shanlin.p2p.app.service.ExperienceBidService;
import com.shanlin.p2p.app.service.FundAccountFlowService;
import com.shanlin.p2p.app.service.FundAccountService;
import com.shanlin.p2p.app.service.InvestRecordService;
import com.shanlin.p2p.app.service.RedPacketRecordService;
import com.shanlin.p2p.app.service.RemoteInvokeService;
import com.shanlin.p2p.app.service.UserAccountService;
import com.shanlin.p2p.app.service.WithdrawApplyService;
import com.shanlin.p2p.app.vo.BankCardVO;
import com.shanlin.p2p.app.vo.BankVO;

/**
 * 
 * @author zheng xin
 * @createDate 2015年1月28日
 */
@Controller
@RequestMapping("/account")
public class AccountAction extends BaseAction {

	@Resource
	private AccountService accountManageService;

	@Resource
	private FundAccountService fundAccountService;

	@Resource
	private FundAccountFlowService fundAccountFlowService;

	@Resource
	private BidRepaymentRecordService bidRepaymentRecordService;

	@Resource
	private InvestRecordService investRecordService;

	@Resource
	private BidCreditRecordService bidCreditRecordService;

	@Resource
	private ExperienceAccountService experienceAccountService;

	@Resource
	private ExperienceBidRecordService experienceBidRecordService;

	@Resource
	private RemoteInvokeService remoteInvokeService;

	@Resource
	private CreditAssignmentApplyService creditAssignmentApplyService;

	@Resource
	private ExperienceBidService experienceBidService;
	
	@Resource
	private RedPacketRecordService redPacketRecordService;
	
	@Resource
	private DeductionRecordService deductionRecordService;
	
	@Resource
	private BidDao bidDao;
	
	@Resource
	private AreaService areaService;
	
	@Resource
	private WithdrawApplyService withdrawApplyService;
	
	@Resource
	private BankCardService bankCardService;
	
	@Resource
	private AuthAccountService authAccountService;
	
	@Resource
	private UserSafeStatusDao safeStatusDao;
	
	@Resource
	private UserAccountService userAccountService;
	
	@Resource
	private ChargeService chargeService;
	
	/**
	 * 用户资产
	 * 
	 * @param userId
	 *            用户id
	 * @return
	 */
	@RequestMapping("/asset")
	@ResponseBody
	public Map<String, Object> accountAsset(@RequestParam Long userId) {
		Map<String, Object> map = new HashMap<>();
		FundAccount fundAccount = fundAccountService.findWlzhByUserId(userId);
		BigDecimal balance = fundAccount.getBalance();
		// 持有债权金额
		BigDecimal holdPrice = bidCreditRecordService.findHoldPriceByUserId(userId);
		// 未还已借金额
		BigDecimal shouldPrice = bidRepaymentRecordService.findShouldPriceByUserId(userId);
		BigDecimal freeze = fundAccountService.findBalanceByUserIdAndType(userId, FundAccountType.SDZH);
		//账户净资产
		map.put("accountAsset", Formater.formatAmount(balance.add(holdPrice == null ? BigDecimal.ZERO : holdPrice).add(freeze == null ? BigDecimal.ZERO : freeze).subtract(shouldPrice == null ? BigDecimal.ZERO : shouldPrice)));
		// 冻结金额
		map.put("freeze", Formater.formatAmount(freeze));
		// 可用余额
		map.put("balance", Formater.formatAmount(balance));
		// 累计收益
		BigDecimal zrProfit = bidCreditRecordService.findAllZrProfit(userId);
		BigDecimal lucre = fundAccountFlowService.findLucreByAccountId(fundAccount.getId());
		lucre = (lucre==null?new BigDecimal(0.00):lucre);
		lucre = lucre.add(zrProfit==null?new BigDecimal(0.00):zrProfit);
		map.put("lucre", Formater.formatAmount(lucre));
		// 累计投资
		map.put("allInvest", Formater.formatAmount(investRecordService.findAllCreditPriceByUser(userId)));
		// 待收本金
		map.put("allWaitSourcePrice", Formater.formatAmount(bidRepaymentRecordService.findAllWaitRecycleSourcePrice(userId)));
		// 待收收益
		map.put("allWaitInterestPrice", Formater.formatAmount(bidRepaymentRecordService.findAllWaitRecycleInterestPrice(userId)));
		Map<String, Object> remoteMap = remoteInvokeService.httpInvokeReturn(memcachedClient.get(MemcachedKeyConfig.LOGIN_NAME.getPrefix() + userId).toString(), RemoteInvokeService.QUERY, null);
		//我的积分
		map.put("credits", remoteMap == null || !remoteMap.containsKey("credits") ? 0 : remoteMap.get("credits"));
		return map;
	}

	/**
	 * 获取银行卡简要信息列表
	 * 
	 * @return
	 */
	@RequestMapping("/bankCards")
	@ResponseBody
	public List<BankCardVO> bankCards(@RequestParam Long userId) {
		List<BankCard> bankCardList = accountManageService.findBankCardByUserId(userId);
		return BeanMapper.mapList(bankCardList, BankCardVO.class);
	}

	/**
	 * 获取银行列表
	 * 
	 * @return
	 */
	@RequestMapping("/banks")
	@ResponseBody
	public List<BankVO> banks() {
		List<Bank> bankList = accountManageService.findAllBank();
		return BeanMapper.mapList(bankList, BankVO.class);
	}

	/**
	 * 已投项目列表
	 * 
	 * @param userId
	 * @param type
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping("/creditorList")
	@ResponseBody
	public Map<String, Object> creditorList(@RequestParam Long userId, @RequestParam int type, @RequestParam int page, @RequestParam int size) {
		Sort sort = null;
		switch (type) {
		case 1://投标中的债权
			sort = new Sort(Direction.DESC, "investTime");
			break;
		case 2://回收中的债权
			sort = new Sort(Direction.DESC, "createDate");
			break;
		case 3://已结清的债权
			sort = new Sort(Direction.DESC, "bid.extend.closeOffTime");
			break;
		}
		return accountManageService.findCreditorListByType(type, userId, getPageable(page, size, sort));
	}

	/**
	 * 已投项目详情(我的投资)
	 * 
	 * @param userId
	 * @param id
	 */
	@RequestMapping("/creditor")
	@ResponseBody
	public Map<String, Object> creditor(@RequestParam Long userId, @RequestParam Long id, @RequestParam int type) {
		Map<String, Object> map = new HashMap<>();
		BidCreditRecord record = null;
		Bid bid = null;
		switch (type) {
		case 1:
			InvestRecord investRecord = investRecordService.findById(id);
			bid = investRecord.getBid();
			map.put("bidNumber", bid.getBidNumber());//标编号
			map.put("buyingPrice", Formater.formatAmount(investRecord.getBuyingPrice()));//原始投资金额
			map.put("rate", Formater.formatRate(bid.getRate()));//年利率
			map.put("loanPeriod", bid.getExtend().getIsByDay() == Judge.S ? bid.getExtend().getLoanPeriod().toString() + "天" : bid.getLoanPeriod().toString() + "个月");//期限
			long dayInMill = 3600000 * 24;
			long temp = dayInMill * bid.getFindPeriod() + bid.getExtend().getCheckTime().getTime() - System.currentTimeMillis();
			String remainTime = "";
			if (temp > 0) {
				int day = (int) Math.ceil(temp / dayInMill);
				int hour = (int) Math.ceil((temp - day * dayInMill) / 3600000);
				int min = (int) Math.ceil((temp - day * dayInMill - hour * 3600000) / 60000);
				remainTime = String.format("%d天%d小时%d分钟", day, hour, min);
			}
			map.put("remainTime", bid.getIsSxbao() == Judge.S ? "--" : remainTime);//剩余时间
			map.put("progress", bid.getIsSxbao() == Judge.S ? "--" : Formater.formatProgress(//进度
					(bid.getAmount().doubleValue() - bid.getResidueAmount().doubleValue()) / bid.getAmount().doubleValue()));
			map.put("title", bid.getTitle());
			map.put("bid", bid.getId());
			break;
		case 2:
			record = accountManageService.findByIdAndUserId(id, userId);
			bid = record.getBid();
			map.put("creditNumber", record.getCreditNumber());//债权ID
			map.put("buyingPrice", Formater.formatAmount(record.getBuyingPrice()));//原始投资金额
			map.put("rate", Formater.formatRate(bid.getRate()));//年利率
			map.put("waitRecyclePrice", Formater.formatAmount(bidRepaymentRecordService.findWaitRecyclePrice(record.getId(), userId)));//待收本息
			map.put("nextRepayDate", Formater.formatDate(bid.getExtend().getNextRepayDate()));//下个还款日
			map.put("status", "回收中");
			map.put("title", bid.getTitle());
			map.put("bid", bid.getId());
			break;
		case 3:
			record = accountManageService.findByIdAndUserId(id, userId);
			bid = record.getBid();
			map.put("creditNumber", record.getCreditNumber());//债权ID
			map.put("buyingPrice", Formater.formatAmount(record.getBuyingPrice()));//原始投资金额
			map.put("obtainPrice", Formater.formatAmount(bidRepaymentRecordService.findObtainPrice(id, userId)));//已赚金额
			map.put("rate", Formater.formatRate(bid.getRate()));//年利率
			map.put("closeOffTime", Formater.formatDate(bid.getExtend().getCloseOffTime()));//结清日期
			map.put("status", "已结清");
			map.put("title", bid.getTitle());
			map.put("bid", bid.getId());
			break;
		default:
			break;
		}
		return map;
	}

	/**
	 * 体验金账户
	 * 
	 * @param userId
	 */
	@RequestMapping("/experienceAccount")
	@ResponseBody
	public Map<String, Object> experienceAccount(@RequestParam Long userId) {
		Map<String, Object> map = new HashMap<>();
		ExperienceAccount experienceAccount = experienceAccountService.findOrAddByUserId(userId);
		map.put("balance", experienceAccount.getBalance().intValue());
		BigDecimal investMoney = experienceBidRecordService.findInvestExperienceMoney(userId);
		map.put("investMoney", investMoney.intValue());
		return map;
	}

	/**
	 * 体验金已投标记录
	 * 
	 * @param userId
	 * @param page
	 * @param size
	 */
	@RequestMapping("/experienceBidRecord")
	@ResponseBody
	public Map<String, Object> experienceBidRecord(@RequestParam Long userId, @RequestParam int page, @RequestParam int size) {
		Page<ExperienceBidRecord> bidRecordPage = experienceBidRecordService.findByUserId(userId, getPageable(page, size, new Sort(Direction.DESC, "investTime")));
		Map<String, Object> map = new HashMap<>();
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> recordMap = null;
		String repalyTime = null;
		for (ExperienceBidRecord bidRecord : bidRecordPage) {
			recordMap = new HashMap<>();
			ExperienceBid bid = experienceBidService.findById(bidRecord.getBidPk());
			recordMap.put("id", bid.getId());
			recordMap.put("title", bid.getTitle());
			recordMap.put("investPrice", bidRecord.getInvestPrice().intValue());
			recordMap.put("interest", bidRecord.getInterest());
			if (bidRecord.getRepalyTime() == null) {
				if (bid.getStatus() == BidStatus.HKZ)
					repalyTime = Formater.formatDate(new DateTime(bid.getLoanTime()).plusDays(bid.getLoanPeriod()).toDate());
				else
					repalyTime = "--";
			} else {
				repalyTime = Formater.formatDate(bidRecord.getRepalyTime());
			}
			recordMap.put("repalyTime", repalyTime);
			list.add(recordMap);
		}
		map.put("hasNext", bidRecordPage.hasNext() ? 1 : 0);
		map.put("content", list);
		return map;
	}

	/**
	 * 体验金来源
	 * 
	 * @param userId
	 * @param page
	 * @param size
	 */
	@RequestMapping("/experienceSource")
	@ResponseBody
	public Map<String, Object> experienceSource(@RequestParam Long userId, @RequestParam int page, @RequestParam int size) {
		Page<ExperienceAccountFlow> sourcePage = experienceAccountService.findSourceByUserId(userId, getPageable(page, size));
		Map<String, Object> map = new HashMap<>();
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> sourceMap = null;
		for (ExperienceAccountFlow accountSource : sourcePage) {
			sourceMap = new HashMap<>();
			sourceMap.put("source", accountSource.getSource().getChineseName());
			sourceMap.put("operateTime", Formater.formatDateTime(accountSource.getOperateTime()));
			sourceMap.put("money", accountSource.getMoney().intValue());
			list.add(sourceMap);
		}
		map.put("hasNext", sourcePage.hasNext() ? 1 : 0);
		map.put("content", list);
		return map;
	}

	/**
	 * 债权转让列表
	 */
	@RequestMapping("/creditorAssignmentList")
	@ResponseBody
	public Map<String, Object> creditorAssignmentList(@RequestParam Long userId, @RequestParam int type, @RequestParam int page, @RequestParam int size) {
		return creditAssignmentApplyService.findSimpleAssignmentByType(userId, type, page, size);
	}

	/**
	 * 债权转让详情
	 */
	@RequestMapping("/creditorAssignmentDetail")
	@ResponseBody
	public Map<String, Object> creditorAssignmentDetail(@RequestParam Long zqId, @RequestParam int type) {
		return creditAssignmentApplyService.findByIdAndType(zqId, type);
	}

	/**
	 * 债权转让
	 */
	@RequestMapping("/transfer")
	@ResponseBody
	public void transfer(@RequestParam Long zqId, @RequestParam BigDecimal price) {
		if (zqId == null) {
			throw new ServiceException(InterfaceResultCode.ILLEGAL_REQUEST, "非法请求");
		}
		if (price.compareTo(BigDecimal.ZERO) <= 0) {
			throw new ServiceException(InterfaceResultCode.FAILED, "转让价格必须大于0");
		}
		creditAssignmentApplyService.transfer(zqId, price);
	}

	/**
	 * 取消转让
	 * 
	 * @param zqId
	 */
	@RequestMapping("/cancel")
	@ResponseBody
	public void cancel(@RequestParam Long zqId) {
		if (zqId == null) {
			throw new ServiceException(InterfaceResultCode.ILLEGAL_REQUEST, "非法请求");
		}
		creditAssignmentApplyService.cancel(zqId);
	}

	/**
	 * 回帐查询列表(收款明细列表)
	 * 
	 * @param userId
	 * @param type
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping("/backAccountList")
	@ResponseBody
	public Map<String, Object> backAccountList(@RequestParam Long userId, @RequestParam int type, @RequestParam int page, @RequestParam int size) {
		Sort sort = new Sort(new Order(Direction.DESC, "shouldDate"), new Order("id"));
		return accountManageService.findBackAccountListByType(type, userId, getPageable(page, size, sort));
	}

	/**
	 * 单条回帐查询详情(单条收款明细详情)(暂时不做)
	 * 
	 * @param userId
	 *            用户Id
	 * @param bidId
	 *            标Id
	 * @param issue
	 *            期号
	 * @param creditId
	 *            债权Id
	 */
	@RequestMapping("/backAccountDetail")
	@ResponseBody
	public Map<String, Object> backAccountDetail(@RequestParam int type, @RequestParam Long userId, @RequestParam Long bidId, @RequestParam Long creditId, @RequestParam Integer issue) {
		return accountManageService.findSingleBackAccountDetail(type, userId, bidId, creditId, issue);
	}

	/**
	 * 我的投资列表(版本2)
	 * 
	 * @param userId
	 * @param type
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping("/creditorListV2")
	@ResponseBody
	public Map<String, Object> creditorListV2(@RequestParam Long userId, @RequestParam int type, @RequestParam int page, @RequestParam int size) {
		Sort sort = null;
		switch (type) {
		case 1://投标中的债权
			sort = new Sort(Direction.DESC, "investTime");
			break;
		case 2://回收中的债权
			sort = new Sort(Direction.DESC, "createDate");
			break;
		case 3://已结清的债权
			sort = new Sort(Direction.DESC, "bid.extend.closeOffTime");
			break;
		}
		return accountManageService.findCreditorListByTypeV2(type, userId, getPageable(page, size, sort));
	}
	
	/**
	 * 查询收益
	 * 
	 * @param userId
	 * @param type
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping("/getProjectSy")
	@ResponseBody
	public Map<String, Object> getProjectSy(@RequestParam Long bidId, @RequestParam BigDecimal creditPrice) {
		return accountManageService.getProjectSy(bidId, creditPrice);
	}
	
	/**
	 * 债权转让列表(版本2)
	 * 
	 * @param userId
	 * @param type
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping("/creditorAssignmentListV2")
	@ResponseBody
	public Map<String, Object> creditorAssignmentListV2(@RequestParam Long userId, @RequestParam int type, @RequestParam int page, @RequestParam int size) {
		return creditAssignmentApplyService.findSimpleAssignmentByTypeV2(userId, type, page, size);
	}
	
	/**
	 * 红包列表
	 * @param userId 用户Id
	 * @param state 红包状态
	 * @param page 页数
	 * @param size 每页条数
	 * @return
	 */
	@RequestMapping("/hbList")
	@ResponseBody
	public Map<String, Object> hbList(@RequestParam Long userId, @RequestParam int state, @RequestParam int page, @RequestParam int size) {
		return redPacketRecordService.findHbList(userId, state, page, size);
	}
	
	/**
	 * 红包列表
	 * @param userId 用户Id
	 * @param state 红包状态
	 * @param page 页数
	 * @param size 每页条数
	 * @return
	 */
	@RequestMapping("/hbList2")
	@ResponseBody
	public Map<String, Object> hbList2(@RequestParam Long userId, @RequestParam int state, @RequestParam int page, @RequestParam int size) {
		return redPacketRecordService.findHbList2(userId, state, page, size);
	}
	
	/**
	 * 红包详情
	 * @param userId 用户Id
	 * @param id 红包记录Id
	 * @return
	 */
	@RequestMapping("/getHbRecord")
	@ResponseBody
	public RedPacketRecord getHbRecord(@RequestParam Long userId, @RequestParam Long id) {
		return redPacketRecordService.findHbRecord(id);
	}
	
	/**
	 * 可使用红包列表
	 * @param userId 用户Id
	 * @param ranges 投资下线
	 * @return
	 */
	// http://127.0.0.1:8200/app/account/getHbList.action?userId=6&ranges=500&bid=554
	@RequestMapping("/getHbList")
	@ResponseBody
	public List<Map<String, Object>> getHbList(@RequestParam Long userId, @RequestParam BigDecimal ranges,@RequestParam(required=false) Long bid) {
		List<RedPacketRecord> records=null;
		Bid vo=null;
		List<Map<String, Object>> list = new ArrayList<>();
		if(bid!=null && bid.intValue()>0){
			vo = bidDao.findOne(bid);
		}
		if(vo!=null && Judge.S==vo.getIsActivity()){
			records = redPacketRecordService.findHbList(userId, ranges,1);
		}else{
			records = redPacketRecordService.findHbList(userId, ranges,0);
		}
		for (RedPacketRecord redPacket : records) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", redPacket.getId());
			map.put("state", redPacket.getState());
			map.put("source", redPacket.getSource());
			map.put("type", redPacket.getType());
			map.put("amount", redPacket.getAmount());
			map.put("ranges", redPacket.getRanges());
			map.put("startTime", Formater.formatDate(redPacket.getStartTime()));
			map.put("endTime", Formater.formatDate(redPacket.getEndTime()));
			list.add(map);
		}
		return list;
	}
	
	/**
	 * 抵扣券列表
	 * @param userId 用户Id
	 * @param state 状态
	 * @param page 页数
	 * @param size 每页条数
	 * @return
	 */
	@RequestMapping("/dkList")
	@ResponseBody
	public Map<String, Object> dkList(@RequestParam Long userId, @RequestParam int state, @RequestParam int page, @RequestParam int size) {
		return deductionRecordService.findDkList(userId, state, page, size);
	}
	
	/**
	 * 抵扣券列表
	 * @param userId 用户Id
	 * @param state 状态
	 * @param page 页数
	 * @param size 每页条数
	 * @return
	 */
	@RequestMapping("/dkList2")
	@ResponseBody
	public Map<String, Object> dkList2(@RequestParam Long userId, @RequestParam int state, @RequestParam int page, @RequestParam int size) {
		return deductionRecordService.findDkList2(userId, state, page, size);
	}
	
	/**
	 * 抵扣券详情
	 * @param userId 用户Id
	 * @param id 红包记录Id
	 * @return
	 */
	@RequestMapping("/getDkRecord")
	@ResponseBody
	public DeductionRecord getDkRecord(@RequestParam Long userId, @RequestParam Long id) {
		return deductionRecordService.findDkRecord(id);
	}
	
	/**
	 * 可使用抵扣券列表
	 * 
	 * @param userId 用户Id
	 * @param ranges 投资下线
	 * @return
	 */
	// http://127.0.0.1:8200/app/account/getDkList.action?userId=6&ranges=500&bid=554
	@RequestMapping("/getDkList")
	@ResponseBody
	public List<Map<String, Object>> getDkList(@RequestParam Long userId, @RequestParam BigDecimal ranges,@RequestParam(required=false) Long bid) {
		List<DeductionRecord> records=null;
		Bid vo=null;
		List<Map<String, Object>> list = new ArrayList<>();
		if(bid!=null && bid.intValue()>0){
			vo = bidDao.findOne(bid);
		}
		if(vo!=null && Judge.S==vo.getIsActivity()){
			records = deductionRecordService.findDkList(userId, ranges,1);
		}else{
			records = deductionRecordService.findDkList(userId, ranges,0);
		}
		for (DeductionRecord deductionRecord : records) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", deductionRecord.getId());
			map.put("state", deductionRecord.getState());
			map.put("source", deductionRecord.getSource());
			map.put("type", deductionRecord.getType());
			map.put("amount", deductionRecord.getAmount());
			map.put("ranges", deductionRecord.getRanges());
			map.put("startTime", Formater.formatDate(deductionRecord.getStartTime()));
			map.put("endTime", Formater.formatDate(deductionRecord.getEndTime()));
			list.add(map);
		}
		return list;
	}
	
	/**
	 * 提现首页
	 * @param userId
	 * @param bankCardId
	 * @param amount
	 * @param withdrawPsd
	 */
	@RequestMapping("/withdrawIndex")
	@ResponseBody
	public Map<String, Object> withdrawIndex(@RequestParam Long userId,@RequestParam Long bankCardId) {
		return withdrawApplyService.withdrawIndex(userId, bankCardId);
	}
	
	
	/**
	 * 提现首页计算用户提现手续费
	 * @param userId
	 * @param amount
	 *
	 */
	@RequestMapping("/withdrawUserLines")
	@ResponseBody
	public Map<String, Object> withdrawUserLines(@RequestParam Long userId,@RequestParam BigDecimal amount) {
		return withdrawApplyService.withdrawUserLines(userId, amount);
		
	}
	/**
	 * 提交申请提现
	 * @param userId
	 * @param bankCardId
	 * @param amount
	 * @param withdrawPsd
	 */
	@RequestMapping("/withdrawApply")
	@ResponseBody
	public void withdrawApply(@RequestParam Long userId,@RequestParam Long bankCardId, @RequestParam BigDecimal amount,@RequestParam String withdrawPsd) {
		withdrawApplyService.opWithdrawApply(userId, bankCardId, amount, withdrawPsd);
	}
	
	/**
	 * 用户银行卡列表
	 * @param userId
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping("/bankCardList")
	@ResponseBody
	public Map<String, Object> bankCardList(@RequestParam Long userId,@RequestParam int page,@RequestParam int size) {
		return bankCardService.findBankCardList(userId, page, size);
	}
	
	/**
	 * 获得银行卡信息
	 * @param userId
	 * @param bankCardId
	 * @return
	 */
	@RequestMapping("/getBankCard")
	@ResponseBody
	public Map<String, Object> getBankCard(@RequestParam Long userId,@RequestParam Long bankCardId) {
		BankCard bankCard=bankCardService.findBankCard(bankCardId);
		Map<String, Object> returnMap=new HashMap<String, Object>();
		returnMap.put("id", bankCard.getId());
		returnMap.put("number", bankCard.getNumber());
		returnMap.put("bankId", bankCard.getBank().getId());
		returnMap.put("bankName", bankCard.getBank().getName());
		returnMap.put("areaId", bankCard.getArea().getId());
		returnMap.put("province", bankCard.getArea().getProvinceName());
		returnMap.put("city", bankCard.getArea().getCityName());
		returnMap.put("county", bankCard.getArea().getCountyName());
		returnMap.put("openingName", bankCard.getOpeningName());
		return returnMap;
	}
	
	/**
	 * 操作银行卡信息
	 * @param userId
	 * @param bankCardId
	 * @param areaId
	 * @param openingName
	 * @param number
	 */
	@RequestMapping("/opBankCard")
	@ResponseBody
	public void opBankCard(@RequestParam Long userId,@RequestParam Long bankId,@RequestParam Long areaId,@RequestParam String openingName,@RequestParam String number,@RequestParam(required=false) Long id) {
		bankCardService.opBankCard(userId,bankId,areaId,openingName,number,id);
	}
	
	/**
	 * 删除银行卡信息
	 * @param userId
	 * @param bankCardId
	 * @param areaId
	 * @param openingName
	 * @param number
	 */
	@RequestMapping("/delBankCard")
	@ResponseBody
	public void delBankCard(@RequestParam Long userId,@RequestParam Long id) {
		bankCardService.delBankCard(id);
	}
	
	/**
	 * 新增银行卡信息
	 * @param userId
	 */
	@RequestMapping("/addBankCard")
	@ResponseBody
	public Map<String, Object> addBankCard(@RequestParam Long userId) {
		AuthAccount authAccount = authAccountService.findOne(userId);
		UserSafeStatus userSafeStatus=safeStatusDao.findOne(userId);
		Map<String, Object> map = new HashMap<>();
		map.put("name", authAccount.getName());
		map.put("idCard", authAccount.getIdentity());
		map.put("isMoneyPwd", userSafeStatus.getMoneyPassStatus()==RealAuthSet.YSZ?1:0);
		return map;
	}
	
	/**
	 * 注册第三方托管
	 * @param userId
	 * @return
	 */
	@RequestMapping("/openAccount")
	public void openAccount(@RequestParam Long userId,@RequestParam(required=false) String retType,HttpServletResponse response)throws Throwable{
		String _retType=StringHelper.isEmpty(retType)?"openAccount":retType;
		Map<String,Object> param=chargeService.openAccount(userId,_retType);
		//请求汇潮开户
		FunctionUtil.submitForm(response, param, Constants.OPENACCOUNT_URL);
	}
	
	/**
	 * 充值首页
	 * @param userId
	 * @return
	 */
	@RequestMapping("/chargeIndex")
	@ResponseBody
	public Map<String, Object> chargeIndex(@RequestParam Long userId) {
		return chargeService.chargeIndex(userId);
	}
	
	/**
	 * 充值处理
	 * @param userId
	 * @param amount
	 * @return
	 */
	@RequestMapping("/opChargeApply")
	@ResponseBody
	public Map<String, Object> opChargeApply(@RequestParam Long userId,@RequestParam BigDecimal amount,@RequestParam(required=false) String retType) {
		String _retType=StringHelper.isEmpty(retType)?"charge":retType;
		return chargeService.opChargeApply(userId, amount,_retType);
	}
	
	
	/**
	 * 计算收益
	 * 
	 * @param userId
	 * @param type
	 * @param page
	 * @param size
	 * @return
	 */
	@RequestMapping("/inComeAction")
	@ResponseBody
	public Map<String, Object> inComeAction() {
		 accountManageService.IncomeRate(); 
		 Map<String, Object> map = new HashMap<>();
		 map.put("linkUrl", "success");
		 return map;
	}
	
	
}
