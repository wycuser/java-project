package com.shanlin.p2p.csai.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shanlin.framework.utils.DateHelper;
import com.shanlin.framework.utils.StringHelper;
import com.shanlin.p2p.app.dao.BidDao;
import com.shanlin.p2p.app.dao.InvestRecordDao;
import com.shanlin.p2p.app.dao.UserAccountDao;
import com.shanlin.p2p.app.model.Bid;
import com.shanlin.p2p.app.model.InvestRecord;
import com.shanlin.p2p.app.model.UserAccount;
import com.shanlin.p2p.app.model.enums.Judge;
import com.shanlin.p2p.csai.controller.CsaiMainAction;
import com.shanlin.p2p.csai.controller.ZhtAction;
import com.shanlin.p2p.csai.service.LqGetP2pService;
import com.shanlin.p2p.csai.util.CsaiBidUtil;
import com.shanlin.p2p.csai.util.CsaiZhtUtil;

/**
 * 获取P2p服务实现类
 * 
 * @author ice
 *
 */
@Service
@Transactional(readOnly = true)
public class LqGetP2pServiceImpl implements LqGetP2pService {

	@Resource
	private BidDao bidDao;

	@Resource
	private UserAccountDao userAccountDao;

	@Resource
	private InvestRecordDao investRecordDao;

	private static final Logger log = LoggerFactory.getLogger(LqGetP2pServiceImpl.class);
	
	// 获取P2p
	@Override
	public Map<String, Object> getP2p(Long bidId) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> mapInner = new HashMap<String, Object>();
		Bid bidPer = bidDao.findOne(bidId);

		if (bidPer != null) {
			Integer tzrs = investRecordDao.findCountInvestRecordUsers(bidId);// 统计投资人数

			mapInner.put("amount", new DecimalFormat("0").format(bidPer.getAmount()));// 募集金额(元)(必须)
			mapInner.put("ev_rate", new DecimalFormat("0.0").format(bidPer.getRate().multiply(new BigDecimal(100))));// 年收益率(%),不要带%(必须)
			mapInner.put("pay_type", CsaiBidUtil.getBidMode(bidPer.getMode(), bidPer.getPayMode()));// 还款方式 1 按月付息 到期还本 2 按季付息,到期还本3 每月等额本息 4 到期还本息 5 按周等额本息还款 6按周付息,到期还本 7:利随本清;8:等本等息;9:按日付息,到期还本;10:按半年付息,到期还本;11:按一年付息,到期还本;100:其他方式;(必须)
			mapInner.put("borrower", StringHelper.truncation(bidPer.getUserAccount().getLoginName(), 2, "***"));// 借款人名称(必须)
			mapInner.put("life_cycle", bidPer.getExtend().getIsByDay() == Judge.S ? bidPer.getExtend().getLoanPeriod() : bidPer.getLoanPeriod() * 30);// 产品周期(天),如果为其他单位,请转换为天(必须)
			mapInner.put("borrow_type", 1);// 借款担保方式 1 抵押借款 2 信用借款 3 质押借款 4 第三方担保(必须)
			mapInner.put("start_price", 100);// 起投金额,单位元(必须)
			mapInner.put("guarant_mode", 1);// 担保方式 0 无担保 1 本息担保 2 本金担保(必须)
			mapInner.put("publish_time", DateHelper.getDateStrYmdHg(bidPer.getPublishTime()));// 发布时间(yyyy-MM-dd)(必须)
			mapInner.put("link_website", CsaiMainAction.serverQzApp + "/app/csai/getP2pRe.action?id=" + bidPer.getId());// 链接地址该值为接入方的标的地址,在此地址中应包括身份码,用户通过该地址进入贵方平台后,只要不要跳出网站,在当前页面和其他任何页面注册和登录都要能够统计到是我们导入的用户.(建议:当通过有身份码的链接进入你们网站后,将身份码记录cookies,在cookies有效限内(30天内)用户注册了就算是我们导入的用户了)(必须)
			mapInner.put("inverst_mans", tzrs);// 投资人数(必须)
			mapInner.put("product_name", bidPer.getTitle());// 产品名称(必须)
			mapInner.put("product_state", CsaiBidUtil.getBidStatus(bidPer, bidPer.getExtend()));// 产品募集状态 -1:流标,0:筹款中,1:已满标,2:已开始还款,3:预发布,4:还款完成,5:逾期(必须)
			mapInner.put("invest_amount", new DecimalFormat("0").format(bidPer.getAmount().subtract(bidPer.getResidueAmount())));// 已募集金额(元)(必须)
			mapInner.put("p2p_product_id", bidPer.getId());// P2P平台产品唯一id(必须)
			mapInner.put("underlying_start", DateHelper.getDateStrYmdHg(bidPer.getPublishTime()));// 标的开始时间(yyyy-MM-dd)(必须)

			map.put("data", mapInner);

			log.info("(希财)获取P2p信息->成功->其中获取P2p返回链接为:" + CsaiMainAction.serverQzApp + "/app/csai/getP2pRe.action?id=" + bidPer.getId()+"("+CsaiZhtUtil.getTime()+")");
		}
		return map;
	}

	// 获取P2p用户(由希财过来的用户)列表
	@Override
	public Map<String, Object> getP2pUserList(Date startDate, Date endDate, Pageable pageable, HttpServletRequest request) {
		Map<String, Object> mapInner = null;
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		Page<UserAccount> listUserAccounts = null;
		if (startDate == null && endDate == null) {
			listUserAccounts = userAccountDao.findCsaiUsers("csai", pageable);
		} else if (startDate == null && endDate != null) {
			listUserAccounts = userAccountDao.findCsaiUsersByEndDate("csai", endDate, pageable);
		} else if (startDate != null && endDate == null) {
			listUserAccounts = userAccountDao.findCsaiUsersByStartDate("csai", startDate, pageable);
		} else {
			listUserAccounts = userAccountDao.findCsaiUsersByBetweenDate("csai", startDate, endDate, pageable);
		}

		if (listUserAccounts != null) {
			for (UserAccount obj : listUserAccounts) {
				mapInner = new HashMap<>();
				BigDecimal totalmoney = investRecordDao.findAllCreditPriceByUser(obj.getId());// 累计投资

				mapInner.put("id", obj.getId());// 用户id
				mapInner.put("username", StringHelper.truncation(obj.getLoginName(), 2, "***"));// 用户名
				mapInner.put("realname", "***");// 真实姓名
				mapInner.put("phone", StringHelper.getDxhMobileNumber(obj.getMobilePhone()));// 电话
				mapInner.put("qq", "***");// QQ
				mapInner.put("email", StringHelper.truncation(obj.getEmail(), 2, "***"));// 邮箱
				mapInner.put("ip", getIpAddr(request));// ip地址
				mapInner.put("regtime", DateHelper.getDateStrForCsai(obj.getRegisterTime()));// 注册时间
				mapInner.put("totalmoney", totalmoney == null ? "0" : new DecimalFormat("0").format(totalmoney));// 投资总金额

				list.add(mapInner);
			}
			map.put("list", list);
			map.put("total", listUserAccounts.getTotalElements());
		}
		
		log.info("(希财)获取获取P2p用户成功"+"("+CsaiZhtUtil.getTime()+")");
		return map;
	}

	//  获取P2p用户(由希财过来的用户)投资统计列表
	@Override
	public Map<String, Object> getP2pUserInveList(Date startDate, Date endDate, Pageable pageable) {
		Map<String, Object> mapInner = null;
		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		Page<InvestRecord> listInvestRecords = null;
		if (startDate == null && endDate == null) {
			listInvestRecords = investRecordDao.findCsaiInvestRecords("csai", pageable);
		} else if (startDate == null && endDate != null) {
			listInvestRecords = investRecordDao.findCsaiInvestRecordsByEndDate("csai", endDate, pageable);
		} else if (startDate != null && endDate == null) {
			listInvestRecords = investRecordDao.findCsaiInvestRecordsByStartDate("csai", startDate, pageable);
		} else {
			listInvestRecords = investRecordDao.findCsaiInvestRecordsByBetweenDate("csai", startDate, endDate, pageable);
		}

		if (listInvestRecords != null) {
			for (InvestRecord obj : listInvestRecords) {
				mapInner = new HashMap<>();

				mapInner.put("id", obj.getId());// 投资id
				mapInner.put("pid", obj.getBid().getId());// P2P网站产品唯一id
				mapInner.put("username", StringHelper.truncation(obj.getUserAccount().getLoginName(), 2, "***"));// 用户名
				mapInner.put("datetime", DateHelper.getDateStrForCsai(obj.getInvestTime()));// 投资时间
				mapInner.put("money", obj.getCreditPrice() == null ? "0" : new DecimalFormat("0").format(obj.getCreditPrice()));// 投资金额
				mapInner.put("commision", 0);// 返佣金额

				list.add(mapInner);
			}
			map.put("list", list);
			map.put("total", listInvestRecords.getTotalElements());
		}
		
		log.info("(希财)获取P2p用户(由希财过来的用户)投资统计列表成功"+"("+CsaiZhtUtil.getTime()+")");
		return map;
	}
	
	public static String getIpAddr(HttpServletRequest request) {
		if (request != null) {
			String ip = request.getHeader("x-forwarded-for");
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
			}
			return ip;
		}
		return "";
	}
}