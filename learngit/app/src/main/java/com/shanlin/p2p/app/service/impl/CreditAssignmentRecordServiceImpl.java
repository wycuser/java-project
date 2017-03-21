package com.shanlin.p2p.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.shanlin.p2p.app.dao.CreditAssignmentRecordDao;
import com.shanlin.p2p.app.service.CreditAssignmentRecordService;

/**
 * 债权转让记录业务实现类
 * @author yangjh
 *
 */
public class CreditAssignmentRecordServiceImpl implements
		CreditAssignmentRecordService {
	
	@Autowired
	private CreditAssignmentRecordDao recordDao; 


}
