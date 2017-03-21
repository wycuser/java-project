package com.shanlin.p2p.app.model.enums;

/** 
 * 资金账户类型
 */
public enum FundAccountType {
	
	/** 
     * 往来账户
     */
    WLZH("往来账户"),

    /** 
     * 风险保证金账户
     */
    FXBZJZH("风险保证金账户"),

    /** 
     * 锁定账户
     */
    SDZH("锁定账户");

    protected final String chineseName;

    private FundAccountType(String chineseName){
        this.chineseName = chineseName;
    }
    /**
     * 获取中文名称.
     * 
     * @return {@link String}
     */
    public String getChineseName() {
        return chineseName;
    }
    /**
     * 解析字符串.
     * 
     * @return {@link FUND_ACCOUNT_TYPE}
     */
//    public static final FUND_ACCOUNT_TYPE parse(String value) {
//        if(StringHelper.isEmpty(value)){
//            return null;
//        }
//        try{
//            return FUND_ACCOUNT_TYPE.valueOf(value);
//        }catch(Throwable t){
//            return null;
//        }
//    }
}
