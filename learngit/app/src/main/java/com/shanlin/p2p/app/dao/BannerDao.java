package com.shanlin.p2p.app.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import com.shanlin.framework.persistence.BaseDao;
import com.shanlin.p2p.app.model.Banner;

public interface BannerDao extends BaseDao<Banner, Long> {
	
	@Query("select b from Banner b where b.type=1 and b.showTime < CURRENT_TIMESTAMP and b.unShowTime > CURRENT_TIMESTAMP order by b.sortIndex desc, b.lastUpdateTime desc")
	List<Banner> findAllBanner(Pageable pageable);
	
	@Query("select b from Banner b where b.type=1 and b.showTime < CURRENT_TIMESTAMP and b.unShowTime > CURRENT_TIMESTAMP order by b.sortIndex, b.lastUpdateTime desc")
	List<Banner> findAllBanner();
	
	@Query(value = "SELECT F02, F05, F06, F09, description FROM S50.T5015 WHERE F01 = ? AND F04 = 'YFB' LIMIT 1", nativeQuery = true)
	Object[][] findWzggById(Long id);

	@Query(value = "SELECT T5011.F06, T5011_1.F02, T5011.F13, T5011.description FROM S50.T5011 INNER JOIN S50.T5011_1 ON T5011.F01 = T5011_1.F01 WHERE T5011.F02 = 'MTBD' AND T5011.F01 = ? AND T5011.F05 = 'YFB' LIMIT 1", nativeQuery = true)
	Object[][] findMtbdById(Long id);

	@Query(value = "SELECT T5011.F06, T5011_1.F02, T5011.F13, T5011.description FROM S50.T5011 INNER JOIN S50.T5011_1 ON T5011.F01 = T5011_1.F01 WHERE T5011.F02 = 'WDHYZX' AND T5011.F01 = ? AND T5011.F05 = 'YFB' LIMIT 1", nativeQuery = true)
	Object[][] findWdhyzxById(Long id);
}
