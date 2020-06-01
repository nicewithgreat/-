package com.booking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.booking.dao.ApplyFixedTable7Mapper;
import com.booking.pojo.ApplyFixedTable7;
import com.booking.pojo.UserTable2;
import com.booking.pojo.UserTable2Example;
import com.booking.service.ApplyFixed7Service;
import com.booking.service.ApplyUnsubscribe6Service;
import com.booking.service.UserTable2Service;

/**
* ��Ŀ����:booking

* �ļ�����:FixedCourtController.java
* ������Ա:nicewithgreat
* ��������:�̶�����ع���
*/
@Controller
public class FixedCourtController {
	@Autowired
	ApplyFixed7Service af7service;
	@Autowired
	ApplyUnsubscribe6Service au6Service;
	@Autowired
	UserTable2Service ut2service;
	
	/*
	 * ����̶���
	 * ����ֵ:{
	 * 	-1:�޸��û�
	 * 	0:����ʧ��
	 * 	1:����ɹ�
	 * }
	 */
	@RequestMapping("/applyFixedCourt")
	@ResponseBody
	public int applyFixedCourt(int user_id,int time_id,int day_of_week) {
		UserTable2Example example = new UserTable2Example();
		UserTable2Example.Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(user_id);		
		List<UserTable2> list = ut2service.selectByExample(example);
		if(list.isEmpty()) {
			return -1;
		}
		
		ApplyFixedTable7 record = new ApplyFixedTable7();
		record.setUserId(user_id);
		record.setTimeId(time_id);
		record.setDayOfWeek(day_of_week);
		record.setApplyState(0);
				
		return af7service.insertSelective(record);
	}
	
	
	

}
