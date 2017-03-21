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
import com.shanlin.p2p.app.dao.DeductionDao;
import com.shanlin.p2p.app.dao.DeductionRecordDao;
import com.shanlin.p2p.app.dao.UserAccountDao;
import com.shanlin.p2p.app.model.DeductionRecord;
import com.shanlin.p2p.app.model.InvestRecord;
import com.shanlin.p2p.app.model.UserAccount;
import com.shanlin.p2p.app.service.DeductionRecordService;
import com.shanlin.p2p.app.service.LetterService;
import com.shanlin.p2p.app.service.SmsService;

@Service
@Transactional(readOnly = true)
public class DeductionRecordServiceImpl implements DeductionRecordService {

	protected static final Logger log = LoggerFactory.getLogger(DeductionRecordServiceImpl.class);

	@Resource
	private DeductionDao deductionDao;

	@Resource
	private DeductionRecordDao deductionRecordDao;

	@Resource
	private UserAccountDao userDao;

	@Resource
	private SmsService smsService;

	@Resource
	private LetterService letterService;

	@Override
	@Transactional
	public void useDeduction(Long userId, Long bid, InvestRecord investRecord, Long id) {
		UserAccount userAccount = userDao.findOne(userId);
		if (null == userAccount) {
			return;
		}
		// 锁定可以使用的抵扣券记录
		DeductionRecord record = deductionRecordDao.lockById(id);
		// 抵扣券绑定标的，并改为已冻结
		String endDate = DateParser.format(record.getEndTime());
		String currDate = DateParser.format(new Date());
		if (null != record && record.getState() == 1 && (investRecord.getBuyingPrice().compareTo(record.getRanges()) >= 0) && (endDate.compareTo(currDate) >= 0)) {
			int result = deductionRecordDao.updateRecord(2, bid, investRecord.getId(), new Date(), record.getId());
			if (result > 0) {
				letterService.sendLetter(userId, "使用抵扣券", "尊敬的用户，您的抵扣券已冻结，标的审核通过后将转给借款人。");
			}
		} else {
			throw new ServiceException(InterfaceResultCode.FAILED, "参数有误");
		}
	}

	@Override
	public Map<String, Object> findDkList(Long userId, int state, int page, int size) {
		Map<String, Object> returnMap = new LinkedHashMap<>();
		Page<DeductionRecord> pageList = null;
		Pageable pageable = new PageRequest(page - 1, size, new Sort(Direction.DESC, "id"));
		if (state == 2 || state == 3) {
			pageList = deductionRecordDao.findDkList2(userId, pageable);
		} else {
			pageList = deductionRecordDao.findDkList(userId, state, pageable);
			returnMap.put("count", pageList.getTotalElements());
		}
		returnMap.put("hasNext", pageList.hasNext() ? 1 : 0);
		returnMap.put("content", pageList.getContent());
		return returnMap;
	}

	@Override
	public DeductionRecord findDkRecord(Long id) {
		return deductionRecordDao.findOne(id);
	}

	@Override
	public List<DeductionRecord> findDkList(Long userId, BigDecimal ranges, int useActivity) {
		if (useActivity > 0) {
			return deductionRecordDao.findDkList(userId, new DateTime().withTimeAtStartOfDay().toDate(), ranges, useActivity);
		}
		return deductionRecordDao.findDkList(userId, new DateTime().withTimeAtStartOfDay().toDate(), ranges);
	}

	@Override
	public Map<String, Object> findDkList2(Long userId, int state, int page, int size) {
		Map<String, Object> returnMap = new LinkedHashMap<>();
		Page<DeductionRecord> pageList = null;
		Pageable pageable = new PageRequest(page - 1, size, new Sort(Direction.DESC, "id"));
		if (state == 1) {
			pageList = deductionRecordDao.findDkList3(userId, pageable);
			returnMap.put("count", pageList.getTotalElements());
		} else if (state == 2 || state == 3) {
			pageList = deductionRecordDao.findDkList2(userId, pageable);
		} else {
			pageList = deductionRecordDao.findDkList(userId, state, pageable);
		}
		returnMap.put("hasNext", pageList.hasNext() ? 1 : 0);
		returnMap.put("content", pageList.getContent());
		return returnMap;
	}
}