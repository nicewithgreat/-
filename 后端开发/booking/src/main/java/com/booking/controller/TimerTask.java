package com.booking.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import com.booking.pojo.ApplyFixedTable7;
import com.booking.pojo.ApplyFixedTable7Example;
import com.booking.pojo.BookHistoryTable4;
import com.booking.pojo.TeamTable3;
import com.booking.pojo.TeamTable3Example;
import com.booking.pojo.TodayCourtTable5;
import com.booking.pojo.TodayCourtTable5Example;
import com.booking.service.ApplyFixed7Service;
import com.booking.service.BookHistory4Service;
import com.booking.service.Team3Service;
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
	@Autowired
	Team3Service tt3service;
	@Autowired
	ApplyFixed7Service af7service;
	@Autowired
	BookHistory4Service bh4service;
	
//	
	//每天凌晨0点更新状态
	@Scheduled(cron = "*0 0 0 * * ?")
	public void freshState(){
		Date now = new Date(); 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//可以方便地修改日期格式
		System.out.print("freshState_" + dateFormat.format( now ));
		
		//当天订场状态全部清0
		TodayCourtTable5 record = new TodayCourtTable5();
		record.setCourtState(0);
		TodayCourtTable5Example example = new TodayCourtTable5Example();
		TodayCourtTable5Example.Criteria criteria = example.createCriteria();
		criteria.andTodayCourtIdIsNotNull();
		tc5service.updateByExampleSelective(record, example);
		
		//查找当天固定场订场情况并写入
		List<TeamTable3> tt3list = tt3service.selectByExample(new TeamTable3Example());
		
		
		ApplyFixedTable7Example af7example = new ApplyFixedTable7Example();
		
		if(!tt3list.isEmpty()) {
			for(TeamTable3 item :tt3list) {
				af7example.or().andApplyFixedIdEqualTo(item.getApplyFixedId());
			}
			List<ApplyFixedTable7> af7list = af7service.selectByExample(af7example);
			
			record = new TodayCourtTable5();
			record.setCourtState(1);
			example = new TodayCourtTable5Example();
			
			//获取场次列表
			//List<TodayCourtTable5> TCList = tc5service.getTodayCourtList();
			Map<Integer,TodayCourtTable5> TCMap = tc5service.getTodayCourtMap();
			
			for(ApplyFixedTable7 item :af7list) {
				//当天订场编号
				example.or().andCourtIdEqualTo(item.getTimeId());
				//插入历史订场信息记录
				insertToHistoryTable(item.getUserId(),TCMap.get(item.getTimeId()).getCourtId(), TCMap.get(item.getTimeId()-1).getTimeId(), 0);//0是已定
				//insertToHistoryTable(item.getUserId(),TCList.get(item.getTimeId()-1).getCourtId(), TCList.get(item.getTimeId()-1).getTimeId(), 0);//0是已定
			}
			tc5service.updateByExampleSelective(record, example);
		}
		
	}
	
	
	//功能描述:插入订场信息 到历史订场信息表
	public int insertToHistoryTable(int user_id,int court_id,int time_id,int book_state) {
		BookHistoryTable4 bh4record = new BookHistoryTable4();
		bh4record.setUserId(user_id);
		bh4record.setCourtId(court_id);
		bh4record.setTimeId(time_id);
		bh4record.setBookState(book_state);
		//时间部分数据库自动获取
		
		return bh4service.insertSelective(bh4record);
	}
}
