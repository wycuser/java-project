package com.shanlin.p2p.app.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shanlin.p2p.app.dao.ExperienceAccountDao;
import com.shanlin.p2p.app.dao.ExperienceAccountFlowDao;
import com.shanlin.p2p.app.model.ExperienceAccount;
import com.shanlin.p2p.app.model.ExperienceAccountFlow;
import com.shanlin.p2p.app.model.enums.ExperienceSource;
import com.shanlin.p2p.app.service.ExperienceAccountService;

@Service
@Transactional(readOnly=true)
public class ExperienceAccountServiceImpl implements ExperienceAccountService {

	@Resource
	private ExperienceAccountDao experienceAccountDao;
	
	@Resource
	private ExperienceAccountFlowDao experienceAccountFlowDao;
	
	public ExperienceAccount findByUserId(Long userId){
		return experienceAccountDao.findByUserId(userId);
	} 
	
	@Override
	@Transactional
	public ExperienceAccount findOrAddByUserId(Long userId) {
		ExperienceAccount experienceAccount = experienceAccountDao.findByUserId(userId);
		if(experienceAccount == null){
			experienceAccount = new ExperienceAccount();
			experienceAccount.setUserId(userId);
			experienceAccount.setBalance(BigDecimal.ZERO);
			experienceAccount = experienceAccountDao.save(experienceAccount);
		}
		return experienceAccount;
	}

	@Override
	@Transactional
	public ExperienceAccount save(ExperienceAccount experienceAccount) {
		return experienceAccountDao.save(experienceAccount);
	}

	@Override
	@Transactional
	public void addExperienceMoney(Long uid, BigDecimal money, ExperienceSource status) {
		ExperienceAccount experienceAccount = experienceAccountDao.findByUserId(uid);
		if(experienceAccount == null){
			experienceAccount = new ExperienceAccount();
			experienceAccount.setUserId(uid);
			experienceAccount.setBalance(money);
		}
		experienceAccount = experienceAccountDao.save(experienceAccount);
		ExperienceAccountFlow flow = new ExperienceAccountFlow();
		flow.setUserId(uid);
		flow.setMoney(money);
		flow.setBalance(experienceAccount.getBalance());
		flow.setSource(status);
		flow.setOperateTime(new Date());
		flow.setCreateTime(new Date());
		experienceAccountFlowDao.save(flow);
	}
	
	@Override
	@Transactional
	public void addExperienceMoneyBySMRZ(Long uid) {
		ExperienceAccount experienceAccount = experienceAccountDao.findByUserId(uid);
		ExperienceAccountFlow zcFlow = experienceAccountFlowDao.findByUserIdAndSource(uid, ExperienceSource.ZC);
		BigDecimal money = new BigDecimal(800);
		if(zcFlow == null)
			money = new BigDecimal(1000);
		if(experienceAccount == null){
			experienceAccount = new ExperienceAccount();
			experienceAccount.setUserId(uid);
			experienceAccount.setBalance(BigDecimal.ZERO);
		}
		experienceAccount.setBalance(money.add(experienceAccount.getBalance()));
		experienceAccount = experienceAccountDao.save(experienceAccount);
		ExperienceAccountFlow flow = new ExperienceAccountFlow();
		flow.setUserId(uid);
		flow.setMoney(money);
		flow.setBalance(experienceAccount.getBalance());
		flow.setOperateTime(new Date());
		flow.setSource(ExperienceSource.SMRZ);
		flow.setCreateTime(new Date());
		experienceAccountFlowDao.save(flow);
	}

	@Override
	public Page<ExperienceAccountFlow> findSourceByUserId(Long userId, Pageable pageable) {
		return experienceAccountFlowDao.findByUserIdAndSourceNot(userId, ExperienceSource.TB, pageable);
	}
}
