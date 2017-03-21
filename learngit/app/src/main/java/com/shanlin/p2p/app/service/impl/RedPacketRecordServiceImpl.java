package com.shanlin.p2p.app.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shanlin.framework.controller.InterfaceResultCode;
import com.shanlin.framework.exception.ServiceException;
import com.shanlin.framework.utils.DateParser;
import com.shanlin.p2p.app.dao.RedPacketDao;
import com.shanlin.p2p.app.dao.RedPacketRecordDao;
import com.shanlin.p2p.app.dao.UserAccountDao;
import com.shanlin.p2p.app.model.InvestRecord;
import com.shanlin.p2p.app.model.RedPacket;
import com.shanlin.p2p.app.model.RedPacketRecord;
import com.shanlin.p2p.app.model.UserAccount;
import com.shanlin.p2p.app.service.LetterService;
import com.shanlin.p2p.app.service.RedPacketRecordService;
import com.shanlin.p2p.app.service.SmsService;

@Service
@Transactional(readOnly = true)
public class RedPacketRecordServiceImpl implements RedPacketRecordService {

	protected static final Logger log = LoggerFactory.getLogger(RedPacketRecordServiceImpl.class);

	@Resource
	private RedPacketDao redPacketDao;

	@Resource
	private RedPacketRecordDao recordDao;

	@Resource
	private UserAccountDao userDao;

	@Resource
	private SmsService smsService;

	@Resource
	private LetterService letterService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.shanlin.p2p.app.service.RedPacketRecordService#sendRedPacket(java
	 * .lang.Long, int, int)
	 */
	@Override
	@Transactional
	public void sendRedPacket(Long userId, int source, int type) {
		UserAccount userAccount = userDao.findOne(userId);
		if (null == userAccount) {
			return;
		}
		RedPacket[] records = redPacketDao.getList(source, type);
		if (null != records) {
			RedPacketRecord packetRecord = null;
			for (RedPacket record : records) {
				packetRecord = new RedPacketRecord();
				packetRecord.setUserId(userId);
				packetRecord.setUserName(userAccount.getLoginName());
				packetRecord.setState(1);
				packetRecord.setSource(record.getSource());
				packetRecord.setType(record.getType());
				packetRecord.setUseActivity(record.getUseActivity());
				packetRecord.setAmount(record.getAmount());
				packetRecord.setStartTime(new Date());
				packetRecord.setEndTime(new DateTime(packetRecord.getStartTime()).plusDays(record.getEffectiveDays()).toDate());
				packetRecord.setRanges(record.getRanges());
				packetRecord.setContent(record.getContent());
				packetRecord.setCreateTime(new Date());
				//保存红包记录
				packetRecord = recordDao.save(packetRecord);
			}
			if (null != packetRecord) {
				letterService.sendLetter(userId, "注册红包", "尊敬的用户，您已注册成功，同时获得红包奖励，请登录官网在“我的账户”里查看，投资可使用红包。");
				smsService.send("尊敬的用户，您已注册成功，同时获得红包奖励，请登录官网在“我的账户”里查看，投资可使用红包。如有疑问请致电：4000166277，更多活动可加入官方微信：(myshanxing)。感谢您对我们的支持与信任。【善行创投】", userAccount.getMobilePhone());
			}
		}
		Long parentId=userDao.getParentId(userId);
		if(null==parentId){
			return;
		}
		UserAccount p_userAccount = userDao.findOne(parentId);
		if (null == p_userAccount) {
			return;
		}
		//推荐人红包
		RedPacket[] p_records = redPacketDao.getList(3, type);
		if (null != p_records) {
			RedPacketRecord packetRecord = null;
			for (RedPacket record : p_records) {
				packetRecord = new RedPacketRecord();
				packetRecord.setUserId(p_userAccount.getId());
				packetRecord.setUserName(p_userAccount.getLoginName());
				packetRecord.setState(5);//未激活
				packetRecord.setChildId(userId);
				packetRecord.setSource(3);//推广
				packetRecord.setType(record.getType());
				packetRecord.setUseActivity(record.getUseActivity());
				packetRecord.setAmount(record.getAmount());
				packetRecord.setStartTime(new Date());
				packetRecord.setEndTime(new DateTime(packetRecord.getStartTime()).plusDays(record.getEffectiveDays()).toDate());
				packetRecord.setRanges(record.getRanges());
				packetRecord.setContent(record.getContent());
				packetRecord.setCreateTime(new Date());
				//保存红包记录
				packetRecord = recordDao.save(packetRecord);
			}
			if (null != packetRecord) {
				letterService.sendLetter(p_userAccount.getId(), "推广红包", "您的好友已应约注册成功，同时获得红包奖励，请登录官网在“我的账户”里查看，投资可使用红包。");
				smsService.send("尊敬的用户，您的好友已应约注册成功，同时获得红包奖励，请登录官网在“我的账户”里查看，投资可使用红包。如有疑问请致电：4000166277，更多活动可加入官方微信：(myshanxing)。感谢您对我们的支持与信任。【善行创投】", p_userAccount.getMobilePhone());
			}
		}
	}

	@Override
	@Transactional
	public void useRedPacket(Long userId, Long bid, InvestRecord investRecord, Long id) {
		UserAccount userAccount = userDao.findOne(userId);
		if (null == userAccount) {
			return;
		}
		//锁定可以使用的红包记录
		RedPacketRecord record = recordDao.lockById(id);
		//红包绑定标的，并改为已冻结
		String endDate=DateParser.format(record.getEndTime());
		String currDate=DateParser.format(new Date());
		if (null != record && record.getState() == 1 
				&& investRecord.getBuyingPrice().compareTo(record.getRanges()) >= 0//投资金额大于等于下限
				&& endDate.compareTo(currDate)>=0) {//红包未过期
			int result = recordDao.updateRecord(2, bid, investRecord.getId(), new Date(), record.getId());
			if (result > 0) {
				letterService.sendLetter(userId, "使用红包", "尊敬的用户，您的红包已冻结，标的审核通过后可以返现哦。");
				//					smsService.send("message", userAccount.getMobilePhone());
			}
		}else{
			throw new ServiceException(InterfaceResultCode.FAILED, "参数有误");
		}
	}

	@Override
	public Map<String, Object> findHbList(Long userId, int state, int page, int size) {
		Map<String, Object> returnMap = new LinkedHashMap<>();
		//		List<Map<String, Object>> list = new ArrayList<>();
		Page<RedPacketRecord> pageList = null;
		Pageable pageable = new PageRequest(page - 1, size, new Sort(Direction.DESC, "id"));
		if (state == 2 || state == 3) {
			pageList = recordDao.findHbList2(userId, pageable);
		} else {
			pageList = recordDao.findHbList(userId, state, pageable);
			returnMap.put("count", pageList.getTotalElements());
		}
		//		if(pageList!=null){
		//			for (RedPacketRecord record : pageList) {
		//				list.add(record.toMap());
		//			}
		//		}
		returnMap.put("hasNext", pageList.hasNext() ? 1 : 0);
		returnMap.put("content", pageList.getContent());
		return returnMap;
	}

	@Override
	public RedPacketRecord findHbRecord(Long id) {
		return recordDao.findOne(id);
	}

	@Override
	public List<RedPacketRecord> findHbList(Long userId, BigDecimal ranges,int useActivity) {
//		Map<String, Object> returnMap = new LinkedHashMap<>();
//		List<Map<String, Object>> list = new ArrayList<>();
		if(useActivity>0){
			return recordDao.findHbList(userId, new DateTime().withTimeAtStartOfDay().toDate(), ranges,useActivity);
		}
		return recordDao.findHbList(userId, new DateTime().withTimeAtStartOfDay().toDate(), ranges);
//		if (null != records) {
//			for (RedPacketRecord record : records) {
//				list.add(record.toMap());
//			}
//		}
//		returnMap.put("hasNext", 0);
//		returnMap.put("content", records);
	}

	@Override
	public Map<String, Object> findHbList2(Long userId, int state, int page,
			int size) {
		Map<String, Object> returnMap = new LinkedHashMap<>();
		Page<RedPacketRecord> pageList = null;
		Pageable pageable = new PageRequest(page - 1, size, new Sort(Direction.DESC, "id"));
		if (state == 1) {
			pageList = recordDao.findHbList3(userId, pageable);
			returnMap.put("count", pageList.getTotalElements());
		}
		else if (state == 2 || state == 3) {
			pageList = recordDao.findHbList2(userId, pageable);
		} else {
			pageList = recordDao.findHbList(userId, state, pageable);
		}
		returnMap.put("hasNext", pageList.hasNext() ? 1 : 0);
		returnMap.put("content", pageList.getContent());
		return returnMap;
	}
}
