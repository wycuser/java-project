package com.shanlin.p2p.app.service.impl;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.UnixCrypt;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shanlin.framework.controller.InterfaceResultCode;
import com.shanlin.framework.exception.ServiceException;
import com.shanlin.p2p.app.dao.CreditAuthProjectDao;
import com.shanlin.p2p.app.dao.FundAccountDao;
import com.shanlin.p2p.app.dao.UserAccountDao;
import com.shanlin.p2p.app.dao.UserExtendCodeDao;
import com.shanlin.p2p.app.dao.UserSafeStatusDao;
import com.shanlin.p2p.app.model.CreditAuthProject;
import com.shanlin.p2p.app.model.FundAccount;
import com.shanlin.p2p.app.model.UserAccount;
import com.shanlin.p2p.app.model.UserExtendCode;
import com.shanlin.p2p.app.model.UserSafeStatus;
import com.shanlin.p2p.app.model.enums.FundAccountType;
import com.shanlin.p2p.app.model.enums.Judge;
import com.shanlin.p2p.app.model.enums.RealAuthIspass;
import com.shanlin.p2p.app.model.enums.UserStatus;
import com.shanlin.p2p.app.service.RedPacketRecordService;
import com.shanlin.p2p.app.service.UserAccountService;

@Service
@Transactional(readOnly = true)
public class UserAccountServiceImpl implements UserAccountService {

	private final static Logger log = LoggerFactory.getLogger(UserAccountServiceImpl.class);
	
	@Resource	
	private UserAccountDao userAccountDao;
	
	@Resource	
	private UserSafeStatusDao userSafeStatusDao;
	
	@Resource
	private FundAccountDao fundAccountDao;
	
	@Resource
	private CreditAuthProjectDao creditAuthProjectDao;
	
	@Resource
	private UserExtendCodeDao userExtendCodeDao;
	
	@Resource
	private RedPacketRecordService redPacketRecordService;
	
	/**
	 * 使用登录名和密码查找用户
	 * 
	 * @param loginName
	 * @param password
	 * @return
	 */
	@Override
	public UserAccount findByLoginNameAndPassword(String loginName,
			String password) {
		UserAccount user = userAccountDao.findByLoginNameOrMobilePhone(loginName, loginName);
		if (user == null || user.getIsDel() == Judge.S) {
			log.info("用户:{} 不存在", loginName);
			throw new ServiceException(InterfaceResultCode.FAILED, "用户不存在");
		}
		String cryptPass = UnixCrypt.crypt(password, DigestUtils.sha256Hex(password));
		if(!cryptPass.equals(user.getPassword())){
			throw new ServiceException(InterfaceResultCode.FAILED, "密码错误");
		}
		UserStatus userStatus = user.getUserStatus();
		if(userStatus != UserStatus.QY){
			throw new ServiceException(InterfaceResultCode.FAILED, "账户被锁定");
		}
		return user;
	}

	/**
	 * 是否存在该手机
	 * 
	 * @param mobilePhone
	 * @return
	 */
	@Override
	public boolean isExistMobilePhone(String mobilePhone) {
		return userAccountDao.getSizeByMobilePhone(mobilePhone).intValue() > 0;
	}
	
	/**
	 * 是否存在该用户
	 * 
	 * @param LoginName
	 * @return
	 */
	@Override
	public boolean isExistLoginName(String loginName) {
		return userAccountDao.getSizeByLoginName(loginName) != null;
	}

	@Override
	public boolean isExistUserAccount(Long userId) {
		return userAccountDao.exists(userId);
	}

	@Override
	public UserAccount findOne(Long userId) {
		return userAccountDao.findOne(userId);
	}

	@Override
	public UserAccount findByLoginName(String loginName) {
		return userAccountDao.findByLoginNameAndIsDel(loginName, Judge.F);
	}

	/**
	 * 是否通过安全认证
	 * @param userId
	 * @return 是否通过安全认证
	 */
	@Override
	public boolean isSafeByUserId(Long userId) {
		UserSafeStatus userSafeStatus = userSafeStatusDao.findOne(userId);
		return userSafeStatus.getIdCardStatus() ==  RealAuthIspass.TG
				//&& userSafeStatus.getMailStatus() == RealAuthIspass.TG
				&& userSafeStatus.getPhoneStatus() == RealAuthIspass.TG;
//				&& userSafeStatus.getMoneyPassStatus() == RealAuthSet.YSZ;
	}

	@Override
	public Integer findRedPacketNum(String loginName) {
		return userAccountDao.findRedPacketNum(loginName);
	}

	@Override
	@Transactional
	public UserAccount register(UserAccount userAccount, String parentCode) {
		if(userAccountDao.getSizeByLoginName(userAccount.getLoginName()).intValue() > 0)
			throw new ServiceException(InterfaceResultCode.FAILED, "用户名已存在");
		if(userAccountDao.getSizeByMobilePhone(userAccount.getMobilePhone()).intValue() > 0)
			throw new ServiceException(InterfaceResultCode.FAILED, "手机号码已存在");
		
		userAccount.setPassword(UnixCrypt.crypt(userAccount.getPassword(),
				DigestUtils.sha256Hex(userAccount.getPassword())));
		UserAccount dbUserAccount = userAccountDao.save(userAccount);
		if(dbUserAccount == null){
			log.error("保存用户失败");
			throw new ServiceException(InterfaceResultCode.SYS_ERROR,"系统异常");
		}
		Long userId = dbUserAccount.getId();
		//添加推广码 S61.T6111
		String myCode = null;
		
		if(StringUtils.isNotBlank(parentCode)){
			UserExtendCode userExtendCode = userExtendCodeDao.findByMyCode(parentCode);
			if(userExtendCode == null)
				userExtendCode = userExtendCodeDao.findByInteriorCode(parentCode);
			if(userExtendCode == null){
				log.error("注册用户{} 添加的推广码{}找不到对应的推广人", userAccount.getLoginName(), parentCode);
				throw new ServiceException(InterfaceResultCode.FAILED, "邀请码错误");
			}
			parentCode = userExtendCode.getMyCode();
			//首次充值奖励记录(T6311)
			userAccountDao.initExtendRewardRecord(userExtendCode.getId(), userId);
			//推广奖励统计更新(T6310)
			userAccountDao.updateExtendRewardStatist(userExtendCode.getId());
		}
		while(true){
			myCode = getMyCode();
			if(userAccountDao.getCodeNum(myCode) == 0){
				userAccountDao.initMyCode(userId, myCode, parentCode);
				break;
			}
		}
		//用户理财统计表 S61.T6115
		userAccountDao.initFundStratist(userId);
		//用户信用账户表 S61.T6116
		userAccountDao.initCreditAccount(userId);
		//用户安全认证表 S61.T6118
		userAccountDao.initUserSafeStatus(userId, RealAuthIspass.TG.name(), userAccount.getMobilePhone());
		//个人基础信息 S61.T6141
		userAccountDao.initAuthAccount(userId);
		//推广奖励统计 S63.T6310
		userAccountDao.initPromoteReward(userId);
		//优选理财统计 S64.T6413
		userAccountDao.initSelectManage(userId);
		//信用档案 S61.T6144
		userAccountDao.initCreditFile(userId);
		//添加资金帐号(往来账户，保证金账户， 锁定账户) S61.T6101
		for(FundAccountType fundAccountType : FundAccountType.values()){
			FundAccount fundAccount = new FundAccount();
//			fundAccount.setUserAccount(dbUserAccount);
			fundAccount.setUserId(userId);
			fundAccount.setType(fundAccountType);
			fundAccount.setFundAccountName(getFundAccountName(fundAccountType.name(), userId));
			fundAccount.setLoginName(dbUserAccount.getLoginName());
			fundAccount.setBalance(BigDecimal.ZERO);
			fundAccount.setLastUpdateTime(new Date());
			fundAccountDao.save(fundAccount);
		}
		//添加用户认证信息 S61.T6120
		List<CreditAuthProject> creditList = creditAuthProjectDao.findAll();
		for (CreditAuthProject project : creditList) {
			userAccountDao.initUserAuthMess(userId, project.getId());
		}
		//发红包
		redPacketRecordService.sendRedPacket(userId, 2, 1);
		return dbUserAccount;
	}
	
	/**
	 * 无用户名注册
	 */
	@Override
	@Transactional
	public UserAccount register2(UserAccount userAccount, String parentCode) {
		String mobile = userAccount.getMobilePhone();
		if(userAccountDao.getSizeByMobilePhone(mobile).intValue() > 0)
			throw new ServiceException(InterfaceResultCode.FAILED, "手机号码已存在");
		StringBuilder accountName = new StringBuilder("sx").append(mobile);
		while(userAccountDao.getSizeByLoginName(accountName.toString()).intValue() > 0){
			accountName.setLength(13);
			accountName.append(getRandom(4));
		}
		userAccount.setLoginName(accountName.toString());
		userAccount.setPassword(UnixCrypt.crypt(userAccount.getPassword(),
				DigestUtils.sha256Hex(userAccount.getPassword())));
		UserAccount dbUserAccount = userAccountDao.save(userAccount);
		if(dbUserAccount == null){
			log.error("保存用户失败");
			throw new ServiceException(InterfaceResultCode.SYS_ERROR,"系统异常");
		}
		Long userId = dbUserAccount.getId();
		//添加推广码 S61.T6111
		String myCode = null;
		
		if(StringUtils.isNotBlank(parentCode)){
			UserExtendCode userExtendCode = userExtendCodeDao.findByMyCode(parentCode);
			if(userExtendCode == null)
				userExtendCode = userExtendCodeDao.findByInteriorCode(parentCode);
			if(userExtendCode == null){
				log.error("注册用户{} 添加的推广码{}找不到对应的推广人", userAccount.getLoginName(), parentCode);
				throw new ServiceException(InterfaceResultCode.FAILED, "邀请码错误");
			}
			parentCode = userExtendCode.getMyCode();
			//首次充值奖励记录(T6311)
			userAccountDao.initExtendRewardRecord(userExtendCode.getId(), userId);
			//推广奖励统计更新(T6310)
			userAccountDao.updateExtendRewardStatist(userExtendCode.getId());
		}
		while(true){
			myCode = getMyCode();
			if(userAccountDao.getCodeNum(myCode) == 0){
				userAccountDao.initMyCode(userId, myCode, parentCode);
				break;
			}
		}
		//用户理财统计表 S61.T6115
		userAccountDao.initFundStratist(userId);
		//用户信用账户表 S61.T6116
		userAccountDao.initCreditAccount(userId);
		//用户安全认证表 S61.T6118
		userAccountDao.initUserSafeStatus(userId, RealAuthIspass.TG.name(), userAccount.getMobilePhone());
		//个人基础信息 S61.T6141
		userAccountDao.initAuthAccount(userId);
		//推广奖励统计 S63.T6310
		userAccountDao.initPromoteReward(userId);
		//优选理财统计 S64.T6413
		userAccountDao.initSelectManage(userId);
		//信用档案 S61.T6144
		userAccountDao.initCreditFile(userId);
		//添加资金帐号(往来账户，保证金账户， 锁定账户) S61.T6101
		for(FundAccountType fundAccountType : FundAccountType.values()){
			FundAccount fundAccount = new FundAccount();
//			fundAccount.setUserAccount(dbUserAccount);
			fundAccount.setUserId(userId);
			fundAccount.setType(fundAccountType);
			fundAccount.setFundAccountName(getFundAccountName(fundAccountType.name(), userId));
			fundAccount.setLoginName(dbUserAccount.getLoginName());
			fundAccount.setBalance(BigDecimal.ZERO);
			fundAccount.setLastUpdateTime(new Date());
			fundAccountDao.save(fundAccount);
		}
		//添加用户认证信息 S61.T6120
		List<CreditAuthProject> creditList = creditAuthProjectDao.findAll();
		for (CreditAuthProject project : creditList) {
			userAccountDao.initUserAuthMess(userId, project.getId());
		}
		//发红包
		redPacketRecordService.sendRedPacket(userId, 2, 1);
		return dbUserAccount;
	}
	/**
	 * 取出一个指定长度大小的随机正整数
	 * 
	 * @param length
	 *            长度需要小于10有效
	 * @return 返回生成的随机数
	 */
	private int getRandom(int length) {
		int num = 1;
		double random = Math.random();
		if (random < 0.1) {
			random = random + 0.1;
		}
		for (int i = 0; i < length; i++) {
			num = num * 10;
		}
		return (int) ((random * num));
	}
	/**
	 * 生成资金账户 账号
	 * 
	 * @param type
	 * @param id
	 * @return
	 */
	private String getFundAccountName(String type, Long userId) {
		DecimalFormat df = new DecimalFormat("00000000000");
		StringBuilder sb = new StringBuilder();
		sb.append(type.substring(0, 1));
		sb.append(df.format(userId));
		return sb.toString();
	}
	
	private String getMyCode() {
		char[] chs = { 'a', 'b', 'c', '1', '2', '3', '4', '5', 'd', 'e', 'f',
				'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
				's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '6', '7', '8', '9' };
		SecureRandom random = new SecureRandom();
		final char[] value = new char[6];
		for (int i = 0; i < 6; i++) {
			value[i] = (char) chs[random.nextInt(chs.length)];
		}
		final String code = new String(value);
		return code;
	}

	@Override
	@Transactional
	public void updateUserAccount(UserAccount account) {
		userAccountDao.save(account);
	}

	@Override
	public boolean isExistEmail(String email) {
		return userAccountDao.getSizeByEmail(email).intValue() > 0;
	}

	@Override
	public UserExtendCode findExtendCodeById(Long userId) {
		return userExtendCodeDao.findOne(userId);
	}

	@Override
	public UserAccount findByMobilePhone(String mobilePhone) {
		return userAccountDao.findByMobilePhone(mobilePhone);
	}

	@Override
	public UserAccount findUserByCode(String pCode) {
		UserExtendCode userExtendCode = userExtendCodeDao.findByMyCode(pCode);
		if(userExtendCode == null)
			userExtendCode = userExtendCodeDao.findByInteriorCode(pCode);
		if(userExtendCode == null)
			return null;
		return userAccountDao.findOne(userExtendCode.getId());
	}

	@Override
	public UserAccount findByEmail(String email) {
		return userAccountDao.findByEmail(email);
	}

	@Override
	public boolean isRegisterHuiChaoPay(Long userId) {
		String huiChaoPayId = userAccountDao.findHuiChaoPayIdByUserId(userId);
		return StringUtils.isNotBlank(huiChaoPayId);
	}

	@Override
	public boolean isSafeByLoginName(String loginName) {
		UserAccount account = userAccountDao.findByLoginNameAndIsDel(loginName, Judge.F);
		if(account == null)
			throw new ServiceException(InterfaceResultCode.FAILED, "找不到该用户");
		UserSafeStatus userSafeStatus = userSafeStatusDao.findOne(account.getId());
		return userSafeStatus.getIdCardStatus() ==  RealAuthIspass.TG
				//&& userSafeStatus.getMailStatus() == RealAuthIspass.TG
				&& userSafeStatus.getPhoneStatus() == RealAuthIspass.TG;
//				&& userSafeStatus.getMoneyPassStatus() == RealAuthSet.YSZ;
	}

	@Override
	public Object findInteriorCode(String myCode) {
		return userExtendCodeDao.findInteriorCode(myCode);
	}

	@Override
	@Transactional
	public void bindPhone(Long userId, String mobilePhone) {
		UserAccount userAccount = userAccountDao.findOne(userId);
		userAccount.setMobilePhone(mobilePhone);
		userAccountDao.save(userAccount);
		UserSafeStatus userSafeStatus = userSafeStatusDao.findOne(userId);
		userSafeStatus.setMobilePhone(mobilePhone);
		userSafeStatus.setPhoneStatus(RealAuthIspass.TG);
		userSafeStatusDao.save(userSafeStatus);
	}

	@Override
	public Page<Object[]> findExtendData(Long userId, int page, int size) {
		long total = userAccountDao.findExtendDataCount(userId);
		List<Object[]> content = total > page * size ?  userAccountDao.findExtendData(userId, page * size, size) : Collections.<Object[]> emptyList();
		return new PageImpl<Object[]>(content, new PageRequest(page, size), total);
	}

}
