package com.shanlin.framework.utils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.shanlin.p2p.app.constant.FeeCode;
import com.shanlin.p2p.app.model.Bid;
import com.shanlin.p2p.app.model.BidExtend;
import com.shanlin.p2p.app.model.BidRepaymentRecord;
import com.shanlin.p2p.app.model.InvestRecord;
import com.shanlin.p2p.app.model.enums.BidPayMode;
import com.shanlin.p2p.app.model.enums.Judge;

public class BdUtil {

	protected static final int DECIMAL_SCALE = 9;
	
	/**
	 * 日期增加月份跟天数
	 * 
	 * @param date
	 *            时间
	 * @param month
	 *            月份
	 * @param day
	 *            天数
	 * @return
	 */
	public static Timestamp getTime(Timestamp date, int month, int day) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, month);
		calendar.add(Calendar.DATE, day);
		return Timestamp.valueOf(dateFormat.format(calendar.getTime()));
	}
	
	/**
	 * 计算收益(已废弃)
	 * 
	 * @param money
	 *            金额
	 * @param rate
	 *            利率
	 * @param qx
	 *            期限
	 * @param flag
	 *            true代表月份 false代表天数
	 * @return
	 */
	@Deprecated
	public static BigDecimal calcIt(BigDecimal money, BigDecimal rate, int qx, boolean flag) {
		BigDecimal total = money.multiply(rate).multiply(new BigDecimal(qx));
		if (flag) {
			return total.divide(new BigDecimal("12"), 2, BigDecimal.ROUND_HALF_EVEN);
		}
		return total.divide(new BigDecimal("360"), 2, BigDecimal.ROUND_HALF_EVEN);
	}

	// 计算收益
	public static BigDecimal calcSy(Date currentDate, Bid t6230, BidExtend t6231, InvestRecord[] t6250s) throws Throwable {
		final Date interestDate = new Date(currentDate.getTime() + t6230.getValueDate() * 86400000);
		final Date endDate = new Date(DateHelper.rollMonth(interestDate.getTime(), t6230.getLoanPeriod()));
		BigDecimal result = new BigDecimal(0.00);

		ArrayList<BidRepaymentRecord> list6252s = null;
		if (t6230.getPayMode() == BidPayMode.ZRY) {// 自然月
			switch (t6230.getMode()) {
			case DEBX: {
				for (InvestRecord t6250 : t6250s) {
					list6252s = calZRY_DEBX(t6230, t6250, interestDate, endDate);
					result = result.add(calcSingle(list6252s));
				}
				break;
			}
			case MYFX: {
				for (InvestRecord t6250 : t6250s) {
					list6252s = calZRY_MYFX(t6230, t6250, interestDate, endDate);
					result = result.add(calcSingle(list6252s));
				}
				break;
			}
			case YCFQ: {
				for (InvestRecord t6250 : t6250s) {
					list6252s = calYCFQ(t6230, t6231, t6250, interestDate, endDate);
					result = result.add(calcSingle(list6252s));
				}
				break;
			}
			case DEBJ: {
				for (InvestRecord t6250 : t6250s) {
					list6252s = calZRY_DEBJ(t6230, t6250, interestDate, endDate);
					result = result.add(calcSingle(list6252s));
				}
				break;
			}
			default:
				throw new Exception("不支持的还款方式");
			}
		} else if (t6230.getPayMode() == BidPayMode.GDR) {// 固定付息日
			switch (t6230.getMode()) {
			case DEBX: {
				for (InvestRecord t6250 : t6250s) {
					list6252s = calGDR_DEBX(t6230, t6250, interestDate, endDate);
					result = result.add(calcSingle(list6252s));
				}
				break;
			}
			case MYFX: {
				for (InvestRecord t6250 : t6250s) {
					list6252s = calGDR_MYFX(t6230, t6250, interestDate, endDate);
					result = result.add(calcSingle(list6252s));
				}
				break;
			}
			case YCFQ: {
				for (InvestRecord t6250 : t6250s) {
					list6252s = calYCFQ(t6230, t6231, t6250, interestDate, endDate);
					result = result.add(calcSingle(list6252s));
				}
				break;
			}
			case DEBJ: {
				for (InvestRecord t6250 : t6250s) {
					list6252s = calGDR_DEBJ(t6230, t6250, interestDate, endDate);
					result = result.add(calcSingle(list6252s));
				}
				break;
			}
			default:
				throw new Exception("不支持的还款方式");
			}
		} else {
			throw new Exception("不支持的付息方式");
		}

		return result;
	}

	// 计算单条投标记录收益(根据标还款记录集合)
	private static BigDecimal calcSingle(ArrayList<BidRepaymentRecord> list6252s) {
		// 计算收益
		BigDecimal result = new BigDecimal(0.00);
		if (list6252s != null) {
			for (BidRepaymentRecord t6252 : list6252s) {

				// 只计算利息
				if (t6252.getFeeCode() == FeeCode.TZ_LX) {
					result = result.add(t6252.getAmount());
				}
			}
		}
		return result;
	}

	// 计算等额本息(自然月)
	private static ArrayList<BidRepaymentRecord> calZRY_DEBX(Bid t6230, InvestRecord t6250, Date interestDate, Date endDate) throws Throwable {
		ArrayList<BidRepaymentRecord> t6252s = new ArrayList<>();
		BigDecimal monthRate = t6230.getRate().setScale(DECIMAL_SCALE, BigDecimal.ROUND_HALF_UP).divide(new BigDecimal(12), DECIMAL_SCALE, BigDecimal.ROUND_HALF_UP);
		BigDecimal remainTotal = t6250.getCreditPrice();

		// 计算月还本息(月还本息=借款金额*月利率*((月利率+1)的借款周期次方)/(((月利率+1)的借款周期次方)-1))
		BigDecimal monthPayTotal = debx(remainTotal, monthRate, t6230.getLoanPeriod());
		for (int term = 1; term <= t6230.getLoanPeriod(); term++) {
			@SuppressWarnings("unused")
			Date date = new Date(DateHelper.rollMonth(interestDate.getTime(), term));
			BigDecimal interest = remainTotal.multiply(monthRate).setScale(2, BigDecimal.ROUND_HALF_UP);
			if (1 == term) {
			}
			{
				// 利息
				BidRepaymentRecord t6252 = new BidRepaymentRecord();

				t6252.setFeeCode(FeeCode.TZ_LX);
				if (t6230.getLoanPeriod() == term) {
					t6252.setAmount(monthPayTotal.subtract(remainTotal));
				} else {
					t6252.setAmount(interest);
				}
				t6252s.add(t6252);
			}
			{
				// 本金
				BidRepaymentRecord t6252 = new BidRepaymentRecord();

				t6252.setFeeCode(FeeCode.TZ_BJ);
				if (t6230.getLoanPeriod() == term) {
					t6252.setAmount(remainTotal);
				} else {
					t6252.setAmount(monthPayTotal.subtract(interest));
				}
				remainTotal = remainTotal.subtract(t6252.getAmount());
				t6252s.add(t6252);
			}
		}
		return t6252s;
	}

	// 每月付息到期还本(自然月)
	private static ArrayList<BidRepaymentRecord> calZRY_MYFX(Bid t6230, InvestRecord t6250, Date interestDate, Date endDate) throws Throwable {
		ArrayList<BidRepaymentRecord> t6252s = new ArrayList<>();
		BigDecimal monthes = new BigDecimal(12);
		for (int term = 1; term <= t6230.getLoanPeriod(); term++) {
			@SuppressWarnings("unused")
			Date date = new Date(DateHelper.rollMonth(interestDate.getTime(), term));
			if (1 == term) {
			}
			{
				// 利息
				BidRepaymentRecord t6252 = new BidRepaymentRecord();

				t6252.setFeeCode(FeeCode.TZ_LX);
				t6252.setAmount(t6250.getCreditPrice().setScale(9, BigDecimal.ROUND_HALF_UP));
				t6252.setAmount(t6252.getAmount().multiply(t6230.getRate()).divide(monthes, DECIMAL_SCALE, BigDecimal.ROUND_HALF_UP));
				t6252s.add(t6252);
			}
			if (term == t6230.getLoanPeriod()) {
				// 本金
				BidRepaymentRecord t6252 = new BidRepaymentRecord();

				t6252.setFeeCode(FeeCode.TZ_BJ);
				t6252.setAmount(t6250.getCreditPrice());
				t6252s.add(t6252);
			}
		}
		return t6252s;
	}

	// 本息一次性付清(自然月)
	private static ArrayList<BidRepaymentRecord> calYCFQ(Bid t6230, BidExtend t6231, InvestRecord t6250, Date interestDate, Date endDate) throws Throwable {
		int days = t6231.getLoanPeriod(); // 借款天数

		// 分支逻辑:按天计算还款计划
		if (Judge.S == t6231.getIsByDay()) {
			if (days <= 0) {
				throw new Exception("按天借款，其天数必须大于0");
			}
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(endDate.getTime());
			cal.add(Calendar.DATE, days);
			endDate = new java.sql.Date(cal.getTimeInMillis());
			return calYCFQ_BY_DAYS(t6230, t6250, interestDate, endDate, days);
		}

		ArrayList<BidRepaymentRecord> t6252s = new ArrayList<>();
		{
			// 利息
			BidRepaymentRecord t6252 = new BidRepaymentRecord();

			t6252.setFeeCode(FeeCode.TZ_LX);
			t6252.setAmount(t6250.getCreditPrice().setScale(9, BigDecimal.ROUND_HALF_UP));
			t6252.setAmount(t6252.getAmount().multiply(t6230.getRate()).multiply(new BigDecimal(t6230.getLoanPeriod())).divide(new BigDecimal(12), DECIMAL_SCALE, BigDecimal.ROUND_HALF_UP));
			t6252s.add(t6252);
		}
		{
			// 本金
			BidRepaymentRecord t6252 = new BidRepaymentRecord();

			t6252.setFeeCode(FeeCode.TZ_BJ);
			t6252.setAmount(t6250.getCreditPrice());
			t6252s.add(t6252);
		}
		return t6252s;
	}

	// 本息一次性付清(按天)
	private static ArrayList<BidRepaymentRecord> calYCFQ_BY_DAYS(Bid t6230, InvestRecord t6250, Date interestDate, Date endDate, int days) throws Throwable {
		ArrayList<BidRepaymentRecord> t6252s = new ArrayList<>();
		{
			// 利息
			BidRepaymentRecord t6252 = new BidRepaymentRecord();

			t6252.setFeeCode(FeeCode.TZ_LX);
			t6252.setAmount(t6250.getCreditPrice().setScale(9, BigDecimal.ROUND_HALF_UP));
			t6252.setAmount(t6252.getAmount().multiply(t6230.getRate()).multiply(new BigDecimal(days)).divide(new BigDecimal(360), DECIMAL_SCALE, BigDecimal.ROUND_HALF_UP));
			t6252s.add(t6252);
		}
		{
			// 本金
			BidRepaymentRecord t6252 = new BidRepaymentRecord();
			t6252.setFeeCode(FeeCode.TZ_BJ);
			t6252.setAmount(t6250.getCreditPrice());
			t6252s.add(t6252);
		}
		return t6252s;
	}

	// 等额本金(自然月)
	private static ArrayList<BidRepaymentRecord> calZRY_DEBJ(Bid t6230, InvestRecord t6250, Date interestDate, Date endDate) throws Throwable {
		{
			Calendar c = Calendar.getInstance();
			c.setTime(interestDate);
			c.add(Calendar.MONTH, t6230.getLoanPeriod());
			endDate = new Date(c.getTimeInMillis());
		}
		// 借款金额
		BigDecimal x = t6250.getCreditPrice();
		// 月利率
		BigDecimal y = t6230.getRate().divide(new BigDecimal(12), DECIMAL_SCALE, BigDecimal.ROUND_HALF_UP);
		// 借款期限
		int terms = t6230.getLoanPeriod();
		// 月还本金
		BigDecimal monthAmount = x.divide(new BigDecimal(terms), 2, BigDecimal.ROUND_HALF_UP);
		// 还本金总额
		BigDecimal total = BigDecimal.ZERO;
		ArrayList<BidRepaymentRecord> t6252s = new ArrayList<>();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(interestDate);
		for (int i = 1; i <= terms; i++) {
			calendar.add(Calendar.MONTH, 1);
			BigDecimal interest = BigDecimal.ZERO;
			if (1 == i) {
			}
			if (i == terms) {
				BigDecimal bj = x.subtract(total).setScale(2, BigDecimal.ROUND_HALF_UP);
				interest = bj.multiply(y).setScale(2, BigDecimal.ROUND_HALF_UP);
				{ // 本金
					BidRepaymentRecord t6252 = new BidRepaymentRecord();

					t6252.setFeeCode(FeeCode.TZ_BJ);
					t6252.setAmount(bj);
					t6252s.add(t6252);
				}
				{ // 利息
					BidRepaymentRecord t6252 = new BidRepaymentRecord();

					t6252.setFeeCode(FeeCode.TZ_LX);
					t6252.setAmount(interest);
					t6252s.add(t6252);
				}
				continue;
			}
			interest = x.subtract(total).multiply(y).setScale(2, BigDecimal.ROUND_HALF_UP);
			{ // 本金
				BidRepaymentRecord t6252 = new BidRepaymentRecord();

				t6252.setFeeCode(FeeCode.TZ_BJ);
				t6252.setAmount(monthAmount);
				t6252s.add(t6252);
			}
			{ // 利息
				BidRepaymentRecord t6252 = new BidRepaymentRecord();

				t6252.setFeeCode(FeeCode.TZ_LX);
				t6252.setAmount(interest);
				t6252s.add(t6252);
			}
			total = total.add(monthAmount);
		}
		return t6252s;
	}

	// 等额本息(固定日)
	private static ArrayList<BidRepaymentRecord> calGDR_DEBX(Bid t6230, InvestRecord t6250, Date interestDate, Date endDate) throws Throwable {
		Calendar calendar = Calendar.getInstance();
		final int inserestStartDay;// 起息日
		{
			calendar.setTime(interestDate);
			inserestStartDay = calendar.get(Calendar.DAY_OF_MONTH);
		}
		{
			Calendar c = Calendar.getInstance();
			c.setTime(interestDate);
			c.add(Calendar.MONTH, t6230.getLoanPeriod());
			endDate = new Date(c.getTimeInMillis());
		}
		if (inserestStartDay == t6230.getPayDay()) {
			// 计算等额本息(自然月)
			return calZRY_DEBX(t6230, t6250, interestDate, endDate);
		}
		if (t6230.getPayDay() > 28) {
			throw new Exception("固定付息日不能大于28");
		}
		// 月利率
		BigDecimal x = t6230.getRate().divide(new BigDecimal(12), DECIMAL_SCALE, BigDecimal.ROUND_HALF_UP);
		// 借款金额
		BigDecimal y = t6250.getCreditPrice();
		// 日利率
		BigDecimal z = t6230.getRate().divide(new BigDecimal(360), DECIMAL_SCALE, BigDecimal.ROUND_HALF_UP);
		// 借款期限
		int n = t6230.getLoanPeriod();
		// 首期天数
		int f = 0;
		// 首期付款日
		Calendar ca = Calendar.getInstance();
		if (inserestStartDay > t6230.getPayDay()) {
			calendar.setTimeInMillis(System.currentTimeMillis());
			calendar.add(Calendar.MONTH, 1);
			calendar.set(Calendar.DAY_OF_MONTH, t6230.getPayDay());
			ca.setTimeInMillis(calendar.getTimeInMillis());
		} else {
			calendar.setTimeInMillis(System.currentTimeMillis());
			calendar.set(Calendar.DAY_OF_MONTH, t6230.getPayDay());
			ca.setTimeInMillis(calendar.getTimeInMillis());
		}
		f = (int) Math.floor((calendar.getTimeInMillis() - interestDate.getTime()) / DateHelper.DAY_IN_MILLISECONDS);
		// 尾期天数
		calendar.setTime(endDate);
		if (calendar.get(Calendar.DAY_OF_MONTH) > t6230.getPayDay()) {
			calendar.set(Calendar.DAY_OF_MONTH, t6230.getPayDay());
		} else {
			calendar.add(Calendar.MONTH, -1);
			calendar.set(Calendar.DAY_OF_MONTH, t6230.getPayDay());
		}
		int l = (int) ((endDate.getTime() - calendar.getTimeInMillis()) / (24 * 3600 * 1000));
		int terms = t6230.getLoanPeriod() + 1;
		ArrayList<BidRepaymentRecord> t6252s = new ArrayList<>();
		BigDecimal total = BigDecimal.ZERO;
		// 月供
		BigDecimal amount = x.multiply(y).multiply(x.add(new BigDecimal(1)).pow(n)).divide(x.add(new BigDecimal(1)).pow(n).subtract(new BigDecimal(1)), DECIMAL_SCALE, BigDecimal.ROUND_HALF_UP);
		for (int i = 1; i <= terms; i++) {
			// 利息
			BigDecimal interest = BigDecimal.ZERO;
			// 本金
			BigDecimal bj = BigDecimal.ZERO;
			if (1 == i) {
				BigDecimal fAmount = amount.multiply(new BigDecimal(f)).divide(new BigDecimal(f).add(new BigDecimal(l)), DECIMAL_SCALE, BigDecimal.ROUND_HALF_UP);
				interest = y.multiply(z).multiply(new BigDecimal(f)).setScale(2, BigDecimal.ROUND_HALF_UP);
				bj = fAmount.subtract(interest).setScale(2, BigDecimal.ROUND_HALF_UP);
				total = total.add(bj);
				{ // 本金
					BidRepaymentRecord t6252 = new BidRepaymentRecord();

					t6252.setFeeCode(FeeCode.TZ_BJ);
					t6252.setAmount(bj);
					t6252s.add(t6252);
				}
				{ // 利息
					BidRepaymentRecord t6252 = new BidRepaymentRecord();

					t6252.setFeeCode(FeeCode.TZ_LX);
					t6252.setAmount(interest);
					t6252s.add(t6252);
				}
				ca.add(Calendar.MONTH, 1);
				continue;
			} else if (i == terms) {
				BigDecimal lAmount = amount.multiply(new BigDecimal(l)).divide(new BigDecimal(f).add(new BigDecimal(l)), DECIMAL_SCALE, BigDecimal.ROUND_HALF_UP);
				bj = y.subtract(total).setScale(2, BigDecimal.ROUND_HALF_UP);
				interest = lAmount.subtract(bj).setScale(2, BigDecimal.ROUND_HALF_UP);
				total = total.add(bj);
				{ // 本金
					BidRepaymentRecord t6252 = new BidRepaymentRecord();

					t6252.setFeeCode(FeeCode.TZ_BJ);
					t6252.setAmount(bj);
					t6252s.add(t6252);
				}
				{ // 利息
					BidRepaymentRecord t6252 = new BidRepaymentRecord();

					t6252.setFeeCode(FeeCode.TZ_LX);
					t6252.setAmount(interest);
					t6252s.add(t6252);
				}

				continue;
			}
			interest = y.subtract(total).multiply(x).setScale(2, BigDecimal.ROUND_HALF_UP);
			bj = amount.subtract(interest).setScale(2, BigDecimal.ROUND_HALF_UP);
			total = total.add(bj);
			{ // 本金
				BidRepaymentRecord t6252 = new BidRepaymentRecord();

				t6252.setFeeCode(FeeCode.TZ_BJ);
				t6252.setAmount(bj);
				t6252s.add(t6252);
			}
			{ // 利息
				BidRepaymentRecord t6252 = new BidRepaymentRecord();

				t6252.setFeeCode(FeeCode.TZ_LX);
				t6252.setAmount(interest);
				t6252s.add(t6252);
			}
			ca.add(Calendar.MONTH, 1);
		}
		return t6252s;
	}

	// 每月付息到期还本(固定日)
	private static ArrayList<BidRepaymentRecord> calGDR_MYFX(Bid t6230, InvestRecord t6250, final Date interestDate, final Date endDate) throws Throwable {
		Calendar calendar = Calendar.getInstance();
		final int inserestStartDay;// 起息日
		{
			calendar.setTime(interestDate);
			inserestStartDay = calendar.get(Calendar.DAY_OF_MONTH);
		}
		if (t6230.getPayDay() > 28) {
			throw new Exception("固定付息日不能大于28");
		}
		if (inserestStartDay == t6230.getPayDay()) {
			// 每月付息到期还本(自然月)
			return calZRY_MYFX(t6230, t6250, interestDate, endDate);
		}
		final int term = t6230.getLoanPeriod() + 1;// 总期数
		BigDecimal yearDays = new BigDecimal(360);
		Date prePayDate = interestDate;// 上次付息日
		Date payDate;
		if (inserestStartDay < t6230.getPayDay()) {
			calendar.setTime(interestDate);
			calendar.set(Calendar.DAY_OF_MONTH, t6230.getPayDay());
			payDate = new Date(calendar.getTimeInMillis());
		} else {
			calendar.setTime(interestDate);
			calendar.set(Calendar.DAY_OF_MONTH, t6230.getPayDay());
			payDate = new Date(DateHelper.rollMonth(calendar.getTimeInMillis(), 1));
		}

		// 生成还款记录
		ArrayList<BidRepaymentRecord> t6252s = new ArrayList<>();
		for (int i = 1; i <= term; i++) {
			if (i == term) {
				payDate = endDate;
			}
			int days = (int) Math.ceil((payDate.getTime() - prePayDate.getTime()) / DateHelper.DAY_IN_MILLISECONDS);
			if (1 == term) {
			}
			{
				// 利息
				BidRepaymentRecord t6252 = new BidRepaymentRecord();

				t6252.setFeeCode(FeeCode.TZ_LX);
				t6252.setAmount(t6250.getCreditPrice().setScale(DECIMAL_SCALE, BigDecimal.ROUND_HALF_UP));
				t6252.setAmount(t6252.getAmount().multiply(t6230.getRate()).multiply(new BigDecimal(days)).divide(yearDays, DECIMAL_SCALE, BigDecimal.ROUND_HALF_UP));
				t6252s.add(t6252);
			}
			if (i == term) {
				// 本金
				BidRepaymentRecord t6252 = new BidRepaymentRecord();

				t6252.setFeeCode(FeeCode.TZ_BJ);
				t6252.setAmount(t6250.getCreditPrice());
				t6252s.add(t6252);
			}
			prePayDate = payDate;
			payDate = new Date(DateHelper.rollMonth(payDate.getTime(), 1));
		}
		return t6252s;
	}

	// 等额本金(固定日)
	private static ArrayList<BidRepaymentRecord> calGDR_DEBJ(Bid t6230, InvestRecord t6250, Date interestDate, Date endDate) throws Throwable {
		Calendar calendar = Calendar.getInstance();
		final int inserestStartDay;// 起息日
		{
			calendar.setTime(interestDate);
			inserestStartDay = calendar.get(Calendar.DAY_OF_MONTH);
		}
		{
			Calendar c = Calendar.getInstance();
			c.setTime(interestDate);
			c.add(Calendar.MONTH, t6230.getLoanPeriod());
			endDate = new Date(c.getTimeInMillis());
		}
		if (inserestStartDay == t6230.getPayDay()) {
			// 等额本金(自然月)
			return calZRY_DEBJ(t6230, t6250, interestDate, endDate);
		}
		if (t6230.getPayDay() > 28) {
			throw new Exception("固定付息日不能大于28");
		}
		// 借款金额
		BigDecimal x = t6250.getCreditPrice();
		// 月利率
		BigDecimal y = t6230.getRate().divide(new BigDecimal(12), DECIMAL_SCALE, BigDecimal.ROUND_HALF_UP);
		// 借款期限
		int terms = t6230.getLoanPeriod() + 1;
		// 还款期限
		int n = t6230.getLoanPeriod();
		// 首期天数
		int f = 0;
		// 首期付款日
		Calendar ca = Calendar.getInstance();
		if (inserestStartDay > t6230.getPayDay()) {
			calendar.setTimeInMillis(System.currentTimeMillis());
			calendar.add(Calendar.MONTH, 1);
			calendar.set(Calendar.DAY_OF_MONTH, t6230.getPayDay());
			ca.setTimeInMillis(calendar.getTimeInMillis());
		} else {
			calendar.setTimeInMillis(System.currentTimeMillis());
			calendar.set(Calendar.DAY_OF_MONTH, t6230.getPayDay());
			ca.setTimeInMillis(calendar.getTimeInMillis());
		}

		f = (int) Math.floor((calendar.getTimeInMillis() - interestDate.getTime()) / DateHelper.DAY_IN_MILLISECONDS);

		// 尾期天数
		calendar.setTime(endDate);
		if (calendar.get(Calendar.DAY_OF_MONTH) > t6230.getPayDay()) {
			calendar.set(Calendar.DAY_OF_MONTH, t6230.getPayDay());
		} else {
			calendar.add(Calendar.MONTH, -1);
			calendar.set(Calendar.DAY_OF_MONTH, t6230.getPayDay());
		}
		int l = (int) ((endDate.getTime() - calendar.getTimeInMillis()) / (24 * 3600 * 1000));
		BigDecimal total = BigDecimal.ZERO;
		BigDecimal monthAmount = x.divide(new BigDecimal(n), 2, BigDecimal.ROUND_HALF_UP);
		ArrayList<BidRepaymentRecord> t6252s = new ArrayList<>();
		for (int i = 1; i <= terms; i++) {
			BigDecimal interest = BigDecimal.ZERO;
			BigDecimal bj = BigDecimal.ZERO;
			if (1 == i) {
				interest = x.multiply(y).multiply(new BigDecimal(f)).divide(new BigDecimal(f).add(new BigDecimal(l)), DECIMAL_SCALE, BigDecimal.ROUND_HALF_UP);
				bj = monthAmount.multiply(new BigDecimal(f)).divide(new BigDecimal(f).add(new BigDecimal(l)), 2, BigDecimal.ROUND_HALF_UP);
				total = total.add(bj);
				{ // 本金
					BidRepaymentRecord t6252 = new BidRepaymentRecord();

					t6252.setFeeCode(FeeCode.TZ_BJ);
					t6252.setAmount(bj);
					t6252s.add(t6252);
				}
				{ // 利息
					BidRepaymentRecord t6252 = new BidRepaymentRecord();

					t6252.setFeeCode(FeeCode.TZ_LX);
					t6252.setAmount(interest);
					t6252s.add(t6252);
				}
				continue;
			}
			if (i == terms) {
				bj = x.subtract(total).setScale(2, BigDecimal.ROUND_HALF_UP);
				interest = bj.multiply(y).multiply(new BigDecimal(l)).divide(new BigDecimal(f).add(new BigDecimal(l)), DECIMAL_SCALE, BigDecimal.ROUND_HALF_UP);
				{ // 本金
					BidRepaymentRecord t6252 = new BidRepaymentRecord();

					t6252.setFeeCode(FeeCode.TZ_BJ);
					t6252.setAmount(bj);
					t6252s.add(t6252);
				}
				{ // 利息
					BidRepaymentRecord t6252 = new BidRepaymentRecord();

					t6252.setFeeCode(FeeCode.TZ_LX);
					t6252.setAmount(interest);
					t6252s.add(t6252);
				}
				continue;
			}
			interest = x.subtract(total).multiply(y).setScale(2, BigDecimal.ROUND_HALF_UP);
			bj = monthAmount;
			total = total.add(bj);
			ca.add(Calendar.MONTH, 1);
			{ // 本金
				BidRepaymentRecord t6252 = new BidRepaymentRecord();

				t6252.setFeeCode(FeeCode.TZ_BJ);
				t6252.setAmount(bj);
				t6252s.add(t6252);
			}
			{ // 利息
				BidRepaymentRecord t6252 = new BidRepaymentRecord();

				t6252.setFeeCode(FeeCode.TZ_LX);
				t6252.setAmount(interest);
				t6252s.add(t6252);
			}
		}
		return t6252s;
	}

	// 计算公式
	private static BigDecimal debx(BigDecimal total, BigDecimal monthRate, int terms) {
		BigDecimal tmp = monthRate.add(new BigDecimal(1)).pow(terms);
		return total.multiply(monthRate).multiply(tmp).divide(tmp.subtract(new BigDecimal(1)), 2, BigDecimal.ROUND_HALF_UP);
	}
}