package com.shanlin.p2p.rotu.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.shanlin.framework.mapper.JsonMapper;
import com.shanlin.framework.utils.StringHelper;
import com.shanlin.p2p.app.constant.Constants;
import com.shanlin.p2p.app.dao.BidDao;
import com.shanlin.p2p.app.dao.InvestRecordDao;
import com.shanlin.p2p.app.model.Bid;
import com.shanlin.p2p.app.model.enums.Judge;
import com.shanlin.p2p.rotu.controller.RotuMainAction;
import com.shanlin.p2p.rotu.service.RotuP2pService;
import com.shanlin.p2p.rotu.util.RotuMainUtil;

/**
 * 融途P2p实现类
 * 
 * @author ice
 *
 */
@Service
@Transactional(readOnly = true)
@SuppressWarnings("unchecked")
public class RotuP2pServiceImpl implements RotuP2pService {

	@Resource
	private BidDao bidDao;

	@Resource
	private InvestRecordDao investRecordDao;

	private static final Logger log = LoggerFactory.getLogger(RotuP2pServiceImpl.class);
	
	public static void main(String[] args) {
		RotuP2pServiceImpl rotuP2pServiceImpl = new RotuP2pServiceImpl();
		rotuP2pServiceImpl.postP2pToRotu(0L);
	}

	// 提交P2p到融途网(http://127.0.0.1:8200/app/rotu/postToRotu.action)
	@Override
	public Boolean postP2pToRotu(Long bidId) {
		Map<String, Object> mapList = new HashMap<String, Object>();
		List<Map<String, Object>> listBorrow = new ArrayList<Map<String, Object>>();
		Map<String, Object> mapBorrowInner = new HashMap<String, Object>();
		Bid bidPer = bidDao.findOne(bidId);

		/** borrow信息查询 ********************************************/
		if (bidPer != null) {
			Integer tzcs = investRecordDao.findCountInvestRecordUsers(bidId);// 统计投资次数

			mapBorrowInner.put("borrowid", bidId);// 标Id
			mapBorrowInner.put("name", bidPer.getTitle());// 标名称
			mapBorrowInner.put("url", (RotuMainAction.serverQzWeb + "/financing/sbtz/bdxq/" + bidId + ".html"));// 标详情url

			int isDay = bidPer.getExtend().getIsByDay() == Judge.S ? 1 : 0;
			mapBorrowInner.put("isday", isDay);// 0月标,1:天标

			if (isDay == 0) {
				mapBorrowInner.put("timelimit", bidPer.getLoanPeriod());// 借款期限(月)（当isday=1时这里为0)
				mapBorrowInner.put("timelimitday", 0);// Isday=1时显示借款期限(天)（当isday=0时这里为0)
			} else {
				mapBorrowInner.put("timelimit", 0);// 借款期限(月)（当isday=1时这里为0)
				mapBorrowInner.put("timelimitday", bidPer.getExtend().getLoanPeriod());// Isday=1时显示借款期限(天)（当isday=0时这里为0)
			}

			mapBorrowInner.put("account", new DecimalFormat("0.00").format(bidPer.getAmount()));// 借款金额 （保留两位小数，如:50000.00)
			mapBorrowInner.put("owner", StringHelper.truncation(bidPer.getUserAccount().getLoginName(), 2, "***"));// 借款人登录名称
			mapBorrowInner.put("apr", new DecimalFormat("0.00").format(bidPer.getRate().multiply(new BigDecimal(100))));// 年利率 （保留两位小数，如：14.18)

			String temp = new DecimalFormat("0").format(bidPer.getBonus());
			if (temp.equals("0")) {
				temp = "0";
				mapBorrowInner.put("award", 0);// 奖励类型（0-无,1-百分比奖励,2-实际奖励金额）
			} else {
				temp = temp + "%";
				mapBorrowInner.put("award", 1);// 奖励类型（0-无,1-百分比奖励,2-实际奖励金额）
			}

			mapBorrowInner.put("partaccount", temp);// 奖励比例（ 当award=0和2时这里为0当award=1时这里为奖励的比例，如：3%）
			mapBorrowInner.put("funds", 0);// 奖励钱（ 当award=0和1时这里为0当award=2时这里为奖励的实际值，如：10）
			mapBorrowInner.put("repaymentType", RotuMainUtil.getBidMode(bidPer.getMode(), bidPer.getPayMode()));// 借款还款类型（0:等额本息,1:到期本息，3:月息期本，由于平台的自定义类型特别多，目前就是这三种类型，如果有其他类型的选择和以上三种类型最接近的一个类型返回
			mapBorrowInner.put("type", 1);// 借款类型(1、给力标,2、净值标,3、秒还标 4、流转标)没有这几个类型的返回1）
			mapBorrowInner.put("addtime", bidPer.getPublishTime().getTime());// 发布时间(long)(发标时间linux时间戳)
			mapBorrowInner.put("sumTender", new DecimalFormat("0.00").format(bidPer.getAmount().subtract(bidPer.getResidueAmount())));// 当前标已完成投标额度
			mapBorrowInner.put("startmoney", new BigDecimal(Constants.SYS_MIN_INVEST_AMOUNT));// 标的起投金额
			mapBorrowInner.put("tenderTimes", tzcs);// 当前标投标次数

			listBorrow.add(mapBorrowInner);
		}

		/** list信息查询 ******************************************/
		Object[] aprAndCountDatas = findSumEveryDayPublishBid();
		mapList.put("apr_data", aprAndCountDatas[0]);// 30天内每天的平均年利率
		mapList.put("count_data", aprAndCountDatas[1]);// 30天内借款金额分布

		mapList.put("time_data", findPlBorrowQxFb());// 平台借款期限分布
		mapList.put("dcount_data", findSumEveryDayChengJiaoAmount());// 30天内每天成交量

		mapList.put("cj_data", findSumTotalChengJiaoAmount());// 平台总成交量
		mapList.put("dh_data", findSumTotalDhkAmount());// 平台总待还金额
		mapList.put("avg_apr", findOneBeforeNlv());// 前一天平均年利率

		/** 开始提交 ************************************************************************************************/
		JsonMapper jsonMapper = JsonMapper.normalMapper();
		String listJson = jsonMapper.toJson(mapList);
		String borrowJson = jsonMapper.toJson(listBorrow);

		String url = RotuMainAction.getUrlQzSecYmd();
		RestTemplate restTemplate = new RestTemplate();
		MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<String, Object>();

		try {
			multiValueMap.add("dangan_id", String.valueOf(RotuMainAction.danganId));
			multiValueMap.add("borrow", borrowJson);
			multiValueMap.add("list", listJson);
			Object obj = restTemplate.postForObject(url, multiValueMap, String.class);
			String result = StringHelper.getSafeAndTrim(obj);
			Map<String, Object> mapResult = jsonMapper.fromJson(result, Map.class);

			if (mapResult.containsKey("code")) {
				if (RotuMainUtil.getSafeAndTrim(mapResult.get("code")).equals("1")) {
					log.info("(App)推送到融途成功,标Id:" + bidId + "(" + RotuMainUtil.getTime() + ")");
					return true;
				} else {
					//					code	说明
					//					 1	成功（测试的时候表示数据合法可以申请使用正式地址）
					//					-1	正在招标的数据为空或返回格式有误(参数borrow为空或格式不对)
					//					-2	平台数据为空或返回格式有误(参数list为空或格式不对)
					//					-3	档案id不合法(不是数字或者小于0，或未通过审核[测试接口不用审核])
					//					-4	标的id为空
					//					-5	借款人为空
					//					-6	标名称为空
					//					-7	标详细地址为空
					//					-8	标类型为空
					//					-9	标的借款金额非法
					//					-10	已经成功借款的金额非法
					//					-11	年利率不合法
					//					-12	类型为天标，但是天数为0
					//					-13	类型为天标，但是月数不为0
					//					-14	类型为月标，但是天数不为0
					//					-15	类型为月标，但是月数是0
					//					-16	还款方式为空
					//					-17	奖励类型为百分比，但是百分比为0
					//					-18	奖励类型为实际奖励，但是实际奖励为0
					//					-19	发标时间为空
					//					-20	起投金额不合法
					//					-21	30天内每天的平均年利率不符合要求(不足30天，或不是数组格式)
					//					-22	平台借款期限分布为空或不合法
					//					-23	30天内借款金额分布不符合要求(不足30天，或不是数组格式)
					//					-24	30天内每天成交量不符合要求（不足30天，或不是数组格式）
					//					-25	平台总成交量为空或0
					//					-26	平台总待还为空或0
					//					-27	前一天的平均利率为空或0
					//					-28	请求时间太频繁，60s内只能请求一次[测试环境无限制]
					//					-29	借款金额为0或者为空
					//					-30	标必须是二维数组
					//					-31	当前标投标次数为空
					//					-32	当前标已完成投标额度为空
					//					-33	发标时间linux时间戳为空
					//					-34	奖励比例为空或者不大于0
					//					-35	借款人为空
					//					-36	标名为空
					// 失败
					log.info("(App)推送到融途失败:" + mapResult + "(" + RotuMainUtil.getTime() + ")");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	// 30天内每天的平均年利率和30天内借款金额分布
	public Object[] findSumEveryDayPublishBid() {
		Object[] results = new Object[2];
		Map<String, Object> mapAprData = new HashMap<String, Object>();
		Map<String, Object> mapCountData = new HashMap<String, Object>();
		List<String> listBefore30Days = RotuMainUtil.getBefore30Days();
		Object[][] objArrs = bidDao.findSumEveryDayPublishBid();
		if (objArrs != null) {
			List<String> listHaveDates = new ArrayList<String>();
			for (Object[] objs : objArrs) {
				String dateSec = RotuMainUtil.getSafeAndTrim(objs[0]);
				BigDecimal temp = (BigDecimal) objs[3];
				String avgNlvStr = RotuMainUtil.getSafeAndTrim(new DecimalFormat("0.00").format(temp));
				BigDecimal pubJkJine = ((BigDecimal) objs[4]).divide(new BigDecimal(10000));
				String pubJkJineStr = RotuMainUtil.getSafeAndTrim(new DecimalFormat("0.00").format(pubJkJine));
				mapAprData.put(dateSec, avgNlvStr);
				mapCountData.put(dateSec, pubJkJineStr);
				listHaveDates.add(dateSec);
			}
			for (String str : listBefore30Days) {
				if (!listHaveDates.contains(str)) {
					mapAprData.put(str, "0.00");
					mapCountData.put(str, "0.00");
				}
			}
		} else {
			for (String str : listBefore30Days) {
				mapAprData.put(str, "0.00");
				mapCountData.put(str, "0.00");
			}
		}

		results[0] = mapAprData;
		results[1] = mapCountData;
		return results;
	}

	// 获取平台借款期限分布
	public String findPlBorrowQxFb() {
		StringBuffer sb = new StringBuffer();
		Object[][] objArrs = bidDao.findPlBorrowQxFb();
		if (objArrs != null) {
			BigDecimal jkJine1_3 = new BigDecimal(0.00);
			BigDecimal jkJine4_6 = new BigDecimal(0.00);
			BigDecimal jkJine7_12 = new BigDecimal(0.00);
			BigDecimal jkJine12Plus = new BigDecimal(0.00);
			for (Object[] objs : objArrs) {
				int jkqx = RotuMainUtil.getSafeInteger(objs[0]);// 借款期限
				BigDecimal jkJine = ((BigDecimal) objs[1]).divide(new BigDecimal(10000));// 借款金额
				// 1-3个月的
				if (0 < jkqx && (jkqx < 3 || jkqx == 3)) {
					jkJine1_3 = jkJine1_3.add(jkJine);
				}
				// 4-6个月的
				if (3 < jkqx && (jkqx < 6 || jkqx == 6)) {
					jkJine4_6 = jkJine4_6.add(jkJine);
				}
				// 7-12个月的
				if (6 < jkqx && (jkqx < 12 || jkqx == 12)) {
					jkJine7_12 = jkJine7_12.add(jkJine);
				}
				// 12个月以上的
				if (12 < jkqx) {
					jkJine12Plus = jkJine12Plus.add(jkJine);
				}
			}
			sb.append("['1-3\\u4e2a\\u6708'," + new DecimalFormat("0.00").format(jkJine1_3) + "],['4-6\\u4e2a\\u6708'," + new DecimalFormat("0.00").format(jkJine4_6) + "],['7-12\\u4e2a\\u6708'," + new DecimalFormat("0.00").format(jkJine7_12) + "],['12\\u4e2a\\u6708\\u4ee5\\u4e0a'," + new DecimalFormat("0.00").format(jkJine12Plus) + "]");
		} else {
			sb.append("['1-3\\u4e2a\\u6708',0.00],['4-6\\u4e2a\\u6708',0.00],['7-12\\u4e2a\\u6708',0.00],['12\\u4e2a\\u6708\\u4ee5\\u4e0a',0.00]");
		}
		return sb.toString();
	}

	// 获取30天内每天成交量
	public Map<String, Object> findSumEveryDayChengJiaoAmount() {
		// 30天内每天成交量
		List<String> listBefore30Days = RotuMainUtil.getBefore30Days();
		Map<String, Object> mapDcountData = new HashMap<String, Object>();
		Object[][] objArrs = bidDao.findSumEveryDayChengJiaoAmount();
		if (objArrs != null) {
			List<String> listHaveDates = new ArrayList<String>();
			for (Object[] objs : objArrs) {
				String dateSec = RotuMainUtil.getSafeAndTrim(objs[0]);
				BigDecimal cjJine = ((BigDecimal) objs[1]).divide(new BigDecimal(10000));// 成交金额
				String cjJineStr = RotuMainUtil.getSafeAndTrim(new DecimalFormat("0.00").format(cjJine));
				mapDcountData.put(dateSec, cjJineStr);
				listHaveDates.add(dateSec);
			}
			for (String str : listBefore30Days) {
				if (!listHaveDates.contains(str)) {
					mapDcountData.put(str, "0.00");
				}
			}
		} else {
			for (String str : listBefore30Days) {
				mapDcountData.put(str, "0.00");
			}
		}
		return mapDcountData;
	}

	// 平台总成交量
	public String findSumTotalChengJiaoAmount() {
		BigDecimal result = bidDao.findSumTotalChengJiaoAmount();
		if (result == null) {
			return "0.00";
		}
		return RotuMainUtil.getSafeAndTrim(new DecimalFormat("0.00").format(result.divide(new BigDecimal(10000))));
	}

	// 平台总待还金额
	public String findSumTotalDhkAmount() {
		BigDecimal result = bidDao.findSumTotalDhkAmount();
		if (result == null) {
			return "0.00";
		}
		return RotuMainUtil.getSafeAndTrim(new DecimalFormat("0.00").format(result.divide(new BigDecimal(10000))));
	}

	// 前一天平均年利率
	public String findOneBeforeNlv() {
		Object[][] objArrs = bidDao.findOneBeforeNlv();
		if (objArrs != null) {
			if (objArrs.length > 0) {
				Object[] objs = objArrs[0];
				BigDecimal temp = ((BigDecimal) objs[3]);
				String avgNlvStr = RotuMainUtil.getSafeAndTrim(new DecimalFormat("0.00").format(temp));
				return avgNlvStr;
			}
		}
		return "0.00";
	}
}