package com.shanlin.p2p.peye.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.shanlin.p2p.app.dao.BidDao;
import com.shanlin.p2p.app.dao.InvestRecordDao;
import com.shanlin.p2p.app.model.Bid;
import com.shanlin.p2p.app.model.enums.BidStatus;
import com.shanlin.p2p.app.model.enums.Judge;
import com.shanlin.p2p.peye.controller.PeyeMainAction;
import com.shanlin.p2p.peye.service.PeyeP2pService;
import com.shanlin.p2p.peye.util.PeyeMainUtil;

/**
 * 天眼P2p实现类
 * 
 * @author ice
 *
 */
@Service
@Transactional(readOnly = true)
public class PeyeP2pServiceImpl implements PeyeP2pService {

	@Resource
	private BidDao bidDao;

	@Resource
	private InvestRecordDao investRecordDao;

	@Override
	public Map<String, Object> getP2pInfors(Integer page, Integer pageSize, Integer statusForm, String startTime, String endTime) {
		LinkedHashMap<String, Object> mapRe = new LinkedHashMap<String, Object>();
		List<LinkedHashMap<String, Object>> list = new ArrayList<LinkedHashMap<String, Object>>();

		Sort sort = new Sort(Direction.DESC, "publishTime");
		Pageable pageable = new PageRequest(page - 1, pageSize, sort);

		ArrayList listStatuss = Lists.newArrayList(BidStatus.HKZ, BidStatus.TBZ, BidStatus.YFB, BidStatus.DFK, BidStatus.YJQ, BidStatus.YDF);

		if (statusForm != null) {
			// 0,正在投标中的借款标;1,已完成(包括还款中和已完成的借款标).
			if (statusForm == 0) {
				listStatuss = Lists.newArrayList(BidStatus.TBZ);
			} else if (statusForm == 1) {
				listStatuss = Lists.newArrayList(BidStatus.HKZ, BidStatus.YJQ, BidStatus.YDF);
			}
		}

		Page<Bid> bidPers = null;
		if (startTime != null && endTime != null) {
			bidPers = bidDao.findPeriodBidByPage(listStatuss, PeyeMainUtil.getDate(startTime), PeyeMainUtil.getDate(endTime), pageable);
		} else {
			bidPers = bidDao.findBidByPage(listStatuss, pageable);
		}

		if (bidPers != null && bidPers.getTotalElements() > 0) {
			mapRe.put("page_count", bidPers.getTotalPages());// 总页数
			mapRe.put("page_index", page);// 当前页
			mapRe.put("result_code", "1");// 数字1标识成功,否则是请求失败.
			mapRe.put("result_msg", "获取数据成功");// 消息

			for (Bid bidPer : bidPers) {
				LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
				if (bidPer != null) {

					// 查询标的投标列表信息
					List<Object[]> listRes = investRecordDao.findAllByBidAndIsCancel(bidPer.getId(), Judge.F);
					int invest_num = listRes.size();// 投资次数

					// 进度
					String bidStatus = "0";
					double process = (bidPer.getAmount().doubleValue() - bidPer.getResidueAmount().doubleValue()) / bidPer.getAmount().doubleValue();
					BidStatus status = bidPer.getStatus();
					if (status == BidStatus.HKZ || status == BidStatus.YJQ || status == BidStatus.YDF) {
						process = 1;
						bidStatus = "1";
					}

					int isDay = (bidPer.getExtend().getIsByDay() == Judge.S ? 1 : 0);
					int cType = (bidPer.getIsActivity() == Judge.S ? 8 : 2);

					map.put("id", bidPer.getId() + "");// 标的Id
					map.put("url", (PeyeMainAction.serverQzWeb + "/financing/sbtz/bdxq/" + bidPer.getId() + ".html"));// 标的详细页面地址链接
					map.put("platform_name", "善行创投");// 平台名称
					map.put("title", bidPer.getTitle());// 借款标题
					map.put("username", bidPer.getUserAccount().getLoginName().hashCode() + "");// 发标人用户名
					map.put("status", bidStatus);// 0,正在投标中的借款标;1,已完成(包括还款中和已完成的借款标).
					map.put("userid", bidPer.getUserAccount().getId() + "");// 发标人的用户编号/ID.
					map.put("c_type", cType);// 0 代表信用标,1 担保标;2 抵押,质押标, 3 秒标; 4 债权转让标(流转标,二级市场标的);5 理财计划(宝类业务);6 其它;7 净值标;8 活动标(体验标).
					map.put("amount", new DecimalFormat("0.00").format(bidPer.getAmount()));// 借款金额以元为单位,精度2位(1000.00),如万元请转换为元.
					map.put("rate", new DecimalFormat("0.00").format(bidPer.getRate().multiply(new BigDecimal(1))));// 借款年利率,如果为月利率或天利率,统一转换为年利率并使用小数表示;精度4位,如:0.0910.

					// 借款期限
					if (isDay == 0) {
						map.put("period", bidPer.getLoanPeriod() + "");// 借款期限(月)
					} else {
						map.put("period", bidPer.getExtend().getLoanPeriod() + "");// 借款期限(天)
					}
					map.put("p_type", isDay == 1 ? "0" : "1");// 期限类型 0代表天，1代表月(不为空)
					map.put("pay_way", PeyeMainUtil.getBidMode(bidPer.getMode(), bidPer.getPayMode()) + "");// 还款方式
					map.put("process", new DecimalFormat("0.0").format((int) (process)));// 完成百分比转换成小数表示.

					String temp = new DecimalFormat("0").format(bidPer.getBonus());
					if (temp.equals("0")) {
						temp = "0";
					} else {
						temp = new DecimalFormat("0.0").format(bidPer.getBonus());
					}
					map.put("reward", temp);// 投标奖励
					map.put("guarantee", "0");// 担保奖励
					map.put("start_time", PeyeMainUtil.getDateStr(bidPer.getPublishTime()));// 标的创建时间格式如:2013-08-10 14:24:01(24小时制).
					if (bidPer.getExtend().getFillBidTime() != null) {
						map.put("end_time", PeyeMainUtil.getDateStr(bidPer.getExtend().getFillBidTime()));// 标的成功时间。（满标的时间）(此标最后一个投标人投标的时间)(格式如:2013-08-10 13:10:00我们要的投资记录最后一笔的时间,请不要理解为标最后的的还款完成日期.)
					}
					map.put("invest_num", invest_num + "");// 这笔借款标有多少个投标记录.
					map.put("c_reward", "0");// 续投奖励
				}
				list.add(map);
			}
			mapRe.put("loans", list);
		}
		// 若没有标时
		else {
			// 若没有标时->返回{"result_code":"-1","result_msg":"未授权的访问!","page_count":"0","page_index":"0","loans":null}
			mapRe.put("result_code", "-1");// 数字1标识成功,否则是请求失败.
			mapRe.put("result_msg", "未授权的访问!");// 消息
			mapRe.put("page_count", "0");
			mapRe.put("page_index", "0");
			mapRe.put("loans", null);
		}
		return mapRe;
	}

	@Override
	public LinkedHashMap<String, Object> getP2pInves(Integer page, Integer pageSize, Long id) {
		Sort sort = new Sort(Direction.DESC, "investTime");
		Pageable pageable = new PageRequest(page - 1, pageSize, sort);

		Bid bidPer = bidDao.findOne(id);
		LinkedHashMap<String, Object> mapRe = new LinkedHashMap<String, Object>();
		if (bidPer == null) {
			mapRe.put("result_code", "-1");// 数字1标识成功,否则是请求失败.
			mapRe.put("result_msg", "未授权的访问!");// 消息
			mapRe.put("page_count", "0");
			mapRe.put("page_index", "0");
			mapRe.put("loans", null);
			return mapRe;
		}

		List<LinkedHashMap<String, Object>> listInner = new ArrayList<LinkedHashMap<String, Object>>();

		// 查询标的投标列表信息
		Page<Object[]> listRes = investRecordDao.findMoreByBidAndIsCancel(bidPer.getId(), Judge.F, pageable);

		if (listRes != null && listRes.getTotalElements() > 0) {
			mapRe.put("result_code", "1");// 数字1标识成功,否则是请求失败.
			mapRe.put("result_msg", "获取数据成功");// 消息
			mapRe.put("page_count", listRes.getTotalPages());// 总页数
			mapRe.put("page_index", page);// 当前页

			for (Object[] record : listRes) {
				LinkedHashMap<String, Object> mapInner = new LinkedHashMap<String, Object>();
				String username = String.valueOf((record[1]).hashCode());// 投标人用户名
				String userid = String.valueOf((record[2]));// 投标人ID
				String money = new DecimalFormat("0").format(((BigDecimal) record[3]));// 投标金额
				String account = new DecimalFormat("0").format(((BigDecimal) record[4]));// 有效金额：投标金额实际生效部分(债权金额)
				String add_time = PeyeMainUtil.getDateStr((Date) record[5]);// 投标时间

				mapInner.put("id", id);// 标的唯一编号(不为空,很重要)
				mapInner.put("useraddress", "");//  用户所在地 
				mapInner.put("link", (PeyeMainAction.serverQzWeb + "/financing/sbtz/bdxq/" + id + ".html"));//  URL链接.
				mapInner.put("username", username);// 投标人的用户名称 
				mapInner.put("userid", userid);// 投标人ID
				mapInner.put("type", "手动");// 例如:手动、自动.
				mapInner.put("money", money);// 投标金额
				mapInner.put("account", account);// 有效金额：投标金额实际生效部分(债权金额)
				mapInner.put("status", "成功");// 投标状态 例如：成功、部分成功、失败
				mapInner.put("add_time", add_time);// 投标时间格式如:2014-03-13 16:44:26.
				listInner.add(mapInner);
			}
			mapRe.put("loans", listInner);
		}
		// 若没有投资记录时
		else {
			// 若没有标时->返回{"result_code":"-1","result_msg":"未授权的访问!","page_count":"0","page_index":"0","loans":null}
			mapRe.put("result_code", "-1");// 数字1标识成功,否则是请求失败.
			mapRe.put("result_msg", "未授权的访问!");// 消息
			mapRe.put("page_count", "0");
			mapRe.put("page_index", "0");
			mapRe.put("loans", null);
		}
		return mapRe;
	}
}