package com.shanlin.p2p.app.service.impl;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shanlin.framework.controller.InterfaceResultCode;
import com.shanlin.framework.exception.ServiceException;
import com.shanlin.framework.utils.StringHelper;
import com.shanlin.framework.utils.ValidateUtil;
import com.shanlin.p2p.app.dao.BankCardDao;
import com.shanlin.p2p.app.model.Area;
import com.shanlin.p2p.app.model.Bank;
import com.shanlin.p2p.app.model.BankCard;
import com.shanlin.p2p.app.model.enums.RealAuthIspass;
import com.shanlin.p2p.app.model.enums.UseStatus;
import com.shanlin.p2p.app.service.BankCardService;

@Service
@Transactional(readOnly = true)
public class BankCardServiceImpl implements BankCardService {
	
	@Resource
	private BankCardDao bankCardDao;

	@Override
	public Map<String, Object> findBankCardList(Long userId, int page, int size) {
		Pageable pageable = new PageRequest(page - 1, size, new Sort(Direction.ASC, "id"));
		Page<BankCard> pageList=bankCardDao.findBankCardPage(userId, UseStatus.QY,pageable);
		Map<String, Object> returnMap = new LinkedHashMap<>();
		returnMap.put("count", pageList.getTotalElements());
		returnMap.put("hasNext", pageList.hasNext() ? 1 : 0);
		returnMap.put("content", pageList.getContent());
		return returnMap;
	}

	@Transactional
	@Override
	public void opBankCard(Long userId,Long bankId,Long areaId,String openingName,String number,Long id) {
		BankCard bankCard=null;
		if(null!=id && id.intValue()>0){
			bankCard=bankCardDao.findByIdAndStatus(id, UseStatus.QY);
		}else{
			if(StringHelper.isEmpty(number) || !ValidateUtil.checkBankCard(number)){
				throw new ServiceException(InterfaceResultCode.FAILED, "银行卡错误");
			}
			String decode_num=null;
			try {
				decode_num=StringHelper.encode(number);
			} catch (Throwable e1) {}
			bankCard=bankCardDao.findByCipherNumber(decode_num,UseStatus.QY);
			if (null!=bankCard){
				throw new ServiceException(InterfaceResultCode.FAILED, "当前银行卡号已存在");
			}
			bankCard=new BankCard();
			bankCard.setCreateTime(new Date());
			bankCard.setSafeStatus(RealAuthIspass.TG);
			bankCard.setStatus(UseStatus.QY);
			StringBuilder sb =  new StringBuilder();
        	sb.append(number.substring(0,3));
        	sb.append("*************");
        	sb.append(number.substring(number.length()-4,number.length()));
        	bankCard.setNumber(sb.toString());
			bankCard.setCipherNumber(decode_num);
		}
		bankCard.setUserId(userId);
		bankCard.setBank(new Bank(bankId));
		bankCard.setArea(new Area(areaId));
		bankCard.setOpeningName(openingName);
		bankCardDao.save(bankCard);
	}

	@Override
	public BankCard findBankCard(Long id) {
		return bankCardDao.findByIdAndStatus(id, UseStatus.QY);
	}

	@Transactional
	@Override
	public void delBankCard(Long id) {
		bankCardDao.delete(id);
	}
}