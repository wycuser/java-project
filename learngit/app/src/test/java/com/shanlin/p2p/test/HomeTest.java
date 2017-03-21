package com.shanlin.p2p.test;

import java.util.List;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.shanlin.framework.mapper.JsonMapper;
import com.shanlin.p2p.app.model.Banner;
import com.shanlin.p2p.app.service.HomeService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:spring/applicationContext*.xml"})
@ActiveProfiles("development")
public class HomeTest {

	@Resource
	private HomeService homeService;
	
	@Test
	public void testBanner(){
		List<Banner> banners = homeService.findBannerByPage(new PageRequest(0, 5));
		System.out.println(banners);
		System.out.println(JsonMapper.nonDefaultMapper().toJson(banners));
		System.out.println(System.currentTimeMillis());
	}
	
	public static void main(String[] args) {
		System.out.println(System.currentTimeMillis());
		System.out.println(System.currentTimeMillis());
	}
}
