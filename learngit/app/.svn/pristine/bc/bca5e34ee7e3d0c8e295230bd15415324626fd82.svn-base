package com.shanlin.p2p.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.shanlin.p2p.app.model.LetterContent;

public interface LetterContentDao extends JpaRepository<LetterContent, Long>{
	
	@Modifying
	@Query(value="insert into S61.T6124 values(?1,?2)", nativeQuery=true)
	void init(Long id, String content);
}
