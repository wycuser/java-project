package com.shanlin.p2p.app.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.shanlin.framework.controller.InterfaceResultCode;
import com.shanlin.framework.exception.ServiceException;
import com.shanlin.framework.utils.BdUtil;
import com.shanlin.framework.utils.Formater;
import com.shanlin.p2p.app.constant.Constants;
import com.shanlin.p2p.app.constant.FeeCode;
import com.shanlin.p2p.app.dao.BidDao;
import com.shanlin.p2p.app.dao.BidExtendDao;
import com.shanlin.p2p.app.dao.BidOrdersDao;
import com.shanlin.p2p.app.dao.BidRepaymentRecordDao;
import com.shanlin.p2p.app.dao.CreditAssignmentApplyDao;
import com.shanlin.p2p.app.dao.DeductionDao;
import com.shanlin.p2p.app.dao.DeductionRecordDao;
import com.shanlin.p2p.app.dao.FundAccountDao;
import com.shanlin.p2p.app.dao.FundAccountFlowDao;
import com.shanlin.p2p.app.dao.InvestRecordDao;
import com.shanlin.p2p.app.dao.OrdersDao;
import com.shanlin.p2p.app.dao.RedPacketRecordDao;
import com.shanlin.p2p.app.dao.SurpriseConfigDao;
import com.shanlin.p2p.app.dao.SurpriseLucreDao;
import com.shanlin.p2p.app.dao.SxbaoConfigDao;
import com.shanlin.p2p.app.dao.SxbaoTypeDao;
import com.shanlin.p2p.app.dao.UserAccountDao;
import com.shanlin.p2p.app.dao.UserLinesDao;
import com.shanlin.p2p.app.model.Bid;
import com.shanlin.p2p.app.model.BidExtend;
import com.shanlin.p2p.app.model.BidOrders;
import com.shanlin.p2p.app.model.CreditAssignmentApply;
import com.shanlin.p2p.app.model.DeductionRecord;
import com.shanlin.p2p.app.model.FundAccount;
import com.shanlin.p2p.app.model.FundAccountFlow;
import com.shanlin.p2p.app.model.InvestRecord;
import com.shanlin.p2p.app.model.Orders;
import com.shanlin.p2p.app.model.RedPacketRecord;
import com.shanlin.p2p.app.model.SurpriseConfig;
import com.shanlin.p2p.app.model.SurpriseLucre;
import com.shanlin.p2p.app.model.SxbaoConfig;
import com.shanlin.p2p.app.model.SxbaoType;
import com.shanlin.p2p.app.model.UserAccount;
import com.shanlin.p2p.app.model.enums.BidStatus;
import com.shanlin.p2p.app.model.enums.CreditAssignmentStatus;
import com.shanlin.p2p.app.model.enums.FundAccountType;
import com.shanlin.p2p.app.model.enums.FundCheckStatus;
import com.shanlin.p2p.app.model.enums.Judge;
import com.shanlin.p2p.app.model.enums.OrdersSource;
import com.shanlin.p2p.app.model.enums.OrdersStatus;
import com.shanlin.p2p.app.model.enums.OrdersType;
import com.shanlin.p2p.app.model.enums.UserType;
import com.shanlin.p2p.app.service.DeductionRecordService;
import com.shanlin.p2p.app.service.FinancingService;
import com.shanlin.p2p.app.service.LetterService;
import com.shanlin.p2p.app.service.RedPacketRecordService;
import com.shanlin.p2p.app.service.SmsService;
import com.shanlin.p2p.app.service.UserLinesService;

@Service
@Transactional(readOnly=true)
public class FinancingServiceImpl implements FinancingService{
	
	private static final Logger log = LoggerFactory.getLogger(FinancingServiceImpl.class);

	@Resource
	private BidDao bidDao;
	
	@Resource
	private CreditAssignmentApplyDao applyDao;
	
	@Resource
	private BidExtendDao bidExtendDao;
	
	@Resource
	private FundAccountDao fundAccountDao;
	
	@Resource
	private FundAccountFlowDao fundAccountFlowDao;
	
	@Resource
	private OrdersDao ordersDao;
	
	@Resource
	private BidOrdersDao bidOrdersDao;
	
	@Resource
	private InvestRecordDao investRecordDao;
	
	@Resource
	private SurpriseLucreDao surpriseLucreDao;
	
	@Resource
	private BidRepaymentRecordDao bidRepaymentRecordDao;
	
	@Resource
	private UserAccountDao userAccountDao;
	
	@Resource
	private SurpriseConfigDao surpriseConfigDao;
	
	@Resource
	private SxbaoTypeDao sxbaoTypeDao;
	
	@Resource
	private SxbaoConfigDao sxbaoConfigDao;
	
	@Resource
	private RedPacketRecordService recordService;
	
	@Resource
	private DeductionRecordService deductionService;
	
	@Resource
	private UserLinesService userLinesService;
	
	@Resource
	private RedPacketRecordDao redPacketRecordDao;
	
	@Resource
	private DeductionDao deductionDao;
	
	@Resource
	private DeductionRecordDao deductionRecordDao;
	
	@Resource
	private SmsService smsService;
	
	@Resource
	private LetterService letterService;
	
	@Resource
	private UserLinesDao userLinesDao;
	
	@Override
	public Map<String, Object> findBidOrAssignmentByType(int type, int page, int size) {
		UserType userType = null;
		switch (type) {
		case 1://个人
			userType = UserType.ZRR;
			break;
		case 2://企业
			userType = UserType.FZRR;
			break;
		}
		Map<String, Object> returnMap = new LinkedHashMap<>();
		List<Map<String, Object>> list = new ArrayList<>();
		//债券转让
		if(type == 3){
			Page<CreditAssignmentApply> creditPage = applyDao
					.findByStatusAndCreditRecordIsMakeOverNow(CreditAssignmentStatus.ZRZ, Judge.S,
							new PageRequest(page - 1, size, new Sort(Direction.DESC, "createTime")));
			for(CreditAssignmentApply assignment : creditPage){
				list.add(assignment.toMap());
			}
			returnMap.put("hasNext", creditPage.hasNext()?1:0);
//			returnMap.put("hasNext", 0);
		} else {
			List<BidStatus> statusList = Lists.newArrayList(BidStatus.HKZ, BidStatus.TBZ, BidStatus.YFB, BidStatus.DFK, BidStatus.YJQ, BidStatus.YDF);
			Page<Bid> bidPage = null;
			Pageable pageable = new PageRequest(page - 1, size, new Sort(new Order("status"), new Order(Direction.DESC, "publishTime")));
			if(userType != null)
				bidPage = bidDao.findBidByPageAndType(statusList, userType, pageable);
			else
				bidPage = bidDao.findBidByPage(statusList, pageable);
			for(Bid bid : bidPage){
				list.add(bid.toMap());
			}
			returnMap.put("hasNext", bidPage.hasNext()?1:0);
		}
		returnMap.put("content", list);
		return returnMap;
	}

	@Override
	public Bid findOneBid(Long id) {
		return bidDao.findOne(id);
	}

	@Override
	public BidExtend findBidExtendById(Long id) {
		return bidExtendDao.findOne(id);
	}

	@Override
	public BidStatus findBidStatusById(Long id) {
		return bidDao.findBidStatusById(id);
	}

	@Override
	@Transactional
	public Long invest(Long id, UserAccount user, BigDecimal money, Long hbId, Long dkId) {
		Long userId = user.getId();
		// 锁定标
		Bid bid = bidDao.lockById(id);
		if(bid == null)
			throw new ServiceException(InterfaceResultCode.FAILED, "指定的标记录不存在");
		if(bid.getUserAccount().getId().compareTo(userId) == 0)
			throw new ServiceException(InterfaceResultCode.FAILED, "自己不能投自己标");
		if(bid.getStatus() != BidStatus.TBZ || new DateTime(bid.getPublishTime()).plusDays(bid.getFindPeriod()).getMillis() - System.currentTimeMillis() <=0)
			throw new ServiceException(InterfaceResultCode.FAILED, "指定的标不是投标中状态,不能投标");
		if(money.compareTo(bid.getResidueAmount()) > 0)
			throw new ServiceException(InterfaceResultCode.FAILED, "投标金额大于可投金额");
		if(bidRepaymentRecordDao.findTimeLimitRecordCount(userId).intValue() > 0)
			throw new ServiceException(InterfaceResultCode.FAILED, "借款逾期未还，不能投标！");
		BigDecimal residue = bid.getResidueAmount().subtract(money);
		if(residue.compareTo(BigDecimal.ZERO) > 0 && residue.compareTo(new BigDecimal(Constants.SYS_MIN_INVEST_AMOUNT)) < 0)
			throw new ServiceException(InterfaceResultCode.FAILED, "剩余可投金额不能低于最低起投金额");
		//新手标的处理
		if (bid.getIsNovice() == Judge.S){
			boolean isVipUser=investRecordDao.isVipUser(userId)>0;
			if(!isVipUser){
				boolean isInvest=investRecordDao.isInvest(bid.getId(),userId)>0;
	        	if(isInvest){
	        		BigDecimal limitAmount=new BigDecimal("10000");
	        		BigDecimal total=investRecordDao.investSum(bid.getId(),userId);
	        		if(total.add(money).compareTo(limitAmount)>0){
	        			throw new ServiceException("小主，您这个标可投1万，剩下的留给其他小伙伴投呗！");
	        		}
	        	}
			}
        }
		//标的扩展信息
//		BidExtend bidExtend=bidExtendDao.findOne(id);
//		if(bidExtend.getLoanPeriod()==1 && hbId>0l){
		if(bid.getIsActivity() == Judge.S && hbId>0l){
			RedPacketRecord record=redPacketRecordDao.findOne(hbId);
			if(record==null || record.getUseActivity()!=1){
				throw new ServiceException(InterfaceResultCode.FAILED, "该红包不支持活动标使用");
			}
		}
		if(bid.getIsActivity() == Judge.S && dkId>0l){
			DeductionRecord record=deductionRecordDao.findOne(dkId);
			if(record==null || record.getUseActivity()!=1){
				throw new ServiceException(InterfaceResultCode.FAILED, "该抵扣券不支持活动标使用");
			}
		}
		
		// 锁定投资人资金账户
		FundAccount fundAccount = fundAccountDao.lockFundAccountByUserIdAndType(userId, FundAccountType.WLZH);
		if(fundAccount == null){
			log.error("用户往来帐号不存在，id{}", userId);
			throw new ServiceException(InterfaceResultCode.SYS_ERROR, "系统繁忙");
		}
		if(fundAccount.getBalance().compareTo(money) < 0)
			throw new ServiceException(InterfaceResultCode.FAILED, "账户余额不足");
		//活动标校验用户在该标的投资总金额是否超过活动标最大投资金额
		BigDecimal xt = Constants.ACTIVE_MAX_BID_AMOUNT;
//		BigDecimal userPrice = investRecordDao.findPriceByUserAndBid(id, userId);
//		if(bid.getIsActivity() == Judge.S && System.currentTimeMillis() >= 1449072000000L && money.add(userPrice == null?BigDecimal.ZERO:userPrice).compareTo(xt) > 0){
//			throw new ServiceException(InterfaceResultCode.FAILED, "同一活动标累计限投"+(xt.intValue()/10000 > 0? xt.intValue()/10000+"万":xt.intValue()));
//		}
		Long investId = investRecordDao.findInvestIdByUserId(userId);
		Long zqzrId = investRecordDao.findZqzrIdByUserId(userId);
		if (bid.getIsActivity() == Judge.S && id == 362l) {
			if(investId != null || zqzrId != null){
				throw new ServiceException(InterfaceResultCode.FAILED, "只允许首次投资的客户投标");
			}else if(money.compareTo(xt) > 0){
				throw new ServiceException(InterfaceResultCode.FAILED, "活动标限投"+(xt.intValue()/10000 > 0? xt.intValue()/10000+"万":xt.intValue()));
			}
		}
		// 插入订单
		Orders orders = new Orders();
		orders.setTypeCode(OrdersType.BID.orderType());
		orders.setCreateTime(new Date());
		orders.setStatus(OrdersStatus.DTJ);
		orders.setSource(OrdersSource.YH);
		orders.setUserId(userId);
		orders = ordersDao.save(orders);
		// 插入投标订单
		BidOrders bidOrders = new BidOrders();
		bidOrders.setOrdersId(orders.getId());
		bidOrders.setUserId(userId);
		bidOrders.setBidPk(id);
		bidOrders.setMoney(money);
		bidOrders = bidOrdersDao.save(bidOrders);
		//减去可投金额
		bid.setResidueAmount(residue);
		//锁定投资人锁定账户
		FundAccount sdzh = fundAccountDao.lockFundAccountByUserIdAndType(userId, FundAccountType.SDZH);
		if(sdzh == null){
			log.error("用户锁定帐号不存在，id{}", userId);
			throw new ServiceException(InterfaceResultCode.SYS_ERROR, "系统繁忙");
		}
		
		BigDecimal zcAmount = money;
		if(dkId!=null&&dkId>0l){
			// 计算抵扣券
			BigDecimal kyDeDuAmount = deductionRecordDao.getKyDeDuAmount(dkId);// 获取可用抵扣金额
			if(money.compareTo(kyDeDuAmount)<0){
				throw new ServiceException("投资金额不能小于抵扣金额");
			}
			zcAmount = money.subtract(kyDeDuAmount);// 投资人支出金额
		}
    	
		fundAccount.setBalance(fundAccount.getBalance().subtract(zcAmount));
		fundAccountDao.save(fundAccount);
		String remark = bid.getIsSxbao()==Judge.F?"散标":"善行宝";
        String title = bid.getIsSxbao()==Judge.F?bid.getBidNumber():bid.getTitle();
        
		//插入往来资金账户流水
		FundAccountFlow wlFlow = new FundAccountFlow();
		wlFlow.setFundAccount(fundAccount);
		wlFlow.setFeeCode(FeeCode.TZ);
		wlFlow.setRemoteFundAccount(sdzh);
		wlFlow.setExpenditure(zcAmount);// 支出
		wlFlow.setBalance(fundAccount.getBalance());
		wlFlow.setCreateTime(new Date());
		wlFlow.setRemark(String.format("%s投资:%s", remark, title));
		wlFlow.setFundCheckStatus(FundCheckStatus.WDZ);
		fundAccountFlowDao.save(wlFlow);
		sdzh.setBalance(sdzh.getBalance().add(zcAmount));
		fundAccountDao.save(sdzh);
		//插入锁定账户流水
		FundAccountFlow sdFlow = new FundAccountFlow();
		sdFlow.setFundAccount(sdzh);
		sdFlow.setFeeCode(FeeCode.TZ);
		sdFlow.setRemoteFundAccount(fundAccount);
		sdFlow.setIncome(zcAmount);
		sdFlow.setBalance(sdzh.getBalance());
		sdFlow.setCreateTime(new Date());
		sdFlow.setRemark(String.format("%s投资:%s", remark, title));
		sdFlow.setFundCheckStatus(FundCheckStatus.WDZ);
		fundAccountFlowDao.save(sdFlow);
		
		//更新计算提现费用的额度
		Map<String, Object> mapUserLines = userLinesService.handleUserLines(userId, money);
		BigDecimal invelines = getSafeBigDecimal(mapUserLines.get("invelines"));
				
		//插入投标记录
		InvestRecord investRecord = new InvestRecord();
		investRecord.setBid(bid);
		investRecord.setUserAccount(user);
		investRecord.setBuyingPrice(money);
		investRecord.setCreditPrice(money);
		investRecord.setInvestTime(new Date());
		investRecord.setIsMobileInvest(Judge.S);
		investRecord.setUsLines(invelines);
		//计算预期收益
		Date currentDate = investRecordDao.getCurrentDate();
		InvestRecord[] investRecords = new InvestRecord[] { investRecord };
		try {
			BigDecimal inCome = BdUtil.calcSy(currentDate, bid, bid.getExtend(), investRecords);
			investRecord.setIncome(inCome);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		investRecord = investRecordDao.save(investRecord);
		bidOrders.setBidRecordId(investRecord.getId());
		bidOrdersDao.save(bidOrders);
		//如果满标
		if(bid.getResidueAmount().compareTo(BigDecimal.ZERO) <= 0){
			bid.getExtend().setFillBidTime(new Date());
			bid.setStatus(BidStatus.DFK);
			//满标发送给龚玉婷、余杭、周总
			String str = "您好，标：" + bid.getTitle() + ",金额：" + bid.getAmount().divide(new BigDecimal(10000)) + "万,期限：" + bid.getLoanPeriod() + "个月 已满标。退订回复TD【善行创投】";
			smsService.send(str, "13421816639", "13808804286", "13590169983");
		}
		bidDao.save(bid);
		orders.setStatus(OrdersStatus.CG);
		ordersDao.save(orders);
		if(hbId>0l){
			recordService.useRedPacket(userId, id, investRecord, hbId);
		}
		/**激活红包*/
		Long parentId=redPacketRecordDao.findParentId(userId);
		if(parentId!=null &&parentId.intValue()>0){
			redPacketRecordDao.activation(new Date(), userId);
			letterService.sendLetter(userId, "红包激活", "亲爱的善粉，您邀请好友系统赠送的红包已激活，投资使用可返现哦~");
		}
		
		if(dkId>0l){
			deductionService.useDeduction(userId, id, investRecord, dkId);
		}
		/**激活抵扣券*/
		parentId=redPacketRecordDao.findParentId(userId);
		if(parentId!=null &&parentId.intValue()>0){
			deductionRecordDao.activation(new Date(), userId);
		}
		return investRecord.getId();
	}

	@Override
	public Page<Object[]> findInvestRecordBy(Long bid, Pageable pageable) {
		return investRecordDao.findByBidAndIsCancel(bid, Judge.F, pageable);
	}

	@Override
	public Long countByBid(Long bid){
		return investRecordDao.countByBidIdAndIsCancel(bid, Judge.F);
	}
	
	@Override
	public BigDecimal findAllBuyingPriceByBid(Bid bid){
		BigDecimal allBuying = investRecordDao.findAllBuyingPriceByBid(bid, Judge.F);
		return allBuying == null ? BigDecimal.ZERO : allBuying;
	}
	
	@Override
	public List<SurpriseLucre> findSurpriseLucreByBid(Long id) {
		return surpriseLucreDao.findByConfigBid(id);
	}

	@Override
	public String findTitleByBid(Long id) {
		return bidDao.findTitleById(id);
	}

	@Override
	public List<Object[]> findRepaymentPlanByBid(Long id) {
		return bidRepaymentRecordDao.findRepaymentPlanByBid(id);
	}

	@Override
	public List<SurpriseConfig> findSurpriseConfigByBid(Long id) {
		return surpriseConfigDao.findByBidOrderByNameAsc(id);
	}

	@Override
	public Map<String, Object> findSxbaoTypes(Pageable pageable) {
		Map<String, Object> map = new HashMap<>();
		Page<Object[]> page = sxbaoTypeDao.findTypes(pageable);
		map.put("hasNext", page.hasNext()?1:0);
		List<Object[]> list = page.getContent();
		List<Map<String, Object>> content = new ArrayList<>();
		for(Object[] arr : list){
			Map<String, Object> args = new HashMap<>();
			args.put("id", arr[0]);
			args.put("name", arr[1]);
			args.put("sellStatus", arr[2]);
			args.put("totalAmount", Formater.formatAmount(arr[3] == null?BigDecimal.ZERO:(BigDecimal)arr[3]));
			content.add(args);
		}
		map.put("content", content);
		return map;
	}

	@Override
	public List<Map<String, Object>> findSxbaoByTypeId(Long typeId) {
		List<SxbaoConfig> configList = sxbaoConfigDao.findByTypeId(typeId);
		List<Map<String, Object>> content = new ArrayList<>();
		for (SxbaoConfig sxbaoConfig : configList) {
			Map<String, Object> args = new HashMap<>();
			args.put("id", sxbaoConfig.getId());
			args.put("title", sxbaoConfig.getTitle());
			Bid bid = sxbaoConfig.getBid();
			args.put("rate", bid.getRate().setScale(4));
			args.put("mode", bid.getMode().getChineseName());
			args.put("loanPeriod", bid.getLoanPeriod());
			args.put("investFloor", sxbaoConfig.getInvestFloor());
			content.add(args);
		}
		return content;
	}
	
	@Override
	public SxbaoConfig findSxbaoConfigById(Long id) {
		return sxbaoConfigDao.findActivityById(id);
	}

	@Override
	public SxbaoType findOneSxbaoType(Long id) {
		return sxbaoTypeDao.findOne(id);
	}

	@Override
	public Long invest(Long id, UserAccount user, BigDecimal money) {
		return invest(id, user, money, 0l, 0l);
	}
	
	/**
	 * 字符检查:是否字符串为空
	 * 
	 * @param str
	 * @return 是否字符串为空
	 */
	public static Boolean isBlank(String str) {
		int strLen;
		if ((str == null) || ((strLen = str.length()) == 0)) {
			return true;
		}

		// 若为null字符串也表示为空
		if (str.trim().equalsIgnoreCase("null")) {
			return true;
		}

		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	
	// 获取安全Integer
	public static Integer getSafeInteger(Object o) {
		if (o == null || isBlank(o.toString())) {
			return 0;
		}
		return Integer.parseInt(o.toString());
	}
	

	// 获取安全BigDecimal
	public static BigDecimal getSafeBigDecimal(Object o) {
		if (o == null) {
			return new BigDecimal(0.00);
		}

		String str = o.toString().trim();
		if (isBlank(str)) {
			return new BigDecimal(0.00);
		}

		return new BigDecimal(str);
	}
}
