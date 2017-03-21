package com.shanlin.p2p.app.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 信用账户
 * @author yangjh
 *
 */
@Entity
@Table(name="T6116", schema="S61")
public class CreditAccount implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/** 主键*/
	@Id
	@Column(name="F01")
	private Long id;
	
	/** 信用积分*/ 
	@Column(name="F02")
	private int creditNum;
	
	/** 信用总额*/
	@Column(name="F03")
	private BigDecimal creditAmount;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="F04")
	private Date lastUpdateTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getCreditNum() {
		return creditNum;
	}

	public void setCreditNum(int creditNum) {
		this.creditNum = creditNum;
	}

	public BigDecimal getCreditAmount() {
		return creditAmount;
	}

	public void setCreditAmount(BigDecimal creditAmount) {
		this.creditAmount = creditAmount;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	@Override
	public String toString() {
		return "CreditAccount [id=" + id + ", creditNum=" + creditNum
				+ ", creditAmount=" + creditAmount + ", lastUpdateTime="
				+ lastUpdateTime + "]";
	}
}
