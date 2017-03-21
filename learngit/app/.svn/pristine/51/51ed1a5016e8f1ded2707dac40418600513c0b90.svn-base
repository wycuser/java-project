package com.shanlin.p2p.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shanlin.p2p.app.model.UserExtendCode;

public interface UserExtendCodeDao extends JpaRepository<UserExtendCode, Long> {

	UserExtendCode findByMyCode(String pCode);

	@Query(value="select b.* from S61.STAFF a, S61.T6111 b where b.F02 = a.ucode and (a.moblie=:code or a.jobNo=:code)", nativeQuery=true)
	UserExtendCode findByInteriorCode(@Param("code") String parentCode);

	@Query(value="select moblie ,jobNo from S61.STAFF where ucode=?1 limit 1", nativeQuery=true)
	Object findInteriorCode(String myCode);

}
