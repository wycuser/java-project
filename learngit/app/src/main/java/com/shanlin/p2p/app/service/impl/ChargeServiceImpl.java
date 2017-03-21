package com.shanlin.p2p.app.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shanlin.framework.utils.FunctionUtil;
import com.shanlin.framework.utils.MD5;
import com.shanlin.framework.utils.StringHelper;
import com.shanlin.p2p.app.constant.Constants;
import com.shanlin.p2p.app.dao.AuthAccountDao;
import com.shanlin.p2p.app.dao.ChargeOradeDao;
import com.shanlin.p2p.app.dao.FundAccountDao;
import com.shanlin.p2p.app.dao.FundAccountFlowDao;
import com.shanlin.p2p.app.dao.OrdersDao;
import com.shanlin.p2p.app.dao.SysConstantDao;
import com.shanlin.p2p.app.dao.UserAccountDao;
import com.shanlin.p2p.app.dao.UserSafeStatusDao;
import com.shanlin.p2p.app.model.AuthAccount;
import com.shanlin.p2p.app.model.ChargeOrade;
import com.shanlin.p2p.app.model.FundAccount;
import com.shanlin.p2p.app.model.Orders;
import com.shanlin.p2p.app.model.UserAccount;
import com.shanlin.p2p.app.model.UserSafeStatus;
import com.shanlin.p2p.app.model.enums.FundAccountType;
import com.shanlin.p2p.app.model.enums.OrdersSource;
import com.shanlin.p2p.app.model.enums.OrdersStatus;
import com.shanlin.p2p.app.model.enums.OrdersType;
import com.shanlin.p2p.app.model.enums.PaymentInstitution;
import com.shanlin.p2p.app.model.enums.RealAuthIspass;
import com.shanlin.p2p.app.service.ChargeService;

@Service
@Transactional(readOnly = true)
public class ChargeServiceImpl implements ChargeService {

	@Resource	
	private UserAccountDao userAccountDao;
	
	@Resource	
	private UserSafeStatusDao userSafeStatusDao;
	
	@Resource
	private AuthAccountDao authAccountDao;
	
	@Resource
	private SysConstantDao sysConstantDao;
	
	@Resource
	private FundAccountDao fundAccountDao;
	
	@Resource
	private OrdersDao ordersDao;
	
	@Resource
	private ChargeOradeDao chargeOradeDao;
	
	@Resource
	private FundAccountFlowDao fundAccountFlowDao;
	
	@Override
	public Map<String, Object> openAccount(Long userId,String retType) {
		Map<String,Object> param=new LinkedHashMap<String,Object>();
		UserAccount userAccount=userAccountDao.findOne(userId);
		UserSafeStatus userSafeStatus=userSafeStatusDao.findOne(userId);
		AuthAccount authAccount = authAccountDao.findOne(userId);
		if(userSafeStatus.getPhoneStatus()==RealAuthIspass.BTG){
			throw new IllegalArgumentException("请先认证手机号码");
		}
		if(userSafeStatus.getIdCardStatus()==RealAuthIspass.BTG){
			throw new IllegalArgumentException("请先认证身份证");
		}
		String code=userAccountDao.findHuiChaoPayIdByUserId(userId);
		if(!StringHelper.isEmpty(code)){
			throw new IllegalArgumentException("该用户已开通第三方托管账户");
		}
		if(userAccount!=null && userSafeStatus!=null && authAccount!=null){
			String card_no=null;
			try {
				card_no = StringHelper.decode(authAccount.getDenseIdentity());
			} catch (Throwable e) {
				e.printStackTrace();
			}
			param.put("number_id",Constants.ACCOUNT_NUMBER);
			param.put("mode","personal");
			param.put("account_name",userAccount.getMobilePhone());
			param.put("real_name",authAccount.getName());
			param.put("card_no",card_no);
			param.put("nick_name",userAccount.getLoginName());
			param.put("advice_url",Constants.OPENACCOUNT_ADVICE_URL);
			param.put("return_url",Constants.RETURN_URL+"?type="+retType);
			param.put("remark",userAccount.getLoginName()+"_openAccount");
			param.put("merchantKey",Constants.MERCHANT_KEY);
			String sign_info=MD5.getMD5ofStr(FunctionUtil.map2StrParm(param));
			param.put("sign_info",sign_info);
			param.remove("merchantKey");
		}
		return param;
	}

	@Override
	public Map<String, Object> chargeIndex(Long userId) {
		Map<String, Object> returnMap=new HashMap<String, Object>();
		FundAccount wlzh=fundAccountDao.findByUserIdAndType(userId, FundAccountType.WLZH);
		UserSafeStatus userSafeStatus=userSafeStatusDao.findOne(userId);
		String code=userAccountDao.findHuiChaoPayIdByUserId(userId);
		returnMap.put("balance", wlzh.getBalance());
		returnMap.put("phoneStat", userSafeStatus.getPhoneStatus()==RealAuthIspass.TG?1:0);
		returnMap.put("cardStat", userSafeStatus.getIdCardStatus()==RealAuthIspass.TG?1:0);
		returnMap.put("isParty", !StringHelper.isEmpty(code)?1:0);
		returnMap.put("CHARGE_MIN_AMOUNT", sysConstantDao.findValueById(Constants.CHARGE_MIN_AMOUNT));
		returnMap.put("CHARGE_MAX_AMOUNT", sysConstantDao.findValueById(Constants.CHARGE_MAX_AMOUNT));
		returnMap.put("CHARGE_RATE", sysConstantDao.findValueById(Constants.CHARGE_RATE));
		returnMap.put("CHARGE_MAX_POUNDAGE", sysConstantDao.findValueById(Constants.CHARGE_MAX_POUNDAGE));
		return returnMap;
	}

	@Override
	@Transactional(noRollbackFor=IllegalArgumentException.class)
	public Map<String, Object> opChargeApply(Long userId, BigDecimal funds,String retType) {
		UserAccount userAccount=userAccountDao.findOne(userId);
		UserSafeStatus userSafeStatus=userSafeStatusDao.findOne(userId);
		if(userSafeStatus.getPhoneStatus()==RealAuthIspass.BTG){
			throw new IllegalArgumentException("请先认证手机号码");
		}
		if(userSafeStatus.getIdCardStatus()==RealAuthIspass.BTG){
			throw new IllegalArgumentException("请先认证身份证");
		}
		String code=userAccountDao.findHuiChaoPayIdByUserId(userId);
		if(StringHelper.isEmpty(code)){
			throw new IllegalArgumentException("请先注册第三方托管账户");
		}
		BigDecimal amount = funds.setScale(2, RoundingMode.HALF_UP);
		String min = sysConstantDao.findValueById(Constants.CHARGE_MIN_AMOUNT);
		String max = sysConstantDao.findValueById(Constants.CHARGE_MAX_AMOUNT);
		if (amount.compareTo(new BigDecimal(min)) < 0
				|| amount.compareTo(new BigDecimal(max)) > 0) {
			StringBuilder builder = new StringBuilder("充值金额必须大于");
			builder.append(min);
			builder.append("元小于");
			builder.append(max);
			builder.append("元");
			throw new IllegalArgumentException(builder.toString());
		}
		String rate=sysConstantDao.findValueById(Constants.CHARGE_RATE);
		String maxRate=sysConstantDao.findValueById(Constants.CHARGE_MAX_POUNDAGE);
		BigDecimal fee=amount.multiply(new BigDecimal(rate));
		if(fee.compareTo(new BigDecimal(maxRate))>0){
			fee=new BigDecimal(maxRate);
		}
		/**保存订单*/
		Orders order=new Orders();
		order.setTypeCode(OrdersType.CHARGE.orderType());
		order.setStatus(OrdersStatus.DQR);
		order.setCreateTime(new Date());
		order.setSource(OrdersSource.APP);
		order.setUserId(userId);
		order=ordersDao.save(order);
		
		/**保存充值订单流水*/
		ChargeOrade chargeOrade=new ChargeOrade();
		chargeOrade.setId(order.getId());
		chargeOrade.setUserId(userId);
		chargeOrade.setAmount(amount);
		chargeOrade.setPoundage(fee);//应收收费
		chargeOrade.setRealPoundage(fee);//实际收费
		chargeOrade.setPayNum(PaymentInstitution.HUICHAO.getInstitutionCode());
		chargeOrade=chargeOradeDao.save(chargeOrade);
		
		Map<String,Object> param=new LinkedHashMap<String,Object>();
		param.put("number_id",Constants.ACCOUNT_NUMBER);
		param.put("out_trade_no",FunctionUtil.getOutTradeNo(1, order.getId(),new Date()));
		param.put("amount",amount);
		param.put("fee",fee);
		param.put("nick_name",userAccount.getLoginName());
		param.put("advice_url",Constants.CHARGE_ADVICE_URL);
		param.put("return_url",Constants.RETURN_URL+"?type="+retType);
		param.put("remark",userAccount.getLoginName()+"_charge_money_"+amount+"_fee_"+fee);
		param.put("merchantKey",Constants.MERCHANT_KEY);
		String sign_info=MD5.getMD5ofStr(FunctionUtil.map2StrParm(param));
		param.put("sign_info",sign_info);
		param.remove("merchantKey");
		String strParam=FunctionUtil.map2StrParm(param);
		Map<String, Object> map = new HashMap<>();
		map.put("linkUrl", Constants.CHARGE_URL+"?"+strParam);
		return map;
	}
}
