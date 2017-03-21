package com.shanlin.p2p.app.model.enums;

public enum SecuredPartyStatus {
	/**
	 * 是
	 */
	S("是"),

	/**
	 * 否
	 */
	F("否");

	protected final String chineseName;

	private SecuredPartyStatus(String chineseName) {
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
