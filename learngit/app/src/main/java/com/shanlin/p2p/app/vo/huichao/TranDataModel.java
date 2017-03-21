package com.shanlin.p2p.app.vo.huichao;

import java.util.List;

public class TranDataModel {
	private String accountNumber;
	private String tradeNo;
	private String tradeType;
	private List<TranModel> tranList;

	public TranDataModel(String accountNumber, String tradeNo) {
		this(accountNumber, tradeNo, "trade");
	}

	public TranDataModel(String accountNumber, String tradeNo, String tradeType) {
		this.accountNumber = accountNumber;
		this.tradeNo = tradeNo;
		this.tradeType = tradeType;
	}

	public String getAccountNumber() {
		return this.accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getTradeNo() {
		return this.tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getTradeType() {
		return this.tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public List<TranModel> getTranList() {
		return this.tranList;
	}

	public void setTranList(List<TranModel> tranList) {
		this.tranList = tranList;
	}

	public String createTranXML() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<accountNumber>" + getAccountNumber()
				+ "</accountNumber>");
		buffer.append("<tradeNo>" + getAccountNumber() + "</tradeNo>");
		buffer.append("<tradeType>" + getTradeType() + "</tradeType>");
		buffer.append("<tranlist>");
		for (TranModel tranModel : getTranList()) {
			buffer.append(tranModel.createXmlTran());
		}
		buffer.append("</tranlist>");
		return buffer.toString();
	}
}
