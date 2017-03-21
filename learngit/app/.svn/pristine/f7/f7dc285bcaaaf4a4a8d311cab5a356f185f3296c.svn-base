package com.shanlin.p2p.app.model.enums;

import com.shanlin.framework.utils.StringHelper;

public enum PaymentInstitution {
	ALLINPAY(100, "通联支付", "allinpay/allinpayCharge.htm", "allinpay/allinpayCheck.htm"), 
	SHUANGQIAN(200, "双乾支付", "shuangqian/shuangqianCharge.htm", "shuangqian/shuangqianCheck.htm"), 
	CHINAPNR(300, "汇付天下", "", ""), 
	TENPAY(400, "财付通", "", ""), 
	HUICHAO(500, "汇潮托管", "", ""), 
	FUYOU(600, "富有托管", "", ""), 
	BAOFU(700, "宝付支付", "baofu/baoFuChargeServlet.htm", "baofu/baoFuCheckServlet.htm"), 
	HUICHAOGATE(800, "汇潮网关支付", "hichao/huiChaoGateChargeServlet.htm", "hichao/huiChaoGateCheckServlet.htm"), 
	LIANLIANGATE(900, "连连支付", "lianlianpay/lianLianPayChagerServlet.htm", "lianlianpay/lianLianCheckServlet.htm"), 
	DINPAY(1000,"智付支付", "dinpay/dinpayCharge.htm", "dinpay/dinpayCheck.htm"), 
	CHINABANK(1100, "网银在线支付", "chinabank/chinaBankCharge.htm", ""), 
	KJTPAY(1200, "快捷通支付", "haier/kjtPayChargeServlet.htm", "haier/kjtCheckServlet.htm"), 
	HEEPAY(1300, "汇付宝支付", "heepay/heepayCharge.htm", "heepay/heepayCheck.htm"), 
	SINAPAY(1400, "新浪网关支付", "sinapay/sinaPayCharge.htm", "sinapay/sinaPayCheck.htm"),
	GFBPAY(1500,"国付宝支付", "gfbpay/gFBCharge.htm", "gfbpay/gFBCheck.htm"),
	BOCOM(1600,"交通银行支付", "bocompay/bOCOMCharge.htm", ""),
	BAOFUTG(1700,"宝付托管","","");
	
	private int institutionCode;
	private String chineseName;
	private String visiteUri; // 访问地址
	private String checkUri; // 查询地址

	private PaymentInstitution(int institutionCode, String chineseName,
			String visiteUri, String notifyUri) {
		this.institutionCode = institutionCode;
		this.chineseName = chineseName;
		this.visiteUri = visiteUri;
		this.checkUri = notifyUri;
	}

	public int getInstitutionCode() {
		return institutionCode;
	}

	public String getChineseName() {
		return chineseName;
	}

	public String getVisiteUri() {
		return visiteUri;
	}
	
	public String getCheckUri()
    {
        return checkUri;
    }

    public static final PaymentInstitution parse(String value) {
		if (StringHelper.isEmpty(value)) {
			return null;
		}
		try {
			return valueOf(value);
		} catch (Throwable t) {
			return null;
		}
	}
    
    public static final String getDescription(int code){
    	for (PaymentInstitution paymentInstitution : PaymentInstitution.values()) {
    		if(paymentInstitution.getInstitutionCode() == code){
    			return paymentInstitution.getChineseName();
    		}
		}
    	return null;
    }
}
