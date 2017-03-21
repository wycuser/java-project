package com.shanlin.p2p.app.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.shanlin.p2p.app.model.enums.Judge;

@Entity
@Table(name="T6231", schema="S62")
public class BidExtend implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="F01")
	private Long id;
	
	/** 总期数*/
	@Column(name="F02")
	private Integer totalPeriods;
	
	/** 剩余期数*/
	@Column(name="F03")
	private Integer remainPeriods;
	
	/** 月化收益率*/
	@Column(name="F04")
	private BigDecimal monIncomeRate;
	
	/** 日化收益率*/
	@Column(name="F05")
	private BigDecimal dayIncomeRate;
	
	/** 下次还款日期*/
	@Temporal(TemporalType.DATE)
	@Column(name="F06")
	private Date nextRepayDate;
	
	/** 项目区域位置ID,参考T5119.F01*/
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="F07")
	private ChineseArea area;
	
	/** 资金用途*/
	@Column(name="F08")
	private String use;
	
	/** 借款描述*/
	@Column(name="F09")
	private String desc;
	
	/** 审核时间*/
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="F10")
	private Date checkTime;
	
	/** 满标时间*/
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="F11")
	private Date fillBidTime;
	
	/** 放款时间*/
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="F12")
	private Date loanTime;
	
	/** 结清时间*/
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="F13")
	private Date closeOffTime;
	
	/** 流标时间*/
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="F15")
	private Date failTime;
	
	/** 还款来源*/
	@Column(name="F16")
	private String repaymentSource;
	
	/** 是否逾期,S:是(逾期);F:否;YZYQ:严重逾期*/
	@Column(name="F19")
	private String overdue;

	/** 是否为按天借款,S:是;F:否*/
	@Enumerated(EnumType.STRING)
	@Column(name="F21")
	private Judge isByDay;
	
	/** 借款周期 按天*/
	@Column(name="F22")
	private Integer loanPeriod;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getTotalPeriods() {
		return totalPeriods;
	}

	public void setTotalPeriods(Integer totalPeriods) {
		this.totalPeriods = totalPeriods;
	}

	public Integer getRemainPeriods() {
		return remainPeriods;
	}

	public void setRemainPeriods(Integer remainPeriods) {
		this.remainPeriods = remainPeriods;
	}

	public BigDecimal getMonIncomeRate() {
		return monIncomeRate;
	}

	public void setMonIncomeRate(BigDecimal monIncomeRate) {
		this.monIncomeRate = monIncomeRate;
	}

	public BigDecimal getDayIncomeRate() {
		return dayIncomeRate;
	}

	public void setDayIncomeRate(BigDecimal dayIncomeRate) {
		this.dayIncomeRate = dayIncomeRate;
	}

	public Date getNextRepayDate() {
		return nextRepayDate;
	}

	public void setNextRepayDate(Date nextRepayDate) {
		this.nextRepayDate = nextRepayDate;
	}

	public ChineseArea getArea() {
		return area;
	}

	public void setArea(ChineseArea area) {
		this.area = area;
	}

	public String getUse() {
		return use;
	}

	public void setUse(String use) {
		this.use = use;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

	public Date getFillBidTime() {
		return fillBidTime;
	}

	public void setFillBidTime(Date fillBidTime) {
		this.fillBidTime = fillBidTime;
	}

	public Date getLoanTime() {
		return loanTime;
	}

	public void setLoanTime(Date loanTime) {
		this.loanTime = loanTime;
	}

	public Date getCloseOffTime() {
		return closeOffTime;
	}

	public void setCloseOffTime(Date closeOffTime) {
		this.closeOffTime = closeOffTime;
	}

	public Date getFailTime() {
		return failTime;
	}

	public void setFailTime(Date failTime) {
		this.failTime = failTime;
	}

	public String getRepaymentSource() {
		return repaymentSource;
	}

	public void setRepaymentSource(String repaymentSource) {
		this.repaymentSource = repaymentSource;
	}

	public Judge getIsByDay() {
		return isByDay;
	}

	public void setIsByDay(Judge isByDay) {
		this.isByDay = isByDay;
	}

	public Integer getLoanPeriod() {
		return loanPeriod;
	}

	public void setLoanPeriod(Integer loanPeriod) {
		this.loanPeriod = loanPeriod;
	}

	public String getOverdue() {
		return overdue;
	}

	public void setOverdue(String overdue) {
		this.overdue = overdue;
	}
	
}
