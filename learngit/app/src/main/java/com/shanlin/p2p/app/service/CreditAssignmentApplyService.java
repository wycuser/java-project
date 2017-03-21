package com.shanlin.p2p.app.service;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.shanlin.p2p.app.model.CreditAssignmentApply;

public interface CreditAssignmentApplyService {
	
	CreditAssignmentApply findFirstZrzAssignment();
	
	Page<CreditAssignmentApply> findZrzAssignmentByPage(Pageable pageable);

	Map<String, Object> findSimpleAssignmentByType(Long userId, int type, int page, int size);
	
	Map<String, Object> findByIdAndType(Long id,int type);
	
	void transfer(Long zqId,BigDecimal price);
	
	Map<String, Object> findById(Long zqId);
	
	void purchase(Long zqId,Long userId);
	
	void cancel(Long zqId);
	
	Map<String, Object> findSimpleAssignmentByTypeV2(Long userId, int type, int page, int size);
}
