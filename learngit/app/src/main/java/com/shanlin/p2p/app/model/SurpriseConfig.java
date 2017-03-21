package com.shanlin.p2p.app.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.shanlin.p2p.app.model.enums.SurpriseName;

/**
 * 惊喜奖配置
 * @author zheng xin
 * @createDate 2015年3月12日
 */
@Entity
@Table(name="surprise_config",schema="S62")
public class SurpriseConfig implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** id*/
	@Id
	@GeneratedValue
	@Column
	private Long id;
	
	/** 标id*/
	@Column
	private Long bid;
	
	/** 惊喜奖名称枚举*/
	@Column
	@Enumerated(EnumType.STRING)
	private SurpriseName name;
	
	/** 收益百分比*/
	@Column
	private BigDecimal rate;
	
	/** 是否生效*/
	@Column
	private int ifEnable;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBid() {
		return bid;
	}

	public void setBid(Long bid) {
		this.bid = bid;
	}

	public SurpriseName getName() {
		return name;
	}

	public void setName(SurpriseName name) {
		this.name = name;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public int getIfEnable() {
		return ifEnable;
	}

	public void setIfEnable(int ifEnable) {
		this.ifEnable = ifEnable;
	}
	
}
