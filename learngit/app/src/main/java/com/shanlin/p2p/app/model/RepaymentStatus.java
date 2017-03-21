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
 * 还款状态
 * @author yangjh
 *
 */
@Entity
@Table(name="repayment_status",schema="S62")
public class RepaymentStatus implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** id*/
	@Id
	@GeneratedValue
	@Column
	private Long id;
	
	/** 标id*/
	@Column
	private Long bid;
	
	/** 是否生效*/
	@Column
	private int num;
	
	/** 是否生效*/
	@Column
	private int status;
	
	/** 发送时间*/
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Date createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBid() {
		return bid;
	}

	public void setBid(Long bid) {
		this.bid = bid;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "RepaymentStatus [id=" + id + ", bid=" + bid + ", num=" + num
				+ ", status=" + status + ", createTime=" + createTime + "]";
	}
	
}
