package com.shanlin.p2p.test;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.shanlin.p2p.app.service.ExperienceBidService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:spring/applicationContext*.xml"})
@ActiveProfiles("development")
public class ExperienceBidServiceTest {
	
	@Resource
	private ExperienceBidService experienceBidService;
	
	@Test
	public void testExperienceBid(){
//		ExperienceBid bid =  experienceBidService.findById(1l);
//		ExperienceBid bid =  experienceBidService.findFirstExperienceBid();
//		System.out.println(bid.getId());
//		userAction.login("zhengxin", "11111");
		
		experienceBidService.invest(8l, 2l, new BigDecimal(1000));
	}
}
