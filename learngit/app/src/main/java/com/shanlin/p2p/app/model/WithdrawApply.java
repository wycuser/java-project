package com.shanlin.p2p.app.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.shanlin.p2p.app.model.enums.WithdrawStatus;

@Entity
@Table(name = "T6130", schema = "S61")
public class WithdrawApply implements Serializable {

	private static final long serialVersionUID = 1L;

	/** Id */
	@Id
	@GeneratedValue
	@Column(name = "F01")
	private Long id;

	/** 用户ID */
	@Column(name = "F02")
	private Long userId;

	/** 银行 */
	@Column(name = "F03")
	private Long bankId;

	/** 提现金额 */
	@Column(name = "F04")
	private BigDecimal amount;

	/** 应收手续费 */
	@Column(name = "F06")
	private BigDecimal poundage;

	/** 实收手续费 */
	@Column(name = "F07")
	private BigDecimal realPoundage;

	/** 创建时间 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "F08")
	private Date createTime;

	@Enumerated(EnumType.STRING)
	@Column(name = "F09")
	private WithdrawStatus status;
	
	private String source;

	/** 银行名称 */
	private String bankName;

	/** 开户行 */
	private String subbranch;

	/** 银行卡号 */
	private String bankNum;
	
	/**用于提现的未投资额度**/
	private BigDecimal usLines;

	public BigDecimal getUsLines() {
		return usLines;
	}

	public void setUsLines(BigDecimal usLines) {
		this.usLines = usLines;
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

	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getPoundage() {
		return poundage;
	}

	public void setPoundage(BigDecimal poundage) {
		this.poundage = poundage;
	}

	public BigDecimal getRealPoundage() {
		return realPoundage;
	}

	public void setRealPoundage(BigDecimal realPoundage) {
		this.realPoundage = realPoundage;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public WithdrawStatus getStatus() {
		return status;
	}

	public void setStatus(WithdrawStatus status) {
		this.status = status;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getSubbranch() {
		return subbranch;
	}

	public void setSubbranch(String subbranch) {
		this.subbranch = subbranch;
	}

	public String getBankNum() {
		return bankNum;
	}

	public void setBankNum(String bankNum) {
		this.bankNum = bankNum;
	}
	
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Override
	public String toString() {
		return "WithdrawApply [id=" + id + ", userId=" + userId + ", bankId="
				+ bankId + ", amount=" + amount + ", poundage=" + poundage
				+ ", realPoundage=" + realPoundage + ", createTime="
				+ createTime + ", status=" + status + ", bankName=" + bankName
				+ ", subbranch=" + subbranch + ", bankNum=" + bankNum + "]";
	}

}
