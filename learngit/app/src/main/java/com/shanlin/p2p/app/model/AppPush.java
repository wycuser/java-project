package com.shanlin.p2p.app.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.shanlin.p2p.app.model.enums.SendType;

/**
 * 极光推送实体
 * 
 * @author ice
 *
 */
@Entity
@Table(name = "app_push", schema = "S71")
public class AppPush implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 主键 */
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	/** 创建人Id **/
	@Column(name = "createId")
	private Integer createId;

	/** 发送个数 **/
	@Column(name = "sendCount")
	private Integer sendCount;

	/** 推送内容 */
	@Column(name = "content")
	private String content;

	/** 推送标题 */
	@Column(name = "title")
	private String title;

	/** 跳转类型(1:内部内容,2:内部模块,3:外部链接) */
	@Column(name = "jtype")
	private Integer jtype;

	/** 内部模块类型(1:主界面,2:用户中心,3:体验标,4:普通标,5:转让标,6:善行宝) */
	@Column(name = "jumpModule")
	private Integer jumpModule;

	/** 链接Url */
	@Column(name = "url")
	private String url;

	/** 发送类型(SY:所有,ZDR指定人) **/
	@Enumerated(EnumType.STRING)
	@Column(name = "sendType")
	private SendType sendType;

	/** 创建时间 **/
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createTime")
	private Date createTime;

	/** 极光推送属性实体 **/
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "appPush")
	private Set<AppPushAttr> appPushAttrs = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getCreateId() {
		return createId;
	}

	public void setCreateId(Integer createId) {
		this.createId = createId;
	}

	public Integer getSendCount() {
		return sendCount;
	}

	public void setSendCount(Integer sendCount) {
		this.sendCount = sendCount;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getJtype() {
		return jtype;
	}

	public void setJtype(Integer jtype) {
		this.jtype = jtype;
	}

	public Integer getJumpModule() {
		return jumpModule;
	}

	public void setJumpModule(Integer jumpModule) {
		this.jumpModule = jumpModule;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public SendType getSendType() {
		return sendType;
	}

	public void setSendType(SendType sendType) {
		this.sendType = sendType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Set<AppPushAttr> getAppPushAttrs() {
		return appPushAttrs;
	}

	public void setAppPushAttrs(Set<AppPushAttr> appPushAttrs) {
		this.appPushAttrs = appPushAttrs;
	}

	@Override
	public String toString() {
		return "AppPush [id=" + id + ", createId=" + createId + ", sendCount=" + sendCount + ", content=" + content + ", title=" + title + ", sendType=" + sendType + ", createTime=" + createTime + "]";
	}
}