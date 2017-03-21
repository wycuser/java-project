package com.shanlin.p2p.app.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.shanlin.p2p.app.model.enums.FundAccountType;

/**
 * 资金账户
 * @author zheng xin
 * @createDate 2015年1月28日
 */
@Entity
@Table(name="T6101", schema="S61")
public class FundAccount implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/** 主键*/
	@Id
	@GeneratedValue
	@Column(name="F01")
	private Long id;
	
	/** 用户账户*/
//	@ManyToOne(fetch=FetchType.LAZY)
//	@JoinColumn(name="F02", updatable=false)
	@Column(name="F02")
//	private UserAccount userAccount;
	private Long userId;
	
	/** 资金账户类型*/
	@Enumerated(EnumType.STRING)
	@Column(name="F03")
	private FundAccountType type;
	
	/** 资金账户名*/
	@Column(name="F04")
	private String fundAccountName;
	
	/** 登录名*/
	@Column(name="F05")
	private String loginName;
	
	/** 余额*/
	@Column(name="F06")
	private BigDecimal balance;
	
	/** 最后更新时间*/
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="F07")
	private Date lastUpdateTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

//	public UserAccount getUserAccount() {
//		return userAccount;
//	}
//
//	public void setUserAccount(UserAccount userAccount) {
//		this.userAccount = userAccount;
//	}
	

	public FundAccountType getType() {
		return type;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setType(FundAccountType type) {
		this.type = type;
	}

	public String getFundAccountName() {
		return fundAccountName;
	}

	public void setFundAccountName(String fundAccountName) {
		this.fundAccountName = fundAccountName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	@Override
	public String toString() {
		return "FundAccount [id=" + id + ", fundAccountType=" + type + ", fundAccountName=" + fundAccountName + ", loginName=" + loginName
				+ ", balance=" + balance + ", lastUpdateTime=" + lastUpdateTime + "]";
	}
	public FundAccount() {
	}
	public FundAccount(Long id) {
		this.id=id;
	}
	
	
}
