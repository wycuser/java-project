package com.shanlin.p2p.app.constant;

import java.math.BigDecimal;

import com.shanlin.framework.config.PropertyFileConfigurer;



/**
 * 系统常量类
 * @author zheng xin
 * @createDate 2015年1月14日
 */
public class Constants {
	
    /**
     * banner url前缀
     */
    public static final String SYS_BANNER_URL_SUFFIX = PropertyFileConfigurer.getContextProperty("sys.banner_url_suffix");
    
    /**
     * 网站名称
     */
    public static final String SYS_SITE_NAME = PropertyFileConfigurer.getContextProperty("sys.site_name");
    
    /**
     * 客服电话
     */
    public static final String SYS_SERVICE_PHONE = PropertyFileConfigurer.getContextProperty("sys.service_phone");
    
    /** 平台AES key*/
    public static final String SYS_AES_KEY = PropertyFileConfigurer.getContextProperty("sys.aes_key");
    
    /** 邮箱正则*/
    public static final String EMAIL_PATTERN = "^(\\w+[_|\\_|\\.]?)*\\w+@(\\w+[_|\\_|\\.]?)*\\w+\\.[a-zA-Z]{2,4}$";
    
    /** 手机正则*/
    public static final String MOBILEPHONE_PATTERN = "^(13|15|18|14|17)[0-9]{9}$";
    
    /** 登录名正则*/
    public static final String LOGINNAME_PATTERN = "^[a-zA-Z]([\\w]{5,17})$";
    
    /** 身份证正则*/
    public static final String IDCARD_PATTERN = "(^\\d{15}$)|(^\\d{17}[0-9Xx]$)";
    
    /** 系统投资最低金额 */
    public static final String SYS_MIN_INVEST_AMOUNT = PropertyFileConfigurer.getContextProperty("sys.min_invest_amount");
    
    /** 国政通身份信息认证 */
    public static final String GZT_TYPE = PropertyFileConfigurer.getContextProperty("gzt.type");
    
    /** 国政通加密私钥 */
    public static final String GZT_KEY = PropertyFileConfigurer.getContextProperty("gzt.key");
    
    /** 国政通账号 */
    public static final String GZT_ACCOUNT = PropertyFileConfigurer.getContextProperty("gzt.account");
    
    /** 国政通密码 */
    public static final String GZT_PASSWORD = PropertyFileConfigurer.getContextProperty("gzt.password");
    
    /** 是否增加体验金*/
    public static final boolean EXPERIENCE_ADD = Boolean.parseBoolean(PropertyFileConfigurer.getContextProperty("experience.add"));
    
    /** 债权持有多少天后可转让 */
    public static final int ZQZR_CY_DAY = Integer.parseInt(PropertyFileConfigurer.getContextProperty("zqzr_cy_day"));
    
    /**债权转让管理费率*/
    public static final BigDecimal ZQZRGLF_RATE=new BigDecimal("0.01");
    
    /**
	 * 三方借款协议
	 */
	public static final Long THREE_JKXY = 1001L;
	/**
	 * 四方借款协议
	 */
	public static final Long FOUR_JKXY = 1002L;
	/**
	 * 债权转让协议
	 */
	public static final Long ZQZRXY = 2001L;
	/**
	 * 优选理财协议
	 */
	public static final Long YXLCXY = 3001L;
	
	/**
     * 债权转让是否自己可投
     */
	public static final boolean ZQZR_SFZJKT=false;
	
	/**汇朝支付 成功码*/
	public static final String SUCCESS_CODE="0000";
	/**汇朝支付 商家KEY*/
	public static final String MERCHANT_KEY=PropertyFileConfigurer.getContextProperty("hcpay.merchant_key");
	/**汇朝支付 商家账户*/
	public static final String ACCOUNT_NUMBER=PropertyFileConfigurer.getContextProperty("hcpay.account_number");
	/**汇朝支付 交易URL*/
	public static final String PAY_URL=PropertyFileConfigurer.getContextProperty("hcpay.trade_url");
	/**汇朝支付 开户URL*/
	public static final String OPENACCOUNT_URL=PropertyFileConfigurer.getContextProperty("hcpay.openAccount_url");
	/**汇朝支付 开户服务器通知地址*/
	public static final String OPENACCOUNT_ADVICE_URL=PropertyFileConfigurer.getContextProperty("hcpay.openAccount_advice_url");
	/**汇朝支付 充值地址*/
	public static final String CHARGE_URL=PropertyFileConfigurer.getContextProperty("hcpay.charge_url");
	/**汇朝支付 充值服务器通知地址*/
	public static final String CHARGE_ADVICE_URL=PropertyFileConfigurer.getContextProperty("hcpay.charge_advice_url");
	/**汇朝支付 页面通知地址*/
	public static final String RETURN_URL=PropertyFileConfigurer.getContextProperty("hcpay.return_url");
    
	/**绩效管理系统URL*/
	public static final String JXGL_SYSTEM_URL = PropertyFileConfigurer.getContextProperty("system.jxgl_system_url");
	
	/** 活动标最大投资金额*/
	public static final BigDecimal ACTIVE_MAX_BID_AMOUNT = new BigDecimal(PropertyFileConfigurer.getContextProperty("system.active_max_bid_amount"));
	
	/**提现最低金额（元）*/
	public static final String WITHDRAW_MIN_FUNDS="SYSTEM.WITHDRAW_MIN_FUNDS";
	/**提现最高金额（元）*/
	public static final String WITHDRAW_MAX_FUNDS="SYSTEM.WITHDRAW_MAX_FUNDS";
	/**提现密码最大输入次数*/
	public static final String WITHDRAW_MAX_INPUT="SYSTEM.WITHDRAW_MAX_INPUT";
	/**提现手续费计算方式, ED:按额度(默认方式);BL:按比例*/
	public static final String WITHDRAW_POUNDAGE_WAY ="SYSTEM.WITHDRAW_POUNDAGE_WAY";
	/**[按比例]提现手续费比例值*/
	public static final String WITHDRAW_POUNDAGE_PROPORTION="SYSTEM.WITHDRAW_POUNDAGE_PROPORTION";
	/**[按比例]提现手续费最低费用*/
	public static final String WITHDRAW_POUNDAGE_MIN="SYSTEM.WITHDRAW_POUNDAGE_MIN";
	/**[按额度]提现手续费1-5万收费*/
	public static final String WITHDRAW_POUNDAGE_1_5="SYSTEM.WITHDRAW_POUNDAGE_1_5";
	/**[按额度]提现手续费5-20万收费*/
	public static final String WITHDRAW_POUNDAGE_5_20="SYSTEM.WITHDRAW_POUNDAGE_5_20";
	/**充值最低金额（元）*/
	public static final String CHARGE_MIN_AMOUNT="SYSTEM.CHARGE_MIN_AMOUNT";
	/**充值最高金额（元）*/
	public static final String CHARGE_MAX_AMOUNT="SYSTEM.CHARGE_MAX_AMOUNT";
	/**用户充值手续费率*/
	public static final String CHARGE_RATE="SYSTEM.CHARGE_RATE";
	/**用户充值最高手续费率*/
	public static final String CHARGE_MAX_POUNDAGE="SYSTEM.CHARGE_MAX_POUNDAGE";
	
}
