package com.booking.service;

import java.util.Date;
import java.util.List;

import com.booking.pojo.ScheduledTimeTable1;
import com.booking.pojo.ScheduledTimeTable1Example;

/**
* 项目名称:booking

* 文件名称:ScheduledTime1Service.java
* 创建人员:nicewithgreat
* 功能描述:
*/
public interface ScheduledTime1Service {
	List<ScheduledTimeTable1> selectByExample(ScheduledTimeTable1Example example);
	
	//////////
	public ScheduledTimeTable1 getItem(int index);
	public Date concatTodayDate(Date date);
	
}
