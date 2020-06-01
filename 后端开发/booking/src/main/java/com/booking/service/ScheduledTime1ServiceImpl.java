package com.booking.service;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.dao.ScheduledTimeTable1Mapper;
import com.booking.pojo.ScheduledTimeTable1;
import com.booking.pojo.ScheduledTimeTable1Example;

/**
* ��Ŀ����:booking

* �ļ�����:ScheduledTime1ServiceImpl.java
* ������Ա:nicewithgreat
* ��������:
*/
@Service
public class ScheduledTime1ServiceImpl implements ScheduledTime1Service {
	
	@Autowired
	private ScheduledTimeTable1Mapper mapper;
	
	/*�������������ʼ�� ȫ�ֱ���ʱ��α�*/
	protected List<ScheduledTimeTable1> scheduledList;
	@PostConstruct
	public void init() {
		if(scheduledList == null) {
			scheduledList = mapper.selectByExample(new ScheduledTimeTable1Example());
//			for(ScheduledTimeTable1 sdtime1 : scheduledList) {
//				System.out.println(sdtime1.toString());
//			}	
		}
	}
	
	//��ȡscheduledList��
	public ScheduledTimeTable1 getItem(int index) {
		return scheduledList.get(index);
	}
	
	public Date concatTodayDate(Date date) {
		Date newdate = new Date();
		date.setDate(newdate.getDate());
		System.out.println(date);
		return date;
	}
	
	public List<ScheduledTimeTable1> getScheduledList(){
		return scheduledList;
	}
	
	@Override
	public List<ScheduledTimeTable1> selectByExample(ScheduledTimeTable1Example example) {
		// TODO Auto-generated method stub
		return mapper.selectByExample(example);
	}

}
