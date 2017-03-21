package com.shanlin.p2p.app.service.impl;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shanlin.framework.controller.InterfaceResultCode;
import com.shanlin.framework.exception.ServiceException;
import com.shanlin.p2p.app.dao.FundAccountDao;
import com.shanlin.p2p.app.model.FundAccount;
import com.shanlin.p2p.app.model.enums.FundAccountType;
import com.shanlin.p2p.app.service.FundAccountService;

@Service
@Transactional(readOnly = true)
public class FundAccountServiceImpl implements FundAccountService {
	
	private final static Logger log = LoggerFactory.getLogger(FundAccountServiceImpl.class);
	
	@Resource
	private FundAccountDao fundAccountDao;
	
	@Override
	public FundAccount findWlzhByUserId(Long userId) {
		FundAccount fundAccount = fundAccountDao.findByUserIdAndType(userId, FundAccountType.WLZH);
		if(fundAccount == null){
			log.error("用户往来帐号不存在，id{}", userId);
			throw new ServiceException(InterfaceResultCode.SYS_ERROR, "系统繁忙");
		}
		return fundAccount;
	}

	@Override
	public BigDecimal findBalanceByUserIdAndType(Long userId, FundAccountType type) {
		return fundAccountDao.findBalanceByUserIdAndType(userId, type);
	}

}
