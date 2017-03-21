package com.shanlin.p2p.app.service;

import java.util.Map;

import com.shanlin.p2p.app.model.BankCard;

/**
 * 用户银行卡管理
 * @author yangjh
 *
 */

public interface BankCardService {

	/**
	 * 查询用户银行卡列表
	 * @param userId
	 * @param page
	 * @param size
	 * @return
	 */
	Map<String, Object> findBankCardList(Long userId, int page, int size);
	
	/**
	 * 保存用户银行卡信息
	 * @param bankCard
	 */
	void opBankCard(Long userId,Long bankId,Long areaId,String openingName,String number,Long id);
	
	/**
	 * 删除银行卡
	 * @param id
	 */
	void delBankCard(Long id);
	
	/**
	 * 查询用户银行卡信息
	 * @param id
	 * @return
	 */
	BankCard findBankCard(Long id);
}
