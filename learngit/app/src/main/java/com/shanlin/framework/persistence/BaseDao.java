package com.shanlin.framework.persistence;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface BaseDao<T, ID extends Serializable> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

	@Query(value="SELECT CURRENT_DATE()",nativeQuery=true)
	Date getCurrentDate();
	
	@Query(value="SELECT CURRENT_TIMESTAMP()",nativeQuery=true)
	Timestamp getCurrentTimestamp();
	
	@Query(value="SELECT F01 FROM S71.T7101 LIMIT 1",nativeQuery=true)
	Long getPTID();
	
	@Query(value="SELECT IFNULL(SUM(F02),0) FROM S10._1010 WHERE F01='SYSTEM.ZQZRGLF_RATE' LIMIT 1",nativeQuery=true)
	BigDecimal getZqzrFee();
	
	@Query(value="SELECT F02 FROM S62.T6211 WHERE F01=? LIMIT 1",nativeQuery=true)
	String getBidType(Long id);
}
