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

import com.shanlin.p2p.app.model.enums.ExperienceRepalyStatus;

@Entity
@Table(name="Expemoneyrecord", schema="S10")
public class ExperienceBidRecord implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column
	private Long id;
	
	/** 用户id*/
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	private UserAccount userAccount;
//	@Column(name="user_id")
//	private Long userId;
	
	/** 体验标id*/
	@Column(name="subjectid")
	private Long bidPk;
	
	/** 投资时间*/
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="invetime")
	private Date investTime;
	
	/** 投资金额*/
	@Column(name="invemoney")
	private BigDecimal investPrice;
	
	/** 投资利息*/
	@Column(name="inveint")
	private BigDecimal interest;
	
	/** 状态*/
	@Enumerated(EnumType.STRING)
	@Column(name="estatus")
	private ExperienceRepalyStatus status = ExperienceRepalyStatus.WHK;
	
	/** 还款时间*/
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="hcrepalytime")
	private Date repalyTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

//	public Long getUserId() {
//		return userId;
//	}
//
//	public void setUserId(Long userId) {
//		this.userId = userId;
//	}

	public Long getBidPk() {
		return bidPk;
	}

	public void setBidPk(Long bidPk) {
		this.bidPk = bidPk;
	}

	public Date getInvestTime() {
		return investTime;
	}

	public void setInvestTime(Date investTime) {
		this.investTime = investTime;
	}

	public BigDecimal getInvestPrice() {
		return investPrice;
	}

	public void setInvestPrice(BigDecimal investPrice) {
		this.investPrice = investPrice;
	}

	public BigDecimal getInterest() {
		return interest;
	}

	public void setInterest(BigDecimal interest) {
		this.interest = interest;
	}

	public Date getRepalyTime() {
		return repalyTime;
	}

	public void setRepalyTime(Date repalyTime) {
		this.repalyTime = repalyTime;
	}

	public UserAccount getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	public ExperienceRepalyStatus getStatus() {
		return status;
	}

	public void setStatus(ExperienceRepalyStatus status) {
		this.status = status;
	}
	
}
