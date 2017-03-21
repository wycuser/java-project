package com.shanlin.p2p.app.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.shanlin.p2p.app.model.enums.FundCheckStatus;

/**
 * 资金流水
 * 
 * @author zheng xin
 * @createDate 2015年1月9日
 */
@Entity
@Table(name="T6102", schema="S61")
public class FundAccountFlow implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/** 主键*/
	@Id
	@GeneratedValue
	@Column(name="F01")
	private Long id;
	
	/** 资金账户*/
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="F02")
	private FundAccount fundAccount;
	
	/** 交易类型id*/
	@Column(name="F03")
	private Integer feeCode;
	
	/** 对方账户*/
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="F04")
	private FundAccount remoteFundAccount;
	
	/** 创建时间*/
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="F05")
	private Date createTime;
	
	/** 收入*/
	@Column(name="F06")
	private BigDecimal income = BigDecimal.ZERO;
	
	/** 支出*/
	@Column(name="F07")
	private BigDecimal expenditure = BigDecimal.ZERO;
	
	/** 余额*/
	@Column(name="F08")
	private BigDecimal balance;
	
	/** 备注*/
	@Column(name="F09")
	private String remark;
	
	/** 对账状态*/
	@Enumerated(EnumType.STRING)
	@Column(name="F10")
	private FundCheckStatus fundCheckStatus=FundCheckStatus.WDZ;
	
	/** 对账时间*/
//	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name="F11")
//	private Date checkTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public BigDecimal getIncome() {
		return income;
	}

	public void setIncome(BigDecimal income) {
		this.income = income;
	}

	public BigDecimal getExpenditure() {
		return expenditure;
	}

	public void setExpenditure(BigDecimal expenditure) {
		this.expenditure = expenditure;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public FundCheckStatus getFundCheckStatus() {
		return fundCheckStatus;
	}

	public void setFundCheckStatus(FundCheckStatus fundCheckStatus) {
		this.fundCheckStatus = fundCheckStatus;
	}

//	public Date getCheckTime() {
//		return checkTime;
//	}
//
//	public void setCheckTime(Date checkTime) {
//		this.checkTime = checkTime;
//	}

	public FundAccount getFundAccount() {
		return fundAccount;
	}

	public void setFundAccount(FundAccount fundAccount) {
		this.fundAccount = fundAccount;
	}

	public Integer getFeeCode() {
		return feeCode;
	}

	public void setFeeCode(Integer feeCode) {
		this.feeCode = feeCode;
	}

	public FundAccount getRemoteFundAccount() {
		return remoteFundAccount;
	}

	public void setRemoteFundAccount(FundAccount remoteFundAccount) {
		this.remoteFundAccount = remoteFundAccount;
	}

	@Override
	public String toString() {
		return "FundAccountFlow [id=" + id + ", feeCode=" + feeCode
				+ ", createTime=" + createTime + ", income=" + income
				+ ", expenditure=" + expenditure + ", balance=" + balance
				+ ", remark=" + remark + ", fundCheckStatus=" + fundCheckStatus
				+ "]";
	}

}
