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
* ��Ŀ����:booking

* �ļ�����:TodayCourtController.java
* ������Ա:nicewithgreat
* ��������:���쳡�ζ������
*/

@Controller
public class TodayCourtController {
	@Autowired
	TodayCourt5Service tc5service;
	
	
	/*��ȡĿǰ�Ľ��충�������*/
	@RequestMapping("/getTodayCourt")
	@ResponseBody
	public Object getTodayCourt(){
//		List<TodayCourtTable5> t5list= (tc5service.selectByExampleWith(new TodayCourtTable5Example())) .stream().collect(Collectors.toList());
//		for(TodayCourtTable5 l5 : t5list) {
//			System.out.println(l5.toString());
//		}
		
		return tc5service.selectByExampleWith(new TodayCourtTable5Example());
	}
	
	/*Ԥ���������Ч���г��أ���Ҫǰ���ж��Ƿ�����Ч�ڼ�*/
	@RequestMapping("/bookTodayFreeCourt")
	@ResponseBody
	public int bookTodayFreeCourt(int today_court_id) {
		//��today_court_id��Ӧ�����и���״̬Ϊ1
		TodayCourtTable5 record = new TodayCourtTable5();
		record.setCourtState(1);
		TodayCourtTable5Example example = new TodayCourtTable5Example();
		example.createCriteria().andTodayCourtIdEqualTo(today_court_id);
		
		//�޸ĳɹ�����1
		return tc5service.updateByExampleSelective(record, example);	
	}
	
	
	
}
