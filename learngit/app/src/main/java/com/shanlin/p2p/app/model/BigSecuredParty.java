package com.shanlin.p2p.app.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.shanlin.p2p.app.model.enums.SecuredPartyStatus;

/**
 * 标担保方列表(T6236)
 * 
 * @author yangjh
 * @createDate 2015年4月27日
 */
@Entity
@Table(name = "T6236", schema = "S62")
public class BigSecuredParty implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 主键 */
	@Id
	@GeneratedValue
	@Column(name = "F01")
	private Long id;

	/** 标ID,参考T6230.F01 */
	@Column(name = "F02")
	private Long bidPk;

	/** 担保方ID,参考T6110.F01 */
	@Column(name = "F03")
	private Long userPk;

	/** 是否主担保方,S:是;F:否; */
	@Column(name = "F04")
	private SecuredPartyStatus status;

	/** 担保情况 */
	@Column(name = "F05")
	private String detail;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBidPk() {
		return bidPk;
	}

	public void setBidPk(Long bidPk) {
		this.bidPk = bidPk;
	}

	public Long getUserPk() {
		return userPk;
	}

	public void setUserPk(Long userPk) {
		this.userPk = userPk;
	}

	public SecuredPartyStatus getStatus() {
		return status;
	}

	public void setStatus(SecuredPartyStatus status) {
		this.status = status;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	@Override
	public String toString() {
		return "BigSecuredParty [id=" + id + ", bidPk=" + bidPk + ", userPk="
				+ userPk + ", status=" + status + ", detail=" + detail + "]";
	}

}
