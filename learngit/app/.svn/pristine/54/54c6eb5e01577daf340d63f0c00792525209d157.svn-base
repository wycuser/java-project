package com.shanlin.p2p.app.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.shanlin.p2p.app.model.enums.Judge;

/**
 * 善行宝类型
 * @author zheng xin
 * @createDate 2015年7月22日
 */
@Entity
@Table(name="sxbao_type", schema="S62")
public class SxbaoType implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/** id*/
	@Id
	@GeneratedValue
	private Long id;
	
	/** 名称*/
	private String name;
	
	/** 发售状态*/
	@Enumerated(EnumType.STRING)
	private Judge sellStatus;
	
	/** 已售总额*/
	@Transient
	private BigDecimal totalAmount;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="type")
	private Set<SxbaoConfig> configs = new HashSet<>();
	
	public SxbaoType() {
	}
	
	public SxbaoType(Long id) {
		this.id = id;
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

	public Judge getSellStatus() {
		return sellStatus;
	}

	public void setSellStatus(Judge sellStatus) {
		this.sellStatus = sellStatus;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Set<SxbaoConfig> getConfigs() {
		return configs;
	}

	public void setConfigs(Set<SxbaoConfig> configs) {
		this.configs = configs;
	}
	
}
