package com.shanlin.p2p.app.model.enums;

/**
 * 标付息方式
 * @author zheng xin
 * @createDate 2015年2月3日
 */
public enum BidPayMode {
	/** 
     * 自然月
     */
    ZRY("自然月"),

    /** 
     * 固定日
     */
    GDR("固定日");

    protected final String chineseName;

    private BidPayMode(String chineseName){
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
