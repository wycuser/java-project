package com.shanlin.p2p.app.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.UnixCrypt;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shanlin.framework.controller.BaseAction;
import com.shanlin.framework.controller.InterfaceResultCode;
import com.shanlin.framework.exception.ServiceException;
import com.shanlin.framework.utils.Formater;
import com.shanlin.p2p.app.constant.Constants;
import com.shanlin.p2p.app.constant.MemcachedKeyConfig;
import com.shanlin.p2p.app.constant.MessageConstants;
import com.shanlin.p2p.app.dao.BidDao;
import com.shanlin.p2p.app.dao.BidRepaymentRecordDao;
import com.shanlin.p2p.app.dao.RepaymentStatusDao;
import com.shanlin.p2p.app.model.Bid;
import com.shanlin.p2p.app.model.FundAccount;
import com.shanlin.p2p.app.model.RepaymentStatus;
import com.shanlin.p2p.app.model.UserAccount;
import com.shanlin.p2p.app.model.UserExtendCode;
import com.shanlin.p2p.app.model.UserSafeStatus;
import com.shanlin.p2p.app.model.enums.FundAccountType;
import com.shanlin.p2p.app.model.enums.RealAuthSet;
import com.shanlin.p2p.app.service.BidCreditRecordService;
import com.shanlin.p2p.app.service.BidRepaymentRecordService;
import com.shanlin.p2p.app.service.BidService;
import com.shanlin.p2p.app.service.EmailService;
import com.shanlin.p2p.app.service.ExperienceAccountService;
import com.shanlin.p2p.app.service.FundAccountFlowService;
import com.shanlin.p2p.app.service.FundAccountService;
import com.shanlin.p2p.app.service.InvestRecordService;
import com.shanlin.p2p.app.service.LetterService;
import com.shanlin.p2p.app.service.LoanApplyService;
import com.shanlin.p2p.app.service.RemoteInvokeService;
import com.shanlin.p2p.app.service.SafeCertifiedService;
import com.shanlin.p2p.app.service.SmsService;
import com.shanlin.p2p.app.service.UserAccountService;

/**
 * 
 * @author zheng xin
 * @createDate 2015年1月28日
 */
@Controller
@RequestMapping("/user")
public class UserAction extends BaseAction {
	
	private static final Logger log = LoggerFactory.getLogger(UserAction.class);

	@Resource
	private UserAccountService userAccountService;

	@Resource
	private FundAccountService fundAccountService;
	
	@Resource
	private InvestRecordService investRecordService;

	@Resource
	private BidCreditRecordService bidCreditRecordService;

	@Resource
	private BidRepaymentRecordService bidRepaymentRecordService;

	@Resource
	private FundAccountFlowService fundAccountFlowService;

	@Resource
	private LetterService letterService;

	@Resource
	private SmsService smsService;

	@Resource
	private EmailService emailService;

	@Resource
	private RemoteInvokeService remoteInvokeService;

	@Resource
	private ExperienceAccountService experienceAccountService;

	@Resource
	private SafeCertifiedService safeCertifiedService;
	
	@Resource
	private BidService bidService;
	
	@Resource
	private ThreadPoolExecutor threadPool;
	
	@Resource
	private BidDao bidDao;
	
	@Resource
	private  RepaymentStatusDao repaymentStatusDao;
	
	@Resource
	private BidRepaymentRecordDao bidRepaymentRecordDao;
	
	@Resource
	private LoanApplyService loanApplyService;
	
	/**
	 * 发送注册短信验证码
	 * 
	 * @param mobilePhone
	 * @param type
	 *            1 注册手机号码 2 找回密码
	 */
	@RequestMapping("/sendVerifyCode")
	@ResponseBody
	public void registerVerifyCode(@RequestParam(required = false) String mobilePhone, @RequestParam(required = false) String email, @RequestParam(defaultValue = "-1") int type) {
		String vCode = getRandomNumber();
		Map<String, Object> param = new HashMap<>(1);
		param.put("code", vCode);
		switch (type) {
		case 1://注册手机号码
				// 校验参数
			Validate.matchesPattern(mobilePhone, Constants.MOBILEPHONE_PATTERN, "手机号码格式不正确");
			if (userAccountService.isExistMobilePhone(mobilePhone))
				throw new IllegalArgumentException("手机号码已存在");
			// 发送短信
			smsService.send(Formater.formatString(MessageConstants.SMS_SEND_REGISTER_VERIFYCODE, param), mobilePhone);
			//保存到memcache
			memcachedClient.set(MemcachedKeyConfig.VERIFYCODE, mobilePhone, vCode);
			break;
		case 2://手机找回密码
				// 校验参数
			Validate.matchesPattern(mobilePhone, Constants.MOBILEPHONE_PATTERN, "手机号码格式不正确");
			if (!userAccountService.isExistMobilePhone(mobilePhone))
				throw new IllegalArgumentException("没有该手机号码记录");
			// 发送短信
			smsService.send(Formater.formatString(MessageConstants.SMS_SEND_FIND_VERIFYCODE, param), mobilePhone);
			//保存到memcache
			memcachedClient.set(MemcachedKeyConfig.VERIFYCODE, mobilePhone, vCode);
			break;
		case 3://邮箱找回密码
				// 校验参数
			Validate.matchesPattern(mobilePhone, Constants.EMAIL_PATTERN, "邮箱格式不正确");
			if (!userAccountService.isExistEmail(email))
				throw new IllegalArgumentException("没有该邮箱记录");
			//发送邮件
			emailService.send("找回密码", MessageConstants.SMS_SEND_FIND_VERIFYCODE, param, email);
			//保存到memcache
			memcachedClient.set(MemcachedKeyConfig.VERIFYCODE, email, vCode);
		default:
			throw new IllegalArgumentException("参数异常");
		}
	}

	/**
	 * 用户登录
	 * 
	 * @param loginName
	 *            用户名/手机号码
	 * @param password
	 *            密码
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> login(@RequestParam String loginName, @RequestParam String password) {
		// 校验参数
		Validate.notBlank(loginName, "用户名不能为空");
		Validate.notBlank(password, "密码不能为空");
		UserAccount user = userAccountService.findByLoginNameAndPassword(loginName, password);
		Map<String, Object> map = new HashMap<>(4);
		Long uid = user.getId();
		String ln = user.getLoginName();
		String token = getToken(uid, ln);
		//保存token到memcache
		memcachedClient.set(MemcachedKeyConfig.LOGIN_TOKEN, uid, token);
		memcachedClient.set(MemcachedKeyConfig.LOGIN_NAME, uid, ln);
		//增加积分
		//remoteInvokeService.httpInvoke(user.getLoginName(), RemoteInvokeService.LOGIN, null);
		map.put("userId", uid);
		map.put("loginName", ln);
		map.put("mobilePhone", user.getMobilePhone());
		map.put("token", token);
		return map;
	}

	/**
	 * 用户注册
	 * 
	 * @param loginName
	 * @param password
	 * @param mobilePhone
	 * @param vCode
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> register(@RequestParam String loginName, @RequestParam String password, @RequestParam String mobilePhone, @RequestParam String vCode, @RequestParam(required = false) String pCode) {
		// 校验参数
		Validate.matchesPattern(loginName, Constants.LOGINNAME_PATTERN, "用户名为6-18个字符，可使用字母、数字、下划线，需以字母开头");
		Validate.notBlank(password, "密码不能为空");
		if (password.length() < 6 || password.length() > 20) {
			throw new IllegalArgumentException("密码不能小于6位，大于20位");
		}
		Validate.matchesPattern(mobilePhone, Constants.MOBILEPHONE_PATTERN, "手机号码格式不正确");
		Validate.notBlank(vCode, "验证码不能为空");
		if (!memcachedClient.check(MemcachedKeyConfig.VERIFYCODE, mobilePhone, vCode)) {
			throw new IllegalArgumentException("无效的验证码或验证码已过期");
		}
		//注册用户
		UserAccount userAccount = new UserAccount();
		userAccount.setLoginName(loginName);
		userAccount.setPassword(password);
		userAccount.setMobilePhone(mobilePhone);
		userAccount.setRegisterTime(new Date());
		userAccount = userAccountService.register(userAccount, pCode);
		String ln = userAccount.getLoginName();
		Long uid = userAccount.getId();
		String token = getToken(uid, ln);
		// 发送站内信
		Map<String, Object> args = new HashMap<>();
		args.put("name", ln);
		letterService.sendLetter(uid, "注册成功", MessageConstants.LETTER_USER_REGISTER_SUCCESS, args);
		//保存token到memcache
		memcachedClient.set(MemcachedKeyConfig.LOGIN_TOKEN, uid, token);
		memcachedClient.set(MemcachedKeyConfig.LOGIN_NAME, uid, ln);
		if (StringUtils.isNotBlank(pCode)) {
			UserAccount parent = userAccountService.findUserByCode(pCode);
			if (parent != null) {
				//增加积分
				remoteInvokeService.httpInvoke(ln, RemoteInvokeService.REFERRER, null);
				remoteInvokeService.httpInvoke(parent.getLoginName(), RemoteInvokeService.RECOMMENDED, null);
			}
		}
		//添加体验金
		//		if(Constants.EXPERIENCE_ADD){
		//			BigDecimal money = new BigDecimal(200);
		//			experienceAccountService.addExperienceMoney(uid, money, ExperienceSource.ZC);
		//		}
		//同步论坛和善林会
		remoteInvokeService.addBbsAccount(ln, password, mobilePhone);
		
		//remoteInvokeService.httpInvoke(ln, RemoteInvokeService.REGISTER, null);
		Map<String, Object> map = new HashMap<>(4);
		map.put("userId", uid);
		map.put("loginName", ln);
		map.put("mobilePhone", userAccount.getMobilePhone());
		map.put("token", token);
		return map;
	}
	
	/**
	 * 用户注册
	 * 
	 * @param loginName
	 * @param password
	 * @param mobilePhone
	 * @param vCode
	 */
	@RequestMapping(value = "/registerV2", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> registerV2(@RequestParam String mobilePhone, @RequestParam String password, @RequestParam String vCode, @RequestParam(required = false) String pCode) {
		// 校验参数
//		Validate.matchesPattern(loginName, Constants.LOGINNAME_PATTERN, "用户名为6-18个字符，可使用字母、数字、下划线，需以字母开头");
		Validate.notBlank(password, "密码不能为空");
		if (password.length() < 6 || password.length() > 20) {
			throw new IllegalArgumentException("密码不能小于6位，大于20位");
		}
		Validate.matchesPattern(mobilePhone, Constants.MOBILEPHONE_PATTERN, "手机号码格式不正确");
		Validate.notBlank(vCode, "验证码不能为空");
		if (!memcachedClient.check(MemcachedKeyConfig.VERIFYCODE, mobilePhone, vCode)) {
			throw new IllegalArgumentException("无效的验证码或验证码已过期");
		}
		//注册用户
		UserAccount userAccount = new UserAccount();
//		userAccount.setLoginName(loginName);
		userAccount.setPassword(password);
		userAccount.setMobilePhone(mobilePhone);
		userAccount.setRegisterTime(new Date());
		userAccount = userAccountService.register2(userAccount, pCode);
		String ln = userAccount.getLoginName();
		Long uid = userAccount.getId();
		String token = getToken(uid, ln);
		// 发送站内信
		Map<String, Object> args = new HashMap<>();
		args.put("name", ln);
		letterService.sendLetter(uid, "注册成功", MessageConstants.LETTER_USER_REGISTER_SUCCESS, args);
		//保存token到memcache
		memcachedClient.set(MemcachedKeyConfig.LOGIN_TOKEN, uid, token);
		memcachedClient.set(MemcachedKeyConfig.LOGIN_NAME, uid, ln);
		if (StringUtils.isNotBlank(pCode)) {
			UserAccount parent = userAccountService.findUserByCode(pCode);
			if (parent != null) {
				//增加积分
				remoteInvokeService.httpInvoke(ln, RemoteInvokeService.REFERRER, null);
				remoteInvokeService.httpInvoke(parent.getLoginName(), RemoteInvokeService.RECOMMENDED, null);
			}
		}
		//添加体验金
		//		if(Constants.EXPERIENCE_ADD){
		//			BigDecimal money = new BigDecimal(200);
		//			experienceAccountService.addExperienceMoney(uid, money, ExperienceSource.ZC);
		//		}
		//同步论坛和善林会
		remoteInvokeService.addBbsAccount(ln, password, mobilePhone);
		
		//remoteInvokeService.httpInvoke(ln, RemoteInvokeService.REGISTER, null);
		Map<String, Object> map = new HashMap<>(4);
		map.put("userId", uid);
		map.put("loginName", ln);
		map.put("mobilePhone", userAccount.getMobilePhone());
		map.put("token", token);
		return map;
	}

	/**
	 * 用户中心 我的财富
	 * 
	 * @param userId
	 *            用户id
	 * @return
	 */
	@RequestMapping("/index")
	@ResponseBody
	public Map<String, Object> index(@RequestParam Long userId) {
		Map<String, Object> map = new HashMap<>();
		UserAccount userAccount = userAccountService.findOne(userId);
		FundAccount fundAccount = fundAccountService.findWlzhByUserId(userId);
		map.put("userId", userId);
		map.put("loginName", userAccount.getLoginName());
		map.put("mobilePhone", userAccount.getMobilePhone() == null? " " : userAccount.getMobilePhone());
		// 账户可用余额
		BigDecimal balance = fundAccount.getBalance();
		// 持有债权金额
		BigDecimal holdPrice = bidCreditRecordService.findHoldPriceByUserId(userId);
		// 未还已借金额
		BigDecimal shouldPrice = bidRepaymentRecordService.findShouldPriceByUserId(userId);
		// 全部收益
		BigDecimal lucre = fundAccountFlowService.findLucreByAccountId(fundAccount.getId());
		// 当天收益
		BigDecimal currLucre = fundAccountFlowService.findCurrentDateLucreByAccountId(fundAccount.getId());
		BigDecimal freeze = fundAccountService.findBalanceByUserIdAndType(userId, FundAccountType.SDZH);
		map.put("accountAsset", Formater.formatAmount(balance.add(holdPrice == null ? BigDecimal.ZERO : holdPrice).add(freeze == null ? BigDecimal.ZERO : freeze).subtract(shouldPrice == null ? BigDecimal.ZERO : shouldPrice)));
		map.put("balance", Formater.formatAmount(balance));
		map.put("currentDateLucre", Formater.formatAmount(currLucre));
		map.put("lucre", Formater.formatAmount(lucre));
		map.put("isAuth", userAccountService.isSafeByUserId(userId) ? 1 : 0);
		map.put("redPacketNum", userAccountService.findRedPacketNum(userAccount.getLoginName()));
		map.put("unRead", letterService.getUnReadCount(userId));
		return map;
	}

	/**
	 * 退出登录
	 * 
	 * @param userId
	 */
	@RequestMapping("/logout")
	@ResponseBody
	public Long logout(@RequestParam Long userId) {
		memcachedClient.delete(MemcachedKeyConfig.LOGIN_TOKEN.getPrefix() + userId);
		return userId;
	}

	/**
	 * 修改用户密码
	 * 
	 * @param userId
	 * @param oldPass
	 * @param newPass
	 */
	@RequestMapping("/updatePassword")
	@ResponseBody
	public void updatePassword(@RequestParam Long userId, @RequestParam String oldPass, @RequestParam String newPass) {
		if (newPass.trim().length() < 6 || newPass.trim().length() > 20) {
			throw new IllegalArgumentException("密码不能小于6位，大于20位");
		}
		UserAccount account = userAccountService.findOne(userId);
		if (!account.getPassword().equals(UnixCrypt.crypt(oldPass, DigestUtils.sha256Hex(oldPass)))) {
			throw new IllegalArgumentException("原密码错误");
		}
		UserSafeStatus userSafeStatus = safeCertifiedService.findOne(account.getId());
		String cryptPass = UnixCrypt.crypt(newPass, DigestUtils.sha256Hex(newPass));
		if (userSafeStatus.getMoneyPassStatus() == RealAuthSet.YSZ && userSafeStatus.getMoneyPass().equals(cryptPass))
			throw new IllegalArgumentException("不能和提现密码相同");
		account.setPassword(cryptPass);
		userAccountService.updateUserAccount(account);
		remoteInvokeService.updatePass(account.getLoginName(), oldPass, newPass);
		//重新登录
		memcachedClient.delete(MemcachedKeyConfig.LOGIN_TOKEN.getPrefix() + userId);
	}

	public String getToken(Long userId, String loginName) {
		return DigestUtils.md5Hex(new StringBuilder(loginName).append(userId).append(System.currentTimeMillis()).toString());
	}

	/**
	 * 我的推广
	 * 
	 * @param userId
	 * @param model
	 * @return
	 */
	@RequestMapping("/myExtendCode")
	public String myExtendCode(@RequestParam Long userId, Model model) {
		UserExtendCode extendCode = userAccountService.findExtendCodeById(userId);
		if (extendCode == null)
			throw new ServiceException(InterfaceResultCode.FAILED, "找不到邀请码");
		Object interiorCode = userAccountService.findInteriorCode(extendCode.getMyCode());
		model.addAttribute("interiorCode", interiorCode);
		model.addAttribute(extendCode);
		return "user/myExtendCode";
	}

	/**
	 * 忘记密码第一步
	 * 
	 * @param type
	 * @param mobilePhone
	 * @param email
	 * @param vCode
	 */
	@RequestMapping("/resetPasswordOne")
	@ResponseBody
	public void resetPasswordOne(@RequestParam int type, @RequestParam(required = false) String mobilePhone, @RequestParam(required = false) String email, @RequestParam String vCode) {
		String suffix = null;
		switch (type) {
		case 1:
			if (mobilePhone == null)
				throw new IllegalArgumentException("手机不能为空");
			suffix = mobilePhone;
			break;
		case 2:
			if (email == null)
				throw new IllegalArgumentException("邮箱不能为空");
			suffix = email;
			break;
		default:
			return;
		}
		if (!memcachedClient.check(MemcachedKeyConfig.VERIFYCODE, suffix, vCode)) {
			throw new IllegalArgumentException("无效的验证码或验证码已过期");
		}
		memcachedClient.set(MemcachedKeyConfig.RESET_PASSWORD, suffix, suffix);
	}

	/**
	 * 忘记密码第二步
	 * 
	 * @param type
	 * @param mobilePhone
	 * @param email
	 * @param newPass
	 */
	@RequestMapping("/resetPasswordTwo")
	@ResponseBody
	public void resetPasswordTwo(@RequestParam int type, @RequestParam(required = false) String mobilePhone, @RequestParam(required = false) String email, @RequestParam String newPass) {
		if (newPass.trim().length() < 6 || newPass.trim().length() > 20) {
			throw new IllegalArgumentException("密码不能小于6位，大于20位");
		}

		String suffix = null;
		UserAccount userAccount = null;
		switch (type) {
		case 1:
			if (mobilePhone == null)
				throw new IllegalArgumentException("手机不能为空");
			suffix = mobilePhone;
			userAccount = userAccountService.findByMobilePhone(mobilePhone);
			break;
		case 2:
			if (email == null)
				throw new IllegalArgumentException("邮箱不能为空");
			suffix = email;
			userAccount = userAccountService.findByEmail(email);
			break;
		default:
			return;
		}
		if (!memcachedClient.check(MemcachedKeyConfig.RESET_PASSWORD, suffix, suffix)) {
			throw new IllegalArgumentException("无效的验证码或验证码已过期");
		}
		if (userAccount != null) {
			UserSafeStatus userSafeStatus = safeCertifiedService.findOne(userAccount.getId());
			String cryptPass = UnixCrypt.crypt(newPass, DigestUtils.sha256Hex(newPass));
			if (userSafeStatus.getMoneyPassStatus() == RealAuthSet.YSZ && userSafeStatus.getMoneyPass().equals(cryptPass))
				throw new IllegalArgumentException("不能和提现密码相同");
			userAccount.setPassword(cryptPass);
			userAccountService.updateUserAccount(userAccount);
			remoteInvokeService.updatePass(userAccount.getLoginName(), newPass, newPass);
		}
	}

	/**
	 * 每日签到
	 * 
	 * @param loginName
	 */
	@RequestMapping("/everydaySignIn")
	@ResponseBody
	public void everydaySignIn(@RequestParam String loginName) {
		if (!userAccountService.isExistLoginName(loginName))
			throw new IllegalArgumentException("该用户不存在");
		//remoteInvokeService.httpInvoke(loginName, RemoteInvokeService.LOGIN, null);
	}
	
	/**
	 * 用户中心 我的财富(版本2)
	 * 
	 * @param userId
	 *            用户id
	 * @return
	 */
	@RequestMapping("/indexV2")
	@ResponseBody
	public Map<String, Object> indexV2(@RequestParam Long userId) {
		Map<String, Object> map = new HashMap<>();
		UserAccount userAccount = userAccountService.findOne(userId);
		FundAccount fundAccount = fundAccountService.findWlzhByUserId(userId);
		map.put("userId", userId);
		map.put("loginName", userAccount.getLoginName());
		map.put("mobilePhone", userAccount.getMobilePhone() == null? "" : userAccount.getMobilePhone());
		// 账户可用余额
		BigDecimal balance = fundAccount.getBalance();
		// 未还已借金额
		BigDecimal shouldPrice = bidRepaymentRecordService.findShouldPriceByUserId(userId);
		BigDecimal lucre = fundAccountFlowService.findLucreByAccountId(fundAccount.getId());
		BigDecimal freeze = fundAccountService.findBalanceByUserIdAndType(userId, FundAccountType.SDZH);
		BigDecimal allWaitSourcePrice = bidRepaymentRecordService.findAllWaitRecycleSourcePrice(userId);
		BigDecimal allWaitInterestPrice = bidRepaymentRecordService.findAllWaitRecycleInterestPriceV2(userId);
		BigDecimal accountAssetZe = balance.add(freeze).add(allWaitSourcePrice).add(allWaitInterestPrice);
		BigDecimal zrProfit = bidCreditRecordService.findAllZrProfit(userId);
		lucre = (lucre==null?new BigDecimal(0.00):lucre);
		lucre = lucre.add(zrProfit==null?new BigDecimal(0.00):zrProfit);
		
		// 净资产
		map.put("accountAsset", Formater.formatAmount(accountAssetZe.subtract(shouldPrice)));
		map.put("balance", Formater.formatAmount(balance));
		// 累计收益
		map.put("lucre", Formater.formatAmount(lucre));
		map.put("isAuth", userAccountService.isSafeByUserId(userId) ? 1 : 0);
//		map.put("unRead", letterService.getUnReadCount(userId));
		map.put("unRead", 0);
		// 资产总额
		map.put("accountAssetZe", Formater.formatAmount(accountAssetZe));
		// 冻结金额
		map.put("freeze", Formater.formatAmount(freeze));
		// 累计投资
		map.put("allInvest", Formater.formatAmount(investRecordService.findAllCreditPriceByUser(userId)));
		// 待收本金
		map.put("allWaitSourcePrice", Formater.formatAmount(allWaitSourcePrice));
		// 待收收益
//		BigDecimal price = (allWaitSourcePrice.add(allWaitInterestPrice)).subtract(allBuyingPrice);
		map.put("allWaitInterestPrice", Formater.formatAmount(allWaitInterestPrice));
		return map;
	}
	
	@RequestMapping("/bindPhone")
	@ResponseBody
	public void bindPhone(@RequestParam Long userId, @RequestParam String mobilePhone,@RequestParam String vCode) {
		Validate.matchesPattern(mobilePhone, Constants.MOBILEPHONE_PATTERN, "手机号码格式不正确");
		if (userAccountService.isExistMobilePhone(mobilePhone)){
			throw new IllegalArgumentException("手机号码已存在");
		}
		Validate.notBlank(vCode, "验证码不能为空");
		if (!memcachedClient.check(MemcachedKeyConfig.VERIFYCODE, mobilePhone, vCode)) {
			throw new IllegalArgumentException("无效的验证码或验证码已过期");
		}
		userAccountService.bindPhone(userId, mobilePhone);
	}
	
	@RequestMapping("/findExtendData")
	@ResponseBody
	public Map<String, Object> findExtendData(@RequestParam Long userId, @RequestParam int page, @RequestParam int size){
		Page<Object[]> extendDataPage = userAccountService.findExtendData(userId, page, size);
		Map<String, Object> map = new HashMap<>();
		map.put("content", extendDataPage.getContent());
		map.put("hasNext", extendDataPage.hasNext());
		map.put("number", extendDataPage.getNumber());
		map.put("size", extendDataPage.getSize());
		return map;
	}
	
	/**
	 * 查询借款统计信息
	 * @param userId
	 */
	@RequestMapping("/credit")
	@ResponseBody
	public Map<String, Object> credit(@RequestParam Long userId){
		return bidService.getMyLoanCount(userId);
	}
	
	/**
	 * 我的借款列表
	 * @param userId
	 * @param page
	 * @param size
	 */
	@RequestMapping("/creditList")
	@ResponseBody
	public Map<String, Object> creditList(@RequestParam Long userId, @RequestParam int page, @RequestParam int size,int type){
		if(type==0){
			return bidService.getHkzJk(userId, page, size);
		}
		if(type==1){
			return bidService.getYhqJk(userId, page, size);
		}
		throw new IllegalArgumentException("参数异常");
	}
	
	/**
	 * 还款详情
	 * @param bid
	 * @param userId
	 */
	@RequestMapping("/loanDetail")
	@ResponseBody
	public Map<String, Object> loanDetail(@RequestParam Long userId,@RequestParam Long bid){
		return bidService.loanDetail(bid, userId);
	}
	
	/**
	 * 还款操作
	 * @param bid
	 * @param userId
	 */
	@RequestMapping("/payment")
	@ResponseBody
	public void payment(final @RequestParam Long userId,final @RequestParam Long bid,final @RequestParam int num){
		if (bid <= 0){
			throw new ServiceException(InterfaceResultCode.FAILED,"标记录不存在");
		}
		Bid vo=bidDao.findOne(bid);
		if(null==vo || (null!=vo && vo.getUserAccount().getId()!=userId))
			throw new ServiceException(InterfaceResultCode.FAILED,"标记录不存在");
		RepaymentStatus repaymentStatus=repaymentStatusDao.findByBidAndNum(bid, num);
		if(repaymentStatus!=null){
			throw new ServiceException(InterfaceResultCode.FAILED,"正在还款中...");
		}
		final Long[] ids=bidRepaymentRecordDao.getWHRecordIds(bid, num);
		if (ids == null || ids.length == 0) {
			throw new ServiceException(InterfaceResultCode.FAILED,"当期已还请");
		}
		//添加还款状态记录
		bidService.addRepaymentStatus(bid, num);;
		threadPool.execute(new Runnable() {
		@Override
		public void run() {
			for (Long id : ids) {
				try{
					bidService.repayment(id, bid, num);
				} catch(Exception e){
					log.error("还款失败_id:"+id,e);
					break;
				}
			}
		}
		});
	}
	
	/**
	 * 添加借款申请
	 * @param userId
	 */
	@RequestMapping("/addLoans")
	@ResponseBody
	public void addLoans(@RequestParam Long userId) {
		loanApplyService.addLoans(userId);
	}
}