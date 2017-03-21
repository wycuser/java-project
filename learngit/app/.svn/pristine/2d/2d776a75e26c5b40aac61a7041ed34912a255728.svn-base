package com.shanlin.p2p.app.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 用户理财统计
 * @author zheng xin
 * @createDate 2015年1月20日
 */
@Entity
@Table(name="T6115", schema="S61")
public class UserFundStatist implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name="F01")
	private Long id;
	
	@Column(name="F02")
	private BigDecimal loanTotal;
	
	@Column(name="F03")
	private BigDecimal investTotal;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="F04")
	private Date lastUpdateTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getLoanTotal() {
		return loanTotal;
	}

	public void setLoanTotal(BigDecimal loanTotal) {
		this.loanTotal = loanTotal;
	}

	public BigDecimal getInvestTotal() {
		return investTotal;
	}

	public void setInvestTotal(BigDecimal investTotal) {
		this.investTotal = investTotal;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	
}
