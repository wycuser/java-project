package com.shanlin.p2p.app.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.joda.time.DateTime;

import com.shanlin.framework.utils.Formater;
import com.shanlin.p2p.app.model.enums.BidStatus;

/**
 * 体验标
 * @author zheng xin
 * @createDate 2015年3月18日
 */
@Table(name="Expemonyinfo", schema="S10")
@Entity
public class ExperienceBid implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	@Column
	private Long id;
	
	/** 借款账户*/
//	@Column(name="account_name")
//	private String loginName;
	
	/** 借款标题*/
	@Column(name="title")
	private String title;
	
	/** 借款金额*/
	@Column(name="borrmoney")
	private BigDecimal amount;
	
	/** 年利率*/
	@Column(name="borrint")
	private BigDecimal rate;
	
	/** 借款期限*/
	@Column(name="borrterm")
	private Integer loanPeriod;
	
	/** 筹款期限*/
	@Column(name="fundraterm")
	private Integer findPeriod;
	
	/** 状态*/
	@Enumerated(EnumType.STRING)
	@Column(name="estatus")
	private BidStatus status;
	
	/** 发布时间*/
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="releasetime")
	private Date publishTime;
	
	/** 满标时间*/
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="deloantime")
	private Date fillBidTime;
	
	/** 创建时间*/
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="processtime")
	private Date createTime;
	
	/** 可投金额*/
	@Column(name="cancasemoney")
	private BigDecimal residueAmount;
	
	/** 放款时间*/
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="loantime")
	private Date loanTime;
	
	/** 还款时间*/
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="repaymenttime")
	private Date repayDate;

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

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public Integer getLoanPeriod() {
		return loanPeriod;
	}

	public void setLoanPeriod(Integer loanPeriod) {
		this.loanPeriod = loanPeriod;
	}

	public Integer getFindPeriod() {
		return findPeriod;
	}

	public void setFindPeriod(Integer findPeriod) {
		this.findPeriod = findPeriod;
	}

	public BidStatus getStatus() {
		return status;
	}

	public void setStatus(BidStatus status) {
		this.status = status;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public BigDecimal getResidueAmount() {
		return residueAmount;
	}

	public void setResidueAmount(BigDecimal residueAmount) {
		this.residueAmount = residueAmount;
	}

	public Date getLoanTime() {
		return loanTime;
	}

	public void setLoanTime(Date loanTime) {
		this.loanTime = loanTime;
	}

	public Date getRepayDate() {
		return repayDate;
	}

	public void setRepayDate(Date repayDate) {
		this.repayDate = repayDate;
	}
	
	public Date getFillBidTime() {
		return fillBidTime;
	}

	public void setFillBidTime(Date fillBidTime) {
		this.fillBidTime = fillBidTime;
	}

	public Map<String, Object> toMap(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", this.getId());
		map.put("title", this.getTitle());
		map.put("rate", Formater.formatRate(this.getRate().divide(new BigDecimal(100)), false));
		map.put("loanPeriod", this.getLoanPeriod().toString() + "天");
		map.put("amount", this.getAmount().doubleValue() > 10000? this.getAmount().divide(new BigDecimal(10000)) + "万" 
				: this.getAmount().setScale(0) + "元");
		map.put("mode", "利息到期一次付清");
		String statusTemp = null;
		String statusButtonTemp = null;
		BigDecimal statusNum = null;
		if(this.status == BidStatus.TBZ || this.status == BidStatus.DFK){
			if(new DateTime(this.publishTime).plusDays(this.findPeriod).getMillis() - System.currentTimeMillis() <= 0){
				statusTemp = "完";
				statusButtonTemp = "已完结";
			}else if(this.status == BidStatus.DFK){
				statusTemp = this.status.getSimpleName();
				statusButtonTemp = this.status.getChineseName();
			}else{
				statusNum = this.amount.subtract(this.residueAmount)
						.divide(this.amount, 2, BigDecimal.ROUND_DOWN).multiply(new BigDecimal(100));
				statusButtonTemp = "立即投资";
			}
		}else{
			statusTemp = this.status.getSimpleName();
			statusButtonTemp = this.status.getChineseName();
		}
		map.put("status", statusNum != null?statusNum:statusTemp);
		map.put("statusButton", statusButtonTemp);
		map.put("type", 2);
		return map;
	}
}
