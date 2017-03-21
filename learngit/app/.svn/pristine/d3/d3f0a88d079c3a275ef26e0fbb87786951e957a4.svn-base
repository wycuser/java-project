package com.shanlin.p2p.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.shanlin.framework.persistence.BaseDao;
import com.shanlin.p2p.app.model.SxbaoConfig;

public interface SxbaoConfigDao extends BaseDao<SxbaoConfig, Long> {

	@Query("select c from SxbaoConfig c join fetch c.bid where c.type.id=?")
	List<SxbaoConfig> findByTypeId(Long typeId);
	
	@Query("select c from SxbaoConfig c join fetch c.bid where c.id=? and c.type.sellStatus='S'")
	SxbaoConfig findActivityById(Long id);
}
