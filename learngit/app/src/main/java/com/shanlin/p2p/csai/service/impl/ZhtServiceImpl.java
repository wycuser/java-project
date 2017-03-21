package com.shanlin.p2p.csai.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shanlin.p2p.app.dao.BidDao;
import com.shanlin.p2p.app.dao.InvestRecordDao;
import com.shanlin.p2p.app.dao.UserAccountDao;
import com.shanlin.p2p.app.model.UserAccount;
import com.shanlin.p2p.app.service.SmsService;
import com.shanlin.p2p.app.service.UserAccountService;
import com.shanlin.p2p.csai.service.ZhtService;

/**
 * 账户通实现类
 * 
 * @author ice
 *
 */
@Service
@Transactional(readOnly = true)
public class ZhtServiceImpl extends CsaiServiceImpl implements ZhtService {

	@Resource
	private BidDao bidDao;

	@Resource
	private UserAccountDao userAccountDao;

	@Resource
	private InvestRecordDao investRecordDao;

	@Resource
	private SmsService smsService;

	@Resource
	private UserAccountService userAccountService;

	private static final Logger log = LoggerFactory.getLogger(ZhtServiceImpl.class);
	
	// 判断是否希财用户已注册过
	@Override
	public Boolean isRegistered(String phone, String name, String pid, String t, String urlQzSecYmd) {
		UserAccount userAccount = userAccountDao.findCsaiUser("csai", phone, name);
		log.info("(希财)账户通->用户是否已注册过"+(userAccount!=null));
		return (userAccount!=null);
	}
}