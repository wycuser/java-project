package com.shanlin.p2p.app.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 标债权记录(T6251)
 * @author yangjh
 * @createDate 2015年4月30日
 */
@Entity
@Table(name="T5125", schema="S51")
public class Agreement implements Serializable{

	private static final long serialVersionUID = 1L;
	
	 /** 
     * 协议范本ID，非自增 
     */
	@Id
	@Column(name="F01")
    private Long id;

    /** 
     * 最新版本号
     */
	@Column(name="F02")
	private Long versionNum;

    /** 
     * 类型名称
     */
	@Column(name="F03")
	private String name;

    /** 
     * 更新时间
     */
	@Column(name="F04")
	private Date updateDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getVersionNum() {
		return versionNum;
	}

	public void setVersionNum(Long versionNum) {
		this.versionNum = versionNum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
}
