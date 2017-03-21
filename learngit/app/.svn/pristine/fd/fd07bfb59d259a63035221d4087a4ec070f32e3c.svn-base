package com.shanlin.p2p.app.dao;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shanlin.p2p.app.model.FundAccountFlow;
import com.shanlin.p2p.app.model.enums.FundAccountType;

public interface FundAccountFlowDao extends JpaRepository<FundAccountFlow, Long>{
	
	List<FundAccountFlow> findByFundAccountId(Long id);
	
	/**
	 * 获取用户当天收益
	 * 利息,逾期罚息,提前还款违约金,有效推广
	 * @param userId
	 * @return
	 */
	@Query(value="SELECT SUM(T6102.F06) "
			+ "from S61.T6102 "
			+ "WHERE T6102.F03 in(7002,7004,7005,7008,9001,9003) "
			+ "AND T6102.F02 = (SELECT T6101.F01 FROM S61.T6101 WHERE T6101.F02 = ?1 AND T6101.F03 = 'WLZH') "
			+ "AND date(T6102.F05) = curdate()", nativeQuery=true)
	BigDecimal findCurrentDateLucreByUserId(Long userId);
	
	/**
	 * 获取用户收益
	 * 利息,逾期罚息,提前还款违约金,有效推广
	 * @param userId
	 * @return
	 */
	@Query(value="SELECT SUM(T6102.F06) "
			+ "from S61.T6102 "
			+ "WHERE T6102.F03 in(7002,7004,7005,7008,9001,9003) "
			+ "AND T6102.F02 = (SELECT T6101.F01 FROM S61.T6101 WHERE T6101.F02 = :userId AND T6101.F03 = 'WLZH')"
			, nativeQuery=true)
	BigDecimal findLucreByUserId(@Param("userId") Long userId);
	
	/**
	 * 获取用户当天收益
	 * 利息,逾期罚息,提前还款违约金,有效推广,惊喜奖返现,体验金利息
	 * @param userId
	 * @return
	 */
	@Query(value="SELECT SUM(T6102.F06) "
			+ "from S61.T6102 "
			+ "WHERE T6102.F03 in(7002,7004,7005,7008,9001,9003) "
			+ "AND T6102.F02=?1 "
			+ "AND date(T6102.F05) = curdate()", nativeQuery=true)
	BigDecimal findCurrentDateLucreByAccountId(Long accountId);
	
	/**
	 * 获取用户收益
	 * 利息,逾期罚息,提前还款违约金,有效推广,惊喜奖返现,体验金利息
	 * @param userId
	 * @return
	 */
	@Query(value="SELECT SUM(T6102.F06) "
			+ "from S61.T6102 "
			+ "WHERE T6102.F03 in(7002,7004,7005,7008,9001,9003) "
			+ "AND T6102.F02=?1"
			, nativeQuery=true)
	BigDecimal findLucreByAccountId(Long accountId);

	@Query("select f from FundAccountFlow f where f.fundAccount.userId=?1 and f.fundAccount.type=?2")
	Page<FundAccountFlow> findByUserIdAndFundAccountType(Long userId, FundAccountType wlzh, Pageable pageable);
}
