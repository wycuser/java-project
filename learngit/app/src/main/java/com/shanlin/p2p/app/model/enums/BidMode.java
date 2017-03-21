package com.shanlin.p2p.app.model.enums;

/**
 * 标还款模式
 * @author zheng xin
 * @createDate 2015年2月3日
 */
public enum BidMode {
	/** 
     * 等额本息
     */
    DEBX("等额本息"),

    /** 
     * 每月付息,到期还本
     */
    MYFX("按月付息,到期还清"),

    /** 
     * 本息到期一次付清
     */
    YCFQ("到期一次付清"),

    /** 
     * 等额本金
     */
    DEBJ("等额本金");

    protected final String chineseName;

    private BidMode(String chineseName){
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
