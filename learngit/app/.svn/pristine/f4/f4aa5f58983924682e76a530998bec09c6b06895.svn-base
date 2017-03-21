package com.shanlin.p2p.app.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 标的费率
 * @author yangjh
 *
 */
@Entity
@Table(name="T6238", schema="S62")
public class BidRate implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="F01")
	private Long id;
	
	@Column(name="F02")
	private BigDecimal serveRate;
	
	@Column(name="F03")
	private BigDecimal manageRate;
	
	@Column(name="F04")
	private BigDecimal overdueRate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getServeRate() {
		return serveRate;
	}

	public void setServeRate(BigDecimal serveRate) {
		this.serveRate = serveRate;
	}

	public BigDecimal getManageRate() {
		return manageRate;
	}

	public void setManageRate(BigDecimal manageRate) {
		this.manageRate = manageRate;
	}

	public BigDecimal getOverdueRate() {
		return overdueRate;
	}

	public void setOverdueRate(BigDecimal overdueRate) {
		this.overdueRate = overdueRate;
	}

	@Override
	public String toString() {
		return "BidRate [id=" + id + ", serveRate=" + serveRate
				+ ", manageRate=" + manageRate + ", overdueRate=" + overdueRate
				+ "]";
	}
	
}
