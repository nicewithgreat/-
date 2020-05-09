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
* 项目名称:booking

* 文件名称:TimerTask.java
* 创建人员:nicewithgreat
* 功能描述:定时任务,设定每天凌晨场地状态全部更新为0
*/
@Controller
@EnableScheduling
public class TimerTask {
	@Autowired
	TodayCourt5Service tc5service;
//	
	//每天凌晨0点更新状态
	@Scheduled(cron = "*0 0 0 * * ?")
	public void freshState(){
		Date now = new Date(); 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//可以方便地修改日期格式
		System.out.print("freshState_" + dateFormat.format( now ));
		
		TodayCourtTable5 record = new TodayCourtTable5();
		record.setCourtState(0);
		TodayCourtTable5Example example = new TodayCourtTable5Example();
		example.createCriteria().andTodayCourtIdIsNotNull();
		tc5service.updateByExampleSelective(record, example);
	}
}
