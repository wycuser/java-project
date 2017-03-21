package com.shanlin.p2p.app.vo;

/**
 * 银行简要信息
 * @author zheng xin
 * @createDate 2015年1月28日
 */
public class BankVO {
	
	/** id*/
	private Long id;
	
	/** 银行名称*/
	private String name;
	
	/** 图片地址 */
	private String picUrl;
	
	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
