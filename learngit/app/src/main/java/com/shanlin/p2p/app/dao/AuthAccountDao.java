package com.shanlin.p2p.app.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.shanlin.p2p.app.model.AuthAccount;

public interface AuthAccountDao extends JpaRepository<AuthAccount, Long> {

	@Query("select count(a) from AuthAccount a where a.denseIdentity = ?1")
	Integer getSizeByIdCard(String idCard);

	@Query(value="SELECT F03 FROM S71.T7122 WHERE F01 = ?1 AND F02 = ?2 limit 1", nativeQuery=true)
	String findStatusByIdCardAndUserName(String idCard, String userName);
	
	@Modifying
	@Query(value="INSERT INTO S71.T7122 SET F01 = ?1, F02 = ?2, F03 = ?3", nativeQuery=true)
	void insertIdCardMess(String idCard, String userName, String status);
	
	@Modifying
	@Query(value="INSERT INTO S71.T7124 SET F02 = ?1, F03 = ?2, F04 = ?3, F05 = ?4", nativeQuery=true)
	void insertIdCardLog(String idCard, String userName, String status, int result);
	
	List<AuthAccount> findByBornTimeIsNullAndIdentityIsNotNull();
}
