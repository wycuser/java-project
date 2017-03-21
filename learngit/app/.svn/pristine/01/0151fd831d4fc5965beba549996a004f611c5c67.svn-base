package com.shanlin.p2p.app.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.shanlin.p2p.app.model.enums.AreaLevel;
import com.shanlin.p2p.app.model.enums.UseStatus;

/**
 * 中国行政区域
 * @author zheng xin
 * @createDate 2015年1月28日
 */
@Entity
@Table(name="T5019", schema="S50")
public class ChineseArea implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/** id*/
	@Id
	@GeneratedValue
	@Column(name="F01")
	private Long id;
	
	/** 省级ID*/
	@Column(name="F02")
	private Integer provinceId;
	
	/** 市级ID*/
	@Column(name="F03")
	private Integer cityId;
	
	/** 县级ID*/
	@Column(name="F04")
	private Integer countyId;
	
	/** 名称*/
	@Column(name="F05")
	private String name;
	
	/** 省级名称*/
	@Column(name="F06")
	private String provinceName;
	
	/** 市级名称*/
	@Column(name="F07")
	private String cityName;
	
	/** 县级名称*/
	@Column(name="F08")
	private String countyName;
	
	/** 电话区号*/
	@Column(name="F09")
	private String areaCode;
	
	/** 邮政编码*/
	@Column(name="F10")
	private String postcode;
	
	/** 级别,SHENG:省级;SHI:市级;XIAN:县级*/
	@Enumerated(EnumType.STRING)
	@Column(name="F11")
	private AreaLevel level;
	
	/** 简拼*/
	@Column(name="F12")
	private String simpleName;
	
	/** 状态*/
	@Enumerated(EnumType.STRING)
	@Column(name="F13")
	private UseStatus status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public Integer getCountyId() {
		return countyId;
	}

	public void setCountyId(Integer countyId) {
		this.countyId = countyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public AreaLevel getLevel() {
		return level;
	}

	public void setLevel(AreaLevel level) {
		this.level = level;
	}

	public String getSimpleName() {
		return simpleName;
	}

	public void setSimpleName(String simpleName) {
		this.simpleName = simpleName;
	}

	public UseStatus getStatus() {
		return status;
	}

	public void setStatus(UseStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ChineseArea [id=" + id + ", provinceId=" + provinceId + ", cityId=" + cityId + ", countyId=" + countyId + ", name=" + name + ", provinceName="
				+ provinceName + ", cityName=" + cityName + ", countyName=" + countyName + ", areaCode=" + areaCode + ", postcode=" + postcode + ", level="
				+ level + ", simpleName=" + simpleName + ", status=" + status + "]";
	}
	
}
