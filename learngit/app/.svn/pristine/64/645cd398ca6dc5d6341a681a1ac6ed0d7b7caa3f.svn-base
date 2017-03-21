package com.shanlin.p2p.test;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.shanlin.p2p.app.dao.BidCreditRecordDao;
import com.shanlin.p2p.app.dao.BidRepaymentRecordDao;
import com.shanlin.p2p.app.dao.CreditAssignmentApplyDao;
import com.shanlin.p2p.app.dao.CreditAssignmentRecordDao;
import com.shanlin.p2p.app.model.BidCreditRecord;
import com.shanlin.p2p.app.model.CreditAssignmentApply;
import com.shanlin.p2p.app.model.enums.CreditAssignmentStatus;
import com.shanlin.p2p.app.model.enums.Judge;
import com.shanlin.p2p.app.service.CreditAssignmentApplyService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:spring/applicationContext*.xml"})
@ActiveProfiles("test")
public class ZqzrTest {

	@Autowired
	private CreditAssignmentApplyDao applyDao;
	
	@Autowired
	private CreditAssignmentApplyService applyService;
	
	@Autowired
	private CreditAssignmentRecordDao recordDao;
	
	@Autowired
	private BidCreditRecordDao bidCreditRecordDao;
	
	@Resource
	private BidRepaymentRecordDao bidRepaymentRecordDao;
	
	@Test
	@Transactional
	public void testGm(){
		applyService.purchase(21L, 2L);
	}
	
	@Test
	public void testSelect(){
		Pageable pageable = new PageRequest(0, 10, new Sort(Direction.DESC,"createTime"));
//		Pageable pageable = new PageRequest(1, 10, new Sort(Direction.DESC,"T6231.F12"));
		Page<Object[]> page = applyDao.findZqzrzList(3L, pageable);
		System.out.println(page.getTotalElements());
	}
	
	@Test
	public void testZqkzc(){
		//可转让的债权
		System.out.println();
		Date date1=new DateTime().plusDays(3).toDate();
		Date date2=new DateTime().plusDays(0).toDate();
		Pageable pageable = new PageRequest(0, 10, new Sort(Direction.DESC,"bid.extend.loanTime"));
		Page<Object[]> page = bidCreditRecordDao.findZqkzcList(4L,date1, date2, pageable);
		System.out.println("可转让的债权 testZqkzc()>>>>"+page.getTotalElements());
	}
	
	@Test
	public void testZqyzc(){
		//已转出的债权
		Pageable pageable = new PageRequest(0, 10, new Sort(Direction.DESC,"buyTime"));
		Page<Object[]> page = recordDao.findZqyzcList(4L, pageable);
		System.out.println("已转出的债权 testZqyzc()>>>>"+page.getTotalElements());
	}
	
	@Test
	public void testZqyzr(){
		//已转入的债权
		Pageable pageable = new PageRequest(0, 10, new Sort(Direction.DESC,"buyTime"));
		Page<Object[]> page = recordDao.findZqyzrList(4L, pageable);
		for (Object[] objects : page) {
			System.out.println("objects>>"+objects[0]+" "+objects[1]+" "+objects[2]);
		}
		System.out.println("已转入的债权 testZqyzr()>>>>"+page.getTotalElements());
	}
	
	
	@Test
	public void testObject(){
		//已转入的债权
		CreditAssignmentApply entity = applyDao.findByCreditRecordId(9L);
		System.out.println("已转入的债权 testZqyzr()>>>>"+entity.toString());
	}
	
	@Test
	public void testObject2(){
		//已转入的债权
		recordDao.findByCreditAssignmentApplyId(1L);
//		System.out.println("已转入的债权 testZqyzr()>>>>"+entity.toString());
	}
	
	@Test
	public void testsave(){
		//已转入的债权
		CreditAssignmentApply apply=new CreditAssignmentApply();
		BidCreditRecord creditRecord=bidCreditRecordDao.findOne(15L);
		creditRecord.setIsMakeOverNow(Judge.S);
		apply.setPrice(BigDecimal.valueOf(11.1));
		apply.setOriginalPrice(BigDecimal.valueOf(11.1));
		apply.setServiceCharge(BigDecimal.valueOf(0.1));
		apply.setCreditRecord(creditRecord);
		apply.setStatus(CreditAssignmentStatus.ZRZ);
		apply.setCreateTime(new Date());
		CreditAssignmentApply entity = applyDao.save(apply);
		bidCreditRecordDao.save(creditRecord);
		System.out.println(" testsave()>>>>"+entity.getId());
	}
	
	@Test
	public void testgetDate(){
		Date nextDate = bidRepaymentRecordDao.findEndDateByCreditId(17L);
		System.out.println(">>>>nextDate:"+nextDate);
	}
	
	@Resource
	private CreditAssignmentApplyService creditAssignmentApplyService;
	
	@Test
	public void testzr(){
		creditAssignmentApplyService.transfer(21L, new BigDecimal("23"));
	}
	@Test
	public void testPTID(){
		System.out.println(applyDao.getCurrentTimestamp());
	}
	
	
}
