package com.shanlin.p2p.app.model.enums;

public enum SendType {

	/**
	 * 所有
	 */
	SY("所有"),

	/**
	 * 指定人
	 */
	ZDR("指定人");

	protected final String chineseName;

	private SendType(String chineseName) {
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
