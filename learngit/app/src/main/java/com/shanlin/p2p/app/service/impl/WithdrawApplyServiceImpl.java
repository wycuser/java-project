package com.shanlin.p2p.app.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.UnixCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import com.shanlin.framework.controller.InterfaceResultCode;
import com.shanlin.framework.exception.ServiceException;
import com.shanlin.framework.utils.StringHelper;
import com.shanlin.p2p.app.constant.Constants;
import com.shanlin.p2p.app.constant.FeeCode;
import com.shanlin.p2p.app.dao.AuthAccountDao;
import com.shanlin.p2p.app.dao.BankCardDao;
import com.shanlin.p2p.app.dao.BankDao;
import com.shanlin.p2p.app.dao.BidRepaymentRecordDao;
import com.shanlin.p2p.app.dao.FundAccountDao;
import com.shanlin.p2p.app.dao.FundAccountFlowDao;
import com.shanlin.p2p.app.dao.SysConstantDao;
import com.shanlin.p2p.app.dao.UserAccountDao;
import com.shanlin.p2p.app.dao.UserLinesDao;
import com.shanlin.p2p.app.dao.UserSafeStatusDao;
import com.shanlin.p2p.app.dao.WithdrawApplyDao;
import com.shanlin.p2p.app.model.AuthAccount;
import com.shanlin.p2p.app.model.BankCard;
import com.shanlin.p2p.app.model.FundAccount;
import com.shanlin.p2p.app.model.FundAccountFlow;
import com.shanlin.p2p.app.model.UserAccount;
import com.shanlin.p2p.app.model.UserLines;
import com.shanlin.p2p.app.model.UserSafeStatus;
import com.shanlin.p2p.app.model.WithdrawApply;
import com.shanlin.p2p.app.model.enums.FundAccountType;
import com.shanlin.p2p.app.model.enums.RealAuthIspass;
import com.shanlin.p2p.app.model.enums.RealAuthSet;
import com.shanlin.p2p.app.model.enums.UseStatus;
import com.shanlin.p2p.app.model.enums.WithdrawStatus;
import com.shanlin.p2p.app.service.UserLinesService;
import com.shanlin.p2p.app.service.WithdrawApplyService;

@Service
@Transactional(readOnly = true)
public class WithdrawApplyServiceImpl implements WithdrawApplyService {

	@Resource
	private WithdrawApplyDao withdrawApplyDao;
	
	@Resource
	private FundAccountDao fundAccountDao;
	
	@Resource
	private SysConstantDao sysConstantDao;
	
	@Resource
	private UserAccountDao userAccountDao;
	
	@Resource
	private UserSafeStatusDao userSafeStatusDao;
	
	@Resource
	private BidRepaymentRecordDao bidRepaymentRecordDao;
	
	@Resource
	private BankCardDao bankCardDao;
	
	@Resource
	private BankDao bankDao;
	
	@Resource
	private FundAccountFlowDao flowDao; 
	
	@Resource
	private AuthAccountDao authAccountDao;
	
	@Resource
	private UserLinesDao userLinesDao;
	
	@Resource
	private UserLinesService userLinesService;
	
	
	
	@Override
	public Map<String, Object> withdrawIndex(Long userId, Long bankCardId) {
		Map<String, Object> returnMap=new HashMap<String, Object>();
		UserSafeStatus userSafeStatus = userSafeStatusDao.findOne(userId);
		returnMap.put("cardStat", userSafeStatus.getIdCardStatus()==RealAuthIspass.TG?1:0);
		returnMap.put("isMoneyPwd", userSafeStatus.getMoneyPassStatus()==RealAuthSet.YSZ?1:0);
		returnMap.put("phoneStat", userSafeStatus.getPhoneStatus()==RealAuthIspass.TG?1:0);
		String code=userAccountDao.findHuiChaoPayIdByUserId(userId);
		returnMap.put("isParty", !StringHelper.isEmpty(code)?1:0);
		BankCard bankCard=null;
		if(bankCardId.intValue()>0){
			bankCard=bankCardDao.findOne(bankCardId);
		}else{
			bankCard=bankCardDao.findFirstByUserIdAndStatus(userId,UseStatus.QY);
		}
		if(bankCard==null){
			returnMap.put("count", 0);
			return returnMap;
		}
		// 往来账户
		FundAccount wlzh=fundAccountDao.findByUserIdAndType(userId, FundAccountType.WLZH);
		AuthAccount authAccount=authAccountDao.findOne(userId);
		returnMap.put("count", 1);
		returnMap.put("balance", wlzh.getBalance());
		returnMap.put("bankCardId", bankCard.getId());
		returnMap.put("number", bankCard.getNumber());
		returnMap.put("bankName", bankCard.getBank().getName());
		returnMap.put("picUrl", bankCard.getBank().getPicUrl());
		returnMap.put("authName", authAccount.getName());
		returnMap.put("WITHDRAW_MIN_FUNDS", sysConstantDao.findValueById(Constants.WITHDRAW_MIN_FUNDS));
		returnMap.put("WITHDRAW_MAX_FUNDS", sysConstantDao.findValueById(Constants.WITHDRAW_MAX_FUNDS));
		returnMap.put("WITHDRAW_POUNDAGE_WAY", sysConstantDao.findValueById(Constants.WITHDRAW_POUNDAGE_WAY));
		returnMap.put("WITHDRAW_POUNDAGE_PROPORTION", sysConstantDao.findValueById(Constants.WITHDRAW_POUNDAGE_PROPORTION));
		returnMap.put("WITHDRAW_POUNDAGE_MIN", sysConstantDao.findValueById(Constants.WITHDRAW_POUNDAGE_MIN));
		returnMap.put("WITHDRAW_POUNDAGE_1_5", sysConstantDao.findValueById(Constants.WITHDRAW_POUNDAGE_1_5));
		returnMap.put("WITHDRAW_POUNDAGE_5_20", sysConstantDao.findValueById(Constants.WITHDRAW_POUNDAGE_5_20));
		returnMap.put("WITHDRAW_ED_FUNDS", "5000");
		return returnMap;
	}
	
	@Override
	public Map<String, Object> withdrawUserLines(Long userId,BigDecimal amount){
		Map<String, Object> returnMap=new HashMap<String, Object>();
		
		//提现手续费最低费用
		BigDecimal proMin = new BigDecimal(sysConstantDao.findValueById(Constants.WITHDRAW_POUNDAGE_MIN));
		String _proportion = sysConstantDao.findValueById(Constants.WITHDRAW_POUNDAGE_PROPORTION);
		if(StringHelper.isEmpty(_proportion) || _proportion.contains("-")) {
			throw new ServiceException(InterfaceResultCode.FAILED,"系统繁忙，请稍后再试！");
		}
		BigDecimal min = new BigDecimal(sysConstantDao.findValueById(Constants.WITHDRAW_MIN_FUNDS));
		BigDecimal max = new BigDecimal(sysConstantDao.findValueById(Constants.WITHDRAW_MAX_FUNDS));
		BigDecimal zero = new BigDecimal(0);
		if (amount.compareTo(min) < 0 || amount.compareTo(max) > 0
				|| amount.compareTo(zero) <= 0) {
			throw new ServiceException(InterfaceResultCode.FAILED, "提现金额必须在"+min+","+max+"之间");
		}
		
		//计算提现手续费
		Map<String, Object> mapUserLines = userLinesService.withdrawleUserLines(userId, amount, _proportion);
		BigDecimal poundage = getSafeBigDecimal(mapUserLines.get("poundage"));
		
		
		poundage = proMin.compareTo(poundage) > 0?proMin:poundage;
		returnMap.put("poundage", poundage);
		return returnMap;
		
	}
	
	@Override
	@Transactional(noRollbackFor=IllegalArgumentException.class)
	public void opWithdrawApply(Long userId,Long bankCardId,BigDecimal funds,String withdrawPsd) {
		if (StringHelper.isEmpty(withdrawPsd)) {
			throw new ServiceException(InterfaceResultCode.FAILED, "提现密码为空");
		}
		BigDecimal min = new BigDecimal(sysConstantDao.findValueById(Constants.WITHDRAW_MIN_FUNDS));
		BigDecimal max = new BigDecimal(sysConstantDao.findValueById(Constants.WITHDRAW_MAX_FUNDS));
		BigDecimal zero = new BigDecimal(0);
		if (funds.compareTo(min) < 0 || funds.compareTo(max) > 0
				|| funds.compareTo(zero) <= 0) {
			throw new ServiceException(InterfaceResultCode.FAILED, "提现金额必须在"+min+","+max+"之间");
		}
		UserAccount userAccount=userAccountDao.findOne(userId);
		int count = userAccount.getErrPassCount();
		int maxCount = Integer.parseInt(sysConstantDao.findValueById(Constants.WITHDRAW_MAX_INPUT));
		if (count >= maxCount) {
			throw new ServiceException(InterfaceResultCode.FAILED,"您今日提现密码输入错误已到最大次数，请改日再试!");
		}
		UserSafeStatus safeStatus=userSafeStatusDao.findOne(userId);
		String withdrawPass=UnixCrypt.crypt(withdrawPsd,DigestUtils.sha256Hex(withdrawPsd));
		if (!withdrawPass.equals(safeStatus.getMoneyPass())) {
			//提現密碼不一致，增加輸入提現密碼次數
			userAccount.setErrPassCount(userAccount.getErrPassCount()+1);
			userAccountDao.save(userAccount);
			String errorMsg = null;
			if (count + 1 >= maxCount) {
				errorMsg = "您今日提现密码输入错误已到最大次数，请改日再试!";
			} else {
				StringBuilder builder = new StringBuilder("提现密码错误,您最多还可以输入");
				builder.append(maxCount - (count + 1));
				builder.append("次");
				errorMsg = builder.toString();
			}
			throw new IllegalArgumentException(errorMsg);
		}
		int repaymentCount=bidRepaymentRecordDao.findTimeLimitRecordCount(userId);
		if(repaymentCount>0){
			throw new ServiceException(InterfaceResultCode.FAILED,"您还有逾期借款未还，请先还借款！");
		}
		BankCard bankCard=bankCardDao.findOne(bankCardId);
		if(null==bankCard){
			throw new ServiceException(InterfaceResultCode.FAILED,"银行卡不存在");
		}
		BigDecimal poundage = null;
		String pundageWay = sysConstantDao.findValueById(Constants.WITHDRAW_POUNDAGE_WAY);
		
		//用于提现的额度
		BigDecimal txlines = new BigDecimal(0);
		UserLines userLines=userLinesDao.LockFindUserLines(userId);
		
		//是否是借款用户 ，不为null则表示是借款用户
		Integer inBorrow = userLinesDao.findByBorrow(userId);
		
		if("BL".equals(pundageWay)) {
			// 按比例计算
			String _proportion = sysConstantDao.findValueById(Constants.WITHDRAW_POUNDAGE_PROPORTION);
			if(StringHelper.isEmpty(_proportion) || _proportion.contains("-")) {
				throw new ServiceException(InterfaceResultCode.FAILED,"系统繁忙，请稍后再试！");
			}
			//poundage = funds.multiply(new BigDecimal(_proportion)).setScale(2, BigDecimal.ROUND_HALF_UP);
			//提现手续费最低费用
			BigDecimal proMin = new BigDecimal(sysConstantDao.findValueById(Constants.WITHDRAW_POUNDAGE_MIN));
		
			
		  if(userLines!=null && inBorrow==null){
			if(funds.compareTo(userLines.getLinesr()) > 0){
				txlines = userLines.getLinesr();
				BigDecimal userInvest = funds.subtract(userLines.getLinesr());
			    poundage = (userLines.getLinesr().multiply(new BigDecimal(_proportion)).add(userInvest.multiply(new BigDecimal(0.005)))).setScale(2, BigDecimal.ROUND_HALF_UP);
				
			}else{
				txlines = funds;
				poundage = (funds.multiply(new BigDecimal(_proportion))).setScale(2, BigDecimal.ROUND_HALF_UP);
			}
			
		   }else{
			  
			  poundage = funds.multiply(new BigDecimal(_proportion)).setScale(2, BigDecimal.ROUND_HALF_UP);
			 
			  
		   }
			
		  poundage = proMin.compareTo(poundage) > 0?proMin:poundage;
			//poundage = proMin.compareTo(poundage) > 0?proMin:poundage;
		} else {
			// 按额度计算[1-50000), [50000, ]
			if (funds.compareTo(new BigDecimal(5000)) < 0) {
				poundage = new BigDecimal(sysConstantDao.findValueById(Constants.WITHDRAW_POUNDAGE_1_5));
			} else {
				poundage = new BigDecimal(sysConstantDao.findValueById(Constants.WITHDRAW_POUNDAGE_5_20));
			}
		}
		BigDecimal amount = funds.add(poundage);// 提现应付金额
		// 往来账户
		FundAccount wlzh=fundAccountDao.lockFundAccountByUserIdAndType(userId, FundAccountType.WLZH);
		if (amount.compareTo(wlzh.getBalance()) > 0) {
			if(funds.compareTo(wlzh.getBalance()) > 0 || funds.compareTo(poundage) <= 0){
				throw new ServiceException(InterfaceResultCode.FAILED,"账户余额不足");
			}else{
				amount = funds;
				funds = funds.subtract(poundage);
			}
		}
		// 锁定账户
		FundAccount sdzh=fundAccountDao.lockFundAccountByUserIdAndType(userId, FundAccountType.SDZH);
		//保存提现申请记录
		WithdrawApply withdrawApply=new WithdrawApply();
		withdrawApply.setUserId(userId);
		withdrawApply.setBankId(bankCardId);
		withdrawApply.setAmount(funds);
		withdrawApply.setPoundage(poundage);
		withdrawApply.setRealPoundage(poundage);
		withdrawApply.setCreateTime(new Date());
		withdrawApply.setStatus(WithdrawStatus.DSH);
		withdrawApply.setSource("APP");
		withdrawApply.setBankName(bankCard.getBankName());
		withdrawApply.setSubbranch(bankCard.getOpeningName());
		withdrawApply.setBankNum(bankCard.getCipherNumber());
		withdrawApply.setUsLines(txlines);
		withdrawApplyDao.save(withdrawApply);
		
		//更新用户提现计算提现手续费的额度
		  if(userLines!=null && inBorrow==null){
				if(userLines.getLinesr().compareTo(funds) >= 0){
					BigDecimal userInvest = userLines.getLinesr().subtract(funds);
					userLinesDao.updUserLines(userInvest, userId);
					
				}else{
					userLinesDao.updUserLines(BigDecimal.ZERO, userId);
				}
				
	       }
		//更新往来帐户
		//wlzh.setBalance(wlzh.getBalance().subtract(amount));
		//添加往来账户流水记录
		{
			wlzh.setBalance(wlzh.getBalance().subtract(funds));
			// 锁定账户流水
			FundAccountFlow flow=new FundAccountFlow();
			flow.setFundAccount(new FundAccount(wlzh.getId()));
			flow.setFeeCode(FeeCode.TX);
			flow.setRemoteFundAccount(new FundAccount(sdzh.getId()));
			flow.setExpenditure(funds);
			flow.setBalance(wlzh.getBalance());
			flow.setCreateTime(new Date());
			flow.setRemark("提现金额");
			flowDao.save(flow);
		}
		if (poundage.compareTo(zero) > 0) {
			wlzh.setBalance(wlzh.getBalance().subtract(poundage));
			// 往来账户流水
			FundAccountFlow flow=new FundAccountFlow();
			flow.setFundAccount(new FundAccount(wlzh.getId()));
			flow.setFeeCode(FeeCode.TX_SXF);
			flow.setRemoteFundAccount(new FundAccount(sdzh.getId()));
			flow.setExpenditure(poundage);
			flow.setBalance(wlzh.getBalance());
			flow.setCreateTime(new Date());
			flow.setRemark("提现手续费");
			flowDao.save(flow);
		}
		wlzh.setLastUpdateTime(new Date());
		fundAccountDao.save(wlzh);
		
		//更新锁定帐户
		//sdzh.setBalance(wlzh.getBalance().add(amount));
		{
			sdzh.setBalance(sdzh.getBalance().add(funds));
			// 锁定账户流水
			FundAccountFlow flow=new FundAccountFlow();
			flow.setFundAccount(new FundAccount(sdzh.getId()));
			flow.setFeeCode(FeeCode.TX);
			flow.setRemoteFundAccount(new FundAccount(wlzh.getId()));
			flow.setExpenditure(funds);
			flow.setBalance(sdzh.getBalance());
			flow.setCreateTime(new Date());
			flow.setRemark("提现金额");
			flowDao.save(flow);
		}
		if (poundage.compareTo(zero) > 0) {
			sdzh.setBalance(sdzh.getBalance().add(poundage));
			// 锁定账户流水
			FundAccountFlow flow=new FundAccountFlow();
			flow.setFundAccount(new FundAccount(sdzh.getId()));
			flow.setFeeCode(FeeCode.TX_SXF);
			flow.setRemoteFundAccount(new FundAccount(wlzh.getId()));
			flow.setExpenditure(poundage);
			flow.setBalance(sdzh.getBalance());
			flow.setCreateTime(new Date());
			flow.setRemark("提现手续费");
			flowDao.save(flow);
		}
		sdzh.setLastUpdateTime(new Date());
		fundAccountDao.save(sdzh);
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

}