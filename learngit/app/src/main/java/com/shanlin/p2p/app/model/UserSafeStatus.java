package com.shanlin.p2p.app.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.shanlin.p2p.app.model.enums.RealAuthIspass;
import com.shanlin.p2p.app.model.enums.RealAuthSet;

/**
 * 用户安全认证状态
 * @author zheng xin
 * @createDate 2015年1月19日
 */
@Entity
@Table(name="T6118", schema="S61")
public class UserSafeStatus implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** 用户ID,参考T6110.F01*/
	@Id
	@GeneratedValue
	@Column(name="F01")
	private Long id;
	
	/** 身份认证状态 TG:通过;BTG:不通过*/
	@Enumerated(EnumType.STRING)
	@Column(name="F02")
	private RealAuthIspass idCardStatus;
	
	/** 手机认证状态 TG:通过;BTG:不通过*/
	@Enumerated(EnumType.STRING)
	@Column(name="F03")
	private RealAuthIspass phoneStatus;
	
	/** 邮箱认证状态 TG:通过;BTG:不通过*/
	@Enumerated(EnumType.STRING)
	@Column(name="F04")
	private RealAuthIspass mailStatus;

	/** 提现密码 YSZ:已设置;WSZ:未设置*/
	@Enumerated(EnumType.STRING)
	@Column(name="F05")
	private RealAuthSet moneyPassStatus;
	
	/** 手机号码*/
	@Column(name="F06")
	private String mobilePhone;
	
	/** 邮箱*/
	@Column(name="F07")
	private String mail;
	
	/** 提现密码*/
	@Column(name="F08")
	private String moneyPass;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RealAuthIspass getIdCardStatus() {
		return idCardStatus;
	}

	public void setIdCardStatus(RealAuthIspass idCardStatus) {
		this.idCardStatus = idCardStatus;
	}

	public RealAuthIspass getPhoneStatus() {
		return phoneStatus;
	}

	public void setPhoneStatus(RealAuthIspass phoneStatus) {
		this.phoneStatus = phoneStatus;
	}

	public RealAuthIspass getMailStatus() {
		return mailStatus;
	}

	public void setMailStatus(RealAuthIspass mailStatus) {
		this.mailStatus = mailStatus;
	}

	public RealAuthSet getMoneyPassStatus() {
		return moneyPassStatus;
	}

	public void setMoneyPassStatus(RealAuthSet moneyPassStatus) {
		this.moneyPassStatus = moneyPassStatus;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getMoneyPass() {
		return moneyPass;
	}

	public void setMoneyPass(String moneyPass) {
		this.moneyPass = moneyPass;
	}
	
}
