package com.shanlin.p2p.app.model.enums;

public enum SurpriseName {
	
	/** 
     * 首标奖
     */
    SBJ("首投奖",0.01,"第一个投标会员"),
    
    /** 
     * 新人奖
     */
    XRJ("新人奖",0.01,"尚未投过标的会员现在来投标"),
    
    /** 
     * 收官奖
     */
    SGJ("收官奖",0.01,"本标最后投标的会员"),
    
    /** 
     * 土豪奖
     */
    THJ("土豪奖",0.02,"投标金额为两万以上的会员"),
    
    /** 
     * 秒杀奖
     */
    MXJ("秒杀奖",0.05,"本标一次性投完标的会员")
    ;
	
	protected final String chineseName;
	
	protected final double rate;
	
	protected final String desc;
	
	private SurpriseName(String chineseName, double rate, String desc){
		this.chineseName = chineseName;
		this.rate = rate;
		this.desc = desc;
	}
	
    /**
     * 获取中文名称.
     * 
     * @return {@link String}
     */
    public String getChineseName() {
        return chineseName;
    }
    
//    public double getRate() {
//    	return rate;
//    }
    
    public String getDesc() {
		return desc;
	}
}
