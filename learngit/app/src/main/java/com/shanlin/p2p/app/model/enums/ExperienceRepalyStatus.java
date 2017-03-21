package com.shanlin.p2p.app.model.enums;

public enum ExperienceRepalyStatus {
	/** 
     * 已还款
     */
    YHK("已还款"),

    /** 
     * 未还款
     */
    WHK("未还款");

    protected final String chineseName;

    private ExperienceRepalyStatus(String chineseName){
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
