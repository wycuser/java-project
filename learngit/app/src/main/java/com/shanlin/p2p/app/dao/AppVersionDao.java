package com.shanlin.p2p.app.dao;

import com.shanlin.framework.persistence.BaseDao;
import com.shanlin.p2p.app.model.AppVersion;

public interface AppVersionDao extends BaseDao<AppVersion, Long> {
	
	AppVersion findFirstByTypeOrderByVersionCodeDesc(String type);
}
