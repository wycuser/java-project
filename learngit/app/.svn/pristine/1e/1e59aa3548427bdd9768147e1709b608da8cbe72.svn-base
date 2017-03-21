package com.shanlin.p2p.app.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.shanlin.p2p.app.model.enums.BidMode;

/**
 * 善行宝配置
 * @author zheng xin
 * @createDate 2015年7月22日
 */
@Entity
@Table(name="sxbao_config", schema="S62")
public class SxbaoConfig implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Long id;
	
	/** 标题*/
	private String title;
	
	/** 年化利率*/
	private BigDecimal rate;
	
	/** 投资下限*/
	private BigDecimal investFloor;
	
	/** 投资上限*/
	private BigDecimal investCeiling;
	
	/** 借款周期（月）*/
	private Integer loanPeriod;
	
	/** 还款方式*/
	@Enumerated(EnumType.STRING)
	private BidMode mode;
	
	/** 本期标的*/
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="bid")
	private Bid bid;
	
	/** 善行宝类型id*/
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="typeId")
	private SxbaoType type;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="sxbaoConfig")
	private Set<Bid> bids = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public BigDecimal getInvestFloor() {
		return investFloor;
	}

	public void setInvestFloor(BigDecimal investFloor) {
		this.investFloor = investFloor;
	}

	public BigDecimal getInvestCeiling() {
		return investCeiling;
	}

	public void setInvestCeiling(BigDecimal investCeiling) {
		this.investCeiling = investCeiling;
	}

	public Integer getLoanPeriod() {
		return loanPeriod;
	}

	public void setLoanPeriod(Integer loanPeriod) {
		this.loanPeriod = loanPeriod;
	}

	public BidMode getMode() {
		return mode;
	}

	public void setMode(BidMode mode) {
		this.mode = mode;
	}

//	public Long getBidPk() {
//		return bidPk;
//	}
//
//	public void setBidPk(Long bidPk) {
//		this.bidPk = bidPk;
//	}

	public SxbaoType getType() {
		return type;
	}

	public void setType(SxbaoType type) {
		this.type = type;
	}

	public Set<Bid> getBids() {
		return bids;
	}

	public void setBids(Set<Bid> bids) {
		this.bids = bids;
	}

	public Bid getBid() {
		return bid;
	}

	public void setBid(Bid bid) {
		this.bid = bid;
	}
	
}
