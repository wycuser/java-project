package com.shanlin.p2p.app.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.shanlin.p2p.app.model.enums.Judge;
import com.shanlin.p2p.app.model.enums.UseStatus;

/**
 * 信用认证项(T5123)
 * @author zheng xin
 * @createDate 2015年1月21日
 */
@Entity
@Table(name="T5123", schema="S51")
public class CreditAuthProject implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/** 主键*/
	@Id
	@GeneratedValue
	@Column(name="F01")
	private Long id;
	
	/** 类型名称*/
	@Column(name="F02")
	private String type;
	
	/** 必要认证,S:是;F:否*/
	@Enumerated(EnumType.STRING)
	@Column(name="F03")
	private Judge isMust;
	
	/** 必要认证,S:是;F:否*/
	@Enumerated(EnumType.STRING)
	@Column(name="F04")
	private UseStatus status;
	
	/** 最高分数*/
	@Column(name="F05")
	private Long maxScore;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Judge getIsMust() {
		return isMust;
	}

	public void setIsMust(Judge isMust) {
		this.isMust = isMust;
	}

	public UseStatus getStatus() {
		return status;
	}

	public void setStatus(UseStatus status) {
		this.status = status;
	}

	public Long getMaxScore() {
		return maxScore;
	}

	public void setMaxScore(Long maxScore) {
		this.maxScore = maxScore;
	}
	
}
