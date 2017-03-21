package com.shanlin.p2p.app.model.enums;

public enum OrdersSource {
	 /** 
     * 系统
     */
    XT("系统"),

    /** 
     * 用户
     */
    YH("用户"),

    /** 
     * 后台
     */
    HT("后台"),

    /** 
     * 后台
     */
    APP("APP");

    protected final String chineseName;

    private OrdersSource(String chineseName){
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
