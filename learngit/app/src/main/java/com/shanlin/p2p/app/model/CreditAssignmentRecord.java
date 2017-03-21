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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 债权转让记录(T6262)
 * @author yangjh
 * @createDate 2015年4月27日
 */
@Entity
@Table(name="T6262", schema="S62")
public class CreditAssignmentRecord implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/** 主键 */
	@Id
	@GeneratedValue
	@Column(name = "F01")
	private Long id;

	/** 债权转让申请(T6260) */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "F02")
	private CreditAssignmentApply creditAssignmentApply;
//	
//	/** 债权转让协议版本号(T6261) */
//	@ManyToOne
//	@JoinColumn(name = "F02",insertable=false, updatable=false)
//	private CreditAssignmentVersion creditAssignmentVersion;

	/** 购买人ID,参考T6110.F01*/
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="F03")
	private UserAccount userAccount;

	/** 购买价格 */
	@Column(name = "F04")
	private BigDecimal buyAmount;

	/** 转让价格 */
	@Column(name = "F05")
	private BigDecimal assignmentAmount;
	
	/** 转让手续费 */
	@Column(name = "F06")
	private BigDecimal assignmentFees;
	
	/** 购买时间 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="F07")
	private Date buyTime;
	
	/** 转入盈亏 */
	@Column(name = "F08")
	private BigDecimal intoProfit;
	
	/** 转出盈亏 */
	@Column(name = "F09")
	private BigDecimal outProfit;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CreditAssignmentApply getCreditAssignmentApply() {
		return creditAssignmentApply;
	}

	public void setCreditAssignmentApply(CreditAssignmentApply creditAssignmentApply) {
		this.creditAssignmentApply = creditAssignmentApply;
	}

	public UserAccount getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	public BigDecimal getBuyAmount() {
		return buyAmount;
	}

	public void setBuyAmount(BigDecimal buyAmount) {
		this.buyAmount = buyAmount;
	}

	public BigDecimal getAssignmentFees() {
		return assignmentFees;
	}

	public void setAssignmentFees(BigDecimal assignmentFees) {
		this.assignmentFees = assignmentFees;
	}

	public Date getBuyTime() {
		return buyTime;
	}

	public void setBuyTime(Date buyTime) {
		this.buyTime = buyTime;
	}

	public BigDecimal getIntoProfit() {
		return intoProfit;
	}

	public void setIntoProfit(BigDecimal intoProfit) {
		this.intoProfit = intoProfit;
	}

	public BigDecimal getOutProfit() {
		return outProfit;
	}

	public void setOutProfit(BigDecimal outProfit) {
		this.outProfit = outProfit;
	}
//
//	public CreditAssignmentVersion getCreditAssignmentVersion() {
//		return creditAssignmentVersion;
//	}
//
//	public void setCreditAssignmentVersion(
//			CreditAssignmentVersion creditAssignmentVersion) {
//		this.creditAssignmentVersion = creditAssignmentVersion;
//	}

	public BigDecimal getAssignmentAmount() {
		return assignmentAmount;
	}

	public void setAssignmentAmount(BigDecimal assignmentAmount) {
		this.assignmentAmount = assignmentAmount;
	}

	
}
