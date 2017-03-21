package com.shanlin.p2p.app.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.UnixCrypt;
import org.apache.commons.lang3.Validate;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shanlin.framework.controller.BaseAction;
import com.shanlin.framework.utils.DateParser;
import com.shanlin.p2p.app.constant.Constants;
import com.shanlin.p2p.app.constant.MemcachedKeyConfig;
import com.shanlin.p2p.app.model.AuthAccount;
import com.shanlin.p2p.app.model.UserAccount;
import com.shanlin.p2p.app.model.UserSafeStatus;
import com.shanlin.p2p.app.model.enums.RealAuthIspass;
import com.shanlin.p2p.app.model.enums.RealAuthSet;
import com.shanlin.p2p.app.service.AuthAccountService;
import com.shanlin.p2p.app.service.EmailService;
import com.shanlin.p2p.app.service.ExperienceAccountService;
import com.shanlin.p2p.app.service.ExtendService;
import com.shanlin.p2p.app.service.RemoteInvokeService;
import com.shanlin.p2p.app.service.SafeCertifiedService;
import com.shanlin.p2p.app.service.SmsService;
import com.shanlin.p2p.app.service.UserAccountService;

/**
 * 安全认证 控制层
 * @author zheng xin
 * @createDate 2015年1月21日
 */
@Controller
@RequestMapping("/safeCertified")
public class SafeCertifiedAction extends BaseAction{
	
	private static final Logger log = LoggerFactory.getLogger(SafeCertifiedAction.class);
	
	@Resource
	private SafeCertifiedService safeCertifiedService;
	
	@Resource
	private AuthAccountService authAccountService;
	
	@Resource
	private UserAccountService userAccountService;
	
	@Resource
	private EmailService emailService;
	
	@Resource
	private SmsService smsService;
	
	@Resource
	private RemoteInvokeService remoteInvokeService;
	
	@Resource
	private ExperienceAccountService experienceAccountService;
	
	@Resource
	private ExtendService extendService;
	/**
	 * 安全认证
	 * @param userId
	 * @return
	 */
	@RequestMapping("/index")
	@ResponseBody
	public Map<String, Object> index(@RequestParam Long userId){
		UserSafeStatus userSafeStatus = safeCertifiedService.findOne(userId);
		Map<String, Object> map = new HashMap<>();
		map.put("cardStat", userSafeStatus.getIdCardStatus()==RealAuthIspass.TG?1:0);
		map.put("mailStat", userSafeStatus.getMailStatus()==RealAuthIspass.TG?1:0);
		map.put("mail", userSafeStatus.getMail());
		map.put("phoneStat", userSafeStatus.getPhoneStatus()==RealAuthIspass.TG?1:0);
		map.put("mobilePhone", userSafeStatus.getPhoneStatus()==RealAuthIspass.TG? userSafeStatus.getMobilePhone() : "");
		map.put("moneyPassStat", userSafeStatus.getMoneyPassStatus()==RealAuthSet.YSZ?1:0);
		return map;
	}
	
	/**
	 * 身份认证详细页
	 * @param userId
	 * @return
	 */
	@RequestMapping("/idcard")
	@ResponseBody
	public Map<String, Object> idCard(@RequestParam Long userId){
		AuthAccount authAccount = authAccountService.findOne(userId);
		Map<String, Object> map = new HashMap<>();
		map.put("name", authAccount.getName());
		map.put("idCard", authAccount.getIdentity());
		return map;
	}
	
	/**
	 * 添加身份证
	 * @param userId
	 * @param name
	 * @param idCard
	 */
	@RequestMapping(value="/idcard/add", method=RequestMethod.POST)
	@ResponseBody
	public void addIdCard(@RequestParam Long userId, @RequestParam String name, @RequestParam String idCard){
		Validate.notBlank(name, "姓名不能为空");
		Validate.matchesPattern(idCard, Constants.IDCARD_PATTERN, "身份证号码格式不正确");
		AuthAccount authAccount = authAccountService.findOne(userId);
		if(authAccount.getIsPass() == RealAuthIspass.TG)
			throw new IllegalArgumentException("您已通过实名验证");
		if(authAccountService.isExistIdCard(idCard))
			throw new IllegalArgumentException("该身份证已认证");
		if(new DateTime().getYear() - Integer.parseInt(idCard.substring(6, 10)) < 18)
			throw new IllegalArgumentException("必须年满18周岁");
		log.info("userId:{}, name:{}, idCard:{}", userId, name, idCard);
		if(!authAccountService.check(idCard, name))
			throw new IllegalArgumentException("认证不通过");
		
		authAccount.setName(name);
		authAccount.setBornTime(DateParser.getBirthday(idCard));
		authAccount.setIdentity(idCard);
		authAccount.setIsPass(RealAuthIspass.TG);
		authAccountService.updateIdCard(authAccount);
		UserSafeStatus userSafeStatus = safeCertifiedService.findOne(userId);
		userSafeStatus.setIdCardStatus(RealAuthIspass.TG);
		safeCertifiedService.updateUserSafeStatus(userSafeStatus);
		//实名推广奖励
		try {
			extendService.anthReward(userId);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		
		//增加积分
		UserAccount user = userAccountService.findOne(userId);
		remoteInvokeService.httpInvoke(user.getLoginName(), RemoteInvokeService.BIND_IDCARD_AUTH, "idcard=" + idCard);
		//增加体验金
//		if(Constants.EXPERIENCE_ADD){
//			experienceAccountService.addExperienceMoneyBySMRZ(userId);
//		}
	}
	
	/**
	 * 修改提现密码
	 * @param userId
	 * @param oldPass
	 * @param newPass
	 */
	@RequestMapping(value="/moneyPass/update", method=RequestMethod.POST)
	@ResponseBody
	public void updateMoneyPass(@RequestParam Long userId, @RequestParam String oldPass, @RequestParam String newPass){
		if(newPass.trim().length() < 6 || newPass.trim().length() > 20){
			throw new IllegalArgumentException("提现密码不能小于6位，大于20位");
		}
		UserSafeStatus userSafeStatus = safeCertifiedService.findOne(userId);
		UserAccount user = userAccountService.findOne(userId);
		if(!userSafeStatus.getMoneyPass().equals(UnixCrypt.crypt(oldPass, DigestUtils.sha256Hex(oldPass)))){
			throw new IllegalArgumentException("原密码错误");
		}
		String cryptPass = UnixCrypt.crypt(newPass, DigestUtils.sha256Hex(newPass));
		if(user.getPassword().equals(cryptPass))
			throw new IllegalArgumentException("不能和登录密码相同");
		
		userSafeStatus.setMoneyPass(cryptPass);
		userSafeStatus.setMoneyPassStatus(RealAuthSet.YSZ);
		safeCertifiedService.updateUserSafeStatus(userSafeStatus);
	}
	
	/**
	 * 添加提现密码
	 * @param userId
	 * @param newPass
	 */
	@RequestMapping(value="/moneyPass/add", method=RequestMethod.POST)
	@ResponseBody
	public void addMoneyPass(@RequestParam Long userId, @RequestParam String newPass){
		if(newPass.trim().length() < 6 || newPass.trim().length() > 20){
			throw new IllegalArgumentException("提现密码不能小于6位，大于20位");
		}
		UserSafeStatus userSafeStatus = safeCertifiedService.findOne(userId);
		UserAccount user = userAccountService.findOne(userId);
		String cryptPass = UnixCrypt.crypt(newPass, DigestUtils.sha256Hex(newPass));
		if(userSafeStatus.getMoneyPassStatus() == RealAuthSet.YSZ)
			throw new IllegalArgumentException("已设置提现密码");
		if(user.getPassword().equals(cryptPass))
			throw new IllegalArgumentException("不能和登录密码相同");
		userSafeStatus.setMoneyPass(cryptPass);
		userSafeStatus.setMoneyPassStatus(RealAuthSet.YSZ);
		safeCertifiedService.updateUserSafeStatus(userSafeStatus);
		//增加积分
		remoteInvokeService.httpInvoke(user.getLoginName(), RemoteInvokeService.BIND_MONEY_PASS_AUTH, null);
	}
	
	/**
	 * 添加或修改邮箱
	 * @param userId
	 * @param vCode
	 * @param email
	 */
	@RequestMapping(value="/email/save", method=RequestMethod.POST)
	@ResponseBody
	public void saveEmail(@RequestParam Long userId, @RequestParam String vCode, @RequestParam String email){
		Validate.matchesPattern(email, Constants.EMAIL_PATTERN, "邮箱格式不正确");
		if(!memcachedClient.check(MemcachedKeyConfig.VERIFYCODE, email, vCode))
			throw new IllegalArgumentException("无效的验证码或验证码已过期");
		if(!userAccountService.isExistEmail(email))
			throw new IllegalArgumentException("邮箱已存在");
		UserSafeStatus userSafeStatus = safeCertifiedService.findOne(userId);
		UserAccount user = userAccountService.findOne(userId);
		boolean isFirst = userSafeStatus.getMailStatus() != RealAuthIspass.TG;
		userSafeStatus.setMail(email);
		userSafeStatus.setMailStatus(RealAuthIspass.TG);
		safeCertifiedService.updateUserSafeStatus(userSafeStatus);
		user.setEmail(email);
		userAccountService.updateUserAccount(user);
		if(isFirst)
			//增加积分
			remoteInvokeService.httpInvoke(user.getLoginName(), RemoteInvokeService.BIND_EMAIL_AUTH, "email=" + email);
		else
			remoteInvokeService.httpInvoke(user.getLoginName(), RemoteInvokeService.EDIT_EMAIL, "email=" + email);
	}
	
	/**
	 * 添加或修改手机
	 * @param userId
	 * @param vCode
	 * @param mobilePhone
	 */
	@RequestMapping(value="/mobilephone/save", method=RequestMethod.POST)
	@ResponseBody
	public void saveMobilephone(@RequestParam Long userId, @RequestParam String vCode, @RequestParam String mobilePhone){
		Validate.matchesPattern(mobilePhone, Constants.MOBILEPHONE_PATTERN, "手机号码格式不正确");
		if(!memcachedClient.check(MemcachedKeyConfig.VERIFYCODE, mobilePhone, vCode))
			throw new IllegalArgumentException("无效的验证码或验证码已过期");
		if(!userAccountService.isExistMobilePhone(mobilePhone))
			throw new IllegalArgumentException("手机号码已存在");
		
		UserSafeStatus userSafeStatus = safeCertifiedService.findOne(userId);
		UserAccount user = userAccountService.findOne(userId);
		userSafeStatus.setMobilePhone(mobilePhone);
		userSafeStatus.setPhoneStatus(RealAuthIspass.TG);
		safeCertifiedService.updateUserSafeStatus(userSafeStatus);
		user.setMobilePhone(mobilePhone);
		userAccountService.updateUserAccount(user);
		//修改PHP手机号码
		remoteInvokeService.httpInvoke(user.getLoginName(), RemoteInvokeService.EDIT_MOBILE, "mobile=" + mobilePhone);
	}
	
	/**
	 * 忘记提现密码第一步
	 * @param mobilePhone
	 * @param vCode
	 */
	@RequestMapping("/resetMoneyPassOne")
	@ResponseBody
	public void resetMoneyPassOne(@RequestParam String mobilePhone, @RequestParam String vCode){
		if(!memcachedClient.check(MemcachedKeyConfig.VERIFYCODE, mobilePhone, vCode)){
			throw new IllegalArgumentException("无效的验证码或验证码已过期");
		}
		memcachedClient.set(MemcachedKeyConfig.RESET_MONEY_PASSWORD, mobilePhone, mobilePhone);
	}
	
	/**
	 * 忘记提现密码第二步
	 * @param userId
	 * @param mobilePhone
	 * @param newPass
	 */
	@RequestMapping("/resetMoneyPassTwo")
	@ResponseBody
	public void resetMoneyPassTwo(@RequestParam Long userId, @RequestParam String mobilePhone, @RequestParam String newPass){
		if(newPass.trim().length() < 6 || newPass.trim().length() > 20){
			throw new IllegalArgumentException("提现密码不能小于6位，大于20位");
		}
		if(!memcachedClient.check(MemcachedKeyConfig.RESET_MONEY_PASSWORD, mobilePhone, mobilePhone)){
			throw new IllegalArgumentException("无效的验证码或验证码已过期");
		}
		UserSafeStatus userSafeStatus = safeCertifiedService.findOne(userId);
		UserAccount user = userAccountService.findOne(userId);
		String cryptPass = UnixCrypt.crypt(newPass, DigestUtils.sha256Hex(newPass));
		if(userSafeStatus.getMoneyPassStatus() == RealAuthSet.WSZ)
			throw new IllegalArgumentException("未设置提现密码");
		if(user.getPassword().equals(cryptPass))
			throw new IllegalArgumentException("不能和登录密码相同");
		userSafeStatus.setMoneyPass(cryptPass);
		safeCertifiedService.updateUserSafeStatus(userSafeStatus);
	}
}