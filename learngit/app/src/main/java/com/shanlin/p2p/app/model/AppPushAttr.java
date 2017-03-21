package com.shanlin.p2p.app.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 极光推送属性实体
 * 
 * @author ice
 *
 */
@Entity
@Table(name = "app_push_attr", schema = "S71")
public class AppPushAttr implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 主键 */
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	/** 键 */
	@Column(name = "jkey")
	private String jkey;

	/** 值 */
	@Column(name = "jvalue")
	private String jvalue;

	/** 极光推送实体 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "jpushId")
	private AppPush appPush;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getJkey() {
		return jkey;
	}

	public void setJkey(String jkey) {
		this.jkey = jkey;
	}

	public String getJvalue() {
		return jvalue;
	}

	public void setJvalue(String jvalue) {
		this.jvalue = jvalue;
	}

	public AppPush getAppPush() {
		return appPush;
	}

	public void setAppPush(AppPush appPush) {
		this.appPush = appPush;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Jpuextr [id=" + id + ", jkey=" + jkey + ", jvalue=" + jvalue + "]";
	}
}