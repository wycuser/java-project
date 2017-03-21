package com.shanlin.p2p.app.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shanlin.p2p.app.model.Letter;
import com.shanlin.p2p.app.model.enums.LetterStatus;

public interface LetterDao extends JpaRepository<Letter, Long> {

	Page<Letter> findByUserIdAndStatusNot(Long userId, LetterStatus status, Pageable page);
	
	@Query("select count(l) from Letter l where l.userId=?1 and l.status=?2")
	Integer getCountByUserIdAndStatus(Long userId, LetterStatus wd);

}
