package com.shanlin.p2p.test;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.shanlin.p2p.app.dao.BidDao;
import com.shanlin.p2p.app.model.UserAccount;
import com.shanlin.p2p.app.service.FinancingService;
import com.shanlin.p2p.app.service.UserAccountService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:spring/applicationContext*.xml"})
@ActiveProfiles("development")
public class BidTest {
	
	@Resource
	private BidDao bidDao;
	
	@Resource
	private FinancingService financingService;
	
	@Resource
	private UserAccountService userAccountService;
	
	@Test
	public void getOne(){
		System.out.println(bidDao.findOne(25l));
		financingService.invest(90l, userAccountService.findOne(2l), new BigDecimal(100));
	}
}
