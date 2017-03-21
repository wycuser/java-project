package com.shanlin.p2p.app.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.shanlin.p2p.app.model.UserAccount;
import com.shanlin.p2p.app.model.enums.Judge;

public interface UserAccountDao extends JpaRepository<UserAccount, Long>{
	
	@Modifying
	@Query("update UserAccount u set u.email=?1 where u.id=?2")
	int updateEmailByUserId(String email, Long id);

	/**
	 * 使用登录名和密码查找用户
	 * @param loginName
	 * @param cryptPass
	 * @return
	 */
	UserAccount findByLoginNameAndPasswordAndIsDel(String loginName, String cryptPass, Judge IsDel);

	/**
	 * 查看手机号码存在的个数
	 * @param mobilePhone
	 * @return
	 */
	@Query("select count(u) from UserAccount u where u.mobilePhone=?1")
	Integer getSizeByMobilePhone(String mobilePhone);

	UserAccount findByLoginNameAndIsDel(String loginName, Judge IsDel);
	
	UserAccount findByLoginNameOrMobilePhone(String loginName, String mobilePhone);
	
	@Query(value="select count(reward_id) from S10.rewarduser where (reward_id = 9 or reward_id = 19) and login_name=?1", nativeQuery=true)
	Integer findRedPacketNum(String loginName);
	
	@Query("select count(u) from UserAccount u where u.loginName=?1")
	Integer getSizeByLoginName(String loginName);
	
	/**
	 * 用户理财统计表
	 */
	@Modifying
	@Query(value="insert into S61.T6115 set F01 = ?1", nativeQuery=true)
	void initFundStratist(Long userId);
	
	/**
	 * 用户信用账户表
	 */
	@Modifying
	@Query(value="insert into S61.T6116 set F01 = ?1", nativeQuery=true)
	void initCreditAccount(Long userId);
	
	/**
	 * 用户安全认证表
	 */
	@Modifying
	@Query(value="insert into S61.T6118 set F01 = ?1, F03=?2, F06=?3", nativeQuery=true)
	void initUserSafeStatus(Long userId, String phoneStatus, String mobilePhone);
	
	/**
	 * 个人基础信息
	 */
	@Modifying
	@Query(value="insert into S61.T6141 set F01 = ?1", nativeQuery=true)
	void initAuthAccount(Long userId);
	
	/**
	 * 推广奖励统计
	 */
	@Modifying
	@Query(value="insert into S63.T6310 set F01 = ?1", nativeQuery=true)
	void initPromoteReward(Long userId);
	
	/**
	 * 优选理财统计
	 */
	@Modifying
	@Query(value="insert into S64.T6413 set F01 = ?1", nativeQuery=true)
	void initSelectManage(Long userId);
	
	/**
	 * 信用档案
	 */
	@Modifying
	@Query(value="insert into S61.T6144 set F01 = ?1", nativeQuery=true)
	void initCreditFile(Long userId);
	
	/**
	 * 查询推广码数量
	 */
	@Query(value="select count(1) from S61.T6111 where F02=?1", nativeQuery=true)
	int getCodeNum(String code);
	
	/**
	 * 我的推广码
	 */
	@Modifying
	@Query(value="insert into S61.T6111 set F01 = ?1, F02 = ?2, F03=?3", nativeQuery=true)
	void initMyCode(Long userId, String code, String parentCode);
	
	/**
	 * 用户认证信息
	 */
	@Modifying
	@Query(value="insert into S61.T6120 set F01 = ?1, F02 = ?2", nativeQuery=true)
	void initUserAuthMess(Long userId, Long projectId);
	
	/**
	 * 首次充值奖励记录(T6311)
	 */
	@Modifying
	@Query(value="insert into S63.T6311 set F02 = ?1, F03 = ?2", nativeQuery=true)
	void initExtendRewardRecord(Long parentId, Long userId);
	
	/**
	 * 推广奖励统计更新
	 */
	@Modifying(clearAutomatically=true)
	@Query(value="update S63.T6310 set F02 = F02 + 1 where F01 = ?1", nativeQuery=true)
	void updateExtendRewardStatist(Long parentId);

	@Query("select count(u) from UserAccount u where u.email=?1")
	Integer getSizeByEmail(String email);

	UserAccount findByMobilePhone(String mobilePhone);

	UserAccount findByEmail(String email);

	@Query(value="SELECT F03 FROM S61.T6119 WHERE T6119.F01 = ?1 LIMIT 1", nativeQuery=true)
	String findHuiChaoPayIdByUserId(Long userId);

	@Query("select u.email from UserAccount u where u.email is not null")
	String[] findByEmailNotNull();
	
	@Query(value="SELECT a FROM UserAccount a WHERE a.part3Bs=?1")
	Page<UserAccount> findCsaiUsers(String part3Bs, Pageable pageable);
	
	@Query(value="SELECT a FROM UserAccount a WHERE a.part3Bs=?1 and ?2 >= a.registerTime")
	Page<UserAccount> findCsaiUsersByEndDate(String part3Bs, Date endDate, Pageable pageable);
	
	@Query(value="SELECT a FROM UserAccount a WHERE a.part3Bs=?1 and a.registerTime >= ?2")
	Page<UserAccount> findCsaiUsersByStartDate(String part3Bs, Date startDate, Pageable pageable);
	
	@Query(value="SELECT a FROM UserAccount a WHERE a.part3Bs=?1 and ?3 >= a.registerTime and a.registerTime >= ?2")
	Page<UserAccount> findCsaiUsersByBetweenDate(String part3Bs, Date startDate, Date endDate, Pageable pageable);
	
	@Query(value="SELECT a FROM UserAccount a WHERE a.part3Bs=?1 and (a.mobilePhone=?2 or a.loginName=?3)")
	UserAccount findCsaiUser(String part3Bs, String phone, String name);
	
	/**
	 * 推广人Id
	 * @param id
	 * @return
	 */
	@Query(value="SELECT F01 FROM S61.T6111 WHERE F02=(SELECT F03 FROM S61.T6111 WHERE F01=?1)",nativeQuery=true)
	Long getParentId(Long id);

	@Query(value="select n.* from (SELECT c.F02 'loginname',d.F02 'name',(select sum(b.F04) from S62.T6250 b where b.F03 = a.F01) 'amount' FROM S61.T6111 a, S61.T6110 c, S61.T6141 d, S61.T6111 e WHERE a.F01 = c.F01 AND a.F01 = d.F01 AND a.F03 = e.F02 AND e.F01 = ?1 GROUP BY c.F02,d.F02 ORDER BY c.F09 DESC) n limit ?2, ?3",nativeQuery=true)
	List<Object[]> findExtendData(Long userId, int page, int size);
	
	@Query(value="select count(*) from (SELECT c.F02 'loginname',d.F02 'name' FROM S61.T6111 a, S61.T6110 c, S61.T6141 d, S61.T6111 e WHERE a.F01 = c.F01 AND a.F01 = d.F01 AND a.F03 = e.F02 AND e.F01 = ?1 GROUP BY c.F02,d.F02 ORDER BY c.F09 DESC) n",nativeQuery=true)
	long findExtendDataCount(Long userId);
}
