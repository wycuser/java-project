package com.shanlin.p2p.app.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 债权转让记录(T6507)
 * 
 * @author yangjh
 * @createDate 2015年5月04日
 */
@Entity
@Table(name = "T6507", schema = "S65")
public class CreditAssignmentOrder implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "F01")
	private Long id;

	/** 债权申请ID,参考T6260.F01 */
	@Column(name = "F02")
	private Long applyId;

	/** 购买人ID,参考T6110.F01 */
	@Column(name = "F03")
	private Long userId;

	/** 购买价格 */
	@Column(name = "F04")
	private BigDecimal buyAmount;

	/** 转让价格 */
	@Column(name = "F05")
	private BigDecimal assignmentAmount;

	/** 转让手续费 */
	@Column(name = "F06")
	private BigDecimal assignmentFees;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getApplyId() {
		return applyId;
	}

	public void setApplyId(Long applyId) {
		this.applyId = applyId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public BigDecimal getBuyAmount() {
		return buyAmount;
	}

	public void setBuyAmount(BigDecimal buyAmount) {
		this.buyAmount = buyAmount;
	}

	public BigDecimal getAssignmentAmount() {
		return assignmentAmount;
	}

	public void setAssignmentAmount(BigDecimal assignmentAmount) {
		this.assignmentAmount = assignmentAmount;
	}

	public BigDecimal getAssignmentFees() {
		return assignmentFees;
	}

	public void setAssignmentFees(BigDecimal assignmentFees) {
		this.assignmentFees = assignmentFees;
	}
}
