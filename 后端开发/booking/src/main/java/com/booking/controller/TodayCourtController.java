package com.booking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.booking.pojo.TodayCourtTable5;
import com.booking.pojo.TodayCourtTable5Example;
import com.booking.service.TodayCourt5Service;

/**
* 项目名称:booking

* 文件名称:TodayCourtController.java
* 创建人员:nicewithgreat
* 功能描述:当天场次订场情况
*/

@Controller
public class TodayCourtController {
	@Autowired
	TodayCourt5Service tc5service;
	
	
	/*获取目前的今天订场场情况*/
	@RequestMapping("/getTodayCourt")
	@ResponseBody
	public Object getTodayCourt(){
//		List<TodayCourtTable5> t5list= (tc5service.selectByExampleWith(new TodayCourtTable5Example())) .stream().collect(Collectors.toList());
//		for(TodayCourtTable5 l5 : t5list) {
//			System.out.println(l5.toString());
//		}
		
		return tc5service.selectByExampleWith(new TodayCourtTable5Example());
	}
	
	/*预定当天的有效空闲场地，需要前端判断是否处于有效期间*/
	@RequestMapping("/bookTodayFreeCourt")
	@ResponseBody
	public int bookTodayFreeCourt(int today_court_id) {
		//将today_court_id对应的项中更新状态为1
		TodayCourtTable5 record = new TodayCourtTable5();
		record.setCourtState(1);
		TodayCourtTable5Example example = new TodayCourtTable5Example();
		example.createCriteria().andTodayCourtIdEqualTo(today_court_id);
		
		//修改成功返回1
		return tc5service.updateByExampleSelective(record, example);	
	}
	
	
	
}
