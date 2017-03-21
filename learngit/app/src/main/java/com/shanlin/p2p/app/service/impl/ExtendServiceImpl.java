package com.shanlin.p2p.app.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shanlin.framework.controller.InterfaceResultCode;
import com.shanlin.framework.exception.ServiceException;
import com.shanlin.framework.utils.FunctionClientUtil;
import com.shanlin.framework.utils.StringHelper;
import com.shanlin.p2p.app.constant.Constants;
import com.shanlin.p2p.app.constant.FeeCode;
import com.shanlin.p2p.app.dao.ExtendDao;
import com.shanlin.p2p.app.dao.FundAccountDao;
import com.shanlin.p2p.app.dao.FundAccountFlowDao;
import com.shanlin.p2p.app.dao.UserAccountDao;
import com.shanlin.p2p.app.model.FundAccount;
import com.shanlin.p2p.app.model.FundAccountFlow;
import com.shanlin.p2p.app.model.enums.FundAccountType;
import com.shanlin.p2p.app.service.ExtendService;
import com.shanlin.p2p.app.vo.huichao.TranDataModel;
import com.shanlin.p2p.app.vo.huichao.TranModel;

@Service
@Transactional(readOnly=true)
public class ExtendServiceImpl implements ExtendService {

	protected Logger log = LoggerFactory.getLogger(ExtendServiceImpl.class);
	
	@Resource
	private ExtendDao extendDao;
	
	@Resource
	private FundAccountDao fundAccountDao;
	
	@Resource
	private UserAccountDao userAccountDao;
	
	@Resource
	private FundAccountFlowDao fundAccountFlowDao;
	
	@Override
	@Transactional
	public void anthReward(Long userId) {
		int count=extendDao.lockRewardCount();
		if(count<=0){
			throw new ServiceException(InterfaceResultCode.FAILED, "实名推广奖励：次数不足");
		}
		Long parentId=userAccountDao.getParentId(userId);
		String code=userAccountDao.findHuiChaoPayIdByUserId(parentId);
		if(StringHelper.isEmpty(code)){
			throw new ServiceException(InterfaceResultCode.FAILED, "实名推广奖励:"+parentId+"未注册第三方");
		}
		int authCount=extendDao.authCount(parentId);
		if(authCount>=20){
			throw new ServiceException(InterfaceResultCode.FAILED, "实名推广奖励:"+parentId+"已推广了用户个数为"+authCount);
		}
		FundAccount outWlzh = fundAccountDao.lockFundAccountByUserIdAndType(extendDao.getPTID(), FundAccountType.WLZH);
		FundAccount inWlzh = fundAccountDao.lockFundAccountByUserIdAndType(parentId, FundAccountType.WLZH);
		if (outWlzh == null || inWlzh == null) {
			throw new ServiceException(InterfaceResultCode.FAILED, "往来资金账户不存在");
		}
		BigDecimal amount=new BigDecimal("2");
		Date currentDate=new Date();
		// 扣减平台资金账户
		{
			// 扣减支出锁定账户
			outWlzh.setBalance(outWlzh.getBalance().subtract(amount));
			if (outWlzh.getBalance().compareTo(BigDecimal.ZERO) < 0) {
				throw new ServiceException("支出用户账户余额不足");
			}
			outWlzh.setLastUpdateTime(currentDate);
			outWlzh = fundAccountDao.save(outWlzh);
			//添加流水记录
			FundAccountFlow wlFlow = new FundAccountFlow();
			wlFlow.setFundAccount(outWlzh);
			wlFlow.setFeeCode(FeeCode.PTHB);
			wlFlow.setRemoteFundAccount(inWlzh);
			wlFlow.setExpenditure(amount);
			wlFlow.setBalance(outWlzh.getBalance());
			wlFlow.setCreateTime(currentDate);
			wlFlow.setRemark(String.format("实名推广奖励"));
			fundAccountFlowDao.save(wlFlow);
		}
		// 增加收入锁定账户
		{
			inWlzh.setBalance(inWlzh.getBalance().add(amount));
			inWlzh.setLastUpdateTime(currentDate);
			inWlzh = fundAccountDao.save(inWlzh);
			//添加流水记录
			FundAccountFlow wlFlow = new FundAccountFlow();
			wlFlow.setFundAccount(inWlzh);
			wlFlow.setFeeCode(FeeCode.PTHB);
			wlFlow.setRemoteFundAccount(outWlzh);
			wlFlow.setIncome(amount);
			wlFlow.setBalance(inWlzh.getBalance());
			wlFlow.setCreateTime(currentDate);
			wlFlow.setRemark(String.format("实名推广奖励"));
			fundAccountFlowDao.save(wlFlow);
		}
					
		/** 转账处理 */
		String outTradeNo = "tg" + parentId + "_" + String.valueOf(System.currentTimeMillis());
		String accountNumber = Constants.ACCOUNT_NUMBER;
		String key = Constants.MERCHANT_KEY;
		TranDataModel tranDataModel = new TranDataModel(accountNumber, "tg_reward");
		List<TranModel> tranModelList = new ArrayList<TranModel>();
		String outName = accountNumber;
		String inName = inWlzh.getLoginName();
		TranModel jyModel = new TranModel(outTradeNo, amount.doubleValue(), inName, outName, accountNumber, key);
		tranModelList.add(jyModel);
		tranDataModel.setTranList(tranModelList);
		//请求第三方.发送转账请求
		Map<String, Object> result = FunctionClientUtil.sendTrade(tranDataModel);
		String resCode = result.get("resCode").toString();
		if (!resCode.contains(Constants.SUCCESS_CODE)) {
			throw new ServiceException(InterfaceResultCode.FAILED, "实名推广奖励:"+result.toString());
		}
		extendDao.addAuthReward(userId, parentId);
		extendDao.updRewardCount();
	}

}
