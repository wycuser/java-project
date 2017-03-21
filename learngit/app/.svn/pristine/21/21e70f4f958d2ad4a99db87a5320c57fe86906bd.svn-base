package com.shanlin.p2p.app.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import com.shanlin.p2p.app.model.enums.AreaLevel;

@Entity
@Table(name="T5019", schema="S50")
public class Area implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "F01")
	private Long id;
	
	/** 省级ID*/
	@Column(name = "F02")
	private Long provinceId;
	
	/**市级ID*/
	@Column(name = "F03")
	private Long cityId;
	
	/**县级ID*/
	@Column(name = "F04")
	private Long countyId;
	
	/**名称*/
	@Column(name = "F05")
	private String name;
	
	/**省级名称*/
	@Column(name = "F06")
	private String provinceName;
	
	/**市级名称*/
	@Column(name = "F07")
	private String cityName;
	
	/**县级名称*/
	@Column(name = "F08")
	private String countyName;
	
	/**级别*/
	@Enumerated(EnumType.STRING)
	@Column(name = "F11")
	private AreaLevel level;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public Long getCountyId() {
		return countyId;
	}

	public void setCountyId(Long countyId) {
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

	public AreaLevel getLevel() {
		return level;
	}

	public void setLevel(AreaLevel level) {
		this.level = level;
	}

	@Override
	public String toString() {
		return "Area [id=" + id + ", provinceId=" + provinceId + ", cityId="
				+ cityId + ", countyId=" + countyId + ", name=" + name
				+ ", provinceName=" + provinceName + ", cityName=" + cityName
				+ ", countyName=" + countyName + ", level=" + level + "]";
	}
	
	public Area(){}
	public Area(Long id){this.id=id;}
}
