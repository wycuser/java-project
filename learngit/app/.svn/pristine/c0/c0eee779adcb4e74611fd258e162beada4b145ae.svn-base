package com.shanlin.p2p.app.dao;

import org.springframework.data.jpa.repository.Query;

import com.shanlin.framework.persistence.BaseDao;
import com.shanlin.p2p.app.model.ExternalSystemLog;

public interface ExternalSystemLogDao extends BaseDao<ExternalSystemLog, Long>{

	@Query(nativeQuery=true, value="select id from S10.call_external_sys_log where api=5 and loginName=?1 and status = 1 and sendTime >= current_date() limit 1 for update")
	Long currentDateLogin(String loginName);
	
}
