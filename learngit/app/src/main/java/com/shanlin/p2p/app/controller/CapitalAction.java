package com.shanlin.p2p.app.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shanlin.framework.controller.BaseAction;
import com.shanlin.framework.mapper.BeanMapper;
import com.shanlin.framework.utils.Formater;
import com.shanlin.p2p.app.constant.FeeCode;
import com.shanlin.p2p.app.dao.BidCreditRecordDao;
import com.shanlin.p2p.app.dao.BidDao;
import com.shanlin.p2p.app.dao.InvestRecordDao;
import com.shanlin.p2p.app.model.Bid;
import com.shanlin.p2p.app.model.BidCreditRecord;
import com.shanlin.p2p.app.model.FundAccountFlow;
import com.shanlin.p2p.app.model.InvestRecord;
import com.shanlin.p2p.app.service.FundAccountFlowService;
import com.shanlin.p2p.app.vo.SimpleFundFlowVO;

@Controller
@RequestMapping("/user/capital")
public class CapitalAction extends BaseAction{
	
	@Resource
	private FundAccountFlowService fundAccountFlowService;
	
	@Resource
	private BidDao bidDao;

	@Resource
	private BidCreditRecordDao bidCreditRecordDao;
	
	@Resource
	private InvestRecordDao investRecordDao;
	
	// 资金明细
	@RequestMapping(value="/tradingRecord")
	@ResponseBody
	public Map<String, Object> tradingRecord(@RequestParam Long userId, @RequestParam int page, @RequestParam int size){
		Page<FundAccountFlow> fundFlows = fundAccountFlowService.findWlzhFlowByUserId(userId, 
				getPageable(page, size, new Sort(Direction.DESC, "id")));
		Map<String, Object> map = new HashMap<>();
//		map.put("hasPrevious", fundFlows.hasPrevious()?1:0);
		map.put("hasNext", fundFlows.hasNext()?1:0);
		map.put("content", BeanMapper.mapList(fundFlows.getContent(), SimpleFundFlowVO.class));
		return map;
	}
	
	@RequestMapping(value="/tradingRecord/content")
	@ResponseBody
	public Map<String, Object> content(@RequestParam Long userId, @RequestParam Long id){
		FundAccountFlow flow = fundAccountFlowService.findById(id);
		Map<String, Object> map = new HashMap<>();
		if(flow.getFundAccount().getUserId().compareTo(userId) == 0){
			map.put("id", flow.getId());
			map.put("createTime", Formater.formatDateTime(flow.getCreateTime()));
			map.put("type", FeeCode.getChineseName(flow.getFeeCode()));
			map.put("income", Formater.formatAmount(flow.getIncome()));
			map.put("expenditure", Formater.formatAmount(flow.getExpenditure()));
			map.put("balance", Formater.formatAmount(flow.getBalance()));
			map.put("remark", flow.getRemark());
			map.put("bid", 0);
			if(flow.getRemark().contains("000000") || flow.getRemark().contains("撤销散标投资")){
				String str=flow.getRemark();
				String[] strs=str.split(":");
				String prefix=strs[1];
				String number="";//编号
				String suffix="";//后缀
				if(prefix.contains(" ")){
					int _index=prefix.indexOf(" ");
					number=prefix.substring(0, _index);
					suffix=" "+prefix.substring(_index, prefix.length()).replace(" ", "");
				}else{
					number=prefix;
				}
				String title="";
				if(strs[0].contains("撤销散标投资")){
					Long tzId=Long.valueOf(number);
					InvestRecord record=investRecordDao.findOne(tzId);
					if(record!=null){
						title=record.getBid().getTitle();
						map.put("bid", record.getBid().getId());
					}
					map.put("remark",strs[0]+":"+title+suffix);
					return map;
				}
				if(prefix.contains("Z")){
					BidCreditRecord record=bidCreditRecordDao.findByCreditNumber(number);
					if(record!=null){
						title=record.getBid().getTitle();
						map.put("bid", record.getBid().getId());
					}
				}else{
					Bid bid=bidDao.findBybidNumber(number);
					if(bid!=null){
						title=bid.getTitle();
						map.put("bid", bid.getId());
					}
				}
				map.put("remark",strs[0]+":"+title+suffix);
			}
		}
		return map;
	}
}
