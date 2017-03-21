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
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.shanlin.framework.controller.InterfaceResultCode;
import com.shanlin.framework.exception.ServiceException;
import com.shanlin.framework.utils.DateHelper;
import com.shanlin.framework.utils.Formater;
import com.shanlin.framework.utils.FunctionClientUtil;
import com.shanlin.framework.utils.FunctionUtil;
import com.shanlin.p2p.app.constant.Constants;
import com.shanlin.p2p.app.constant.FeeCode;
import com.shanlin.p2p.app.dao.AgreementDao;
import com.shanlin.p2p.app.dao.BidCreditRecordDao;
import com.shanlin.p2p.app.dao.BidRepaymentRecordDao;
import com.shanlin.p2p.app.dao.CreditAssignmentApplyDao;
import com.shanlin.p2p.app.dao.CreditAssignmentOrderDao;
import com.shanlin.p2p.app.dao.CreditAssignmentRecordDao;
import com.shanlin.p2p.app.dao.CreditAssignmentStatisticsDao;
import com.shanlin.p2p.app.dao.CreditAssignmentVersionDao;
import com.shanlin.p2p.app.dao.FundAccountDao;
import com.shanlin.p2p.app.dao.FundAccountFlowDao;
import com.shanlin.p2p.app.dao.OrdersDao;
import com.shanlin.p2p.app.model.Agreement;
import com.shanlin.p2p.app.model.BidCreditRecord;
import com.shanlin.p2p.app.model.BidRepaymentRecord;
import com.shanlin.p2p.app.model.CreditAssignmentApply;
import com.shanlin.p2p.app.model.CreditAssignmentOrder;
import com.shanlin.p2p.app.model.CreditAssignmentRecord;
import com.shanlin.p2p.app.model.CreditAssignmentStatistics;
import com.shanlin.p2p.app.model.CreditAssignmentVersion;
import com.shanlin.p2p.app.model.FundAccount;
import com.shanlin.p2p.app.model.FundAccountFlow;
import com.shanlin.p2p.app.model.Orders;
import com.shanlin.p2p.app.model.UserAccount;
import com.shanlin.p2p.app.model.enums.BidRepaymentStatus;
import com.shanlin.p2p.app.model.enums.CreditAssignmentStatus;
import com.shanlin.p2p.app.model.enums.FundAccountType;
import com.shanlin.p2p.app.model.enums.Judge;
import com.shanlin.p2p.app.model.enums.OrdersSource;
import com.shanlin.p2p.app.model.enums.OrdersStatus;
import com.shanlin.p2p.app.model.enums.OrdersType;
import com.shanlin.p2p.app.service.CreditAssignmentApplyService;
import com.shanlin.p2p.app.vo.huichao.TradeMap;
import com.shanlin.p2p.app.vo.huichao.TranDataModel;
import com.shanlin.p2p.app.vo.huichao.TranModel;

@Service
@Transactional(readOnly = true)
public class CreditAssignmentApplyServiceImpl implements CreditAssignmentApplyService {

	private static final Logger log = LoggerFactory.getLogger(CreditAssignmentApplyServiceImpl.class);
	@Resource
	private CreditAssignmentApplyDao applyDao;

	@Resource
	private BidCreditRecordDao bidCreditRecordDao;

	@Resource
	private CreditAssignmentRecordDao creditAssignmentRecordDao;

	@Resource
	private BidRepaymentRecordDao bidRepaymentRecordDao;

	@Resource
	private AgreementDao agreementDao;

	@Resource
	private CreditAssignmentVersionDao versionDao;

	@Resource
	private FundAccountDao fundAccountDao;

	@Resource
	private OrdersDao ordersDao;

	@Resource
	private FundAccountFlowDao fundAccountFlowDao;

	@Resource
	private CreditAssignmentStatisticsDao statisticsDao;

	@Resource
	private CreditAssignmentOrderDao creditAssignmentOrderDao;

	@Override
	public CreditAssignmentApply findFirstZrzAssignment() {
		return applyDao.findFirstByStatusAndCreditRecordIsMakeOverNow(CreditAssignmentStatus.ZRZ, Judge.S);
	}

	@Override
	public Page<CreditAssignmentApply> findZrzAssignmentByPage(Pageable pageable) {
		return applyDao.findByStatusAndCreditRecordIsMakeOverNow(CreditAssignmentStatus.ZRZ, Judge.S, pageable);
	}

	@Override
	public Map<String, Object> findSimpleAssignmentByType(Long userId, int type, int page, int size) {
		if (type < 1 || type > 4)
			throw new ServiceException(InterfaceResultCode.ILLEGAL_REQUEST, "非法请求");
		Map<String, Object> returnMap = new LinkedHashMap<>();
		List<Map<String, Object>> list = new ArrayList<>();
		Page<Object[]> pageList = null;
		Pageable pageable = null;
		switch (type) {
		case 1:
			//转让中的债权
			pageable = new PageRequest(page - 1, size, Direction.DESC, "createTime");
			pageList = applyDao.findZqzrzList(userId, pageable);
			break;
		case 2:
			//可转让的债权
			pageable = new PageRequest(page - 1, size, Direction.DESC, "bid.extend.loanTime");
			Date date1 = new DateTime().plusDays(3).toDate();
			Date date2 = new DateTime().plusDays(-(Constants.ZQZR_CY_DAY)).toDate();
			pageList = bidCreditRecordDao.findZqkzcList(userId, date1, date2, pageable);
			break;
		case 3:
			//已转出的债权
			pageable = new PageRequest(page - 1, size, Direction.DESC, "buyTime");
			pageList = creditAssignmentRecordDao.findZqyzcList(userId, pageable);
			break;
		case 4:
			//已转入的债权
			pageable = new PageRequest(page - 1, size, Direction.DESC, "buyTime");
			pageList = creditAssignmentRecordDao.findZqyzrList(userId, pageable);
			break;
		}
		Map<String, Object> map = null;
		for (Object[] objects : pageList) {
			map = new LinkedHashMap<String, Object>();
			/** zqId：债权标识 creditNumber：债权ID title:转让名称 */
			map.put("zqId", objects[0]);
			map.put("creditNumber", objects[1]);
			map.put("title", objects[2]);
			list.add(map);
		}
		returnMap.put("hasNext", pageList.hasNext() ? 1 : 0);
		returnMap.put("content", list);
		return returnMap;
	}

	@Override
	public Map<String, Object> findByIdAndType(Long id, int type) {
		Map<String, Object> returnMap = new LinkedHashMap<>();
		if (type == 2) {
			BidCreditRecord record = bidCreditRecordDao.findOne(id);
			/**
			 * issueNum:剩余期数 nextRepayDate:下一还款日 rate:年利率 dsbx:待收本息
			 * holdPrice:债权价值
			 */
			returnMap.put("creditNumber", record.getCreditNumber());
			returnMap.put("issueNum", record.getBid().getExtend().getRemainPeriods() + "/" + record.getBid().getExtend().getTotalPeriods() + "个月");
			returnMap.put("nextRepayDate", record.getBid().getExtend().getNextRepayDate());
			returnMap.put("rate", Formater.formatRate(record.getBid().getRate()));
			returnMap.put("dsbx", Formater.formatAmount(bidRepaymentRecordDao.findDsbxById(record.getId())) + "元");
			returnMap.put("holdPrice", Formater.formatAmount(record.getHoldPrice()) + "元");
			returnMap.put("fee", applyDao.getZqzrFee());
			return returnMap;
		}
		CreditAssignmentApply record = applyDao.findOne(id);
		if (record == null)
			throw new ServiceException(InterfaceResultCode.SYS_ERROR, "系统繁忙");
		//债权ID
		returnMap.put("creditNumber", record.getCreditRecord().getCreditNumber());
		/** type：类型 1.转让中的债权 2.可转让的债权 3.已转出的债权 4.已转入的债权 */
		if (type == 1) {
			/** issueNum:剩余期数 rate:年利率 holdPrice:债权价值 price:转让价格 status:状态 */
			returnMap.put("issueNum", record.getCreditRecord().getBid().getExtend().getRemainPeriods() + "/" + record.getCreditRecord().getBid().getExtend().getTotalPeriods() + "个月");
			returnMap.put("rate", Formater.formatRate(record.getCreditRecord().getBid().getRate()));
			returnMap.put("holdPrice", Formater.formatAmount(record.getCreditRecord().getHoldPrice()) + "元");
			returnMap.put("price", Formater.formatAmount(record.getPrice()) + "元");
			returnMap.put("status", record.getStatus().getChineseName());
			return returnMap;
		}
		CreditAssignmentRecord entity = creditAssignmentRecordDao.findByCreditAssignmentApplyId(record.getId());
		if (entity == null)
			throw new ServiceException(InterfaceResultCode.SYS_ERROR, "系统繁忙");
		if (type == 3) {
			/**
			 * assignmentAmount:交易金额 buyAmount:转出时债权价值 assignmentFees:交易费用
			 * outProfit:盈亏
			 */
			returnMap.put("assignmentAmount", Formater.formatAmount(entity.getAssignmentAmount()) + "元");
			returnMap.put("buyAmount", Formater.formatAmount(entity.getBuyAmount()) + "元");
			returnMap.put("assignmentFees", Formater.formatAmount(entity.getAssignmentFees()) + "元");
			returnMap.put("outProfit", Formater.formatAmount(entity.getOutProfit()) + "元");
			return returnMap;
		}
		if (type == 4) {
			/**
			 * issueNum:剩余期数 rate:年利率 buyAmount:转入时债权价值 assignmentAmount:交易金额
			 * buyTime:转入时间
			 */
			returnMap.put("issueNum", record.getCreditRecord().getBid().getExtend().getRemainPeriods() + "/" + record.getCreditRecord().getBid().getExtend().getTotalPeriods() + "个月");
			returnMap.put("rate", Formater.formatRate(record.getCreditRecord().getBid().getRate()));
			returnMap.put("buyAmount", Formater.formatAmount(entity.getBuyAmount()) + "元");
			returnMap.put("assignmentAmount", Formater.formatAmount(entity.getAssignmentAmount()) + "元");
			returnMap.put("buyTime", Formater.formatDate(entity.getBuyTime()));
			return returnMap;
		}
		throw new ServiceException(InterfaceResultCode.ILLEGAL_REQUEST, "非法请求");
	}

	@Override
	@Transactional
	public void transfer(Long zqId, BigDecimal price) {
		BidCreditRecord record = bidCreditRecordDao.findOne(zqId);
		if (record == null)
			throw new ServiceException(InterfaceResultCode.FAILED, "债权记录不存在");
		if (record.getIsMakeOverNow() == Judge.S)
			throw new ServiceException(InterfaceResultCode.FAILED, "该债权正在转让");
		int count = bidRepaymentRecordDao.findRecordCountByCreditId(zqId);
		if (count > 0)
			throw new ServiceException(InterfaceResultCode.FAILED, "存在逾期未还，不能转让");
		Date nextDate = bidRepaymentRecordDao.findEndDateByCreditId(zqId);
		if (null == nextDate)
			throw new ServiceException(InterfaceResultCode.FAILED, "不存在未还记录");
		nextDate.setTime(nextDate.getTime() - DateHelper.DAY_IN_MILLISECONDS * 3);
		Date currentDate = bidRepaymentRecordDao.getCurrentDate();
		if (DateHelper.beforeDate(nextDate, currentDate))
			throw new ServiceException(InterfaceResultCode.FAILED, "离下个还款日少于3天，不能转让");
		CreditAssignmentApply entity = new CreditAssignmentApply();
		entity.setCreditRecord(record);
		entity.setPrice(price);
		entity.setOriginalPrice(record.getHoldPrice());
		entity.setCreateTime(new Date());
		entity.setEndDate(nextDate);
		entity.setStatus(CreditAssignmentStatus.DSH);
		entity.setServiceCharge(applyDao.getZqzrFee());
		//添加债权转让申请
		entity = applyDao.save(entity);
		//修改标债权记录
		record.setIsMakeOverNow(Judge.S);
		bidCreditRecordDao.save(record);

		Agreement agreement = agreementDao.findOne(Constants.ZQZRXY);
		CreditAssignmentVersion version = new CreditAssignmentVersion();
		version.setId(entity.getId());
		version.setNum(agreement.getVersionNum());
		//添加债权转让协议版本号
		versionDao.save(version);
	}

	@Override
	public Map<String, Object> findById(Long zqApplyId) {
		CreditAssignmentApply record = applyDao.findOne(zqApplyId);
		if (record == null)
			throw new ServiceException(InterfaceResultCode.FAILED, "债权记录不存在");
		/**
		 * creditNumber:债权编码 holdPrice:债权价值 dsbx:待收本息 price:转让价格 issueNum:剩余期数
		 * repaymentWay:还款方式 rate:年利率
		 */
		Map<String, Object> returnMap = new LinkedHashMap<>();
		returnMap.put("zqApplyId", record.getId());
		returnMap.put("creditNumber", record.getCreditRecord().getCreditNumber());
		returnMap.put("holdPrice", Formater.formatAmount(record.getCreditRecord().getHoldPrice()) + "元");
		returnMap.put("dsbx", Formater.formatAmount(bidRepaymentRecordDao.findDsbxById(record.getCreditRecord().getId())) + "元");
		returnMap.put("price", Formater.formatAmount(record.getPrice()) + "元");
		returnMap.put("issueNum", record.getCreditRecord().getBid().getExtend().getRemainPeriods() + "/" + record.getCreditRecord().getBid().getExtend().getTotalPeriods() + "个月");
		returnMap.put("repaymentWay", record.getCreditRecord().getBid().getMode().getChineseName());
		returnMap.put("rate", Formater.formatRate(record.getCreditRecord().getBid().getRate()));
		returnMap.put("title", record.getCreditRecord().getBid().getTitle());
		returnMap.put("bidType", applyDao.getBidType(record.getCreditRecord().getBid().getType()));
		return returnMap;
	}

	@Override
	@Transactional
	public void purchase(Long zqApplyId, Long userId) {
		CreditAssignmentApply record = applyDao.lockById(zqApplyId);
		if (record == null)
			throw new ServiceException(InterfaceResultCode.FAILED, "线上债权转让申请不存在");
		if (record.getStatus() != CreditAssignmentStatus.ZRZ)
			throw new ServiceException(InterfaceResultCode.FAILED, "线上债权转让申请不是转让中状态,不能转让");
		Date currentDate = bidRepaymentRecordDao.getCurrentDate();
		if (!DateHelper.beforeOrMatchDate(currentDate, record.getEndDate()))
			throw new ServiceException(InterfaceResultCode.FAILED, "线上债权转让申请已到截至日期,不能转让");
		if (userId.compareTo(record.getCreditRecord().getUserAccount().getId()) == 0)
			throw new ServiceException(InterfaceResultCode.FAILED, "不能购买自己的债权");
		if (userId.compareTo(record.getCreditRecord().getBid().getUserAccount().getId()) == 0)
			throw new ServiceException(InterfaceResultCode.FAILED, "您为此债权的借款人,不能购买");

		BigDecimal fee = record.getPrice().multiply(record.getServiceCharge());
		BigDecimal total = record.getPrice().add(fee);
		// 锁定购买人往来账户
		FundAccount fundAccount = fundAccountDao.lockFundAccountByUserIdAndType(userId, FundAccountType.WLZH);
		if (fundAccount == null) {
			log.error("用户往来帐号不存在，id{}", userId);
			throw new ServiceException(InterfaceResultCode.SYS_ERROR, "系统繁忙");
		}
		if (fundAccount.getBalance().compareTo(total) < 0)
			throw new ServiceException(InterfaceResultCode.FAILED, "账户余额不足");

		// 插入订单
		Orders orders = new Orders();
		orders.setTypeCode(OrdersType.BID_EXCHANGE.orderType());
		orders.setCreateTime(new Date());
		;
		orders.setStatus(OrdersStatus.DTJ);
		orders.setSource(OrdersSource.YH);
		orders.setUserId(userId);
		orders = ordersDao.save(orders);
		//债权转让记录
		CreditAssignmentOrder creditAssignmentOrder = new CreditAssignmentOrder();
		creditAssignmentOrder.setId(orders.getId());
		creditAssignmentOrder.setApplyId(zqApplyId);
		creditAssignmentOrder.setUserId(userId);
		creditAssignmentOrder.setBuyAmount(record.getOriginalPrice());
		creditAssignmentOrder.setAssignmentAmount(record.getPrice());
		creditAssignmentOrder.setAssignmentFees(fee);
		creditAssignmentOrder = creditAssignmentOrderDao.save(creditAssignmentOrder);
		//确定订单
		orders.setStatus(OrdersStatus.DQR);
		orders.setSubmitTime(applyDao.getCurrentDate());
		orders = ordersDao.save(orders);

		// 锁定债权信息
		BidCreditRecord t6251 = bidCreditRecordDao.lockById(record.getCreditRecord().getId());
		if (t6251 == null) {
			throw new ServiceException(InterfaceResultCode.FAILED, "债权信息不存在");
		}
		if (t6251.getHoldPrice().compareTo(BigDecimal.ZERO) <= 0) {
			throw new ServiceException(InterfaceResultCode.FAILED, "债权价值不能为0");
		}
		// 锁定还款计划
		List<BidRepaymentRecord> list = bidRepaymentRecordDao.LockByCreditId(record.getCreditRecord().getId());

		//转让往来账户
		FundAccount zrwlzh = null;
		if (fundAccount.getUserId() == t6251.getUserAccount().getId()) {
			zrwlzh = fundAccount;
		} else {
			zrwlzh = fundAccountDao.lockFundAccountByUserIdAndType(t6251.getUserAccount().getId(), FundAccountType.WLZH);
		}
		if (zrwlzh == null) {
			throw new ServiceException(InterfaceResultCode.FAILED, "转让人往来资金账户不存在");
		}
		// 扣减受让人资金账户
		fundAccount.setBalance(fundAccount.getBalance().subtract(creditAssignmentOrder.getAssignmentAmount()));
		fundAccount.setLastUpdateTime(ordersDao.getCurrentDate());
		fundAccount = fundAccountDao.save(fundAccount);
		{
			//添加流水记录
			FundAccountFlow wlFlow = new FundAccountFlow();
			wlFlow.setFundAccount(fundAccount);
			wlFlow.setFeeCode(FeeCode.ZQZR_MR);
			wlFlow.setRemoteFundAccount(zrwlzh);
			wlFlow.setExpenditure(creditAssignmentOrder.getAssignmentAmount());
			wlFlow.setBalance(fundAccount.getBalance());
			wlFlow.setCreateTime(new Date());
			wlFlow.setRemark(String.format("购买债权:%s", t6251.getCreditNumber()));
			fundAccountFlowDao.save(wlFlow);
		}
		// 增加转让人资金账户
		zrwlzh.setBalance(zrwlzh.getBalance().add(creditAssignmentOrder.getAssignmentAmount()));
		zrwlzh.setLastUpdateTime(ordersDao.getCurrentDate());
		zrwlzh = fundAccountDao.save(zrwlzh);
		{
			//添加流水记录
			FundAccountFlow wlFlow = new FundAccountFlow();
			wlFlow.setFundAccount(zrwlzh);
			wlFlow.setFeeCode(FeeCode.ZQZR_MC);
			wlFlow.setRemoteFundAccount(fundAccount);
			wlFlow.setIncome(creditAssignmentOrder.getAssignmentAmount());
			wlFlow.setBalance(zrwlzh.getBalance());
			wlFlow.setCreateTime(new Date());
			wlFlow.setRemark(String.format("转让债权:%s", t6251.getCreditNumber()));
			fundAccountFlowDao.save(wlFlow);
		}
		//手续费处理
		if (fee.compareTo(BigDecimal.ZERO) > 0) {
			// 扣转让手续费
			if (zrwlzh.getBalance().compareTo(fee) < 0)
				throw new ServiceException(InterfaceResultCode.FAILED, "转让人往来资金账户余额不足,扣转让手续费失败");
			Long pid = applyDao.getPTID();
			// 锁定平台往来账户
			FundAccount ptwlzh = fundAccountDao.lockFundAccountByUserIdAndType(pid, FundAccountType.WLZH);
			if (ptwlzh == null)
				throw new ServiceException(InterfaceResultCode.FAILED, "平台往来资金账户不存在");

			// 扣减转让人资金账户
			zrwlzh.setBalance(zrwlzh.getBalance().subtract(fee));
			zrwlzh.setLastUpdateTime(ordersDao.getCurrentDate());
			zrwlzh = fundAccountDao.save(zrwlzh);
			{
				//添加流水记录
				FundAccountFlow wlFlow = new FundAccountFlow();
				wlFlow.setFundAccount(zrwlzh);
				wlFlow.setFeeCode(FeeCode.ZQZR_SXF);
				wlFlow.setRemoteFundAccount(ptwlzh);
				wlFlow.setExpenditure(fee);
				wlFlow.setBalance(zrwlzh.getBalance());
				wlFlow.setCreateTime(new Date());
				wlFlow.setRemark(String.format("转让债权手续费:%s", t6251.getCreditNumber()));
				fundAccountFlowDao.save(wlFlow);
			}
			// 增加平台资金账户
			ptwlzh.setBalance(ptwlzh.getBalance().add(fee));
			ptwlzh.setLastUpdateTime(ordersDao.getCurrentDate());
			ptwlzh = fundAccountDao.save(ptwlzh);
			{
				//添加流水记录
				FundAccountFlow wlFlow = new FundAccountFlow();
				wlFlow.setFundAccount(ptwlzh);
				wlFlow.setFeeCode(FeeCode.ZQZR_SXF);
				wlFlow.setRemoteFundAccount(zrwlzh);
				wlFlow.setIncome(fee);
				wlFlow.setBalance(ptwlzh.getBalance());
				wlFlow.setCreateTime(new Date());
				wlFlow.setRemark(String.format("转让债权手续费:%s", t6251.getCreditNumber()));
				fundAccountFlowDao.save(wlFlow);
			}
		}
		// 扣减转出人持有债权
		t6251.setHoldPrice(BigDecimal.ZERO);
		t6251 = bidCreditRecordDao.save(t6251);

		// 生成新的债权编码
		int index = bidCreditRecordDao.countByInvestRecordId(t6251.getInvestRecordId());
		if (index > 0) {
			index = index + 1;
		} else {
			index = 1;
		}
		// 插入受让人债权记录
		BidCreditRecord newT6251 = new BidCreditRecord();
		if (t6251.getCreditNumber().contains("-")) {
			newT6251.setCreditNumber(t6251.getCreditNumber().substring(0, t6251.getCreditNumber().length() - 1) + index);
		} else {
			newT6251.setCreditNumber(String.format("%s-%s", t6251.getCreditNumber(), Integer.toString(index)));
		}
		newT6251.setBid(t6251.getBid());
		newT6251.setUserAccount(new UserAccount(creditAssignmentOrder.getUserId()));
		newT6251.setBuyingPrice(creditAssignmentOrder.getAssignmentAmount());
		newT6251.setOriginalPrice(creditAssignmentOrder.getBuyAmount());
		newT6251.setHoldPrice(creditAssignmentOrder.getBuyAmount());
		newT6251.setIsMakeOverNow(Judge.F);
		newT6251.setCreateDate(new Date());
		newT6251.setBeginDate(t6251.getBeginDate());
		newT6251.setInvestRecordId(t6251.getInvestRecordId());
		newT6251.setOrderId(orders.getId());
		newT6251 = bidCreditRecordDao.save(newT6251);
		// 修改还款计划
		List<Long> ids = new ArrayList<Long>();
		for (BidRepaymentRecord bidRepaymentRecord : list) {
			ids.add(bidRepaymentRecord.getId());
		}
		bidRepaymentRecordDao.updGatherUserIdAndCreditIdByIds(creditAssignmentOrder.getUserId(), newT6251.getId(), ids);
		// 插入购买记录
		{
			CreditAssignmentRecord entity = new CreditAssignmentRecord();
			entity.setCreditAssignmentApply(record);
			entity.setUserAccount(new UserAccount(creditAssignmentOrder.getUserId()));
			entity.setBuyAmount(creditAssignmentOrder.getBuyAmount());
			entity.setAssignmentAmount(creditAssignmentOrder.getAssignmentAmount());
			entity.setAssignmentFees(fee);
			entity.setBuyTime(new Date());
			entity.setIntoProfit(entity.getBuyAmount().subtract(entity.getAssignmentAmount()));
			entity.setOutProfit(entity.getAssignmentAmount().subtract(entity.getBuyAmount()).subtract(fee));
			creditAssignmentRecordDao.save(entity);
		}
		// 插入购买人统计数据
		{
			CreditAssignmentStatistics entity = statisticsDao.findOne(creditAssignmentOrder.getUserId());
			BigDecimal zrProfit = creditAssignmentOrder.getBuyAmount().subtract(creditAssignmentOrder.getAssignmentAmount());
			BigDecimal intoPrice = creditAssignmentOrder.getBuyAmount();
			BigDecimal intoProfit = creditAssignmentOrder.getBuyAmount().subtract(creditAssignmentOrder.getAssignmentAmount());
			if (entity == null) {
				entity = new CreditAssignmentStatistics();
				entity.setId(creditAssignmentOrder.getUserId());
				entity.setZrProfit(zrProfit);
				entity.setIntoPrice(intoPrice);
				entity.setIntoProfit(intoProfit);
				entity.setIntoCount(1);
				entity.setOutPrice(BigDecimal.ZERO);
				entity.setOutProfit(BigDecimal.ZERO);
				entity.setOutCount(0);
			} else {
				entity.setZrProfit(entity.getZrProfit().add(zrProfit));
				entity.setIntoPrice(entity.getIntoPrice().add(intoPrice));
				entity.setIntoProfit(entity.getIntoProfit().add(intoProfit));
				entity.setIntoCount(entity.getIntoCount() + 1);
			}
			statisticsDao.save(entity);
		}
		// 插入卖出者统计数据
		{
			CreditAssignmentStatistics entity = statisticsDao.findOne(t6251.getUserAccount().getId());
			BigDecimal zrProfit = creditAssignmentOrder.getAssignmentAmount().subtract(creditAssignmentOrder.getBuyAmount()).subtract(fee);
			BigDecimal outPrice = creditAssignmentOrder.getBuyAmount();
			BigDecimal outProfit = creditAssignmentOrder.getAssignmentAmount().subtract(creditAssignmentOrder.getBuyAmount()).subtract(fee);
			if (entity == null) {
				entity = new CreditAssignmentStatistics();
				entity.setId(t6251.getUserAccount().getId());
				entity.setZrProfit(zrProfit);
				entity.setIntoPrice(BigDecimal.ZERO);
				entity.setIntoProfit(BigDecimal.ZERO);
				entity.setIntoCount(0);
				entity.setOutPrice(outPrice);
				entity.setOutProfit(outProfit);
				entity.setOutCount(1);
			} else {
				entity.setZrProfit(entity.getZrProfit().add(zrProfit));
				entity.setOutPrice(entity.getOutPrice().add(outPrice));
				entity.setOutProfit(entity.getOutProfit().add(outProfit));
				entity.setOutCount(entity.getOutCount() + 1);
			}
			statisticsDao.save(entity);
		}
		t6251.setIsMakeOverNow(Judge.F);
		bidCreditRecordDao.save(t6251);
		record.setStatus(CreditAssignmentStatus.YJS);
		applyDao.save(record);

		//完成订单
		orders.setStatus(OrdersStatus.CG);
		orders.setSubmitTime(applyDao.getCurrentDate());
		orders = ordersDao.save(orders);

		/** 转账处理 */
		String outTradeNo = FunctionUtil.getOutTradeNo(7, creditAssignmentOrder.getId(), orders.getCreateTime());
		String accountNumber = Constants.ACCOUNT_NUMBER;
		String key = Constants.MERCHANT_KEY;
		TranDataModel tranDataModel = new TranDataModel(accountNumber, "zqall_" + orders.getId());
		List<TranModel> tranModelList = new ArrayList<TranModel>();
		double amount = creditAssignmentOrder.getAssignmentAmount().doubleValue();
		String outName = fundAccount.getLoginName();
		String inName = zrwlzh.getLoginName();
		TranModel jyModel = new TranModel(outTradeNo, amount, inName, outName, accountNumber, key);
		tranModelList.add(jyModel);
		//手续费
		if (creditAssignmentOrder.getAssignmentFees().compareTo(new BigDecimal(0)) > 0) {
			String feeoutTradeNo = FunctionUtil.getOutTradeNo(8, creditAssignmentOrder.getId(), orders.getCreateTime());
			TranModel feeModel = new TranModel(feeoutTradeNo, creditAssignmentOrder.getAssignmentFees().doubleValue(), accountNumber, inName, accountNumber, key);
			tranModelList.add(feeModel);
		}
		tranDataModel.setTranList(tranModelList);
		//请求第三方.发送转账请求
		Map<String, Object> result = FunctionClientUtil.sendTrade(tranDataModel);
		String resCode = result.get("resCode").toString();
		if (!resCode.contains(Constants.SUCCESS_CODE)) {
			throw new ServiceException(InterfaceResultCode.FAILED, TradeMap.getName(resCode));
		}
	}

	@Override
	@Transactional
	public void cancel(Long zqId) {
		CreditAssignmentApply record = applyDao.lockById(zqId);
		if (record == null)
			throw new ServiceException(InterfaceResultCode.FAILED, "该债权不存在");
		BidCreditRecord creditRecord = bidCreditRecordDao.lockById(record.getCreditRecord().getId());
		if (creditRecord.getIsMakeOverNow() == Judge.F)
			throw new ServiceException(InterfaceResultCode.FAILED, "该债权已下架");

		//把申请债权的状态改为已结束
		record.setStatus(CreditAssignmentStatus.YJS);
		applyDao.save(record);

		//更改债权记录的转让状态设置为F
		creditRecord.setIsMakeOverNow(Judge.F);
		bidCreditRecordDao.save(creditRecord);
	}

	@Override
	public Map<String, Object> findSimpleAssignmentByTypeV2(Long userId, int type, int page, int size) {
		if (type < 1 || type > 4)
			throw new ServiceException(InterfaceResultCode.ILLEGAL_REQUEST, "非法请求");

		Map<String, Object> mapInner = null;
		Map<String, Object> returnMap = new LinkedHashMap<>();
		List<Map<String, Object>> list = new ArrayList<>();
		Pageable pageable = null;
		switch (type) {
		case 1:
			//转让中的债权
			pageable = new PageRequest(page - 1, size, Direction.DESC, "createTime");
			Page<CreditAssignmentApply> creditAssignmentApplys = applyDao.findZqzrzListV2(userId, pageable);
			for (CreditAssignmentApply obj : creditAssignmentApplys) {
				mapInner = new HashMap<>();
				mapInner.put("zqId", obj.getId());
				mapInner.put("creditNumber", obj.getCreditRecord().getCreditNumber());
				mapInner.put("title", obj.getCreditRecord().getBid().getTitle());// 项目名称
				mapInner.put("createTime", Formater.formatDate(obj.getCreateTime()));// 申请转让时间(创建时间)T6260.F05
				mapInner.put("holdPrice", Formater.formatAmount(obj.getCreditRecord().getHoldPrice()));// 剩余本金->(持有债权金额)T6251.F07
				list.add(mapInner);
			}
			returnMap.put("content", list);
			returnMap.put("hasNext", creditAssignmentApplys.hasNext() ? 1 : 0);
			break;
		case 2:
			//可转让的债权
			pageable = new PageRequest(page - 1, size, Direction.DESC, "bid.extend.loanTime");
			Date date1 = new DateTime().plusDays(3).toDate();
			Date date2 = new DateTime().plusDays(-(Constants.ZQZR_CY_DAY)).toDate();
			Page<BidCreditRecord> bidCreditRecords = bidCreditRecordDao.findZqkzcListV2(userId, date1, date2, pageable);
			for (BidCreditRecord obj : bidCreditRecords) {
				mapInner = new HashMap<>();
				mapInner.put("zqId", obj.getId());
				mapInner.put("creditNumber", obj.getCreditNumber());
				mapInner.put("title", obj.getBid().getTitle());// 项目名称
				mapInner.put("jkPeriod", obj.getBid().getLoanPeriod() > 0 ? obj.getBid().getLoanPeriod() + "个月" : obj.getBid().getExtend().getLoanPeriod() + "天");// 借款期限
				BigDecimal price = bidRepaymentRecordDao.findDsbxPrice(obj.getId(), Lists.newArrayList(BidRepaymentStatus.WH), Lists.newArrayList(FeeCode.TZ_LX, FeeCode.TZ_BJ));
				mapInner.put("dsbx", Formater.formatAmount(price));// 待收本息
				list.add(mapInner);
			}
			returnMap.put("content", list);
			returnMap.put("hasNext", bidCreditRecords.hasNext() ? 1 : 0);
			break;
		case 3:
			//已转出的债权
			pageable = new PageRequest(page - 1, size, Direction.DESC, "buyTime");
			Page<CreditAssignmentRecord> creditAssignmentRecords = creditAssignmentRecordDao.findZqyzcListV2(userId, pageable);
			for (CreditAssignmentRecord obj : creditAssignmentRecords) {
				mapInner = new HashMap<>();
				mapInner.put("zqId", obj.getCreditAssignmentApply().getId());
				mapInner.put("creditNumber", obj.getCreditAssignmentApply().getCreditRecord().getCreditNumber());
				mapInner.put("title", obj.getCreditAssignmentApply().getCreditRecord().getBid().getTitle());// 项目名称
				mapInner.put("buyTime", Formater.formatDate(obj.getBuyTime()));// 转让时间
				mapInner.put("outProfit", Formater.formatAmount(obj.getOutProfit()));// 转出盈亏
				list.add(mapInner);
			}
			returnMap.put("content", list);
			returnMap.put("hasNext", creditAssignmentRecords.hasNext() ? 1 : 0);
			break;
		case 4:
			//已转入的债权
			pageable = new PageRequest(page - 1, size, Direction.DESC, "buyTime");
			Page<CreditAssignmentRecord> creditAssignmentRecordYzrs = creditAssignmentRecordDao.findZqyzrListV2(userId, pageable);
			for (CreditAssignmentRecord obj : creditAssignmentRecordYzrs) {
				mapInner = new HashMap<>();
				mapInner.put("zqId", obj.getCreditAssignmentApply().getId());
				mapInner.put("creditNumber", obj.getCreditAssignmentApply().getCreditRecord().getCreditNumber());
				mapInner.put("title", obj.getCreditAssignmentApply().getCreditRecord().getBid().getTitle());// 项目名称
				mapInner.put("buyTime", Formater.formatDate(obj.getBuyTime()));// 转入时间
				mapInner.put("intoProfit", Formater.formatAmount(obj.getIntoProfit()));// 转入盈亏
				list.add(mapInner);
			}
			returnMap.put("content", list);
			returnMap.put("hasNext", creditAssignmentRecordYzrs.hasNext() ? 1 : 0);
			break;
		}

		return returnMap;
	}
}
