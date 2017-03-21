package com.shanlin.p2p.app.service.impl;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shanlin.framework.utils.Formater;
import com.shanlin.p2p.app.dao.LetterContentDao;
import com.shanlin.p2p.app.dao.LetterDao;
import com.shanlin.p2p.app.model.Letter;
import com.shanlin.p2p.app.model.LetterContent;
import com.shanlin.p2p.app.model.enums.LetterStatus;
import com.shanlin.p2p.app.service.LetterService;

@Service
@Transactional(readOnly=true)
public class LetterServiceImpl implements LetterService {
	
	private static final Logger log = LoggerFactory.getLogger(LetterServiceImpl.class);
	
	@Resource
	private LetterDao letterDao;
	
	@Resource
	private LetterContentDao letterContentDao;
	
	@Override
	@Transactional
	public void sendLetter(Long userId, String title, String content) {
		Letter letter = new Letter();
		letter.setUserId(userId);
		letter.setTitle(title);
		letter.setStatus(LetterStatus.WD);
		letter.setSendTime(new Date());
		letter = letterDao.save(letter);
		letterContentDao.init(letter.getId(), content);
	}
	
	@Override
	@Transactional
	public void sendLetter(Long userId, String title, String content, Map<String, Object> args){
//		String content = PropertyFileConfigurer.getContextProperty(letterKey);
		if(content == null){
			log.error("站内信模版找不到");
//			throw new ServiceException(InterfaceResultCode.SYS_ERROR, "系统异常");
			return;
		}
		sendLetter(userId, title, Formater.formatString(content, args));
	}

	@Override
	public Page<Letter> findAll(Long userId, Pageable pageable) {
		return letterDao.findByUserIdAndStatusNot(userId, LetterStatus.SC, pageable);
	}

	@Override
	public LetterContent findContentById(Long id) {
		return letterContentDao.findOne(id);
	}

	@Override
	@Transactional
	public void updateLetter(Letter letter) {
		letterDao.save(letter);
	}

	@Override
	public Integer getUnReadCount(Long userId) {
		return letterDao.getCountByUserIdAndStatus(userId, LetterStatus.WD);
	}
}
