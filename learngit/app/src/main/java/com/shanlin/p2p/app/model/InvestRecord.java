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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.shanlin.p2p.app.model.enums.Judge;

/**
 * 投资记录(T6250)
 * 
 * @author zheng xin
 * @createDate 2015年1月14日
 */
@Entity
@Table(name = "T6250", schema = "S62")
public class InvestRecord implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 主键 */
	@Id
	@GeneratedValue
	@Column(name = "F01")
	private Long id;

	/** 标ID,参考T6230.F01 */
	@ManyToOne
	@JoinColumn(name = "F02")
	private Bid bid;

	/** 投资人ID,参考T6110.F01 */
	@ManyToOne
	@JoinColumn(name = "F03")
	private UserAccount userAccount;

	/** 购买价格 */
	@Column(name = "F04")
	private BigDecimal buyingPrice;

	/** 债权金额 */
	@Column(name = "F05")
	private BigDecimal creditPrice;

	/** 投标时间 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "F06")
	private Date investTime;

	/** 是否取消 */
	@Enumerated(EnumType.STRING)
	@Column(name = "F07")
	private Judge isCancel = Judge.F;

	/** 是否放款 */
	@Enumerated(EnumType.STRING)
	@Column(name = "F08")
	private Judge isLoan = Judge.F;

	/** 是否自动投标 */
//	@Column(name = "F09")
//	private JUDGE isAutoInvest;
	
	/** 是否手机投标 */
	@Enumerated(EnumType.STRING)
	@Column(name = "isMobileInvest")
	private Judge isMobileInvest;
	
	
	/** 预期收益 */
	@Column(name = "income")
	private BigDecimal income;
	
	
	/** 投资所使用的计算提现手续费的额度 */
	@Column(name = "usLines")
	private BigDecimal usLines;
	
	

	public BigDecimal getUsLines() {
		return usLines;
	}

	public void setUsLines(BigDecimal usLines) {
		this.usLines = usLines;
	}

	public BigDecimal getIncome() {
		return income;
	}

	public void setIncome(BigDecimal income) {
		this.income = income;
	}

	public Judge getIsMobileInvest() {
		return isMobileInvest;
	}

	public void setIsMobileInvest(Judge isMobileInvest) {
		this.isMobileInvest = isMobileInvest;
	}

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

//	public Long getInvestUserId() {
//		return investUserId;
//	}
//
//	public void setInvestUserId(Long investUserId) {
//		this.investUserId = investUserId;
//	}

	public BigDecimal getBuyingPrice() {
		return buyingPrice;
	}

	public Bid getBid() {
		return bid;
	}

	public void setBid(Bid bid) {
		this.bid = bid;
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

	public BigDecimal getCreditPrice() {
		return creditPrice;
	}

	public void setCreditPrice(BigDecimal creditPrice) {
		this.creditPrice = creditPrice;
	}

	public Date getInvestTime() {
		return investTime;
	}

	public void setInvestTime(Date investTime) {
		this.investTime = investTime;
	}

	public Judge getIsCancel() {
		return isCancel;
	}

	public void setIsCancel(Judge isCancel) {
		this.isCancel = isCancel;
	}

	public Judge getIsLoan() {
		return isLoan;
	}

	public void setIsLoan(Judge isLoan) {
		this.isLoan = isLoan;
	}

//	public JUDGE getIsAutoInvest() {
//		return isAutoInvest;
//	}
//
//	public void setIsAutoInvest(JUDGE isAutoInvest) {
//		this.isAutoInvest = isAutoInvest;
//	}

//	@Override
//	public String toString() {
//		return "InvestRecord [id=" + id + ", bidPk=" + bidPk + ", investUserId=" + investUserId + ", buyingPrice=" + buyingPrice + ", creditPrice="
//				+ creditPrice + ", investTime=" + investTime + ", isCancel=" + isCancel + ", isLoan=" + isLoan + ", isAutoInvest=" + isAutoInvest + "]";
//	}
}
