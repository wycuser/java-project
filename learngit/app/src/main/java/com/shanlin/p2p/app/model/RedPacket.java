package com.shanlin.p2p.app.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "redpacket", schema = "S10")
public class RedPacket implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Long id;
	
	/** 发放类型 1.手动发放 2.注册 3.推广*/
	private int source;
	
	/** 红本类型 1.投资红包 2.现金红包 */
	private int type;
	
	/** 是否用于活动 1.是 2.否 */
	private int useActivity;
	
	/** 红包金额 */
	private BigDecimal amount = BigDecimal.ZERO;
	
	/** 有效天数 */
	private int effectiveDays;
	
	/** 投资下限 */
	private BigDecimal ranges = BigDecimal.ZERO;
	
	/** 状态 1.开启  2.关闭 */
	private int state;
	
	/** 创建时间 */
	private Timestamp createTime;
	
	/** 红包规则内容 */
	private String content;
	
	public RedPacket(){}
	
	/**
	 * @param source 发放类型 1.手动发放 2.注册
	 * @param type 红本类型 1.投资红包 2.现金红包
	 */
	public RedPacket(int source,int type){
		this.source=source;
		this.type=type;
		this.state=1;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public int getEffectiveDays() {
		return effectiveDays;
	}

	public void setEffectiveDays(int effectiveDays) {
		this.effectiveDays = effectiveDays;
	}

	public BigDecimal getRanges() {
		return ranges;
	}

	public void setRanges(BigDecimal ranges) {
		this.ranges = ranges;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public int getUseActivity() {
		return useActivity;
	}

	public void setUseActivity(int useActivity) {
		this.useActivity = useActivity;
	}

}