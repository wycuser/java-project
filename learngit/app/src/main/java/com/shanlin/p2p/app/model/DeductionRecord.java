package com.shanlin.p2p.app.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "deduction_record", schema = "S10")
public class DeductionRecord implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Long id;
	
	private Long userId;
	
	private String userName;
	
	private Long bid;
	
	private Long tzid;
	
	private Long childId;
	
	/** 状态 1.未使用 2.已冻结 3.已使用 4.已过期 5.未激活 */
	private int state;
	
	/** 发放类型 1.手动发放 2.注册 3.推广,4.抽奖*/
	private int source;
	
	/** 红本类型 1.投资抵扣券 */
	private int type;
	
	/** 是否用于活动 1.是 2.否 */
	private int useActivity;
	
	/** 抵扣券金额 */
	private BigDecimal amount;
	
	/** 开始时间 */
	private Date startTime;
	
	/** 结束时间 */
	private Date endTime;
	
	/** 投资下限 */
	private BigDecimal ranges;
	
	/** 抵扣券规则内容 */
	private String content;
	
	/** 创建时间 */
	private Date createTime;
	
	private Date updateTime;

	@JsonIgnore
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getBid() {
		return bid;
	}

	public void setBid(Long bid) {
		this.bid = bid;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getSource() {
		return source;
	}

	public void setSource(int source) {
		this.source = source;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	@JsonFormat(pattern="yyyy-MM-dd", timezone = "GMT+8")
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@JsonFormat(pattern="yyyy-MM-dd", timezone = "GMT+8")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public BigDecimal getRanges() {
		return ranges;
	}

	public void setRanges(BigDecimal ranges) {
		this.ranges = ranges;
	}

	@JsonIgnore
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getTzid() {
		return tzid;
	}

	public void setTzid(Long tzid) {
		this.tzid = tzid;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getChildId() {
		return childId;
	}

	public void setChildId(Long childId) {
		this.childId = childId;
	}

	public Map<String, Object> toMap(){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", this.id);
		map.put("userId", this.userId);
		map.put("userName", this.userName);
		map.put("bid", this.bid);
		map.put("tzid", this.tzid);
		map.put("state", this.state);
		map.put("source", this.source);
		map.put("type", this.type);
		map.put("amount", this.amount);
		map.put("ranges", this.ranges);
		map.put("content", this.content);
		map.put("startTime", this.startTime);
		map.put("endTime", this.endTime);
		return map;
	}
	
	public String getStateName(){
		if(this.state==1)
			return "未使用";
		if(this.state==2)
			return "已冻结";
		if(this.state==3)
			return "已使用";
		if(this.state==4)
			return "已过期";
		if(this.state==5)
			return "未激活";
		return "";
	}
	
	/**
	 * 1.手动发放 2.注册 3.推广
	 * @return
	 */
	public String getSourceName(){
		if(this.source==1)
			return "手动发放";
		if(this.source==2)
			return "注册";
		if(this.source==3){
			if(this.state==5){
				return "推广(未激活)";
			}
			return "推广";
		}
		if(this.source==4){
			return "抽奖";
		}
		return "";
	}
	
	/**
	 * 红本类型 1.投资红包 2.现金红包 
	 * @return
	 */
	public String getTypeName(){
		if(this.type==1)
			return "投资红包";
		if(this.type==2)
			return "现金红包";
		return "";
	}

	public int getUseActivity() {
		return useActivity;
	}

	public void setUseActivity(int useActivity) {
		this.useActivity = useActivity;
	}
}