package com.shanlin.p2p.app.model.enums;

import java.util.HashMap;
import java.util.Map;

public enum OrdersType {
	/**
	 * 充值
	 */
	CHARGE(10001, "充值"),
	/**
	 * 提现
	 */
	WITHDRAW(10002, "提现"),
	/**
	 * 保证金
	 */
	BOND(10003, "保证金"),
	/**
	 * 逾期垫付
	 */
	ADVANCE(10004, "逾期垫付"),
	/**
	 * 散标投标
	 */
	BID(20001, "散标投标"),
	/**
	 * 散标投标撤销
	 */
	BID_CANCEL(20002, "散标投标撤销"),
	/**
	 * 散标放款
	 */
	BID_CONFIRM(20003, "散标放款"),
	/**
	 * 散标还款
	 */
	BID_REPAYMENT(20004, "散标还款"),
	/**
	 * 散标债权转让
	 */
	BID_EXCHANGE(20005, "散标债权转让"),
	/**
     * 体验金投标
     */
	BID_EXPERIENCE(20006, "体验金投标"),
	/**
     * 体验金放款
     */
    BID_EXPERIENCE_CONFIRM(20007, "体验金放款"),
    /**
     * 体验金还款
     */
    BID_EXPERIENCE_REPAYMENT(20008, "体验金还款"),
	/**
	 * 购买优选理财
	 */
	FINANCIAL_PURCHASE(30001, "购买优选理财"),
	/**
	 * 优选理财还款
	 */
	FINANCIAL_REPAYMENT(30002, "优选理财还款"),
	/**
	 * 优选理财放款
	 */
	FINANCIAL_LOAN(30003, "优选理财放款"),
	/**
	 * 订单金额冻结
	 */
	FREEZE(40001, "订单金额冻结"),
	/**
	 * 订单金额解冻
	 */
	UNFREEZE(40002, "订单金额解冻"),
	/**
	 * 转账订单
	 */
	TRANSFER(50001, "转账订单");

	int orderType;
	String chineseName;

	protected static Map<Integer, String> maps = new HashMap<Integer, String>();
	static {
		for (OrdersType orderType : values()) {
			maps.put(orderType.orderType, orderType.chineseName);
		}
	}

	private OrdersType(int orderType, String chineseName) {
		this.orderType = orderType;
		this.chineseName = chineseName;
	}

	public int orderType() {
		return orderType;
	}

	public String getChineseName() {
		return chineseName;
	}

	public static String getTypeName(int typeId) {
		return maps.get(typeId);
	}
}
