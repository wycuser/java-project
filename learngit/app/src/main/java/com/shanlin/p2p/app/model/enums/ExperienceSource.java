package com.shanlin.p2p.app.model.enums;

public enum ExperienceSource {
	
	/** 
     * 游戏
     */
	YX("游戏"),

    /** 
     * 注册
     */
	ZC("注册"),
	/** 
	 * 实名认证
	 */
	SMRZ("实名认证"),
	/** 
	 * 投标
	 */
	TB("投标"),
	/** 
	 * 口令
	 */
	KL("口令"),
	/** 
	 * 序号
	 */
	XH("序号"),
	
	/** 
	 * 资深会员
	 */
	LYH("资深会员"),
	
	/** 
     * 流标返还
     */
	LBHF("流标返还");

    protected final String chineseName;

    private ExperienceSource(String chineseName){
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
