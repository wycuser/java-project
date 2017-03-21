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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.shanlin.framework.utils.Formater;
import com.shanlin.p2p.app.model.enums.CreditAssignmentStatus;
import com.shanlin.p2p.app.model.enums.Judge;

/**
 * 债权转让申请(T6260)
 * @author zheng xin
 * @createDate 2015年2月5日
 */
@Entity
@Table(name="T6260", schema="S62")
public class CreditAssignmentApply implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/** 主键*/
	@Id
	@GeneratedValue
	@Column(name="F01")
	private Long id;
	
	/** 债权记录*/
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="F02")
	private BidCreditRecord creditRecord;
	
	/** 转让价格*/
	@Column(name="F03")
	private BigDecimal price;
	
	/** 转让债权*/
	@Column(name="F04")
	private BigDecimal originalPrice;
	
	/** 创建时间*/
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="F05")
	private Date createTime;
	
	/** 结束日期*/
	@Temporal(TemporalType.DATE)
	@Column(name="F06")
	private Date endDate;
	
	/** 状态,DSH:待审核;ZRZ:转让中;YJS:已结束;*/
	@Enumerated(EnumType.STRING)
	@Column(name="F07")
	private CreditAssignmentStatus status;
	
	/** 转让手续费率*/
	@Column(name="F08")
	private BigDecimal serviceCharge;

	public Map<String, Object> toMap(){
		Map<String, Object> map = new HashMap<String, Object>();
		Bid bid = this.getCreditRecord().getBid();
		map.put("id", this.id);
		map.put("title", bid.getTitle());
		map.put("rate", Formater.formatRate(bid.getRate(), false));
		map.put("loanPeriod", bid.getExtend().getRemainPeriods() + "/" + bid.getExtend().getTotalPeriods());
		map.put("amount", this.getPrice().doubleValue() > 10000? this.getPrice().divide(new BigDecimal(10000)) + "万" 
				: this.getPrice().setScale(0) + "元");
		map.put("mode", bid.getMode().getChineseName());
		map.put("safeType", bid.getIsFieldWork() == Judge.S? "实地认证":"100%本息保障");
		map.put("status", 0);
		map.put("statusButton", "立即投资");
		map.put("type", 1);
		return map;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BidCreditRecord getCreditRecord() {
		return creditRecord;
	}

	public void setCreditRecord(BidCreditRecord creditRecord) {
		this.creditRecord = creditRecord;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(BigDecimal originalPrice) {
		this.originalPrice = originalPrice;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public CreditAssignmentStatus getStatus() {
		return status;
	}

	public void setStatus(CreditAssignmentStatus status) {
		this.status = status;
	}

	public BigDecimal getServiceCharge() {
		return serviceCharge;
	}

	public void setServiceCharge(BigDecimal serviceCharge) {
		this.serviceCharge = serviceCharge;
	}

}
