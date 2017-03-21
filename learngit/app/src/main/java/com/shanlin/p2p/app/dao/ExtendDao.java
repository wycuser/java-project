package com.shanlin.p2p.app.dao;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.shanlin.framework.persistence.BaseDao;
import com.shanlin.p2p.app.model.Extend;
	
public interface ExtendDao extends BaseDao<Extend, Long> {

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query(value="SELECT num FROM S61.reward_count where id=1", nativeQuery=true)
	Integer lockRewardCount();
	
	@Modifying
	@Query(value="update S61.reward_count set num=num-1 where id=1", nativeQuery=true)
	void updRewardCount();
	
	@Modifying
	@Query(value="INSERT INTO S61.auth_record SET userId=?,parentId=?,createTime=CURRENT_TIMESTAMP", nativeQuery=true)
	void addAuthReward(Long userId,Long parentId);
	
	@Query(value="select count(1) from S61.auth_record A where parentId=?", nativeQuery=true)
	Integer authCount(Long parentId);
}
