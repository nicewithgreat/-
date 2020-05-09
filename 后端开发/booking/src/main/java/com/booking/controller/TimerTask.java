package com.booking.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import com.booking.pojo.TodayCourtTable5;
import com.booking.pojo.TodayCourtTable5Example;
import com.booking.service.TodayCourt5Service;

/**
* ��Ŀ����:booking

* �ļ�����:TimerTask.java
* ������Ա:nicewithgreat
* ��������:��ʱ����,�趨ÿ���賿����״̬ȫ������Ϊ0
*/
@Controller
@EnableScheduling
public class TimerTask {
	@Autowired
	TodayCourt5Service tc5service;
//	
	//ÿ���賿0�����״̬
	@Scheduled(cron = "*0 0 0 * * ?")
	public void freshState(){
		Date now = new Date(); 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//���Է�����޸����ڸ�ʽ
		System.out.print("freshState_" + dateFormat.format( now ));
		
		TodayCourtTable5 record = new TodayCourtTable5();
		record.setCourtState(0);
		TodayCourtTable5Example example = new TodayCourtTable5Example();
		example.createCriteria().andTodayCourtIdIsNotNull();
		tc5service.updateByExampleSelective(record, example);
	}
}
