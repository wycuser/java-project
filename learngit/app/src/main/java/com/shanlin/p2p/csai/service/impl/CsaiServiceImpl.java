package com.shanlin.p2p.csai.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.shanlin.framework.cache.SlMemcachedClient;
import com.shanlin.framework.mapper.JsonMapper;
import com.shanlin.framework.utils.StringHelper;
import com.shanlin.p2p.csai.controller.CsaiMainAction;
import com.shanlin.p2p.csai.util.CsaiZhtUtil;

/**
 * 希财主实现类
 * 
 * @author ice
 *
 */
@Service
@Transactional(readOnly = true)
public class CsaiServiceImpl {

	@Resource
	protected SlMemcachedClient memcachedClient;

	// 获取令牌(并款村)
	@SuppressWarnings("unchecked")
	public String getBaseAccessToken() {
		String accessToken = "";
		String url = "http://api.csai.cn/oauth2/access_token2";
		RestTemplate restTemplate = new RestTemplate();
		MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<String, Object>();
		try {
			// 获取缓存中的令牌
			accessToken = memcachedClient.get("csaiAccessToken");
			if (accessToken == null) {
				multiValueMap.add("client_id", CsaiMainAction.getShNo());
				multiValueMap.add("client_secret", CsaiMainAction.getShKey());
				Object obj = restTemplate.postForObject(url, multiValueMap, String.class);
				// 返回格式{access_token="",expires_in = 864000 }
				String result = StringHelper.getSafeAndTrim(obj);
				JsonMapper jsonMapper = JsonMapper.normalMapper();
				Map<String, Object> map = jsonMapper.fromJson(result, Map.class);

				int expiresIn = CsaiZhtUtil.getSafeInteger(map.get("expires_in"));
				String accessToKen = CsaiZhtUtil.getSafeAndTrim(map.get("access_token"));

				// 将希财的令牌存入缓存
				memcachedClient.set("csaiAccessToken", expiresIn, accessToKen);
				return accessToken;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return accessToken;
	}
}