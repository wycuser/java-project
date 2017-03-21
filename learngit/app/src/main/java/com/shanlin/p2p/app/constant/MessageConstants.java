package com.shanlin.p2p.app.constant;

import com.shanlin.framework.config.PropertyFileConfigurer;

public class MessageConstants {
	
	/**
	 * 短信测试
	 */
	public static final boolean SMS_DEBUG = Boolean.parseBoolean(PropertyFileConfigurer.getContextProperty("sms.debug"));
	
	 /**
     * 注册成功站内信模版
     */
    public static final String LETTER_USER_REGISTER_SUCCESS = PropertyFileConfigurer.getContextProperty("letter.user_register_success");
    
    /**
     * 发送短信验证码模版
     */
    public static final String SMS_SEND_REGISTER_VERIFYCODE = PropertyFileConfigurer.getContextProperty("sms.send_register_verifycode");
    
    /**
     * 找回密码短信验证码模版
     */
    public static final String SMS_SEND_FIND_VERIFYCODE = PropertyFileConfigurer.getContextProperty("sms.send_find_verifycode");
    
    public static final String SMS_LETTER_INVEST = PropertyFileConfigurer.getContextProperty("letter.invest");
    
    /**投资人,投标收款*/
	public static final String LETTER_TZR_TBSK = PropertyFileConfigurer.getContextProperty("letter.tzr_tbsk");
	/**借款人,借款还款*/
	public static final String LETTER_JKR_JKHK = PropertyFileConfigurer.getContextProperty("letter.jkr_jkhk");
}
