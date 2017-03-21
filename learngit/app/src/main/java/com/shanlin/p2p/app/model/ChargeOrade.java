package com.shanlin.p2p.app.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T6502", schema = "S65")
public class ChargeOrade implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "F01")
	private Long id;

	/** 用户ID,参考T6110.F01 */
	@Column(name = "F02")
	private Long userId;

	/** 充值金额 */
	@Column(name = "F03")
	private BigDecimal amount;

	/** 应收手续费 */
	@Column(name = "F04")
	private BigDecimal poundage;

	/** 实收手续费 */
	@Column(name = "F05")
	private BigDecimal realPoundage;

	/** 银行卡号 */
	@Column(name = "F06")
	private String bankNum;

	/** 支付公司代号 */
	@Column(name = "F07")
	private int payNum;

	/** 流水单号,充值成功时记录 */
	@Column(name = "F08")
	private String orderNum;

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

	public String getBankNum() {
		return bankNum;
	}

	public void setBankNum(String bankNum) {
		this.bankNum = bankNum;
	}

	public int getPayNum() {
		return payNum;
	}

	public void setPayNum(int payNum) {
		this.payNum = payNum;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	@Override
	public String toString() {
		return "ChargeOrade [id=" + id + ", userId=" + userId + ", amount="
				+ amount + ", poundage=" + poundage + ", realPoundage="
				+ realPoundage + ", bankNum=" + bankNum + ", payNum=" + payNum
				+ ", orderNum=" + orderNum + "]";
	}
}
