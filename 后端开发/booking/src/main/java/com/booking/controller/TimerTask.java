package com.booking.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import com.booking.pojo.ApplyFixedTable7;
import com.booking.pojo.ApplyFixedTable7Example;
import com.booking.pojo.TeamTable3;
import com.booking.pojo.TeamTable3Example;
import com.booking.pojo.TodayCourtTable5;
import com.booking.pojo.TodayCourtTable5Example;
import com.booking.service.ApplyFixed7Service;
import com.booking.service.Team3Service;
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
	@Autowired
	Team3Service tt3service;
	@Autowired
	ApplyFixed7Service af7service;
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
		TodayCourtTable5Example.Criteria criteria = example.createCriteria();
		criteria.andTodayCourtIdIsNotNull();
		tc5service.updateByExampleSelective(record, example);
		
		//���ҵ���̶������������д��
		List<TeamTable3> tt3list = tt3service.selectByExample(new TeamTable3Example());
		
		
		ApplyFixedTable7Example af7example = new ApplyFixedTable7Example();
		
		for(TeamTable3 item :tt3list) {
			af7example.or().andApplyFixedIdEqualTo(item.getApplyFixedId());
		}
		List<ApplyFixedTable7> af7list = af7service.selectByExample(af7example);
		
		
		record = new TodayCourtTable5();
		record.setCourtState(1);
		example = new TodayCourtTable5Example();

		for(ApplyFixedTable7 item :af7list) {
			//���충�����
			example.or().andCourtIdEqualTo(item.getTimeId());
		}
		tc5service.updateByExampleSelective(record, example);
		

	}
}
