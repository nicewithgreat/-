package com.booking.service;

import java.util.Date;
import java.util.List;

import com.booking.pojo.ScheduledTimeTable1;
import com.booking.pojo.ScheduledTimeTable1Example;

/**
* ��Ŀ����:booking

* �ļ�����:ScheduledTime1Service.java
* ������Ա:nicewithgreat
* ��������:
*/
public interface ScheduledTime1Service {
	List<ScheduledTimeTable1> selectByExample(ScheduledTimeTable1Example example);
	
	//////////
	public ScheduledTimeTable1 getItem(int index);
	public Date concatTodayDate(Date date);
	
}
