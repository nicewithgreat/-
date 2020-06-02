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
* 项目名称:booking

* 文件名称:Team3ServiceImpl.java
* 创建人员:nicewithgreat
* 功能描述:
*/
@Service
public class Team3ServiceImpl implements Team3Service {
	
	@Autowired
	TeamTable3Mapper mapper;
	
	/*启动服务器后初始化 全局变量时间段表*/
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
