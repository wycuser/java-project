package com.shanlin.p2p.app.model;

import java.io.Serializable;
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

import com.shanlin.p2p.app.model.enums.OrdersSource;
import com.shanlin.p2p.app.model.enums.OrdersStatus;

/**
 * 订单
 * @author zheng xin
 * @createDate 2015年3月11日
 */
@Entity
@Table(name="T6501", schema="S65")
public class Orders implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	@Column(name="F01")
	private Long id;
	
	/** 类型编码*/
	@Column(name="F02")
	private Integer typeCode;
	
	/** 状态,DTJ:待提交;YTJ:已提交;DQR:待确认;CG:成功;SB:失败;*/
	@Column(name="F03")
	@Enumerated(EnumType.STRING)
	private OrdersStatus status;
	
	/** 创建时间*/
	@Column(name="F04")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	
	/** 提交时间*/
	@Column(name="F05")
	@Temporal(TemporalType.TIMESTAMP)
	private Date submitTime;
	
	/** 完成时间*/
	@Column(name="F06")
	@Temporal(TemporalType.TIMESTAMP)
	private Date finishTime;
	
	/** 订单来源,XT:系统;YH:用户;HT:后台;*/
	@Column(name="F07")
	@Enumerated(EnumType.STRING)
	private OrdersSource source;
	
	/** 用户ID,参考T6110.F01*/
	@Column(name="F08")
	private Long userId;
	
	/** 后台帐号ID,参考T7110.F01*/
	@Column(name="F09")
	private Long consoleUserId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(Integer typeCode) {
		this.typeCode = typeCode;
	}

	public OrdersStatus getStatus() {
		return status;
	}

	public void setStatus(OrdersStatus status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public OrdersSource getSource() {
		return source;
	}

	public void setSource(OrdersSource source) {
		this.source = source;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getConsoleUserId() {
		return consoleUserId;
	}

	public void setConsoleUserId(Long consoleUserId) {
		this.consoleUserId = consoleUserId;
	}
	
}
