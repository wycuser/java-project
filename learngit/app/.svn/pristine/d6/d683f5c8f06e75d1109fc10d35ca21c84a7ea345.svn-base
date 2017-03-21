package com.shanlin.p2p.app.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.shanlin.p2p.app.model.enums.ExperienceSource;

/**
 * 体验金流水
 * @author zheng xin
 * @createDate 2015年3月18日
 */
@Entity
@Table(name="userexpemony2", schema="S10")
public class ExperienceAccountFlow implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	@Column(name="id")
	private Long id;
	
	/** 用户id*/
	@Column(name="user_id")
	private Long userId;
	
	/** 体验金金额*/
	@Column(name="money")
	private BigDecimal money;
	
	/** 体验金的来源*/
	@Enumerated(EnumType.STRING)
	@Column(name="source")
	private ExperienceSource source;
	
	/** 操作时间*/
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="gettime")
	private Date operateTime;
	
	/** 剩余金额*/
	@Column(name="balance")
	private BigDecimal balance;
	
	/** 备注*/
	@Column(name="remark")
	private String remark;
	
	/** 被邀名字*/
	@Column(name="inviteName")
	private String inviteName;
	
	/** 创建时间*/
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="createTime")
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

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public ExperienceSource getSource() {
		return source;
	}

	public void setSource(ExperienceSource source) {
		this.source = source;
	}

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getInviteName() {
		return inviteName;
	}

	public void setInviteName(String inviteName) {
		this.inviteName = inviteName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
