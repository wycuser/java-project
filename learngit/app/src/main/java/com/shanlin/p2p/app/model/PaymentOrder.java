package com.shanlin.p2p.app.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 还款订单
 * @author yangjh
 *
 */
@Entity
@Table(name="T6506", schema="S65")
public class PaymentOrder implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="F01")
	private Long id;
	
	@Column(name="F02")
	private Long investUserId;
	
	@Column(name="F03")
	private Long bid;
	
	/** 债权ID,参考T6251.F01*/
	@Column(name="F04")
	private Long creditId;
	
	/** 期号*/
	@Column(name="F05")
	private Integer issue;
	
	/** 金额*/
	@Column(name="F06")
	private BigDecimal amount;
	
	/** 交易类型ID,参考T5122.F01*/
	@Column(name="F07")
	private Integer feeCode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getInvestUserId() {
		return investUserId;
	}

	public void setInvestUserId(Long investUserId) {
		this.investUserId = investUserId;
	}

	public Long getBid() {
		return bid;
	}

	public void setBid(Long bid) {
		this.bid = bid;
	}

	public Long getCreditId() {
		return creditId;
	}

	public void setCreditId(Long creditId) {
		this.creditId = creditId;
	}

	public Integer getIssue() {
		return issue;
	}

	public void setIssue(Integer issue) {
		this.issue = issue;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Integer getFeeCode() {
		return feeCode;
	}

	public void setFeeCode(Integer feeCode) {
		this.feeCode = feeCode;
	}

	@Override
	public String toString() {
		return "PaymentOrder [id=" + id + ", investUserId=" + investUserId
				+ ", bid=" + bid + ", creditId=" + creditId + ", issue="
				+ issue + ", amount=" + amount + ", feeCode=" + feeCode + "]";
	}
}
