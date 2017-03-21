package com.shanlin.p2p.app.service;

import com.shanlin.p2p.app.model.AuthAccount;

/**
 * 实名认证接口
 * @author yangjh
 * @time 2015年1月14日
 */
public interface AuthAccountService {

	/**
	 * 根据Id来查询用户实名信息
	 * @param id
	 * @return
	 */
	AuthAccount findOne(Long id);

	void updateIdCard(AuthAccount authAccount);

	boolean isExistIdCard(String idCard);
	
	boolean check(String idCard, String userName);
}
