package com.shanlin.p2p.app.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.shanlin.framework.controller.InterfaceResultCode;
import com.shanlin.framework.exception.ServiceException;
import com.shanlin.p2p.app.constant.Constants;
import com.shanlin.p2p.app.dao.ExperienceAccountDao;
import com.shanlin.p2p.app.dao.ExperienceAccountFlowDao;
import com.shanlin.p2p.app.dao.ExperienceBidDao;
import com.shanlin.p2p.app.dao.ExperienceBidRecordDao;
import com.shanlin.p2p.app.dao.UserAccountDao;
import com.shanlin.p2p.app.model.ExperienceAccount;
import com.shanlin.p2p.app.model.ExperienceAccountFlow;
import com.shanlin.p2p.app.model.ExperienceBid;
import com.shanlin.p2p.app.model.ExperienceBidRecord;
import com.shanlin.p2p.app.model.enums.BidStatus;
import com.shanlin.p2p.app.model.enums.ExperienceSource;
import com.shanlin.p2p.app.service.ExperienceBidService;

@Service
@Transactional(readOnly=true)
public class ExperienceBidServiceImpl implements ExperienceBidService {

	private Logger log = LoggerFactory.getLogger(ExperienceBidServiceImpl.class);
	
	@Resource
	private ExperienceBidDao experienceBidDao;
	
	@Resource
	private ExperienceAccountDao experienceAccountDao;
	
	@Resource
	private ExperienceBidRecordDao experienceBidRecordDao;
	
	@Resource
	private ExperienceAccountFlowDao experienceAccountFlowDao;
	
	@Resource
	private UserAccountDao userAccountDao;
	
	@Override
	public ExperienceBid findFirstExperienceBid() {
		return experienceBidDao.findFirstByStatusInOrderByStatusAscCreateTimeDesc(
				Lists.newArrayList(BidStatus.TBZ, BidStatus.YFB));
	}

	@Override
	public ExperienceBid findById(Long id) {
		return experienceBidDao.findOne(id);
	}

	/**
	 * 投资体验标
	 */
	@Override
	@Transactional
	public void invest(Long id, Long userId, BigDecimal money) {
		// 锁定标
		ExperienceBid bid = experienceBidDao.lockById(id);
		if(bid == null)
			throw new ServiceException(InterfaceResultCode.FAILED, "指定的标记录不存在");
		if(bid.getStatus() != BidStatus.TBZ)
			throw new ServiceException(InterfaceResultCode.FAILED, "指定的标不是投标中状态,不能投标");
		if(money.compareTo(bid.getResidueAmount()) > 0)
			throw new ServiceException(InterfaceResultCode.FAILED, "投标金额大于可投金额");
		BigDecimal residue = bid.getResidueAmount().subtract(money);
		if(residue.compareTo(BigDecimal.ZERO) > 0 && residue.compareTo(new BigDecimal(Constants.SYS_MIN_INVEST_AMOUNT)) < 0)
			throw new ServiceException(InterfaceResultCode.FAILED, "剩余可投金额不能低于最低起投金额");
		// 锁定投资人资金账户
		ExperienceAccount account = experienceAccountDao.lockByUserId(userId);
		if(account == null){
			log.error("用户体验标帐号不存在，id{}", userId);
			throw new ServiceException(InterfaceResultCode.SYS_ERROR, "系统繁忙");
		}
		if(account.getBalance().compareTo(money) < 0)
			throw new ServiceException(InterfaceResultCode.FAILED, "账户余额不足");
		//直接扣除用户体验金
		account.setBalance(account.getBalance().subtract(money));
		experienceAccountDao.save(account);
		//减去标的可投金额
		bid.setResidueAmount(residue);
		// 可投金额为0时，改变标的状态成待放款
		if(residue.compareTo(BigDecimal.ZERO) == 0){
			bid.setStatus(BidStatus.DFK);
			bid.setFillBidTime(new Date());
		}
		experienceBidDao.save(bid);
		//插入投标记录
		ExperienceBidRecord record = new ExperienceBidRecord();
		record.setUserAccount(userAccountDao.findOne(userId));
		record.setBidPk(id);
		record.setInvestPrice(money);
		record.setInvestTime(new Date());
		record.setInterest(money.multiply(
				new BigDecimal(bid.getLoanPeriod()))
				.multiply(bid.getRate()).divide(new BigDecimal(36000), 2, BigDecimal.ROUND_HALF_UP));
		experienceBidRecordDao.save(record);
		//插入体验金流水
		ExperienceAccountFlow flow = new ExperienceAccountFlow();
		flow.setUserId(userId);
		flow.setMoney(money);
		flow.setBalance(account.getBalance());
		flow.setSource(ExperienceSource.TB);
		flow.setCreateTime(new Date());
		flow.setOperateTime(new Date());
		experienceAccountFlowDao.save(flow);
	}

}
