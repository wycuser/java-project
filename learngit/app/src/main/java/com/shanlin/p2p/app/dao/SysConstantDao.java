package com.shanlin.p2p.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shanlin.p2p.app.model.SysConstant;

public interface SysConstantDao extends JpaRepository<SysConstant, String> {

	@Query("select t.value from SysConstant t where t.id=?1")
	String findValueById(String id);
}
