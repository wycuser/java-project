package com.shanlin.p2p.app.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shanlin.p2p.app.dao.PushDao;
import com.shanlin.p2p.app.model.AppPushAttr;
import com.shanlin.p2p.app.model.AppPush;
import com.shanlin.p2p.app.service.PushService;

@Service
@Transactional(readOnly = true)
public class PushServiceImpl implements PushService {

	@Resource
	private PushDao pushDao;

	// 获取极光推送列表
	@Override
	public Map<String, Object> getPushList(Pageable pageable) {
		// 定义,初始化
		Page<AppPush> page = null;
		Map<String, Object> mapInner = null;
		Map<String, Object> returnMap = new HashMap<>(2);
		List<Map<String, Object>> list = new ArrayList<>();
		

		// 获取极光推送列表
		page = pushDao.getPushList(pageable);

		for (AppPush appPush : page.getContent()) {
			mapInner = new HashMap<>();
			List<Map<String, Object>> list2 = new ArrayList<>();
			mapInner.put("id", appPush.getId());
			mapInner.put("url", appPush.getUrl());
			mapInner.put("createId", appPush.getCreateId());
			mapInner.put("content", appPush.getContent());
			mapInner.put("sendCount", appPush.getSendCount());
			mapInner.put("sendType", appPush.getSendType());
			mapInner.put("title", appPush.getTitle());
			mapInner.put("jtype", appPush.getJtype());
			mapInner.put("jumpModule", appPush.getJumpModule());

			String createTimeStr = "";
			Object obj = appPush.getCreateTime();
			if (obj != null) {
				DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				createTimeStr = sdf.format(obj);
			}
			mapInner.put("createTime", createTimeStr);
			
			Set<AppPushAttr> appPushAttrs = appPush.getAppPushAttrs();
			for (AppPushAttr appPushAttr : appPushAttrs) {
				Map<String, Object> mapInner2 = new HashMap<>(3);
				mapInner2.put("id", appPushAttr.getId());
				mapInner2.put("key", appPushAttr.getJkey());
				mapInner2.put("value", appPushAttr.getJvalue());
				list2.add(mapInner2);
			}
			mapInner.put("list", list2);
			list.add(mapInner);
		}
		returnMap.put("content", list);
		returnMap.put("hasNext", page.hasNext() ? 1 : 0);
		return returnMap;
	}

	// 获取极光推送内容详情
	@Override
	public Map<String, Object> getPushContent(Long id) {
		// 定义,初始化
		Map<String, Object> returnMap = new HashMap<>(2);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		// 获取极光推送列表
		AppPush appPush = pushDao.findOne(id);

		returnMap.put("id", appPush.getId());
		returnMap.put("url", appPush.getUrl());
		returnMap.put("title", appPush.getTitle());
		returnMap.put("jtype", appPush.getJtype());
		returnMap.put("content", appPush.getContent());
		returnMap.put("createId", appPush.getCreateId());
		returnMap.put("sendType", appPush.getSendType());
		returnMap.put("sendCount", appPush.getSendCount());
		returnMap.put("jumpModule", appPush.getJumpModule());

		Object obj = appPush.getCreateTime();
		if (obj != null) {
			DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String createTimeStr = sdf.format(obj);
			returnMap.put("createTime", createTimeStr);
		}
		for (AppPushAttr appPushAttr : appPush.getAppPushAttrs()) {
			Map<String, Object> mapInner = new HashMap<>(3);
			mapInner.put("id", appPushAttr.getId());
			mapInner.put("key", appPushAttr.getJkey());
			mapInner.put("value", appPushAttr.getJvalue());
			list.add(mapInner);
		}
		returnMap.put("list", list);
		return returnMap;
	}
}
