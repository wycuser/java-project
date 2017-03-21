package com.shanlin.p2p.app.dao;

import org.springframework.data.jpa.repository.Query;

import com.shanlin.framework.persistence.BaseDao;
import com.shanlin.p2p.app.model.LoanApply;

public interface LoanApplyDao extends BaseDao<LoanApply, Long>{

	/** 查询3天内是否有申请记录 */
	@Query(value="SELECT count(1) from S62.loan_apply where userId=? and date_sub(curdate(),INTERVAL 2 DAY)<=createTime",nativeQuery=true)
	int getLoansCount(Long userId);
}
