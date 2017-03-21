package com.shanlin.p2p.app.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 惊喜奖获奖记录
 * @author zheng xin
 * @createDate 2015年3月12日
 */
@Entity
@Table(name="surprise_lucre",schema="S62")
public class SurpriseLucre implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	@Column
	private Long id;
	
	/** 配置表id*/
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="cid")
	private SurpriseConfig config;
	
	/** 用户id*/
	@Column
	private Long uid;
	
	/** 用户登录名*/
	@Column
	private String accountName;
	
	/** 投资记录标id T6250.F01*/
	@Column
	private Long tid;
	
	/** 投资金额*/
	@Column
	private BigDecimal investAmount;
	
	/** 收益金额*/
	@Column
	private BigDecimal lucreAmount;
	
	/** 发放收益时间*/
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Date grantTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SurpriseConfig getConfig() {
		return config;
	}

	public void setConfig(SurpriseConfig config) {
		this.config = config;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public Long getTid() {
		return tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
	}

	public BigDecimal getInvestAmount() {
		return investAmount;
	}

	public void setInvestAmount(BigDecimal investAmount) {
		this.investAmount = investAmount;
	}

	public BigDecimal getLucreAmount() {
		return lucreAmount;
	}

	public void setLucreAmount(BigDecimal lucreAmount) {
		this.lucreAmount = lucreAmount;
	}

	public Date getGrantTime() {
		return grantTime;
	}

	public void setGrantTime(Date grantTime) {
		this.grantTime = grantTime;
	}
	
}
