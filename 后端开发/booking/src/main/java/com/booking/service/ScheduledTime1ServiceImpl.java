package com.booking.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.dao.ScheduledTimeTable1Mapper;
import com.booking.pojo.ScheduledTimeTable1;
import com.booking.pojo.ScheduledTimeTable1Example;

/**
* 项目名称:booking

* 文件名称:ScheduledTime1ServiceImpl.java
* 创建人员:nicewithgreat
* 功能描述:
*/
@Service
public class ScheduledTime1ServiceImpl implements ScheduledTime1Service {
	
	@Autowired
	private ScheduledTimeTable1Mapper mapper;
	
	/*启动服务器后初始化 全局变量时间段表*/
	protected List<ScheduledTimeTable1> scheduledList;
	protected Map<Integer,ScheduledTimeTable1> scheduledMap = new HashMap<>();
	
	@PostConstruct
	public void init() {
		if(scheduledList == null) {
			scheduledList = mapper.selectByExample(new ScheduledTimeTable1Example());
//			for(ScheduledTimeTable1 sdtime1 : scheduledList) {
//				System.out.println(sdtime1.toString());
//			}	
		}
		
		for(ScheduledTimeTable1 item : scheduledList) {
			scheduledMap.put(item.getTimeId(), item);
		}
	}
	
	//获取scheduledList项
	public ScheduledTimeTable1 getItem(int index) {
		return scheduledList.get(index-1);
	}
	
	public Date concatTodayDate(Date date) {
		Date newdate = new Date();
		date.setYear(newdate.getYear());
		date.setMonth(newdate.getMonth());
		date.setDate(newdate.getDate());
		return date;
	}
	
	public List<ScheduledTimeTable1> getScheduledList(){
		return scheduledList;
	}
	
	public Map<Integer,ScheduledTimeTable1> getScheduledMap(){
		return scheduledMap;
	}
	
	@Override
	public List<ScheduledTimeTable1> selectByExample(ScheduledTimeTable1Example example) {
		// TODO Auto-generated method stub
		return mapper.selectByExample(example);
	}

}
