package com.shanlin.p2p.app.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 债权转让协议版本号(T6261)
 * 
 * @author yangjh
 * @createDate 2015年4月27日
 */
@Entity
@Table(name = "T6261", schema = "S62")
public class CreditAssignmentVersion implements Serializable {

	private static final long serialVersionUID = 1L;
	/** 主键 */
	@Id
	@Column(name = "F01")
	private Long id;

	/** 版本号 */
	@Column(name = "F02")
	private Long num;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getNum() {
		return num;
	}

	public void setNum(Long num) {
		this.num = num;
	}

}
