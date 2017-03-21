package com.shanlin.p2p.app.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.shanlin.p2p.app.model.enums.UseStatus;

/**
 * 银行
 * 
 * @author zheng xin
 * @createDate 2015年1月27日
 */
@Entity
@Table(name = "T5020", schema = "S50")
public class Bank implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 银行自增ID */
	@Id
	@GeneratedValue
	@Column(name = "F01")
	private Long id;

	/** 银行名称 */
	@Column(name = "F02")
	private String name;

	/** 状态 */
	@Enumerated(EnumType.STRING)
	@Column(name = "F03")
	private UseStatus status;

	/** 代码 */
	@Column(name = "F04")
	private String code;

	/** 图片地址 */
	private String picUrl;

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

	public UseStatus getStatus() {
		return status;
	}

	public void setStatus(UseStatus status) {
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	@Override
	public String toString() {
		return "Bank [id=" + id + ", name=" + name + ", status=" + status
				+ ", code=" + code + ", picUrl=" + picUrl + "]";
	}

	public Bank() {

	}

	public Bank(Long id) {
		this.id = id;
	}

}
