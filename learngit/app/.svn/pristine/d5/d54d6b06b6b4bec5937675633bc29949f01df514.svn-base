package com.shanlin.p2p.app.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shanlin.framework.controller.InterfaceResultCode;
import com.shanlin.framework.exception.ServiceException;
import com.shanlin.p2p.app.dao.LoanApplyDao;
import com.shanlin.p2p.app.model.LoanApply;
import com.shanlin.p2p.app.service.LoanApplyService;

@Service
@Transactional(readOnly=true)
public class LoanApplyServiceImpl implements LoanApplyService {
	
	@Resource
	private LoanApplyDao loanApplyDao;

	@Override
	@Transactional
	public void addLoans(Long userId) {
		int loansCount=loanApplyDao.getLoansCount(userId);
		if(loansCount>0){
			throw new ServiceException(InterfaceResultCode.FAILED, "您在3天内已提交过申请");
		}
		LoanApply entities=new LoanApply();
		entities.setUserId(userId);
		entities.setCreateTime(new Date());
		loanApplyDao.save(entities);
	}

}
