package com.booking.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.booking.pojo.BookHistoryTable4;
import com.booking.pojo.BookHistoryTable4Example;
import com.booking.pojo.ScheduledTimeTable1;
import com.booking.pojo.TodayCourtTable5;
import com.booking.pojo.TodayCourtTable5Example;
import com.booking.service.BookHistory4Service;
import com.booking.service.ScheduledTime1Service;
import com.booking.service.TodayCourt5Service;

/**
* 项目名称:booking

* 文件名称:TodayCourtController.java
* 创建人员:nicewithgreat
* 功能描述:当天场次订场情况
*/

@Controller
public class TodayCourtController {
	@Autowired
	TodayCourt5Service tc5service;
	@Autowired
	BookHistory4Service bh4service;
	@Autowired
	ScheduledTime1Service st1service;
	
	
	/*获取目前的今天订场场情况,该情况为连表查询,建议将时间段表、羽毛球场表读入到缓存*/
	@RequestMapping("/getTodayCourtWithOtherInfo")
	@ResponseBody
	public Object getTodayCourtWithOtherInfo(){
//		List<TodayCourtTable5> t5list= (tc5service.selectByExampleWith(new TodayCourtTable5Example())) .stream().collect(Collectors.toList());
//		for(TodayCourtTable5 l5 : t5list) {
//			System.out.println(l5.toString());
//		}
		
		return tc5service.selectByExampleWith(new TodayCourtTable5Example());
	}
	
	
	/*获取目前的今天订场场情况,非连表查询*/
	@RequestMapping("/getTodayCourt")
	@ResponseBody
	public Object getTodayCourt(){
		return tc5service.selectByExample(new TodayCourtTable5Example());
	}
	
	/*预定当天的有效空闲场地，需要前端判断是否处于有效期间*/
	@RequestMapping("/bookTodayFreeCourt")
	@ResponseBody
	public int bookTodayFreeCourt(int user_id,TodayCourtTable5 item) {
		
		//将today_court_id对应的项中更新状态为1
		TodayCourtTable5 tc5record = new TodayCourtTable5();
		tc5record.setCourtState(1);
		TodayCourtTable5Example tc5example = new TodayCourtTable5Example();
		
		TodayCourtTable5Example.Criteria tc5Criteria = tc5example.createCriteria();  
		tc5Criteria.andTodayCourtIdEqualTo(item.getTodayCourtId());
		tc5Criteria.andCourtStateEqualTo(0);

		//更新当天订场信息
		if(tc5service.updateByExampleSelective(tc5record, tc5example) < 1) {
			return 0;
		}
				
		insertToHistoryTable(user_id,item.getCourtId(),item.getTimeId(),1);

		//修改成功返回1
		return 1;
	}
	
	//功能描述:查询个人历史订场信息
	@RequestMapping("/getMyHistoryInfo")
	@ResponseBody
	public Object getMyHistoryInfo(int user_id) {		
		BookHistoryTable4Example example = new BookHistoryTable4Example();
		BookHistoryTable4Example.Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(user_id);
		
		return bh4service.selectByExample(example);
	}
	
	//功能描述:插入订场信息 到历史订场信息表
	@RequestMapping("/insertInfoToHistory")
	@ResponseBody
	public int insertInfoToHistory(int user_id,int court_id,int time_id,int book_state) {
		return insertToHistoryTable(user_id,court_id,time_id,book_state);
	}
	
	public int insertToHistoryTable(int user_id,int court_id,int time_id,int book_state) {
		BookHistoryTable4 bh4record = new BookHistoryTable4();
		bh4record.setUserId(user_id);
		bh4record.setCourtId(court_id);
		bh4record.setTimeId(time_id);
		bh4record.setBookState(book_state);
		//时间部分数据库自动获取
		
		return bh4service.insertSelective(bh4record);
	}
	
	//功能描述：更变已选场地
	/*user_id:用户id
	 *back_bookid:曾经个人修改的历史记录ID
	 *int back_courtid,int back_timeid,
	 * 曾经场地id,曾经时间id
	 *int change_courtid,int change_timeid
	 *要改变的场地id,要改变的时间id
	 *
	 *return:{
	 *	0:插入失败
	 *	1:插入成功
	 *	-1:无效订场
	 *}
	 */
	
	@RequestMapping("/changeTodayCourt")
	@ResponseBody
	public int changeTodayCourt(int user_id,
								int back_bookid,int back_courtid,int back_timeid,
								int change_courtid,int change_timeid) {
		
		BookHistoryTable4Example example = new BookHistoryTable4Example();
		BookHistoryTable4Example.Criteria criteria = example.createCriteria();
		criteria.andBookIdEqualTo(back_bookid);
		criteria.andUserIdEqualTo(user_id);
		criteria.andBookStateEqualTo(0);
		
		ScheduledTimeTable1 item = st1service.getItem(back_courtid);
		criteria.andBookDateBetween(st1service.concatTodayDate(item.getStarttime()), st1service.concatTodayDate(item.getEndtime()));
		//判断该用户已经订了的场地是否有效
		if(bh4service.selectByExample(example)!=null) {
			//有效则可以修改
			TodayCourtTable5 tc5record = new TodayCourtTable5();
			tc5record.setCourtState(1);
			
			TodayCourtTable5Example tc5example = new TodayCourtTable5Example();		
			TodayCourtTable5Example.Criteria tc5Criteria = tc5example.createCriteria();  
			tc5Criteria.andTodayCourtIdEqualTo(change_courtid);
			tc5Criteria.andCourtStateEqualTo(0);
			//如果修改成功则
			if(tc5service.updateByExampleSelective(tc5record, tc5example)==1) {
				
				//修改历史记录为已退
				BookHistoryTable4 bh4record = new BookHistoryTable4();
				bh4record.setBookState(1);
				BookHistoryTable4Example bh4example = new BookHistoryTable4Example();
				BookHistoryTable4Example.Criteria bh4criteria = bh4example.createCriteria();
				bh4criteria.andBookIdEqualTo(back_bookid);
				bh4service.updateByExampleSelective(bh4record, bh4example);
				
				//且修改当天退出的场地信息
				tc5record = new TodayCourtTable5();
				tc5record.setCourtState(0);
				
				tc5example = new TodayCourtTable5Example();		
				tc5Criteria = tc5example.createCriteria();  
				tc5Criteria.andCourtIdEqualTo(change_courtid);
				tc5Criteria.andTimeIdEqualTo(change_timeid);
				tc5Criteria.andCourtStateEqualTo(1);	
				tc5service.updateByExampleSelective(tc5record, tc5example);
				
				
				//再添加重新订场的历史记录
				return insertToHistoryTable(user_id,change_courtid,change_timeid,0);
			}
			return -1;
		}
		
		BookHistoryTable4 bh4record = new BookHistoryTable4();
		bh4record.setBookState(1);
		
		BookHistoryTable4Example bh4example = new BookHistoryTable4Example();
		BookHistoryTable4Example.Criteria bh4criteria = bh4example.createCriteria();
		bh4criteria.andUserIdEqualTo(user_id);
		bh4criteria.andCourtIdEqualTo(back_courtid);
		
		return 0;
	}
	

}
