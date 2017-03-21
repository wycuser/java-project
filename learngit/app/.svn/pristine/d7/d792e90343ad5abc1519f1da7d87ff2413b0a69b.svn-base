package com.shanlin.p2p.app.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shanlin.framework.utils.Formater;
import com.shanlin.p2p.app.constant.FeeCode;

public class SimpleFundFlowVO {
	
	public Long id;
	
	private Integer feeCode;
	
	private Date createTime;
	
	/** 收入*/
	private BigDecimal income;
	
	/** 支出*/
	private BigDecimal expenditure;

	@JsonIgnore
	public Integer getFeeCode() {
		return feeCode;
	}

	public void setFeeCode(Integer feeCode) {
		this.feeCode = feeCode;
	}
	
	public String getType(){
		if(feeCode != null)
			return FeeCode.getChineseName(feeCode);
		else
			return null;
	}
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm", timezone = "GMT+8")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public String getAmount(){
		if(income != null && income.compareTo(BigDecimal.ZERO) > 0){
			return "+" + Formater.formatAmount(income);
		}else if (expenditure != null){
			return "-" + Formater.formatAmount(expenditure);
		}else{
			return null;
		}
	}
	
	@JsonIgnore
	public BigDecimal getIncome() {
		return income;
	}

	public void setIncome(BigDecimal income) {
		this.income = income;
	}

	@JsonIgnore
	public BigDecimal getExpenditure() {
		return expenditure;
	}

	public void setExpenditure(BigDecimal expenditure) {
		this.expenditure = expenditure;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
