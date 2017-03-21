package com.shanlin.p2p.wangd.service.impl;

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

import com.shanlin.p2p.app.dao.BidDao;
import com.shanlin.p2p.app.dao.InvestRecordDao;
import com.shanlin.p2p.app.model.Bid;
import com.shanlin.p2p.app.model.enums.BidStatus;
import com.shanlin.p2p.app.model.enums.Judge;
import com.shanlin.p2p.wangd.controller.WangdMainAction;
import com.shanlin.p2p.wangd.service.WangdP2pService;
import com.shanlin.p2p.wangd.util.WangdMainUtil;

/**
 * 网贷P2p实现类
 * 
 * @author ice
 *
 */
@Service
@Transactional(readOnly = true)
public class WangdP2pServiceImpl implements WangdP2pService {

	@Resource
	private BidDao bidDao;

	@Resource
	private InvestRecordDao investRecordDao;

	@SuppressWarnings("unused")
	@Override
	public Map<String, Object> getP2pInfors(int page, int pageSize) {
		LinkedHashMap<String, Object> mapRe = new LinkedHashMap<String, Object>();
		List<LinkedHashMap<String, Object>> list = new ArrayList<LinkedHashMap<String, Object>>();

		Sort sort = new Sort(Direction.DESC, "publishTime");
		Pageable pageable = new PageRequest(page - 1, pageSize, sort);

		Page<Bid> bidPers = bidDao.findDfkBids(pageable);

		if (bidPers != null) {
			mapRe.put("totalPage", bidPers.getTotalPages());
			mapRe.put("currentPage", page);
			mapRe.put("totalCount", bidDao.findCountAmountBids());// 满标总标数
			mapRe.put("totalAmount", bidDao.findSumAmountBids());// 满标总投资额

			for (Bid bidPer : bidPers) {
				LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
				if (bidPer != null) {

					// 进度
					double process = (bidPer.getAmount().doubleValue() - bidPer.getResidueAmount().doubleValue()) / bidPer.getAmount().doubleValue();
					BidStatus status = bidPer.getStatus();
					if (status == BidStatus.HKZ || status == BidStatus.YJQ || status == BidStatus.YDF) {
						process = 1;
					}

					int isDay = (bidPer.getExtend().getIsByDay() == Judge.S ? 1 : 0);
					String type = (bidPer.getIsActivity() == Judge.S ? "活动标" : "抵押标");

					map.put("title", bidPer.getTitle());// 借款标题
					map.put("amount", new DecimalFormat("0.00").format(bidPer.getAmount()));// 借款金额(若标未满截标，以投标总额为准)
					map.put("schedule", new DecimalFormat("0.00").format((int) (100)));// 进度(网袋只能传满标数据)
					map.put("interestRate", new DecimalFormat("0.00").format(bidPer.getRate().multiply(new BigDecimal(100)))+"%");// 利率

					// 借款期限
					if (isDay == 0) {
						map.put("deadline", bidPer.getLoanPeriod()+"");// 借款期限(月)
					} else {
						map.put("deadline", bidPer.getExtend().getLoanPeriod()+"");// 借款期限(天)
					}
					map.put("deadlineUnit", isDay == 1 ? "天" : "月");// 期限单位仅限天或月
					
					String temp = new DecimalFormat("0").format(bidPer.getBonus());
					if (temp.equals("0")) {
						temp = "0";
					}else{
						temp = new DecimalFormat("0.00").format(bidPer.getBonus());
					}
					map.put("reward", temp);// 奖励
					map.put("type", type);// 押标 ，质押标，信用标，流转标，净值标，秒标等。移动端数据需注明移动端。(对于不参与计算平均利率的秒标（天标）、活动标（体验标），就传“秒标”或是“活动标”) 借款类型可根据平台的情况修改，不限于上述类型。若一个标有多个类型，则在每个类型中间加半角分号“;”（如实地认证+担保，就传“实地认证;担保”）
					map.put("repaymentType", WangdMainUtil.getBidMode(bidPer.getMode(), bidPer.getPayMode())+"");// 还款方式
					map.put("projectId", bidPer.getId()+"");// 产品主键(唯一) 

					map.put("province", "");// 借款人所在省份(非必填)
					map.put("city", "");// 借款人所在城市(非必填)
					map.put("userName", bidPer.getUserAccount().getLoginName().hashCode()+"");// 发标人账号
					map.put("userAvatarUrl", "");// 发标人头像的URL(非必填)
					map.put("amountUsedDesc", "");// 借款用途(非必填)
					map.put("revenue", "");// 营收(非必填)
					map.put("loanUrl", (WangdMainAction.serverQzWeb + "/financing/sbtz/bdxq/" + bidPer.getId() + ".html"));// 标的详细页面地址链接
					map.put("publishTime", WangdMainUtil.getDateStr(bidPer.getPublishTime()));// 发标时间
					if (bidPer.getExtend().getFillBidTime() != null) {
						map.put("successTime", WangdMainUtil.getDateStr(bidPer.getExtend().getFillBidTime()));// 标的成功时间。（满标的时间）(此标最后一个投标人投标的时间)
					}
				}

				List<LinkedHashMap<String, Object>> listInner = new ArrayList<LinkedHashMap<String, Object>>();

				// 查询标的投标列表信息
				List<Object[]> listRes = investRecordDao.findAllByBidAndIsCancel(bidPer.getId(), Judge.F);
				if (listRes != null) {
					for (Object[] record : listRes) {
						LinkedHashMap<String, Object> mapInner = new LinkedHashMap<String, Object>();
						String subscribeUserName = String.valueOf((record[0]).hashCode());// 投标人ID
						String amount = new DecimalFormat("0.00").format(((BigDecimal) record[1]));// 投标金额
						String addDate = WangdMainUtil.getDateStr((Date) record[2]);// 投标时间

						mapInner.put("status", 1);// 投标状态1：全部通过 2：部分通过 注意：平台没有这个字段的默认为1
						mapInner.put("amount", amount);// 投标金额
						mapInner.put("type", 0);// 标识手动或自动投标0：手动 1：自动 注意:平台没有这个字段的默认为0
						mapInner.put("validAmount", amount);// 有效金额 实际中标金额。如平台无’投标金额’和’有效金额’之分，则’投标金额’和’有效金额’传一样的即可。
						mapInner.put("addDate", addDate);// 投标时间
						mapInner.put("subscribeUserName", subscribeUserName);// 投标人ID不能将ID加*隐藏部分字符，否则会导致多个投资人使用同一个ID，导致投资集中度高。
						listInner.add(mapInner);
					}
					map.put("subscribes", listInner);
				} else {
					map.put("subscribes", new ArrayList<Map<String, Object>>());
				}

				list.add(map);
			}

			mapRe.put("borrowList", list);
		} else {
			mapRe.put("totalPage", 0);
			mapRe.put("totalCount", 0);
			mapRe.put("currentPage", 1);
			mapRe.put("totalAmount", 0);
			mapRe.put("borrowList", new ArrayList<Map<String, Object>>());
		}
		return mapRe;
	}
}