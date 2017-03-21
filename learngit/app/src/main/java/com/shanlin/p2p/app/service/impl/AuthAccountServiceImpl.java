package com.shanlin.p2p.app.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shanlin.framework.security.utils.Cryptos;
import com.shanlin.framework.utils.HttpUtil;
import com.shanlin.p2p.app.constant.Constants;
import com.shanlin.p2p.app.dao.AuthAccountDao;
import com.shanlin.p2p.app.model.AuthAccount;
import com.shanlin.p2p.app.service.AuthAccountService;

/**
 * 实名认证接口实现类
 * 
 * @author yangjh
 * @time 2015年1月14日
 */
@Service
@Transactional(readOnly = true)
public class AuthAccountServiceImpl implements AuthAccountService {

	private static final Logger log = LoggerFactory.getLogger(AuthAccountServiceImpl.class);

	@Resource
	private AuthAccountDao authAccountDao;

	private static HashMap<Integer, String> cmap = new HashMap<Integer, String>();

	static {
		cmap.put(11, "参数不正确");
		cmap.put(12, "商户余额不足");
		cmap.put(13, "appkey不存在");
		cmap.put(14, "IP被拒绝");
		cmap.put(20, "身份证中心维护中");
	}

	@Override
	public AuthAccount findOne(Long id) {
		return authAccountDao.findOne(id);
	}

	@Override
	@Transactional
	public void updateIdCard(AuthAccount authAccount) {
		String idCard = authAccount.getIdentity();
		int insex = -1;
		Integer msidcard = Integer.parseInt(idCard.substring(idCard.length() - 2, idCard.length() - 1));
		if (msidcard % 2 == 0) {
			insex = 0;
		} else {
			insex = 1;
		}
		authAccount.setDenseIdentity(Cryptos.aesEncrypt(idCard, Constants.SYS_AES_KEY));
		authAccount.setIdentity(idCard.substring(0, 2) + "***************");
		authAccount.setSex(Integer.valueOf(insex));
		authAccountDao.save(authAccount);
	}

	@Override
	public boolean isExistIdCard(String idCard) {
		return authAccountDao.getSizeByIdCard(Cryptos.aesEncrypt(idCard, Constants.SYS_AES_KEY)).intValue() > 0;
	}

	@Override
	@Transactional
	public boolean check(String idCard, String userName) {
		String status = authAccountDao.findStatusByIdCardAndUserName(idCard, userName);
		if ("TG".equalsIgnoreCase(status))
			return true;
		if ("SB".equalsIgnoreCase(status))
			return false;

		log.info("====== idCard check start ======");
		boolean tag = false;
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("appkey", "d0a9b6c7a54573c5056d8d0cab269cb8");
		map.put("name", userName);
		map.put("cardno", idCard);
		log.info("send idCard map:" + map);
		Map<String, Object> responseMap = HttpUtil.create().post("http://api.id98.cn/api/idcard", map).execute2Map();
		log.info("====== idCard check send end resultJson：{}", responseMap);
		int isok = (int) responseMap.get("isok");
		int code = (int) responseMap.get("code");
		int logStatus = 0;
		if (isok == 1 && code == 1) {
			tag = true;
		} else if (isok == 1 && code == 2) {
			//2 查询不一致
			log.error("身份证号和姓名查询不一致");
			logStatus = 1;
		} else if (isok == 1 && code == 3) {
			//无此身份证号码
			log.error("无此身份证号码");
			logStatus = 2;
		} else if (isok == 0) {
			log.error(cmap.get(Integer.valueOf(code)));
		}
		//收费写入日志
		if (isok == 1) {
			authAccountDao.insertIdCardMess(idCard, userName, tag ? "TG" : "SB");
			authAccountDao.insertIdCardLog(idCard, userName, tag ? "TG" : "SB", logStatus);
		}
		return tag;
	}

}
