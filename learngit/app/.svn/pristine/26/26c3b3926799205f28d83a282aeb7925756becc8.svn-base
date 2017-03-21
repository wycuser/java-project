package com.shanlin.p2p.app.vo.huichao;

import com.shanlin.framework.utils.MD5;

public class TranModel {
	private String outTradeNo;
	private String outName;
	private String inName;
	private double amount;
	private String remark;
	private String secureCode;

	public TranModel(String outTradeNo, double amount, String inName,
			String outName, String accountNumber, String privateKey,
			String remark) {
		this.outTradeNo = outTradeNo;
		this.remark = remark;
		this.amount = amount;
		this.inName = inName;
		this.outName = outName;
		this.secureCode = createSecureCode(accountNumber, privateKey);
	}

	public TranModel(String outTradeNo, double amount, String inName,
			String outName, String accountNumber, String key) {
		this.outTradeNo = outTradeNo;
		this.remark = ("交易流水号:" + getOutTradeNo());
		this.amount = amount;
		this.inName = inName;
		this.outName = outName;
		this.secureCode = createSecureCode(accountNumber, key);
	}

	private String createSecureCode(String accountNumber, String privateKey) {
		String bf = accountNumber + getOutTradeNo() + getOutName()
				+ getInName() + getAmount() + getRemark() + privateKey;
		return MD5.getMD5ofStrBM(bf, "utf-8");
	}

	public String createXmlTran() {
		StringBuffer buffer = new StringBuffer("<tran>");
		buffer.append("<outTradeNo>" + getOutTradeNo() + "</outTradeNo>");
		buffer.append("<outName>" + getOutName() + "</outName>");
		buffer.append("<inName>" + getInName() + "</inName>");
		buffer.append("<amount>" + getAmount() + "</amount>");
		buffer.append("<remark>" + getRemark() + "</remark>");
		buffer.append("<secureCode>" + getSecureCode() + "</secureCode>");
		buffer.append("</tran>");
		return buffer.toString();
	}

	public String getOutTradeNo() {
		return this.outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getOutName() {
		return this.outName;
	}

	public void setOutName(String outName) {
		this.outName = outName;
	}

	public String getInName() {
		return this.inName;
	}

	public void setInName(String inName) {
		this.inName = inName;
	}

	public double getAmount() {
		return this.amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSecureCode() {
		return this.secureCode;
	}
}
