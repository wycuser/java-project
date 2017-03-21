package com.shanlin.p2p.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.shanlin.p2p.app.dao.CreditAssignmentApplyDao;
import com.shanlin.p2p.app.model.CreditAssignmentApply;
import com.shanlin.p2p.app.model.enums.CreditAssignmentStatus;
import com.shanlin.p2p.app.model.enums.Judge;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:spring/applicationContext*.xml"})
@ActiveProfiles("test")
public class CreditAssignmentApplyTest {
	
	@Resource
	private CreditAssignmentApplyDao creditAssignmentApplyDao;
	
	@Test
	public void test(){
//		List<CreditAssignmentApply> apply = creditAssignmentApplyDao.findByStatusAndCreditRecordIsMakeOverNow(CREDIT_ASSIGNMENT_STATUS.ZRZ, JUDGE.S);
//		System.out.println(apply.size());
//		for (CreditAssignmentApply creditAssignmentApply : apply) {
//			System.out.println(creditAssignmentApply.getCreditRecord().getBid().getAmount());
//		}
		CreditAssignmentApply apply = creditAssignmentApplyDao
				.findFirstByStatusAndCreditRecordIsMakeOverNow(CreditAssignmentStatus.ZRZ, Judge.S);
		System.out.println(apply);
	}
}
