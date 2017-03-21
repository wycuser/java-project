package com.shanlin.p2p.app.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 债权转让记录(T6263)
 * @author yangjh
 * @createDate 2015年4月27日
 */
@Entity
@Table(name="T6263", schema="S62")
public class CreditAssignmentStatistics  implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "F01")
	private Long id;
	
	/** 债权转让盈亏*/
	@Column(name = "F02")
	private BigDecimal zrProfit;
	
	/** 成功转入金额*/
	@Column(name = "F03")
	private BigDecimal intoPrice;
	
	/** 债权转入盈亏 */
	@Column(name = "F04")
	private BigDecimal intoProfit;
	
	/**已转入债权笔数*/
	@Column(name = "F05")
	private int intoCount;
	
	/** 成功转出金额*/
	@Column(name = "F06")
	private BigDecimal outPrice;
	
	/**债权转出盈亏*/
	@Column(name = "F07")
	private BigDecimal outProfit;
	
	/**已转出债权笔数*/
	@Column(name = "F08")
	private int outCount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getZrProfit() {
		return zrProfit;
	}

	public void setZrProfit(BigDecimal zrProfit) {
		this.zrProfit = zrProfit;
	}

	public BigDecimal getIntoPrice() {
		return intoPrice;
	}

	public void setIntoPrice(BigDecimal intoPrice) {
		this.intoPrice = intoPrice;
	}

	public BigDecimal getIntoProfit() {
		return intoProfit;
	}

	public void setIntoProfit(BigDecimal intoProfit) {
		this.intoProfit = intoProfit;
	}

	public int getIntoCount() {
		return intoCount;
	}

	public void setIntoCount(int intoCount) {
		this.intoCount = intoCount;
	}

	public BigDecimal getOutPrice() {
		return outPrice;
	}

	public void setOutPrice(BigDecimal outPrice) {
		this.outPrice = outPrice;
	}

	public BigDecimal getOutProfit() {
		return outProfit;
	}

	public void setOutProfit(BigDecimal outProfit) {
		this.outProfit = outProfit;
	}

	public int getOutCount() {
		return outCount;
	}

	public void setOutCount(int outCount) {
		this.outCount = outCount;
	}
	
	
}
