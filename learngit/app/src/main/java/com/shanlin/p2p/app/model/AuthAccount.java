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

import com.shanlin.p2p.app.model.enums.RealAuthIspass;
import com.shanlin.p2p.app.model.enums.RealAuthType;

/**
 * 实名认证
 * 
 * @author yangjh
 * @time 2015年1月14日
 */
@Entity
@Table(name = "T6141", schema = "S61")
public class AuthAccount implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 登录账号ID
	 */
	@Id
	@GeneratedValue
	@Column(name = "F01")
	private Long id;

	/**
	 * 姓名
	 */
	@Column(name = "F02")
	private String name;

	/**
	 * 兴趣类型,LC:理财;JK:借款
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "F03")
	private RealAuthType type;

	/**
	 * 实名认证,TG:通过;BTG:不通过;
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "F04")
	private RealAuthIspass isPass;

	/**
	 * 用户头像文件编码
	 */
	@Column(name = "F05")
	private String picture;

	/**
	 * 身份证号,3-18位星号替换
	 */
	@Column(name = "F06")
	private String identity;

	/**
	 * 身份证号,加密存储,唯一
	 */
	@Column(name = "F07")
	private String denseIdentity;

	/**
	 * 出生日期
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "F08")
	private Date bornTime;

	/**
	 * 性别
	 */
	@Column(name = "sex")
	private Integer sex;

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public RealAuthType getType() {
		return type;
	}

	public void setType(RealAuthType type) {
		this.type = type;
	}

	public RealAuthIspass getIsPass() {
		return isPass;
	}

	public void setIsPass(RealAuthIspass isPass) {
		this.isPass = isPass;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getDenseIdentity() {
		return denseIdentity;
	}

	public void setDenseIdentity(String denseIdentity) {
		this.denseIdentity = denseIdentity;
	}

	public Date getBornTime() {
		return bornTime;
	}

	public void setBornTime(Date bornTime) {
		this.bornTime = bornTime;
	}

	@Override
	public String toString() {
		return "AuthAccount [id=" + id + ", name=" + name + ", type=" + type + ", isPass=" + isPass + ", picture=" + picture + ", identity=" + identity + ", denseIdentity=" + denseIdentity + ", bornTime=" + bornTime + "]";
	}

}
