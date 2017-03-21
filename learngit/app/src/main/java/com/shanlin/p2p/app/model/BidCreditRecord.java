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

import com.shanlin.p2p.app.model.enums.Judge;

/**
 * 标债权记录(T6251)
 * @author zheng xin
 * @createDate 2015年1月14日
 */
@Entity
@Table(name="T6251", schema="S62")
public class BidCreditRecord implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/** 主键*/
	@Id
	@GeneratedValue
	@Column(name="F01")
	private Long id;
	
	/** 债权编码*/
	@Column(name="F02")
	private String creditNumber;
	
	/** 标ID,参考T6230.F01*/
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="F03")
	private Bid bid;
	
	/** 债权人ID,参考T6110.F01*/
	/** 用户账户*/
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="F04")
	private UserAccount userAccount;
//	@Column(name="F04")
//	private Long creditorUserId;
	
	/** 购买价格*/
	@Column(name="F05")
	private BigDecimal buyingPrice;
	
	/** 原始债权金额*/
	@Column(name="F06")
	private BigDecimal originalPrice;
	
	/** 持有债权金额*/
	@Column(name="F07")
	private BigDecimal holdPrice;
	
	/** 是否正在转让*/
	@Enumerated(EnumType.STRING)
	@Column(name="F08")
	private Judge isMakeOverNow;
	
	/** 创建日期*/
	@Temporal(TemporalType.DATE)
	@Column(name="F09")
	private Date createDate;
	
	/** 起息日期*/
	@Temporal(TemporalType.DATE)
	@Column(name="F10")
	private Date beginDate;
	
	/** 投资记录ID,参考T6250.F01*/
	@Column(name="F11")
	private Long investRecordId;
	
	/** 债权转让订单Id*/
	@Column(name="F12")
	private Long orderId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCreditNumber() {
		return creditNumber;
	}

	public void setCreditNumber(String creditNumber) {
		this.creditNumber = creditNumber;
	}

//	public Long getBidPk() {
//		return bidPk;
//	}
//
//	public void setBidPk(Long bidPk) {
//		this.bidPk = bidPk;
//	}

//	public Long getCreditorUserId() {
//		return creditorUserId;
//	}

	public Bid getBid() {
		return bid;
	}

	public void setBid(Bid bid) {
		this.bid = bid;
	}

//	public void setCreditorUserId(Long creditorUserId) {
//		this.creditorUserId = creditorUserId;
//	}

	public BigDecimal getBuyingPrice() {
		return buyingPrice;
	}

	public UserAccount getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	public void setBuyingPrice(BigDecimal buyingPrice) {
		this.buyingPrice = buyingPrice;
	}

	public BigDecimal getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(BigDecimal originalPrice) {
		this.originalPrice = originalPrice;
	}

	public BigDecimal getHoldPrice() {
		return holdPrice;
	}

	public void setHoldPrice(BigDecimal holdPrice) {
		this.holdPrice = holdPrice;
	}

	public Judge getIsMakeOverNow() {
		return isMakeOverNow;
	}

	public void setIsMakeOverNow(Judge isMakeOverNow) {
		this.isMakeOverNow = isMakeOverNow;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public Long getInvestRecordId() {
		return investRecordId;
	}

	public void setInvestRecordId(Long investRecordId) {
		this.investRecordId = investRecordId;
	}

	public BidCreditRecord(){
		
	}
	public BidCreditRecord(Long id){
		this.id=id;
	}
//	@Override
//	public String toString() {
//		return "BidCreditRecord [id=" + id + ", creditNumber=" + creditNumber + ", bidPk=" + bidPk + ", creditorUserId=" + creditorUserId + ", buyingPrice="
//				+ buyingPrice + ", originalPrice=" + originalPrice + ", holdPrice=" + holdPrice + ", isMakeOverNow=" + isMakeOverNow + ", createDate="
//				+ createDate + ", beginDate=" + beginDate + ", investRecordId=" + investRecordId + "]";
//	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	
	/** 债权转让订单Id*/
//	@Column(name="F12")
//	private Long loanOrderId;

	
}
