package com.shanlin.p2p.app.model;

import java.io.Serializable;
import java.util.Date;

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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.shanlin.p2p.app.model.enums.RealAuthIspass;
import com.shanlin.p2p.app.model.enums.UseStatus;

/**
 * 银行卡
 * @author zheng xin
 * @createDate 2015年1月28日
 */
@Entity
@Table(name="T6114", schema="S61")
public class BankCard implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/** Id*/
	@Id
	@GeneratedValue
	@Column(name="F01")
	private Long id;
	
	/** 用户ID*/
	@Column(name="F02")
	private Long userId;
	
	/** 银行*/
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="F03")
	@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"}) 
	private Bank bank;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="F04")
	@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"}) 
	private Area area;
	
	/** 开户行名称*/
	@Column(name="F05")
	private String openingName;
	
	/** 卡号*/
	@Column(name="F06")
	private String number;
	
	/** 加密卡号*/
	@Column(name="F07")
	private String cipherNumber;
	
	/** 状态*/
	@Column(name="F08")
	@Enumerated(EnumType.STRING)
	private UseStatus status;
	
	/** 创建时间*/
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="F09")
	private Date createTime;
	
	/** 安全认证状态*/
	@Enumerated(EnumType.STRING)
	@Column(name="F10")
	private RealAuthIspass safeStatus;
	
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

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public String getOpeningName() {
		return openingName;
	}

	public void setOpeningName(String openingName) {
		this.openingName = openingName;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getCipherNumber() {
		return cipherNumber;
	}

	public void setCipherNumber(String cipherNumber) {
		this.cipherNumber = cipherNumber;
	}

	public UseStatus getStatus() {
		return status;
	}

	public void setStatus(UseStatus status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public RealAuthIspass getSafeStatus() {
		return safeStatus;
	}

	public void setSafeStatus(RealAuthIspass safeStatus) {
		this.safeStatus = safeStatus;
	}

	public String getBankName() {
		return bank.getName();
	}
	
	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	@Override
	public String toString() {
		return "BankCard [id=" + id + ", userId=" + userId + ", bank=" + bank
				+ ", area=" + area + ", openingName=" + openingName
				+ ", number=" + number + ", cipherNumber=" + cipherNumber
				+ ", status=" + status + ", createTime=" + createTime
				+ ", safeStatus=" + safeStatus + "]";
	}
	
}
