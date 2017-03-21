package com.shanlin.p2p.app.model.enums;

public enum WithdrawStatus {
	 /** 
     * 待审核
     */
	DSH("待审核"),

    /** 
     * 待放款
     */
	DFK("待放款"),

    /** 
     * 已放款
     */
	YFK("已放款"),

    /** 
     * 提现失败
     */
	TXSB("提现失败");

    protected final String chineseName;

    private WithdrawStatus(String chineseName){
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
}
