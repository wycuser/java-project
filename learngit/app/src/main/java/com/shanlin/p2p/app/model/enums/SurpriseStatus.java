package com.shanlin.p2p.app.model.enums;


public enum SurpriseStatus {
	/** 
     * 未配置
     */
	WPZ("未配置"),
    
    /** 
     * 已配置
     */
	YPZ("已配置"),
    
    /** 
     * 已放款
     */
	YFK("已放款")
    ;
	
	protected final String chineseName;
    
	private SurpriseStatus(String chineseName){
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
