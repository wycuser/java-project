package com.shanlin.p2p.app.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.shanlin.p2p.app.model.Banner;

public interface HomeService {

	/**
	 * 获取首页banner
	 * @param pageable
	 * @return
	 */
	List<Banner> findBannerByPage(Pageable pageable);
	
	/**
	 * 获取首页所有banner
	 * @return
	 */
	List<Banner> findAllBanner();

	/**
	 * 获取网站公告
	 * @param id
	 * @return
	 */
	Object[][] findWzggById(Long id);
	
	/**
	 * 获取媒体报道
	 * @param id
	 * @return
	 */
	Object[][] findMtbdById(Long id);
	
	/**
	 * 获取行业资讯
	 * @param id
	 * @return
	 */
	Object[][] findWdhyzxById(Long id);
}
