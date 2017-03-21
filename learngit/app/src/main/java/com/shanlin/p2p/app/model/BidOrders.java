package com.shanlin.p2p.app.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 投标订单
 * @author zheng xin
 * @createDate 2015年3月11日
 */
@Entity
@Table(name="T6504", schema="S65")
public class BidOrders implements Serializable{
	
	private static final long serialVersionUID = 1L;

//	@Id
//	@Column(name="F01")
//	private Long id;
	
	/** 订单ID*/
	@Id
	@Column(name="F01")
	private Long ordersId;
	
	/** 投资用户ID,参考T6110.F01*/
	@Column(name="F02")
	private Long userId;
	
	/** 标ID,参考T6230.F01*/
	@Column(name="F03")
	private Long bidPk;
	
	/** 投标金额*/
	@Column(name="F04")
	private BigDecimal money;
	
	/** 投标记录ID,参考T6250.F01,投标成功时记录*/
	@Column(name="F05")
	private Long bidRecordId;

//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}

	public Long getOrdersId() {
		return ordersId;
	}

	public void setOrdersId(Long ordersId) {
		this.ordersId = ordersId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getBidPk() {
		return bidPk;
	}

	public void setBidPk(Long bidPk) {
		this.bidPk = bidPk;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public Long getBidRecordId() {
		return bidRecordId;
	}

	public void setBidRecordId(Long bidRecordId) {
		this.bidRecordId = bidRecordId;
	}
	
}
