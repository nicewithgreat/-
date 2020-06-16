package com.booking.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.booking.pojo.ApplyChangeTable8;
import com.booking.pojo.ApplyUnsubscribeTable6;
import com.booking.pojo.BookHistoryTable4;
import com.booking.pojo.BookHistoryTable4Example;
import com.booking.pojo.ScheduledTimeTable1;
import com.booking.pojo.TodayCourtTable5;
import com.booking.pojo.TodayCourtTable5Example;
import com.booking.service.ApplyChange8Service;
import com.booking.service.ApplyUnsubscribe6Service;
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
	@Autowired
	ApplyUnsubscribe6Service au6service;
	@Autowired
	ApplyChange8Service ac8service;
	
	
	
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
	public int bookTodayFreeCourt(int user_id,/*TodayCourtTable5 item*/
									int todayCourt_id) {
		//获取当天场次对象
		TodayCourtTable5 todayCache = tc5service.getTodayCourtMap().get(todayCourt_id);
		
		//将today_court_id对应的项中更新状态为1
		TodayCourtTable5 tc5record = new TodayCourtTable5();
		tc5record.setCourtState(1);
		TodayCourtTable5Example tc5example = new TodayCourtTable5Example();
		
		TodayCourtTable5Example.Criteria tc5Criteria = tc5example.createCriteria();  
		tc5Criteria.andTodayCourtIdEqualTo(todayCourt_id);
		tc5Criteria.andCourtStateEqualTo(0);

		//更新当天订场信息
		if(tc5service.updateByExampleSelective(tc5record, tc5example) < 1) {
			return 0;
		}
		
		//修改成功返回1		
		return insertToHistoryTable(user_id,todayCache.getCourtId(),todayCache.getTimeId(),0);//0已定
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
	
	private int insertToHistoryTable(int user_id,int court_id,int time_id,int book_state) {
		BookHistoryTable4 bh4record = new BookHistoryTable4();
		bh4record.setUserId(user_id);
		bh4record.setCourtId(court_id);
		bh4record.setTimeId(time_id);
		bh4record.setBookState(book_state);
		//时间部分数据库自动获取
		
		return bh4service.insertSelective(bh4record);
	}
	
	//功能描述：更变已选场地//**************弃用
	/*user_id:用户id
	 *back_bookid:曾经个人修改的历史记录ID
	 *back_todayCourtId:曾经当天场次id
	 *change_todayCourtId:要改变当天场次id
	 *
	 *return:{
	 *	0:插入失败
	 *	1:插入成功
	 *	-1:无效订场
	 *}
	 */
	
	@RequestMapping("/changeTodayCourt")//**************弃用
	@ResponseBody
	public int changeTodayCourt(int user_id,
								int back_bookid,
								/*int back_courtid,int back_timeid,
								int change_courtid,int change_timeid*/
								int back_todayCourtId,int change_todayCourtId) {

		BookHistoryTable4Example example = new BookHistoryTable4Example();
		BookHistoryTable4Example.Criteria criteria = example.createCriteria();
		criteria.andBookIdEqualTo(back_bookid);
		criteria.andUserIdEqualTo(user_id);
		criteria.andBookStateEqualTo(0);
		
		//ScheduledTimeTable1 item = st1service.getItem(tc5service.getTodayCourtByID(back_todayCourtId).getTimeId());//back_timeid
		ScheduledTimeTable1 item = st1service.getScheduledMap().get(tc5service.getTodayCourtByID(back_todayCourtId).getTimeId());///??????
		//判断是否在可修改的时间段内；
//		System.out.println("0"+st1service.concatTodayDate(item.getStarttime()));//error
//		System.out.println("0"+st1service.concatTodayDate(item.getEndtime()));//error
//		System.out.println("1"+item.getStarttime());
//		System.out.println("1"+item.getEndtime());
		criteria.andBookDateBetween(st1service.concatTodayDate(item.getStarttime()), st1service.concatTodayDate(item.getEndtime()));//逻辑错误
		//判断该用户已经订了的场地是否有效
		if(!bh4service.selectByExample(example).isEmpty()) {
			System.out.println("success!");
			//有效则可以修改
			TodayCourtTable5 tc5record = new TodayCourtTable5();
			tc5record.setCourtState(1);
			
			TodayCourtTable5Example tc5example = new TodayCourtTable5Example();		
			TodayCourtTable5Example.Criteria tc5Criteria = tc5example.createCriteria();  
			tc5Criteria.andTodayCourtIdEqualTo(change_todayCourtId);
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
				tc5Criteria.andTodayCourtIdEqualTo(change_todayCourtId);
				//tc5Criteria.andCourtIdEqualTo(TCMap.get(change_todayCourtId).getCourtId());//change_courtid
				//tc5Criteria.andTimeIdEqualTo(TCMap.get(change_todayCourtId).getTimeId());//change_timeid
				tc5Criteria.andCourtStateEqualTo(1);	
				
				tc5service.updateByExampleSelective(tc5record, tc5example);
				
				//再添加重新订场的历史记录
				bh4record = new BookHistoryTable4();
				bh4record.setBookState(1);
				
				bh4example = new BookHistoryTable4Example();
				bh4criteria = bh4example.createCriteria();
				bh4criteria.andUserIdEqualTo(user_id);
				bh4criteria.andCourtIdEqualTo(tc5service.getTodayCourtByID(back_todayCourtId).getCourtId());//back_courtid
				
				return insertToHistoryTable(user_id,tc5service.getTodayCourtByID(change_todayCourtId).getCourtId(),tc5service.getTodayCourtByID(change_todayCourtId).getTimeId(),0);
			}else {
				return 0;
			}
			
		}else {
			return -1;
		}
	}
	

	//普通用户申请换场,假如是在订场后十分钟内换场，则马上更换，否则插入申请换场表
	/*user_id:用户id
	 *book_id:历史订场记录ID
	 *back_todayCourtId:被更换的场次表id
	 *change_todayCourtId:要更换的场次表id
	 *
	 *return:{
	 *	0:申请失败
	 *	1:申请成功
	 *	2:换场失败
	 *	3:换场成功
	 *	-1:无效换场
	 *	-2:请求数据错误
	 *}
	 */	
	@RequestMapping("/applyChangeTodayCourt")
	@ResponseBody
	public int applyChangeTodayCourt(int user_id,int book_id,int back_todayCourtId, int change_todayCourtId) {	//*****可能要改参数
		BookHistoryTable4Example example = new BookHistoryTable4Example();
		BookHistoryTable4Example.Criteria criteria = example.createCriteria();
		criteria.andBookIdEqualTo(book_id);
		criteria.andUserIdEqualTo(user_id);
		
		List<BookHistoryTable4> bh4List = bh4service.selectByExample(example);
		if(bh4List.isEmpty()) {
			//找不到，请求数据出错
			return -2;
		}
		//判断是否是当天订的场
		if(bh4List.get(0).getBookDate().after(todayzero())) {
			//获取当天场次map
			ScheduledTimeTable1 item = st1service.getScheduledMap().get(bh4List.get(0).getTimeId());
			//ScheduledTimeTable1 item = st1service.getItem(tc5service.getTodayCourtByID(bh4List.get(0).getTimeId()).getTimeId());//back_timeid
						
			//判断现在时间是否超过了所订场
			Date now = new Date();
			if(item.getStarttime().after(now)) {
				//判断是否是在订场后十分钟内换场,是则马上更换，否则插入申请换场表
				if(bh4List.get(0).getBookDate().after(new Date(now.getTime() - 600000))) {
					return changeCourtOperate(user_id,book_id,back_todayCourtId,change_todayCourtId) + 2;//+2
				}else {
					//更新历史订场记录状态为 2(换场待审核)
					setBookHistoryState(book_id,2);
					
					ApplyChangeTable8 record = new ApplyChangeTable8();
					record.setUserId(user_id);
					record.setBookId(book_id);
					record.setCourtId(tc5service.getTodayCourtByID(change_todayCourtId).getCourtId());//court_id
					record.setTimeId(tc5service.getTodayCourtByID(change_todayCourtId).getTimeId());//time_id
					record.setApplyState(0);
					return ac8service.insertSelective(record);
				}
				
			}else {System.out.println("现在时间超过了所订场的时间");return -1;}
		}else {System.out.println("不是当天订的场");return -1;}
	}
	

	//普通用户申请退场,订该场的时间段前两个小时之前还是可以退场的
	/*user_id:用户id
	 *book_id:历史订场记录ID
	 *reason:退场原因
	 *
	 *return:{
	 *	0:申请失败
	 *	1:申请成功
	 *	-1:无效退场
	 *	-2:请求数据错误
	 *}
	 */
	@RequestMapping("/applyUnsubscribeTodayCourt")
	@ResponseBody
	public int applyUnsubscribeTodayCourt(int user_id,int book_id,String reason) {		
		BookHistoryTable4Example example = new BookHistoryTable4Example();
		BookHistoryTable4Example.Criteria criteria = example.createCriteria();
		criteria.andBookIdEqualTo(book_id);
		criteria.andUserIdEqualTo(user_id);
		criteria.andBookStateEqualTo(0);//已订
		
		List<BookHistoryTable4> bh4List = bh4service.selectByExample(example);
		if(bh4List.isEmpty()) {
			//找不到，请求数据出错
			return -2;
		}
		
		//判断是否是当天订的场
		if(bh4List.get(0).getBookDate().after(todayzero())) {			
			//获取当天场次map
			
			ScheduledTimeTable1 item = st1service.getScheduledMap().get(bh4List.get(0).getTimeId());
			//ScheduledTimeTable1 item = st1service.getItem(tc5service.getTodayCourtByID(bh4List.get(0).getTimeId()).getTimeId());//back_timeid
			
			//判断现在是否超过了所订场 时间段的前两个小时
//			System.out.println(st1service.concatTodayDate(item.getStarttime()));
//			System.out.println(new Date(new Date().getTime() + 7200000));
			if(st1service.concatTodayDate(item.getStarttime()).after(new Date(new Date().getTime() + 7200000))) {//7200000两个小时
				//更新历史订场记录状态为 3(退场待审核)
				setBookHistoryState(book_id,3);
				
				//在有效时间内就插入申请退场表				
				ApplyUnsubscribeTable6 record = new ApplyUnsubscribeTable6();
				record.setUserId(user_id);
				record.setBookId(book_id);
				record.setReason(reason);
				record.setUnsubscribeState(0);//普通用户：未审核
				return au6service.insertSelective(record);				
			}else {System.out.println("超过了所订场 时间段的前两个小时");return -1;}	
		}else {System.out.println("不是当天订的场");return -1;}
	}
	
	//获取当天凌晨0点的时间
	private Date todayzero() {
		Date today = new Date();
		today.setHours(0);
		today.setMinutes(0);
		today.setSeconds(0);
		return today;
	}
	
	
	//更换当天场地操作
	/*
	 * return 0:更换失败
	 * return 1:更换成功
	 */
	private int changeCourtOperate(int user_id, int back_bookid, int back_todayCourtId, int change_todayCourtId) {
		TodayCourtTable5 tc5record = new TodayCourtTable5();
		tc5record.setCourtState(1);//已定
		
		TodayCourtTable5Example tc5example = new TodayCourtTable5Example();		
		TodayCourtTable5Example.Criteria tc5Criteria = tc5example.createCriteria();  
		tc5Criteria.andTodayCourtIdEqualTo(change_todayCourtId);
		tc5Criteria.andCourtStateEqualTo(0);
		//如果修改成功则
		if(tc5service.updateByExampleSelective(tc5record, tc5example)==1) {
			//且修改当天退出的场地信息
			tc5record = new TodayCourtTable5();
			tc5record.setCourtState(0);
			
			tc5example = new TodayCourtTable5Example();		
			tc5Criteria = tc5example.createCriteria();  
			tc5Criteria.andTodayCourtIdEqualTo(back_todayCourtId);
			tc5Criteria.andCourtStateEqualTo(1);	
			
			tc5service.updateByExampleSelective(tc5record, tc5example);
			
			//修改历史记录为已退
			BookHistoryTable4 bh4record = new BookHistoryTable4();
			bh4record.setBookState(1);//已退
			BookHistoryTable4Example bh4example = new BookHistoryTable4Example();
			BookHistoryTable4Example.Criteria bh4criteria = bh4example.createCriteria();
			bh4criteria.andBookIdEqualTo(back_bookid);
			
			bh4service.updateByExampleSelective(bh4record, bh4example);
			
			//再添加重新订场的历史记录	
			return insertToHistoryTable(user_id,tc5service.getTodayCourtByID(change_todayCourtId).getCourtId(),tc5service.getTodayCourtByID(change_todayCourtId).getTimeId(),0);//0:已定
		}else {return 0;}
	}
	
	/*
	 * 更新历史订场记录状态
	 * 订场状态{
	 * 0(已订);
	 * 1(已退);
	 * 2(换场待审核);
	 * 3(退场待审核);
	 * }
	 */
	private int setBookHistoryState(int book_id,int state) {		
		BookHistoryTable4 record = new BookHistoryTable4();
		record.setBookState(state);
		BookHistoryTable4Example example = new BookHistoryTable4Example();
		//example.createCriteria().andUserIdEqualTo(user_id);
		example.createCriteria().andBookIdEqualTo(book_id);
		
		return bh4service.updateByExampleSelective(record, example);
	}
}
