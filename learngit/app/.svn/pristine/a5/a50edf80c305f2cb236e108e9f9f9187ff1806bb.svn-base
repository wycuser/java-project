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
import com.shanlin.p2p.app.model.Letter;
import com.shanlin.p2p.app.model.LetterContent;
import com.shanlin.p2p.app.model.enums.LetterStatus;
import com.shanlin.p2p.app.service.LetterService;
import com.shanlin.p2p.app.vo.LetterContentVO;
import com.shanlin.p2p.app.vo.LetterVO;

@Controller
@RequestMapping("/user/letter")
public class LetterAction extends BaseAction{
	
	@Resource
	private LetterService letterService;
	
	@RequestMapping(value="/list")
	@ResponseBody
	public Map<String, Object> list(@RequestParam Long userId, @RequestParam int page, @RequestParam int size){
		Page<Letter> letterPage = letterService.findAll(userId,
				getPageable(page, size, new Sort(Direction.DESC, "sendTime")));
		Map<String, Object> map = new HashMap<>();
//		map.put("hasPrevious", letterPage.hasPrevious()?1:0);
		map.put("hasNext", letterPage.hasNext()?1:0);
		map.put("content", BeanMapper.mapList(letterPage.getContent(), LetterVO.class));
		return map;
	}
	
	@RequestMapping(value="/content")
	@ResponseBody
	public LetterContentVO getLetterContent(@RequestParam Long id, @RequestParam Long userId){
		LetterContent content = letterService.findContentById(id);
		if(content != null){
			Letter letter = content.getLetter();
			if(letter.getUserId().compareTo(userId) == 0 && letter.getStatus() != LetterStatus.SC){
				if(letter.getStatus() == LetterStatus.WD){
					letter.setStatus(LetterStatus.YD);
					letterService.updateLetter(letter);
				}
				return BeanMapper.map(content, LetterContentVO.class);
			}
		}
		return null;
	}
}
