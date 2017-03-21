package com.shanlin.p2p.test;

import java.util.Arrays;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.collect.Lists;
import com.shanlin.p2p.app.dao.InvestRecordDao;
import com.shanlin.p2p.app.model.enums.BidStatus;
import com.shanlin.p2p.app.service.AccountService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:spring/applicationContext*.xml"})
@ActiveProfiles("development")
public class AccountTest {
	
	@Resource
	private AccountService accountService;
	
	@Resource
	private InvestRecordDao investRecordDao;
	
	@Test
	public void testQuery(){
		accountService.findCreditorListByType(1, 2l, new PageRequest(1 - 1, 10, new Sort(Direction.DESC, "bid.extendAll.closeOffTime")));
		accountService.findCreditorListByType(2, 2l, new PageRequest(1 - 1, 10, new Sort(Direction.DESC, "createDate")));
		accountService.findCreditorListByType(3, 2l, new PageRequest(1 - 1, 10, new Sort(Direction.DESC, "bid.extendAll.closeOffTime")));
	}
	
	@Test
	public void testInvest(){
		Page<Object[]> objPage = investRecordDao.findSimpleByBidStatusAndUserAccount(Lists.newArrayList(BidStatus.DFK, BidStatus.TBZ), 2L, new PageRequest(0, 10));
		System.out.println(objPage.hasNext());
		for (Object[] objects : objPage) {
			System.out.println(Arrays.toString(objects));
		}
	}
}
