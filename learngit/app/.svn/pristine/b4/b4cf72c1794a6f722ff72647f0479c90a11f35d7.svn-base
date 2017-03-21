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

import com.shanlin.p2p.app.model.enums.LetterStatus;

/**
 * 站内信
 * @author zheng xin
 * @createDate 2015年1月28日
 */
@Entity
@Table(name="T6123", schema="S61")
public class Letter implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name="F01")
	private Long id;
	
	/** 用户id*/
	@Column(name="F02")
	private Long userId;
	
	/** 标题*/
	@Column(name="F03")
	private String title;
	
	/** 发送时间*/
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="F04")
	private Date sendTime;
	
	/** 状态*/
	@Enumerated(EnumType.STRING)
	@Column(name="F05")
	private LetterStatus status;

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public LetterStatus getStatus() {
		return status;
	}

	public void setStatus(LetterStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Letter [id=" + id + ", userId=" + userId + ", title=" + title + ", sendTime=" + sendTime + ", status=" + status + "]";
	}
	
}
