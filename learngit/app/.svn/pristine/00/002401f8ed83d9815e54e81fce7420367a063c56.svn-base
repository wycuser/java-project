package com.shanlin.p2p.app.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shanlin.p2p.app.dao.UserSafeStatusDao;
import com.shanlin.p2p.app.model.UserSafeStatus;
import com.shanlin.p2p.app.service.SafeCertifiedService;

@Service
@Transactional(readOnly=true)
public class SafeCertifiedServiceImpl implements SafeCertifiedService {
	
	@Resource
	private UserSafeStatusDao userSafeStatusDao;
	
	@Override
	public UserSafeStatus findOne(Long userId) {
		return userSafeStatusDao.findOne(userId);
	}

	@Override
	@Transactional
	public void updateUserSafeStatus(UserSafeStatus userSafeStatus) {
		userSafeStatusDao.save(userSafeStatus);
	}

}
