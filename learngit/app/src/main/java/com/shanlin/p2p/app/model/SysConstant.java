package com.shanlin.p2p.app.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="_1010", schema="S10")
public class SysConstant implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 主键*/
	@Id
	@Column(name="F01")
	private String id;
	
	/** 對應的值*/
	@Column(name="F02")
	private String value;
	
	/** 类别*/
	@Column(name="F03")
	private String category;
	
	/** 詳情*/
	@Column(name="F04")
	private String detail;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	@Override
	public String toString() {
		return "SysConstant [id=" + id + ", value=" + value + ", category="
				+ category + ", detail=" + detail + "]";
	}
}
