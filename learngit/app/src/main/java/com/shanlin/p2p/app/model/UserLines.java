package com.shanlin.p2p.app.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "userlines", schema = "S61")
public class UserLines implements Serializable {

	private static final long serialVersionUID = 1L;

	/** Id */
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	/** 用户ID */
	@Column(name = "userid")
	private Long userId;

	/** 提现未投资额度 */
	@Column(name = "linesr")
	private BigDecimal linesr;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public BigDecimal getLinesr() {
		return linesr;
	}

	public void setLinesr(BigDecimal linesr) {
		this.linesr = linesr;
	}

}
