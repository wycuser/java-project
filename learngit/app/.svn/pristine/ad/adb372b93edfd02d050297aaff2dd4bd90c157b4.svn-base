package com.shanlin.p2p.app.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shanlin.p2p.app.constant.Constants;
import com.shanlin.p2p.app.dao.UserLinesDao;
import com.shanlin.p2p.app.model.UserLines;
import com.shanlin.p2p.app.service.UserLinesService;

@Service
@Transactional(readOnly = true)
public class UserLinesServiceImpl implements UserLinesService {

	@Resource
	private UserLinesDao userLinesDao;

	@Override
	public Map<String, Object> handleUserLines(Long userId, BigDecimal money) {
		Map<String, Object> mapRe = new HashMap<String, Object>();
		UserLines userLines = userLinesDao.LockFindUserLines(userId);
		BigDecimal invelines = new BigDecimal(0);
		if (userLines != null) {

			money = money.multiply(new BigDecimal(1.2));
			BigDecimal bliner = userLines.getLinesr().add(money);
			invelines = money;
			userLinesDao.updUserLines(bliner, userId);

		} else {
			money = money.multiply(new BigDecimal(1.2));
			invelines = money;
			userLinesDao.insertUserLines(userId, money);
		}

		mapRe.put("invelines", invelines);
		return mapRe;

	}
	
	//计算提现手续费
	@Override
	public Map<String, Object> withdrawleUserLines(Long userId,BigDecimal amount,String _proportion) {
		Map<String, Object> mapRe = new HashMap<String, Object>();
		BigDecimal poundage = new BigDecimal(0);
		//投资的提现额度
		BigDecimal bci=userLinesDao.findByUserLines(userId);
		//是否是借款用户 ，不为null则表示是借款用户
		Integer inBorrow = userLinesDao.findByBorrow(userId);
		
		
		if(inBorrow!=null){
			poundage = amount.multiply(new BigDecimal(_proportion)).setScale(2, BigDecimal.ROUND_HALF_UP);
		}else{
		  if(bci!=null ){
			if(amount.compareTo(bci) > 0){
				BigDecimal userInvest = amount.subtract(bci);
			    poundage = (bci.multiply(new BigDecimal(_proportion)).add(userInvest.multiply(new BigDecimal(0.005)))).setScale(2, BigDecimal.ROUND_HALF_UP);
			}else{
			    poundage = (amount.multiply(new BigDecimal(_proportion))).setScale(2, BigDecimal.ROUND_HALF_UP);
				
			}
			
		   }else{
			    poundage = amount.multiply(new BigDecimal(0.005)).setScale(2, BigDecimal.ROUND_HALF_UP);
		   }
		}

		mapRe.put("poundage", poundage);
		return mapRe;

	}
	
	
}