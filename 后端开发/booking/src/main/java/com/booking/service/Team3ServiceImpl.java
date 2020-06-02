package com.booking.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.dao.TeamTable3Mapper;
import com.booking.pojo.ScheduledTimeTable1;
import com.booking.pojo.ScheduledTimeTable1Example;
import com.booking.pojo.TeamTable3;
import com.booking.pojo.TeamTable3Example;

/**
* ��Ŀ����:booking

* �ļ�����:Team3ServiceImpl.java
* ������Ա:nicewithgreat
* ��������:
*/
@Service
public class Team3ServiceImpl implements Team3Service {
	
	@Autowired
	TeamTable3Mapper mapper;
	
	/*�������������ʼ�� ȫ�ֱ���ʱ��α�*/
	protected List<TeamTable3> CollegeList;
	@PostConstruct
	public void init() {
		if(CollegeList == null) {
			CollegeList = mapper.selectByExample(new TeamTable3Example());
//			for(ScheduledTimeTable1 sdtime1 : scheduledList) {
//				System.out.println(sdtime1.toString());
//			}	
		}
	}
	
	public List<TeamTable3> getCollegeList(){
		return CollegeList;
	}
	
	@Override
	public int insertSelective(TeamTable3 record) {
		// TODO Auto-generated method stub
		return mapper.insertSelective(record);
	}

	@Override
	public List<TeamTable3> selectByExample(TeamTable3Example example) {
		// TODO Auto-generated method stub
		return mapper.selectByExample(example);
	}

	@Override
	public int updateByExampleSelective(TeamTable3 record, TeamTable3Example example) {
		// TODO Auto-generated method stub
		return mapper.updateByExampleSelective(record, example);
	}

}
