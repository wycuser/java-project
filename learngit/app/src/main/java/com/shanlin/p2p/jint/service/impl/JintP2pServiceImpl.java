package com.shanlin.p2p.jint.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.shanlin.p2p.app.constant.Constants;
import com.shanlin.p2p.app.dao.BidDao;
import com.shanlin.p2p.app.dao.InvestRecordDao;
import com.shanlin.p2p.app.model.Bid;
import com.shanlin.p2p.app.model.enums.BidStatus;
import com.shanlin.p2p.app.model.enums.Judge;
import com.shanlin.p2p.jint.controller.JintMainAction;
import com.shanlin.p2p.jint.service.JintP2pService;
import com.shanlin.p2p.jint.util.JintMainUtil;
import com.shanlin.p2p.rotu.util.RotuMainUtil;

/**
 * 金投P2p实现类
 * 
 * @author ice
 *
 */
@Service
@Transactional(readOnly = true)
@SuppressWarnings("unchecked")
public class JintP2pServiceImpl implements JintP2pService {

	@Resource
	private BidDao bidDao;

	@Resource
	private InvestRecordDao investRecordDao;

	private static final Logger log = LoggerFactory.getLogger(JintP2pServiceImpl.class);
	
	public static void main(String[] args) {
		JintP2pServiceImpl rotuP2pServiceImpl = new JintP2pServiceImpl();
		rotuP2pServiceImpl.postP2pToJint(0L);
	}

	// 提交P2p到金投网http://58.250.171.53:8200/app/jint/postToJint.action?bidId=135
	@SuppressWarnings("rawtypes")
	@Override
	public Boolean postP2pToJint(Long bidId) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();

		Bid bidPer = bidDao.findOne(bidId);
		if (bidPer != null) {

			// 进度
			double process = (bidPer.getAmount().doubleValue() - bidPer.getResidueAmount().doubleValue()) / bidPer.getAmount().doubleValue();
			BidStatus status = bidPer.getStatus();
			if (status == BidStatus.HKZ || status == BidStatus.YJQ || status == BidStatus.YDF) {
				process = 1;
			}

			int isDay = (bidPer.getExtend().getIsByDay() == Judge.S ? 1 : 0);
			int type = (bidPer.getIsActivity() == Judge.S ? 6 : 1);// 标类型

			map.put("productId", bidId);// 产品主键(唯一) 
			map.put("platformId", JintMainAction.getPlId());// 平台ID
			map.put("shortName", "善行创投");// 平台简称
			map.put("title", bidPer.getTitle());// 产品名称
			map.put("amount", new DecimalFormat("0.0").format(bidPer.getAmount()));// 借款金额(若标未满截标，以投标总额为准)
			map.put("beginMoney", new BigDecimal(Constants.SYS_MIN_INVEST_AMOUNT));// 起投金额(没有 填0)
			map.put("complete", (int) (process * 100));// 进度
			map.put("profit", new DecimalFormat("0.00").format(bidPer.getRate().multiply(new BigDecimal(100))));// 利率

			// 借款期限
			if (isDay == 0) {
				map.put("deadline", bidPer.getLoanPeriod());// 借款期限(月)
			} else {
				map.put("deadline", bidPer.getExtend().getLoanPeriod());// 借款期限(天)
			}
			map.put("deadlineUnit", isDay == 1 ? "天" : "月");// 期限单位
			
			String temp = new DecimalFormat("0").format(bidPer.getBonus());
			if (temp.equals("0")) {
				temp = "0";
			}else{
				temp = new DecimalFormat("0.0").format(bidPer.getBonus());
			}
			
			map.put("reward", temp);// 奖励
			map.put("type", type);// 1:抵押标  2:质押标 3:信用标 4:流转标 5:净值标 6:秒标(活动标) 7:其他标
			map.put("payOption", JintMainUtil.getBidMode(bidPer.getMode(), bidPer.getPayMode()));// 还款方式
			map.put("userName", bidPer.getUserAccount().getLoginName().hashCode());// 发标人账号
			map.put("loanUrl", (JintMainAction.serverQzWeb + "/financing/sbtz/bdxq/" + bidId + ".html"));// 标的详细页面地址链接

			map.put("province", "");// 借款人所在省份(非必填)
			map.put("city", "");// 借款人所在城市(非必填)
			map.put("userAvatarUrl", "");// 发标人头像的URL(非必填)
			map.put("amountUsedDesc", "");// 借款用途(非必填)
			map.put("revenue", "");// 营收(非必填)

			// 注意这个参数非必需(文档中写成了必须)
			if (bidPer.getExtend().getFillBidTime() != null) {
				map.put("successTime", JintMainUtil.getDateStr(bidPer.getExtend().getFillBidTime()));// 标的成功时间。（满标的时间）(此标最后一个投标人投标的时间)
			}
			map.put("publishTime", JintMainUtil.getDateStr(bidPer.getPublishTime()));// 发标时间
		}

		map.put("subscribes", new ArrayList<LinkedHashMap<String, Object>>());// 投标记录(非必填)

		list.add(map);

		/** 开始提交 ************************************************************************************************/
		//		JsonMapper jsonMapper = JsonMapper.normalMapper();
		//		String listJson = jsonMapper.toJson(list);

		String url = JintMainAction.getUrlQzSecYmd();
		RestTemplate restTemplate = new RestTemplate();

		try {

			HttpHeaders headers = new HttpHeaders();
			long timestamp = JintMainUtil.getDate().getTime();

			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.add("Accept", "application/json");
			headers.add("Api-Signature", JintMainAction.jqAccount);
			headers.add("Api-Verification", JintMainAction.getJqSign(timestamp));
			headers.add("Api-Timestamp", JintMainUtil.getSafeAndTrim(timestamp));

			HttpEntity requestEntity = new HttpEntity(list, headers);

			ResponseEntity<Map> body = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Map.class);
			Map<String, Object> mapResult = body.getBody();

			log.info("(App)开始推送到金投,url:" + url + ",标Id:" + bidId + "(" + RotuMainUtil.getTime() + ")(" + mapResult + ")");

			if (mapResult.containsKey("code")) {
				if (RotuMainUtil.getSafeAndTrim(mapResult.get("code")).equals("0")) {
					log.info("(App)推送到金投成功,标Id:" + bidId + "(" + RotuMainUtil.getTime() + ")");
					return true;
				} else {
					log.info("(App)推送到金投失败" + mapResult + "(" + RotuMainUtil.getTime() + ")");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}