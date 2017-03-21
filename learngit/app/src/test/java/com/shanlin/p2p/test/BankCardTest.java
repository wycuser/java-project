package com.shanlin.p2p.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.shanlin.p2p.app.dao.BankCardDao;
import com.shanlin.p2p.app.model.BankCard;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:spring/applicationContext*.xml"})
@ActiveProfiles("development")
public class BankCardTest {
	
	@Resource
	private BankCardDao bankCardDao;
	
	@Test
	@Transactional
	public void get(){
		BankCard card = bankCardDao.findOne(1l);
//		List<Map<String, Object>> lists = bankCardDao.findSimpleByUserId(1l);
//		System.out.println(lists);
//		System.out.println(lists.get(0));
	}
}
