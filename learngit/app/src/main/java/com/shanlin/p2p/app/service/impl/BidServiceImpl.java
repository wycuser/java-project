package com.shanlin.p2p.app.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.shanlin.framework.controller.InterfaceResultCode;
import com.shanlin.framework.exception.ServiceException;
import com.shanlin.framework.utils.DateParser;
import com.shanlin.framework.utils.Formater;
import com.shanlin.framework.utils.FunctionClientUtil;
import com.shanlin.framework.utils.FunctionUtil;
import com.shanlin.p2p.app.constant.Constants;
import com.shanlin.p2p.app.constant.FeeCode;
import com.shanlin.p2p.app.constant.MessageConstants;
import com.shanlin.p2p.app.dao.BidCreditRecordDao;
import com.shanlin.p2p.app.dao.BidDao;
import com.shanlin.p2p.app.dao.BidExtendDao;
import com.shanlin.p2p.app.dao.BidRateDao;
import com.shanlin.p2p.app.dao.BidRepaymentRecordDao;
import com.shanlin.p2p.app.dao.CreditAccountDao;
import com.shanlin.p2p.app.dao.CreditAssignmentApplyDao;
import com.shanlin.p2p.app.dao.FundAccountDao;
import com.shanlin.p2p.app.dao.FundAccountFlowDao;
import com.shanlin.p2p.app.dao.OrdersDao;
import com.shanlin.p2p.app.dao.PaymentOrderDao;
import com.shanlin.p2p.app.dao.RepaymentStatusDao;
import com.shanlin.p2p.app.dao.UserAccountDao;
import com.shanlin.p2p.app.model.Bid;
import com.shanlin.p2p.app.model.BidCreditRecord;
import com.shanlin.p2p.app.model.BidExtend;
import com.shanlin.p2p.app.model.BidRate;
import com.shanlin.p2p.app.model.BidRepaymentRecord;
import com.shanlin.p2p.app.model.CreditAccount;
import com.shanlin.p2p.app.model.FundAccount;
import com.shanlin.p2p.app.model.FundAccountFlow;
import com.shanlin.p2p.app.model.Orders;
import com.shanlin.p2p.app.model.PaymentOrder;
import com.shanlin.p2p.app.model.RepaymentStatus;
import com.shanlin.p2p.app.model.UserAccount;
import com.shanlin.p2p.app.model.enums.BidRepaymentStatus;
import com.shanlin.p2p.app.model.enums.BidStatus;
import com.shanlin.p2p.app.model.enums.FundAccountType;
import com.shanlin.p2p.app.model.enums.Judge;
import com.shanlin.p2p.app.model.enums.OrdersSource;
import com.shanlin.p2p.app.model.enums.OrdersStatus;
import com.shanlin.p2p.app.model.enums.OrdersType;
import com.shanlin.p2p.app.model.enums.UserType;
import com.shanlin.p2p.app.service.BidService;
import com.shanlin.p2p.app.service.LetterService;
import com.shanlin.p2p.app.vo.huichao.TranDataModel;
import com.shanlin.p2p.app.vo.huichao.TranModel;

@Service
@Transactional(readOnly=true)
public class BidServiceImpl implements BidService {

	private static final Logger log = LoggerFactory.getLogger(BidServiceImpl.class);
	
	@Resource
	private BidDao bidDao;
	
	@Resource
	private CreditAssignmentApplyDao applyDao;
	
	@Resource
	private UserAccountDao userAccountDao;
	
	@Resource
	private BidRepaymentRecordDao bidRepaymentRecordDao;
	
	@Resource
	private OrdersDao ordersDao;
	
	@Resource
	private PaymentOrderDao paymentOrderDao;
	
	@Resource
	private FundAccountFlowDao fundAccountFlowDao;
	
	@Resource
	private FundAccountDao fundAccountDao;
	
	@Resource
	private BidCreditRecordDao bidCreditRecordDao;
	
	@Resource
	private CreditAccountDao creditAccountDao;
	
	@Resource
	private BidRateDao bidRateDao;
	
	@Resource
	private BidExtendDao bidExtendDao;
	
	@Resource
	private LetterService letterService;
	
	@Resource
	private  RepaymentStatusDao repaymentStatusDao;
	
	@Override
	public List<Bid> findBidByType(UserType userType, Pageable pageable){
		List<BidStatus> statusList = Lists.newArrayList(BidStatus.HKZ, BidStatus.TBZ, BidStatus.YFB, BidStatus.DFK, BidStatus.YJQ, BidStatus.YDF);
		if(userType != null)
			return bidDao.findSimpleBidByPageAndType(statusList, userType, pageable);
		else
			return bidDao.findSimpleBidByPage(statusList, pageable);
	}

	@Override
	public boolean isSubscribers(Long bid,String investName) {
		String result=bidDao.findInvestNameById(bid);
		if(result!=null && !result.equals(investName))
			return false;
		return true;
	}

	@Override
	public Map<String, Object> getMyLoanCount(Long userId) {
		Map<String, Object> returnMap = new LinkedHashMap<>();
		//借款总金额
		returnMap.put("countMoney", Formater.formatAmount(bidRepaymentRecordDao.getJkje(userId)));
		//逾期金额
		returnMap.put("overdueMoney", Formater.formatAmount(bidRepaymentRecordDao.getYqje(userId)));
		//待还金额
		returnMap.put("repayMoney", Formater.formatAmount(bidRepaymentRecordDao.getWhje(userId)));
		//近30天应还金额
		returnMap.put("newRepayMoney", Formater.formatAmount(bidRepaymentRecordDao.get30dh(userId)));
		return returnMap;
	}

	@Override
	public Map<String, Object> getHkzJk(Long userId, int page, int size) {
		Map<String, Object> returnMap = new LinkedHashMap<>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> listMap=null;
		Pageable pageable = new PageRequest(page - 1, size, Direction.DESC, "id");
		Page<Bid> resultPage=bidDao.getHkzJk(userId,pageable);
		for (Bid vo:resultPage.getContent()) {
			listMap=new LinkedHashMap<String, Object>();
			listMap.put("bid", vo.getId());
			listMap.put("title", vo.getTitle());
			listMap.put("key_1", "借款金额");
			listMap.put("value_1", Formater.formatAmount(vo.getAmount())+"元");
			listMap.put("key_2", "年利率");
			listMap.put("value_2", Formater.formatRate(vo.getRate()));
			listMap.put("key_3", "下个还款日");
			listMap.put("value_3", vo.getExtend().getNextRepayDate());
			listMap.put("key_4", "还款金额");
			listMap.put("value_4", Formater.formatAmount(bidDao.getXqhkje(vo.getId()))+"元");
			listMap.put("key_5", "剩余期数");
			listMap.put("value_5", vo.getExtend().getRemainPeriods()+"/"+vo.getExtend().getTotalPeriods()+"期");
			list.add(listMap);
		}
		returnMap.put("content", list);
		returnMap.put("hasNext", resultPage.hasNext() ? 1 : 0);
		return returnMap;
	}

	@Override
	public Map<String, Object> getYhqJk(Long userId, int page, int size) {
		Map<String, Object> returnMap = new LinkedHashMap<>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> listMap=null;
		Pageable pageable = new PageRequest(page - 1, size, Direction.DESC, "id");
		Page<Bid> resultPage=bidDao.getYhqJk(userId,pageable);
		for (Bid vo:resultPage.getContent()) {
			listMap=new LinkedHashMap<String, Object>();
			listMap.put("bid", vo.getId());
			listMap.put("title", vo.getTitle());
			listMap.put("key_1", "借款金额");
			listMap.put("value_1", Formater.formatAmount(vo.getAmount().subtract(vo.getResidueAmount()))+"元");
			listMap.put("key_2", "年利率");
			listMap.put("value_2", Formater.formatRate(vo.getRate()));
			listMap.put("key_3", "还款总额");
			listMap.put("value_3", Formater.formatAmount(bidDao.getHkze(vo.getId()))+"元");
			listMap.put("key_4", "还清日期");
			listMap.put("value_4", DateParser.format(vo.getExtend().getCloseOffTime()));
			listMap.put("key_5", "期限");
			listMap.put("value_5", vo.getExtend().getIsByDay()==Judge.S?vo.getExtend().getLoanPeriod()+"天":vo.getLoanPeriod()+"个月");
			list.add(listMap);
		}
		returnMap.put("content", list);
		returnMap.put("hasNext", resultPage.hasNext() ? 1 : 0);
		return returnMap;
	}

	@Override
	public Map<String, Object> loanDetail(Long bid,Long userId) {
		Map<String, Object> returnMap = new LinkedHashMap<>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> listMap=null;
		RepaymentStatus repaymentStatus=null;
		Object[][] resultList=bidRepaymentRecordDao.loanDetail(bid,userId);
		for (Object[] vo:resultList) {
			listMap=new LinkedHashMap<String, Object>();
			int num=Integer.parseInt(vo[0].toString());
			listMap.put("bid", bid);
			listMap.put("num", num);
			listMap.put("total", Formater.formatAmount(new BigDecimal(vo[1].toString())));
			listMap.put("repayDate", vo[2]);
			listMap.put("isPayment", 1);
			repaymentStatus=repaymentStatusDao.findByBidAndNum(bid, num);
			//还款中。
			if(repaymentStatus!=null && repaymentStatus.getStatus()==1){
				listMap.put("status", "还款中");
				listMap.put("isPayment", 0);
			}else{
				int days=bidRepaymentRecordDao.getOverduePay(bid, num);
				if(days<0){
					listMap.put("status", "未还");
				}else if(days==0){
					listMap.put("status", "已还");
					listMap.put("isPayment", 0);
				}else if(days <= 30){
					listMap.put("status", "逾期");
				}else{
					listMap.put("status", "严重逾期");
				}
			}
			list.add(listMap);
		}
		returnMap.put("content", list);
		returnMap.put("hasNext", 0);
		return returnMap;
	}

	@Override
	@Transactional
	public void repayment(Long id,Long bid,int num){
		Bid t6230=bidDao.lockById(bid);
		if(t6230.getStatus()!=BidStatus.HKZ && t6230.getStatus()!=BidStatus.YDF)
			throw new ServiceException(InterfaceResultCode.FAILED,"不是还款中状态,不能还款");
		int count=bidRepaymentRecordDao.repaymentRecord(bid, num);
		if(count>0){
			throw new ServiceException(InterfaceResultCode.FAILED,"请先还款前面的期数款项");
		}
		//锁定还款记录
		BidRepaymentRecord t6252=bidRepaymentRecordDao.LockById(id);
		if (t6252 == null) {
			throw new ServiceException(InterfaceResultCode.FAILED,"还款记录不存在");
		}
		if (t6252.getStatus() == BidRepaymentStatus.YH) {
			return;
		}
		BigDecimal fee=BigDecimal.ZERO;
		//借款人-- 还款人
		UserAccount t6110_j=userAccountDao.findOne(t6252.getRepaymentUserId());
		FundAccount hkrAccount = fundAccountDao.lockFundAccountByUserIdAndType(t6252.getRepaymentUserId(), FundAccountType.WLZH);
		//投资人-- 借款人
		UserAccount t6110_s=userAccountDao.findOne(t6252.getGatherUserId());
		FundAccount skrAccount = fundAccountDao.lockFundAccountByUserIdAndType(t6252.getGatherUserId(), FundAccountType.WLZH);
		if(hkrAccount==null){
			throw new ServiceException(InterfaceResultCode.FAILED,"还款人来往帐号不存在!");
		}
		if(skrAccount==null){
			throw new ServiceException(InterfaceResultCode.FAILED,"收款人来往帐号不存在!");
		}
		if(hkrAccount.getBalance().compareTo(t6252.getAmount())<0){
			throw new ServiceException(InterfaceResultCode.FAILED,"来往帐号金额不足！");
		}
		//添加订单记录
		Orders orders = new Orders();
		orders.setTypeCode(OrdersType.BID_REPAYMENT.orderType());
		orders.setStatus(OrdersStatus.DTJ);
		orders.setCreateTime(new Date());
		orders.setSource(OrdersSource.APP);
		orders.setUserId(t6252.getRepaymentUserId());
		orders = ordersDao.save(orders);
		//添加还款订单记录
		PaymentOrder t6506=new PaymentOrder();
		t6506.setId(orders.getId());
		t6506.setInvestUserId(t6252.getGatherUserId());
		t6506.setBid(t6252.getBid().getId());
		t6506.setCreditId(t6252.getCreditId());
		t6506.setIssue(t6252.getIssue());
		t6506.setAmount(t6252.getAmount());
		t6506.setFeeCode(t6252.getFeeCode());
		t6506=paymentOrderDao.save(t6506);
		
		orders.setStatus(OrdersStatus.DQR);
		orders.setSubmitTime(new Date());
		orders = ordersDao.save(orders);
		
		// 还款
		String prefix = t6230.getIsSxbao() == Judge.S? "善行宝":"散标";
		String name = t6230.getTitle();
		String comment = String.format("%s还款:%s 第%s期", prefix, name,Integer.toString(t6252.getIssue()));
		{
			// 扣减还款人资金账户
			hkrAccount.setBalance(hkrAccount.getBalance().subtract(t6506.getAmount()));
			if (hkrAccount.getBalance().compareTo(BigDecimal.ZERO) < 0) {
				throw new ServiceException(InterfaceResultCode.FAILED,"还款人账户余额不足");
			}
			hkrAccount.setLastUpdateTime(new Date());
			hkrAccount = fundAccountDao.save(hkrAccount);
			//添加流水记录
			FundAccountFlow wlFlow = new FundAccountFlow();
			wlFlow.setFundAccount(hkrAccount);
			wlFlow.setFeeCode(t6252.getFeeCode());
			wlFlow.setRemoteFundAccount(skrAccount);
			wlFlow.setExpenditure(t6506.getAmount());
			wlFlow.setBalance(hkrAccount.getBalance());
			wlFlow.setCreateTime(new Date());
			wlFlow.setRemark(comment);
			fundAccountFlowDao.save(wlFlow);
		}
		{
			// 增加投资人资金账户
			skrAccount.setBalance(skrAccount.getBalance().add(t6506.getAmount()));
			skrAccount.setLastUpdateTime(new Date());
			skrAccount = fundAccountDao.save(skrAccount);
			//添加流水记录
			FundAccountFlow wlFlow = new FundAccountFlow();
			wlFlow.setFundAccount(skrAccount);
			wlFlow.setFeeCode(t6252.getFeeCode());
			wlFlow.setRemoteFundAccount(hkrAccount);
			wlFlow.setIncome(t6506.getAmount());
			wlFlow.setBalance(skrAccount.getBalance());
			wlFlow.setCreateTime(new Date());
			wlFlow.setRemark(comment);
			fundAccountFlowDao.save(wlFlow);
		}
		
		// 更新债权持有金额
		if (t6506.getFeeCode() == FeeCode.TZ_BJ) {
			// 锁定债权信息
			BidCreditRecord  t6251=bidCreditRecordDao.lockById(t6252.getCreditId());
			if (t6251 == null) {
				throw new ServiceException(InterfaceResultCode.FAILED,"债权记录不存在");
			}
			t6251.setHoldPrice(t6251.getHoldPrice().subtract(t6506.getAmount()));
			if (t6251.getHoldPrice().compareTo(BigDecimal.ZERO) <= 0) {
				t6251.setHoldPrice(BigDecimal.ZERO);
			}
			t6251=bidCreditRecordDao.save(t6251);
			{
				if(t6230.getIsSxbao() == Judge.F){
					// 添加借款人信用额度
					CreditAccount creditAccount=creditAccountDao.lockById(t6110_j.getId());
					creditAccount.setCreditAmount(creditAccount.getCreditAmount().add(t6506.getAmount()));
					creditAccount.setLastUpdateTime(new Date());
					creditAccount=creditAccountDao.save(creditAccount);
					//还款信用额度返回 添加信用流水记录
					creditAccountDao.saveRecord(t6110_j.getId(), FeeCode.XY_HK_FH, t6506.getAmount(), creditAccount.getCreditAmount(), "还款信用额度返回");
				}
			}
		}else if (t6506.getFeeCode() == FeeCode.TZ_LX || t6506.getFeeCode() == FeeCode.TZ_FX) {
			// 计算投资管理费
			BidRate t6238=bidRateDao.findOne(t6230.getId());
			BigDecimal _fee = t6506.getAmount().multiply(t6238.getManageRate()).setScale(2,
					BigDecimal.ROUND_HALF_UP);
			fee=_fee;
			if (t6238 != null && t6238.getManageRate().compareTo(BigDecimal.ZERO) > 0
					&& fee.compareTo(BigDecimal.ZERO) > 0) {
				Long pid = applyDao.getPTID();
				// 锁定平台往来账户
				FundAccount ptwlzh = fundAccountDao.lockFundAccountByUserIdAndType(pid, FundAccountType.WLZH);
				if (ptwlzh == null){
					throw new ServiceException(InterfaceResultCode.FAILED, "平台往来资金账户不存在");
				}
				String feeComment = String.format("投资管理费:%s 第%s期", name,
						Integer.toString(t6252.getIssue()));
				{
					// 扣减收款人管理费
					skrAccount.setBalance(skrAccount.getBalance().subtract(fee));
					skrAccount.setLastUpdateTime(new Date());
					skrAccount = fundAccountDao.save(skrAccount);
					//添加流水记录
					FundAccountFlow wlFlow = new FundAccountFlow();
					wlFlow.setFundAccount(skrAccount);
					wlFlow.setFeeCode(FeeCode.GLF);
					wlFlow.setRemoteFundAccount(ptwlzh);
					wlFlow.setExpenditure(fee);
					wlFlow.setBalance(skrAccount.getBalance());
					wlFlow.setCreateTime(new Date());
					wlFlow.setRemark(feeComment);
					fundAccountFlowDao.save(wlFlow);
				}
				{
					// 平台收投资管理费
					ptwlzh.setBalance(ptwlzh.getBalance().add(fee));
					ptwlzh.setLastUpdateTime(new Date());
					ptwlzh = fundAccountDao.save(ptwlzh);
					//添加流水记录
					FundAccountFlow wlFlow = new FundAccountFlow();
					wlFlow.setFundAccount(ptwlzh);
					wlFlow.setFeeCode(FeeCode.GLF);
					wlFlow.setRemoteFundAccount(skrAccount);
					wlFlow.setIncome(fee);
					wlFlow.setBalance(ptwlzh.getBalance());
					wlFlow.setCreateTime(new Date());
					wlFlow.setRemark(feeComment);
					fundAccountFlowDao.save(wlFlow);
				}
			}
		}
		
		//查询本期数未还条数
		int remain = bidRepaymentRecordDao.whCount(bid, t6252.getIssue());
		//更新还款记录状态为已还
		t6252.setRepaymentTime(new Date());
		t6252.setStatus(BidRepaymentStatus.YH);
		t6252=bidRepaymentRecordDao.save(t6252);
		// 查询本期是否还完
		if (remain == 1) { 
			BidExtend bidExtend=bidExtendDao.LockById(bid);
			Date nextDate = bidRepaymentRecordDao.nextDate(bid, t6252.getIssue()+1);
			if (nextDate != null) {
				//更新下次还款日期
				bidExtend.setNextRepayDate(nextDate);
				bidExtend.setOverdue("F");
				bidExtend=bidExtendDao.save(bidExtend);
			}
			RepaymentStatus repaymentStatus=repaymentStatusDao.findByBidAndNum(bid, num);
			repaymentStatus.setStatus(0);//更新已结束
			repaymentStatusDao.save(repaymentStatus);
			//当期还完。更新剩余期数
			if (bidExtend.getRemainPeriods() > 0) {
				bidExtend.setRemainPeriods(bidExtend.getRemainPeriods()-1);
				bidExtend=bidExtendDao.save(bidExtend);
			}
			if(bidExtend.getRemainPeriods()==0){
				//更新状态为已结清，并保存结清日期
				t6230.setStatus(BidStatus.YJQ);
				t6230=bidDao.save(t6230);
				bidExtend.setCloseOffTime(new Date());
				bidExtend=bidExtendDao.save(bidExtend);
			}
			//判断标是否逾期
			int overdueCount=bidRepaymentRecordDao.overdueCount(bid);
			if(overdueCount>0){
				bidExtend.setOverdue("F");
				bidExtend=bidExtendDao.save(bidExtend);
			}
		}
		orders.setStatus(OrdersStatus.CG);
		orders.setSubmitTime(new Date());
		ordersDao.save(orders);
		
		Map<String, Object> args = new HashMap<>();
		args.put("datetime", Formater.formatDateTime(new Date()));
		args.put("amount", t6252.getAmount());
		//给投资人发送站内信
		String message = Formater.formatString(MessageConstants.LETTER_TZR_TBSK, args);
		letterService.sendLetter(t6110_s.getId(), comment, message);
		//给借款人发送站内信
		message = Formater.formatString(MessageConstants.LETTER_JKR_JKHK, args);
		letterService.sendLetter(t6110_j.getId(), comment, message);
		
		/** 转账处理 */
		String outTradeNo = FunctionUtil.getOutTradeNo(4, orders.getId(), orders.getCreateTime());
		String accountNumber = Constants.ACCOUNT_NUMBER;
		String key = Constants.MERCHANT_KEY;
		TranDataModel tranDataModel = new TranDataModel(accountNumber, orders.getId()+"_"+new Date().getTime());
		List<TranModel> tranModelList = new ArrayList<TranModel>();
		double amount = t6252.getAmount().doubleValue();
		String outName = t6110_j.getLoginName();
		String inName = t6110_s.getLoginName();
		TranModel jyModel = new TranModel(outTradeNo, amount, inName, outName, accountNumber, key);
		tranModelList.add(jyModel);
		//手续费
		if (fee.compareTo(new BigDecimal(0)) > 0) {
			String feeoutTradeNo = FunctionUtil.getOutTradeNo(5, orders.getId(), orders.getCreateTime());
			TranModel feeModel = new TranModel(feeoutTradeNo, fee.doubleValue(), accountNumber, inName, accountNumber, key);
			tranModelList.add(feeModel);
		}
		tranDataModel.setTranList(tranModelList);
		//请求第三方.发送转账请求
		Map<String, Object> result = FunctionClientUtil.sendTrade(tranDataModel);
		String resCode = result.get("resCode").toString();
		if (!resCode.contains(Constants.SUCCESS_CODE)) {
			log.error("还款失败："+resCode);
			throw new ServiceException(InterfaceResultCode.FAILED, resCode);
		}
	}

	@Override
	@Transactional
	public void addRepaymentStatus(Long bid, int num) {
		RepaymentStatus repaymentStatus=repaymentStatusDao.findByBidAndNum(bid, num);
		if(repaymentStatus==null){
			repaymentStatus=new RepaymentStatus();
			repaymentStatus.setBid(bid);
			repaymentStatus.setNum(num);
			repaymentStatus.setStatus(1);
			repaymentStatus.setCreateTime(new Date());
			repaymentStatusDao.save(repaymentStatus);
		}
	}
}