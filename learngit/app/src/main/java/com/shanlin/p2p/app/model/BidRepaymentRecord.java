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

import com.shanlin.p2p.app.model.enums.BidRepaymentStatus;

/**
 * 标还款记录(T6252)
 * @author zheng xin
 * @createDate 2015年1月14日
 */
@Entity
@Table(name="T6252", schema="S62")
public class BidRepaymentRecord implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/** 主键*/
	@Id
	@GeneratedValue
	@Column(name="F01")
	private Long id;
	
	/** 标ID,参考T6230.F01*/
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="F02")
	private Bid bid;
	
	/** 付款用户ID,参考T6110.F01*/
	@Column(name="F03")
	private Long repaymentUserId;
	
	/** 收款用户ID,参考T6110.F01*/
	@Column(name="F04")
	private Long gatherUserId;
	
	/** 交易类型ID,参考T5122.F01*/
	@Column(name="F05")
	private Integer feeCode;
	
	/** 期号*/
	@Column(name="F06")
	private Integer issue;
	
	/** 金额*/
	@Column(name="F07")
	private BigDecimal amount;
	
	/** 应还日期*/
	@Temporal(TemporalType.DATE)
	@Column(name="F08")
	private Date shouldDate;
	
	/** 状态,WH:未还;YH:已还;HKZ:还款中*/
	@Enumerated(EnumType.STRING)
	@Column(name="F09")
	private BidRepaymentStatus status;
	
	/** 应还日期*/
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="F10")
	private Date repaymentTime;
	
	/** 债权ID,参考T6251.F01*/
	@Column(name="F11")
	private Long creditId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

//	public Long getBidPk() {
//		return bidPk;
//	}
//
//	public void setBidPk(Long bidPk) {
//		this.bidPk = bidPk;
//	}

	public Long getRepaymentUserId() {
		return repaymentUserId;
	}

	public void setRepaymentUserId(Long repaymentUserId) {
		this.repaymentUserId = repaymentUserId;
	}

	public Long getGatherUserId() {
		return gatherUserId;
	}

	public void setGatherUserId(Long gatherUserId) {
		this.gatherUserId = gatherUserId;
	}

	public Integer getFeeCode() {
		return feeCode;
	}

	public void setFeeCode(Integer feeCode) {
		this.feeCode = feeCode;
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

	public Date getShouldDate() {
		return shouldDate;
	}

	public void setShouldDate(Date shouldDate) {
		this.shouldDate = shouldDate;
	}

	public BidRepaymentStatus getStatus() {
		return status;
	}

	public void setStatus(BidRepaymentStatus status) {
		this.status = status;
	}

	public Date getRepaymentTime() {
		return repaymentTime;
	}

	public void setRepaymentTime(Date repaymentTime) {
		this.repaymentTime = repaymentTime;
	}

	public Long getCreditId() {
		return creditId;
	}

	public void setCreditId(Long creditId) {
		this.creditId = creditId;
	}
	
	public Bid getBid() {
		return bid;
	}

	public void setBid(Bid bid) {
		this.bid = bid;
	}

	@Override
	public String toString() {
		return "BidRepaymentRecord [id=" + id
				+ ", repaymentUserId=" + repaymentUserId + ", gatherUserId="
				+ gatherUserId + ", feeCode=" + feeCode + ", issue=" + issue
				+ ", amount=" + amount + ", shouldDate=" + shouldDate
				+ ", status=" + status + ", repaymentTime=" + repaymentTime
				+ ", creditId=" + creditId + "]";
	}

}
