package com.shanlin.p2p.app.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import com.shanlin.framework.persistence.BaseDao;
import com.shanlin.p2p.app.model.AppPush;

public interface PushDao extends BaseDao<AppPush, Long> {

	// 获取极光推送列表
	@Query("select a from AppPush a")
	Page<AppPush> getPushList(Pageable pageable);
}