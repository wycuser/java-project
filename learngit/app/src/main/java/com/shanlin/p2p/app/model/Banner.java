package com.shanlin.p2p.app.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shanlin.framework.utils.FileStoreUtil;

/**
 * 轮播图
 * @author zheng xin
 * @createDate 2015年1月23日
 */
@Entity
@Table(name="T5016", schema="S50")
public class Banner implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name="F01")
	private Long id;
	
	/** 排序值*/
	@Column(name="F02")
	private int sortIndex;
	
	/** 广告图片标题*/
	@Column(name="F03")
	private String title;
	
	/** 图片链接*/
	@Column(name="F04")
	private String url;
	
	/** 图片编码*/
	@Column(name="F05")
	private String pictureCode;
	
	/** 创建者,参考T7011.F01*/
	@Column(name="F06")
	private Long createrId;
	
	/** 上架时间*/
	@Column(name="F07")
	@Temporal(TemporalType.TIMESTAMP)
	private Date showTime;
	
	/** 下架时间*/
	@Column(name="F08")
	@Temporal(TemporalType.TIMESTAMP)
	private Date unShowTime;
	
	/** 创建时间*/
	@Column(name="F09")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	
	/** 最后修改时间*/
	@Column(name="F10")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdateTime;
	
	/** 类型 0 web 1 app*/
	@Column(name="type")
	private int type;
	
	public String getPicturePath(){
		return FileStoreUtil.getFileUrlPathByCode(this.getPictureCode());
	}
	
	@JsonIgnore
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	@JsonIgnore
	public int getSortIndex() {
		return sortIndex;
	}

	public void setSortIndex(int sortIndex) {
		this.sortIndex = sortIndex;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@JsonIgnore
	public String getPictureCode() {
		return pictureCode;
	}

	public void setPictureCode(String pictureCode) {
		this.pictureCode = pictureCode;
	}

	@JsonIgnore
	public Long getCreaterId() {
		return createrId;
	}

	public void setCreaterId(Long createrId) {
		this.createrId = createrId;
	}

	@JsonIgnore
	public Date getShowTime() {
		return showTime;
	}

	public void setShowTime(Date showTime) {
		this.showTime = showTime;
	}

	@JsonIgnore
	public Date getUnShowTime() {
		return unShowTime;
	}

	public void setUnShowTime(Date unShowTime) {
		this.unShowTime = unShowTime;
	}

	@JsonIgnore
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@JsonIgnore
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	@JsonIgnore
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Banner [id=" + id + ", sortIndex=" + sortIndex + ", title=" + title + ", url=" + url + ", pictureCode=" + pictureCode + ", createrId=" + createrId
				+ ", showTime=" + showTime + ", unShowTime=" + unShowTime + ", createTime=" + createTime + ", lastUpdateTime=" + lastUpdateTime + ", type="
				+ type + "]";
	}

}
