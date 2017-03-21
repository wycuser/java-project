package com.shanlin.p2p.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.shanlin.p2p.app.dao.BidCreditRecordDao;
import com.shanlin.p2p.app.dao.BidRepaymentRecordDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:spring/applicationContext*.xml"})
@ActiveProfiles("development")
public class BidCreditRecordTest {
	
	@Resource
	private BidCreditRecordDao bidCreditRecordDao;
	
	@Resource
	private BidRepaymentRecordDao bidRepaymentRecordDao;
	@Test
	@Transactional
	public void getOne(){
		System.out.println(bidCreditRecordDao.getOne(1l));
		System.out.println(bidCreditRecordDao.findHoldPriceByUserId(31l));
		System.out.println(bidRepaymentRecordDao.findShouldPriceByUserId(31l));
	}
}
