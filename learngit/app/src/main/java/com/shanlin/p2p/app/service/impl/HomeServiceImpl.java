package com.shanlin.p2p.app.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shanlin.p2p.app.dao.BannerDao;
import com.shanlin.p2p.app.model.Banner;
import com.shanlin.p2p.app.service.HomeService;

@Service
@Transactional(readOnly=true)
public class HomeServiceImpl implements HomeService {

	@Resource
	private BannerDao bannerDao;
	
	@Override
	public List<Banner> findBannerByPage(Pageable pageable){
		return bannerDao.findAllBanner(pageable);
	}

	@Override
	public List<Banner> findAllBanner() {
		return bannerDao.findAllBanner();
	}

	@Override
	public Object[][] findWzggById(Long id) {
		return bannerDao.findWzggById(id);
	}

	@Override
	public Object[][] findMtbdById(Long id) {
		return bannerDao.findMtbdById(id);
	}

	@Override
	public Object[][] findWdhyzxById(Long id) {
		return bannerDao.findWdhyzxById(id);
	}

}