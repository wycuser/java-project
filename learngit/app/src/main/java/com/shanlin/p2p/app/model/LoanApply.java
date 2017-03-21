package com.shanlin.p2p.app.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 借款申请
 * @author yangjh
 *
 */

@Entity
@Table(name="loan_apply", schema="S62")
public class LoanApply implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** 自增ID*/
	@Id
	@GeneratedValue
	@Column
	private Long id;

	/** 用户Id */
	@Column
	private Long userId;
	
	/** 申请时间*/
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Date createTime;

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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "LoanRecord [id=" + id + ", userId=" + userId + ", createTime="
				+ createTime + "]";
	}
}
