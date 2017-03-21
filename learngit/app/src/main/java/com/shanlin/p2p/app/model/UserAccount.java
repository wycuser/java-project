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

import com.shanlin.p2p.app.model.enums.Judge;
import com.shanlin.p2p.app.model.enums.UserRegisterSource;
import com.shanlin.p2p.app.model.enums.UserStatus;
import com.shanlin.p2p.app.model.enums.UserType;

/**
 * 用户账户
 * 
 * @author zheng xin
 */
@Entity
@Table(name="T6110", schema="S61")
public class UserAccount implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	/** 主键*/
	@Id
	@GeneratedValue
	@Column(name="F01")
	private Long id;
	
	/** 用户登录名*/
	@Column(name="F02")
	private String loginName;
	
	/** 用户登录密码*/
	@Column(name="F03")
	private String password;
	
	/** 手机*/
	@Column(name="F04")
	private String mobilePhone;
	
	/** 邮箱*/
	@Column(name="F05")
	private String email;
	
	/** 用户类型*/
	@Enumerated(EnumType.STRING)
	@Column(name="F06")
	private UserType userType = UserType.ZRR;
	
	/** 用户状态*/
	@Enumerated(EnumType.STRING)
	@Column(name="F07")
	private UserStatus userStatus = UserStatus.QY;
	
	/** 用户注册来源*/
	@Enumerated(EnumType.STRING)
	@Column(name="F08")
	private UserRegisterSource userRegisterSource = UserRegisterSource.ZC;
	
	/** 注册时间*/
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="F09")
	private Date registerTime;
	
	/** 是否担保方*/
	@Enumerated(EnumType.STRING)
	@Column(name="F10")
	private Judge isAssure = Judge.F;
	
	/** 提现密码输错次数*/
	@Column(name="F11", insertable=false)
	private Integer errPassCount = Integer.valueOf(0);
	
	/** 是否第一次登录*/
	@Enumerated(EnumType.STRING)
	@Column(name="F12", insertable=false)
	private Judge isFirstLogin = Judge.S;
	
	/** 是否删除*/
	@Enumerated(EnumType.STRING)
	@Column(name="F13", insertable=false)
	private Judge isDel = Judge.F;
	
	/** 第三方会员标识*/
	@Column(name="part3Bs")
	private String part3Bs;
	
	public UserAccount(){}
	
	public UserAccount(Long id){
		this.id=id;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public UserStatus getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(UserStatus userStatus) {
		this.userStatus = userStatus;
	}

	public UserRegisterSource getUserRegisterSource() {
		return userRegisterSource;
	}

	public void setUserRegisterSource(UserRegisterSource userRegisterSource) {
		this.userRegisterSource = userRegisterSource;
	}

	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public Judge getIsAssure() {
		return isAssure;
	}

	public void setIsAssure(Judge isAssure) {
		this.isAssure = isAssure;
	}

	public Integer getErrPassCount() {
		return errPassCount;
	}

	public void setErrPassCount(Integer errPassCount) {
		this.errPassCount = errPassCount;
	}

	public Judge getIsFirstLogin() {
		return isFirstLogin;
	}

	public void setIsFirstLogin(Judge isFirstLogin) {
		this.isFirstLogin = isFirstLogin;
	}

	public Judge getIsDel() {
		return isDel;
	}

	public void setIsDel(Judge isDel) {
		this.isDel = isDel;
	}
	
	public String getPart3Bs() {
		return part3Bs;
	}

	public void setPart3Bs(String part3Bs) {
		this.part3Bs = part3Bs;
	}
	
	@Override
	public String toString() {
		return "UserAccount [id=" + id + ", loginName=" + loginName
				+ ", password=" + password + ", mobilePhone=" + mobilePhone
				+ ", email=" + email + ", registerTime=" + registerTime + "]";
	}
}