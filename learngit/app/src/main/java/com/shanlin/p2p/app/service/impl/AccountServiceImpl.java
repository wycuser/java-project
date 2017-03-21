package com.shanlin.p2p.app.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.shanlin.framework.utils.BdUtil;
import com.shanlin.framework.utils.Formater;
import com.shanlin.p2p.app.constant.FeeCode;
import com.shanlin.p2p.app.dao.BankCardDao;
import com.shanlin.p2p.app.dao.BankDao;
import com.shanlin.p2p.app.dao.BidCreditRecordDao;
import com.shanlin.p2p.app.dao.BidDao;
import com.shanlin.p2p.app.dao.BidRepaymentRecordDao;
import com.shanlin.p2p.app.dao.InvestRecordDao;
import com.shanlin.p2p.app.dao.UserAccountDao;
import com.shanlin.p2p.app.model.Bank;
import com.shanlin.p2p.app.model.BankCard;
import com.shanlin.p2p.app.model.Bid;
import com.shanlin.p2p.app.model.BidCreditRecord;
import com.shanlin.p2p.app.model.InvestRecord;
import com.shanlin.p2p.app.model.UserAccount;
import com.shanlin.p2p.app.model.enums.BidRepaymentStatus;
import com.shanlin.p2p.app.model.enums.BidStatus;
import com.shanlin.p2p.app.model.enums.UseStatus;
import com.shanlin.p2p.app.service.AccountService;

@Service
@Transactional(readOnly = true)
public class AccountServiceImpl implements AccountService {

	@Resource
	private BankCardDao bankCardDao;
	
	@Resource
	private BidDao bidDao;

	@Resource
	private BankDao bankDao;

	@Resource
	private InvestRecordDao investRecordDao;

	@Resource
	private BidCreditRecordDao bidCreditRecordDao;

	@Resource
	private BidRepaymentRecordDao bidRepaymentRecordDao;

	@Resource
	private UserAccountDao userAccountDao;

	@Override
	public List<BankCard> findBankCardByUserId(Long userId) {
		return bankCardDao.findByUserIdAndStatus(userId, UseStatus.QY);
	}

	@Override
	public List<Bank> findAllBank() {
		return bankDao.findByStatus(UseStatus.QY);
	}

	@Override
	public Map<String, Object> findCreditorListByType(int type, Long userId, Pageable pageable) {
		Map<String, Object> returnMap = new HashMap<>(2);
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> map = null;
		Page<Object[]> page = null;
		switch (type) {
		case 1://投标中的债权
			page = investRecordDao.findSimpleByBidStatusAndUserAccount(Lists.newArrayList(BidStatus.TBZ, BidStatus.DFK), userId, pageable);
			break;
		case 3://已结清的债权
			page = bidCreditRecordDao.findSimpleByBidStatusAndUserAccount(Lists.newArrayList(BidStatus.YDF, BidStatus.YJQ), userId, pageable);
			break;
		case 2://回收中的债权
		default:
			page = bidCreditRecordDao.findSimpleByBidStatusAndUserAccountAndHoldPrice(Lists.newArrayList(BidStatus.HKZ), userId, pageable);
			break;
		}
		for (Object[] args : page) {
			map = new HashMap<>();
			map.put("id", args[0]);
			map.put(type == 1 ? "bidNumber" : "creditNumber", args[1]);
			map.put("title", args[2]);
			list.add(map);
		}
		returnMap.put("content", list);
		returnMap.put("hasNext", page.hasNext() ? 1 : 0);
		return returnMap;
	}

	// 获取回帐查询列表
	@Override
	public Map<String, Object> findBackAccountListByType(int type, Long userId, Pageable pageable) {
		// 定义,初始化
		Page<Object[]> page = null;
		Map<String, Object> map = null;
		Map<String, Object> returnMap = new HashMap<>();
		List<Map<String, Object>> list = new ArrayList<>();

		switch (type) {
		// 若为待收款
		case 1:
			page = bidRepaymentRecordDao.findBackAccountListByType(userId, Lists.newArrayList(FeeCode.TZ_LX, FeeCode.TZ_BJ), BidRepaymentStatus.WH, pageable);
			break;
		// 若为已收款
		case 2:
			page = bidRepaymentRecordDao.findBackAccountListByType(userId, Lists.newArrayList(FeeCode.TZ_LX, FeeCode.TZ_BJ), BidRepaymentStatus.YH, pageable);
			break;
		default:
			page = bidRepaymentRecordDao.findBackAccountListByType(userId, Lists.newArrayList(FeeCode.TZ_LX, FeeCode.TZ_BJ), BidRepaymentStatus.WH, pageable);
			break;
		}

		for (Object[] args : page.getContent()) {
			map = new HashMap<>();
			map.put("type", type);
			map.put("id", args[0]);
			map.put("title", args[1]);
			map.put("shouldDate", args[2]);
			map.put("sumAmount", args[3]);
			map.put("reId", args[4]);// 还款记录Id
			map.put("creditId", args[5]);// 
			map.put("issue", args[6]);// 

			list.add(map);
		}
		returnMap.put("content", list);
		returnMap.put("hasNext", page.hasNext() ? 1 : 0);
		return returnMap;
	}

	// 获取单条回帐查询详情
	@Override
	public Map<String, Object> findSingleBackAccountDetail(int type, Long userId, Long bidId, Long creditId, Integer issue) {
		// 定义,初始化
		Map<String, Object> returnMap = new HashMap<>();

		Object[][] objs = bidRepaymentRecordDao.findSingleBackAccountDetail(userId, bidId, creditId, issue, Lists.newArrayList(FeeCode.TZ_LX, FeeCode.TZ_BJ), type == 2 ? BidRepaymentStatus.YH : BidRepaymentStatus.WH);

		if (objs.length > 0 && objs[0] != null) {
			returnMap.put("bidId", objs[0][0]);// 项目标题
			returnMap.put("title", objs[0][1]);// 项目标题
			returnMap.put("issue", objs[0][2]);// 期号
			UserAccount userAccount = userAccountDao.findOne((Long) objs[0][7]);
			returnMap.put("jkrLoginName", userAccount.getLoginName());// 借款人登录用户名
			returnMap.put("typeMx", "回收本息");// 类型明细
			returnMap.put("shouldDate", objs[0][3]);// 收款日期(应还日期)
			returnMap.put("sumAmount", Formater.formatAmount((BigDecimal) objs[0][4]));// 收款金额
			returnMap.put("status", ((BidStatus) objs[0][8]).getChineseName());// 项目状态
		}
		return returnMap;
	}

	@Override
	public BidCreditRecord findByIdAndUserId(Long id, Long userId) {
		return bidCreditRecordDao.findByIdAndUserAccountId(id, userId);
	}

	// 我的投资列表
	@Override
	public Map<String, Object> findCreditorListByTypeV2(int type, Long userId, Pageable pageable) {
		Map<String, Object> mapInner = null;
		Map<String, Object> returnMap = new HashMap<>(2);
		List<Map<String, Object>> list = new ArrayList<>();
		switch (type) {
		case 1://投标中的债权
			Page<InvestRecord> listInvestRecords = investRecordDao.findSimpleByBidStatusAndUserAccountV2(Lists.newArrayList(BidStatus.TBZ, BidStatus.DFK), userId, pageable);
			for (InvestRecord investRecord : listInvestRecords) {
				mapInner = new HashMap<>();
				mapInner.put("id", investRecord.getId());// 投资记录Id(T6250.F01)
				mapInner.put(type == 1 ? "bidNumber" : "creditNumber", investRecord.getBid().getBidNumber());// (标编号)T6230.F25
				mapInner.put("title", investRecord.getBid().getTitle());// (借款标题)T6230.F03
				mapInner.put("publishTime", Formater.formatDate(investRecord.getBid().getPublishTime()));// 开投时间(发布时间)(T6230.F22)
				mapInner.put("buyingPrice", Formater.formatAmount(investRecord.getBuyingPrice()));// 购买价格(T6250.F04)

				/** 计算收益 **********************************************/
				mapInner.put("obtainPrice", Formater.formatAmount(investRecord.getIncome()));// 项目收益
				/**Date currentDate = investRecordDao.getCurrentDate();
				InvestRecord[] investRecords = new InvestRecord[] { investRecord };
				try {
					BigDecimal temp = BdUtil.calcSy(currentDate, investRecord.getBid(), investRecord.getBid().getExtend(), investRecords);
					mapInner.put("obtainPrice", Formater.formatAmount(temp));// 项目收益
				} catch (Throwable e) {
					e.printStackTrace();
				}**/

				list.add(mapInner);
			}
			returnMap.put("content", list);
			returnMap.put("hasNext", listInvestRecords.hasNext() ? 1 : 0);
			break;
		case 2://回收中的债权
			Page<BidCreditRecord> listBidCreditRecords = bidCreditRecordDao.findSimpleByBidStatusAndUserAccountAndHoldPriceV2(Lists.newArrayList(BidStatus.HKZ), userId, pageable);
			for (BidCreditRecord bidCreditRecord : listBidCreditRecords) {
				mapInner = new HashMap<>();
				mapInner.put("id", bidCreditRecord.getId());// 标债权Id(T6251.F01)
				mapInner.put(type == 1 ? "bidNumber" : "creditNumber", bidCreditRecord.getBid().getBidNumber());// (标编号)T6230.F25
				mapInner.put("title", bidCreditRecord.getBid().getTitle());// (借款标题)T6230.F03
				mapInner.put("publishTime", Formater.formatDate(bidCreditRecord.getBid().getPublishTime()));// 开投时间(发布时间)(T6230.F22)
				mapInner.put("buyingPrice", Formater.formatAmount(bidCreditRecord.getBuyingPrice()));// 购买价格(T6251.F05)

				BigDecimal price = bidRepaymentRecordDao.findWaitRecyclePrice(bidCreditRecord.getId(), userId, Lists.newArrayList(BidRepaymentStatus.WH, BidRepaymentStatus.HKZ, BidRepaymentStatus.YH), Lists.newArrayList(FeeCode.TZ_LX, FeeCode.TZ_BJ));
				price = price.subtract(bidCreditRecord.getBuyingPrice());
				mapInner.put("obtainPrice", Formater.formatAmount(price));// 项目收益

				list.add(mapInner);
			}
			returnMap.put("content", list);
			returnMap.put("hasNext", listBidCreditRecords.hasNext() ? 1 : 0);
			break;

		case 3://已结清的债权
			Page<BidCreditRecord> listBidCreditRecordYjqs = bidCreditRecordDao.findSimpleByBidStatusAndUserAccountV2(Lists.newArrayList(BidStatus.YDF, BidStatus.YJQ), userId, pageable);
			for (BidCreditRecord bidCreditRecord : listBidCreditRecordYjqs) {
				mapInner = new HashMap<>();
				mapInner.put("id", bidCreditRecord.getId());// 标债权Id(T6251.F01)
				mapInner.put(type == 1 ? "bidNumber" : "creditNumber", bidCreditRecord.getBid().getBidNumber());// (标编号)T6230.F25
				mapInner.put("title", bidCreditRecord.getBid().getTitle());// (借款标题)T6230.F03
				mapInner.put("publishTime", Formater.formatDate(bidCreditRecord.getBid().getPublishTime()));// 开投时间(发布时间)(T6230.F22)
				mapInner.put("buyingPrice", Formater.formatAmount(bidCreditRecord.getBuyingPrice()));// 购买价格(T6251.F05)

				BigDecimal price = bidRepaymentRecordDao.findWaitRecyclePrice(bidCreditRecord.getId(), userId, Lists.newArrayList(BidRepaymentStatus.YH), Lists.newArrayList(FeeCode.TZ_WYJ, FeeCode.TZ_LX, FeeCode.TZ_FX));
				mapInner.put("obtainPrice", Formater.formatAmount(price));// 项目收益

				list.add(mapInner);
			}
			returnMap.put("content", list);
			returnMap.put("hasNext", listBidCreditRecordYjqs.hasNext() ? 1 : 0);
			break;
		}
		return returnMap;
	}

	// 获取项目收益
	@Override
	public Map<String, Object> getProjectSy(Long bidId, BigDecimal creditPrice) {
		Map<String, Object> mapRe = new HashMap<String, Object>();
		Bid bid = bidDao.findOne(bidId);

		InvestRecord investRecord = new InvestRecord();
		investRecord.setCreditPrice(creditPrice);
		
		/** 计算收益 **********************************************/
		Date currentDate = investRecordDao.getCurrentDate();
		InvestRecord[] investRecords = new InvestRecord[] { investRecord };
		try {
			BigDecimal temp = BdUtil.calcSy(currentDate, bid, bid.getExtend(), investRecords);
			mapRe.put("obtainPrice", Formater.formatAmount(temp));// 项目收益
		} catch (Throwable e) {
			e.printStackTrace();
		}

		return mapRe;
	}
	
	  // 项目收益处理
		@Override
	public void IncomeRate() {
			List<InvestRecord> list = new ArrayList<InvestRecord>();
			list=bidDao.getBidRate();
			for(InvestRecord record: list){
				
				Bid bid = bidDao.findOne(record.getBid().getId());
				Date currentDate = investRecordDao.getCurrentDate();
				InvestRecord[] investRecords = new InvestRecord[] { record };
				try {
				      BigDecimal inCome = BdUtil.calcSy(currentDate, bid, bid.getExtend(), investRecords);
				      //System.out.println("===="+inCome+","+record.getId());
				    int m= bidDao.updateInRate(inCome, record.getId());
				     // System.out.println("m==="+m);
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
			
		
		}
}