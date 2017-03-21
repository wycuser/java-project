package com.shanlin.p2p.test;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.shanlin.framework.mapper.BeanMapper;
import com.shanlin.framework.mapper.JsonMapper;
import com.shanlin.p2p.app.dao.LetterContentDao;
import com.shanlin.p2p.app.dao.LetterDao;
import com.shanlin.p2p.app.model.Letter;
import com.shanlin.p2p.app.model.enums.LetterStatus;
import com.shanlin.p2p.app.service.LetterService;
import com.shanlin.p2p.app.vo.LetterVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:spring/applicationContext*.xml"})
@ActiveProfiles("development")
public class LetterTest {
	
	@Resource
	private LetterContentDao letterContentDao;
	
	@Resource
	private LetterDao letterDao;
	
	@Resource
	private LetterService letterService;
	
	@Transactional
	@Test
	public void save(){
//		Letter letter = new Letter();
//		letter.setSendTime(new Date());
//		letter.setStatus(LETTER_STATUS.WD);
//		letter.setTitle("测试");
//		letter.setUserId(31l);
//		letter = letterDao.save(letter);
//		LetterContent content = new LetterContent();
//		content.setContent("ceshiceshi");
//		content.setLetter(letter);
//		letterContentDao.save(content);
		letterService.sendLetter(31l, "test", "ceshi");
	}
	
	@Test
	public void convent(){
		Letter letter = new Letter();
		letter.setSendTime(new Date());
		letter.setStatus(LetterStatus.WD);
		letter.setTitle("注册成功");
		LetterVO vo = BeanMapper.map(letter, LetterVO.class);
		System.out.println(JsonMapper.nonDefaultMapper().toJson(vo));
		System.out.println(vo);
	}
	
	@Test
	public void test(){
		System.out.println(letterService.getUnReadCount(31l));
	}
}
