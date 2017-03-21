package com.shanlin.p2p.app.service;

import org.springframework.data.domain.Page;

import com.shanlin.p2p.app.model.UserAccount;
import com.shanlin.p2p.app.model.UserExtendCode;

/**
 * 用户业务
 * @author zheng xin
 * @createDate 2015年1月28日
 */
public interface UserAccountService {

	/**
	 * 使用登录名和密码查找用户
	 * @param loginName
	 * @param password
	 * @return
	 */
	UserAccount findByLoginNameAndPassword(String loginName, String password);

	/**
	 * 使用登录名查找用户
	 * @param loginName
	 * @return
	 */
	UserAccount findByLoginName(String loginName);
	
	/**
	 * 是否存在该手机
	 * @param mobilePhone
	 * @return
	 */
	boolean isExistMobilePhone(String mobilePhone);
	
	/**
	 * 是否存在该用户
	 * @param userId
	 * @return 
	 */
	boolean isExistUserAccount(Long userId);
	
	/**
	 * 是否存在该用户名
	 * @param LoginName
	 * @return
	 */
	boolean isExistLoginName(String loginName);
	
	/**
	 * 使用用户id查找用户账户
	 * @param userId
	 * @return 用户账户
	 */
	UserAccount findOne(Long userId);
	
	/**
	 * 是否通过安全认证
	 * @param userId
	 * @return 是否通过安全认证
	 */
	boolean isSafeByUserId(Long userId);
	
	/**
	 * 查找用户的红包个数
	 * @param loginName 登录名
	 * @return
	 */
	Integer findRedPacketNum(String loginName);

	/**
	 * 注册用户
	 * @param userAccount 用户对象
	 * @param parentCode 推广码
	 * @return
	 */
	UserAccount register(UserAccount userAccount, String parentCode);

	void updateUserAccount(UserAccount account);

	/**
	 * 是否存在该邮箱
	 * @param email
	 * @return
	 */
	boolean isExistEmail(String email);
	
	UserExtendCode findExtendCodeById(Long userId);

	UserAccount findByMobilePhone(String mobilePhone);

	UserAccount findUserByCode(String pCode);

	UserAccount findByEmail(String email);
	
	/**
	 * 是否注册第三方托管
	 * @param userId
	 * @return
	 */
	boolean isRegisterHuiChaoPay(Long userId);

	boolean isSafeByLoginName(String loginName);

	Object findInteriorCode(String myCode);

	/**
	 * 注册用户
	 * @param userAccount 用户对象
	 * @param parentCode 推广码
	 * @return
	 */
	UserAccount register2(UserAccount userAccount, String pCode);
	
	/**
	 * 绑定手机号码
	 * @param userId
	 * @param mobilePhone
	 */
	void bindPhone(Long userId, String mobilePhone);

	Page<Object[]> findExtendData(Long userId, int page, int size);

}
