package com.shanlin.p2p.app.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;

import com.shanlin.p2p.app.model.Bank;
import com.shanlin.p2p.app.model.BankCard;
import com.shanlin.p2p.app.model.BidCreditRecord;

/**
 * 帐号管理业务
 * 
 * @author zheng xin
 * @createDate 2015年1月28日
 */
public interface AccountService {

	/**
	 * 获取用户的银行卡
	 * 
	 * @param userId
	 * @return
	 */
	List<BankCard> findBankCardByUserId(Long userId);

	/**
	 * 获取系统支持的银行列表
	 * 
	 * @return
	 */
	List<Bank> findAllBank();

	Map<String, Object> findCreditorListByType(int type, Long userId, Pageable pageable);

	/**
	 * 获取回帐查询列表
	 * 
	 * @param type
	 * @param userId
	 * @param pageable
	 * @return
	 */
	Map<String, Object> findBackAccountListByType(int type, Long userId, Pageable pageable);

	/**
	 * 获取单条回帐查询详情
	 * 
	 * @param type
	 * @param userId
	 * @param pageable
	 * @return
	 */
	Map<String, Object> findSingleBackAccountDetail(int type, Long userId, Long bidId, Long creditId, Integer issue);

	BidCreditRecord findByIdAndUserId(Long id, Long userId);

	Map<String, Object> findCreditorListByTypeV2(int type, Long userId, Pageable pageable);
	
	Map<String, Object> getProjectSy(Long bidId, BigDecimal creditPrice);
	
	void IncomeRate();
}