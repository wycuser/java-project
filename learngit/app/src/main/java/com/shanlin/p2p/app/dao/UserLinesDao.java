package com.shanlin.p2p.app.dao;

import java.math.BigDecimal;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.shanlin.p2p.app.model.UserLines;

public interface UserLinesDao extends JpaRepository<UserLines, Long>{
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select S from UserLines S  where S.userId=?1")
	UserLines LockFindUserLines(Long id);
	
	
	@Query(value="select linesr from S61.userlines s  where s.userid=?1 limit 1", nativeQuery=true)
	BigDecimal findByUserLines(Long id);
	
	@Query(value="select F01 from S61.T6110 where F01 = ?1 and F02  in ( select name from S61.borrow where borrow = 1 ) LIMIT 1", nativeQuery=true)
	Integer findByBorrow(Long id);
	
	@Modifying
	@Query(value="UPDATE S61.userlines SET linesr = ?1 WHERE userid = ?2", nativeQuery=true)
	void updUserLines(BigDecimal linesr, Long userid);
	
	@Modifying
	@Query(value="INSERT INTO S61.userlines SET userid = ?1, linesr = ?2", nativeQuery=true)
	void insertUserLines(Long userid,BigDecimal linesr);

}
