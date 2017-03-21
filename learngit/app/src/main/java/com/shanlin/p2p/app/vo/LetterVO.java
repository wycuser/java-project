package com.shanlin.p2p.app.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;


public class LetterVO {
	
	private Long id;
	
	private String title;
	
	private Date sendTime;
	
	private int status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "LetterVO [title=" + title + ", sendTime=" + sendTime + ", status=" + status + "]";
	}
	
}
