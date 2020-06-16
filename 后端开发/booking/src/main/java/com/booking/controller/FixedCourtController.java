package com.booking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.booking.pojo.ApplyChangeTable8;
import com.booking.pojo.ApplyChangeTable8Example;
import com.booking.pojo.ApplyFixedTable7;
import com.booking.pojo.ApplyFixedTable7Example;
import com.booking.pojo.ApplyUnsubscribeTable6;
import com.booking.pojo.ApplyUnsubscribeTable6Example;
import com.booking.pojo.TeamTable3;
import com.booking.pojo.TeamTable3Example;
import com.booking.pojo.UserTable2;
import com.booking.pojo.UserTable2Example;
import com.booking.service.ApplyChange8Service;
import com.booking.service.ApplyFixed7Service;
import com.booking.service.ApplyUnsubscribe6Service;
import com.booking.service.Team3Service;
import com.booking.service.UserTable2Service;

/**
* 项目名称:booking

* 文件名称:FixedCourtController.java
* 创建人员:nicewithgreat
* 功能描述:固定场相关功能
*/
@Controller
public class FixedCourtController {
	@Autowired
	ApplyFixed7Service af7service;
	@Autowired
	UserTable2Service ut2service;
	@Autowired
	Team3Service tt3service;
	@Autowired
	ApplyChange8Service ac8service;
	@Autowired
	ApplyUnsubscribe6Service au6service;
	
	/*
	 * 判断是不是球队用户
	 */
	public int judge_teamplayer(int user_id) {
		UserTable2Example example = new UserTable2Example();
		UserTable2Example.Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(user_id);	
		criteria.andPropertyEqualTo("球队用户");
		List<UserTable2> list = ut2service.selectByExample(example);

		if(list.isEmpty()) {
			return 0;
		}
		return 1;
	}
	/*
	 * 插入申请固定场表
	 * 返回值:{
	 * 	-1:该用户不符合
	 * 	0:申请失败
	 * 	1:申请成功
	 * }
	 */
	public int insertapplyFixedCourt(int user_id,int today_court_id,int day_of_week,int apply_state) {
		if(judge_teamplayer(user_id) == 0) {
			return -1;
		}
		
		ApplyFixedTable7 record = new ApplyFixedTable7();
		record.setUserId(user_id);
		record.setTimeId(today_court_id);
		record.setDayOfWeek(day_of_week);
		record.setApplyState(apply_state);
				
		return af7service.insertSelective(record);
	}
	
	/*
	 * 获取该球队用户的有效固定场申请表项
	 */
	public ApplyFixedTable7 getTeamFixed(int user_id) {
		ApplyFixedTable7Example example = new ApplyFixedTable7Example();
		ApplyFixedTable7Example.Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(user_id);
		criteria.andApplyStateEqualTo(1);//1:同意
		List<ApplyFixedTable7> list = af7service.selectByExample(example);
		if(list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}
	/*
	  * 球队成员获取历史 申请固定场表
	  * state:
	  * -1:查找全部
	  * -2:查找不包含已退
	  * 其他为找单状态
	  */
	 @RequestMapping("/ApplyFixedCourtListByUser")
	 @ResponseBody
	 public Object ApplyFixedCourtListByUser(int user_id,int state) {
	  ApplyFixedTable7Example example = new ApplyFixedTable7Example();
	  ApplyFixedTable7Example.Criteria criteria = example.createCriteria(); 
	  criteria.andUserIdEqualTo(user_id);
	  if(state == -2) {
	   criteria.andApplyStateNotEqualTo(2);//不包含已退
	  }else if(state != -1) {
	   criteria.andApplyStateEqualTo(state);//找单状态的
	  }
	  
	  return af7service.selectByExample(example);
	 }
	
	
	/*
	 * 申请固定场
	 * 
	 */
	@RequestMapping("/applyFixedCourt")
	 @ResponseBody
	 public int applyFixedCourt(int user_id,int today_court_id,int day_of_week) {
	  ApplyFixedTable7Example example = new ApplyFixedTable7Example();
	  ApplyFixedTable7Example.Criteria criteria = example.createCriteria();
	  criteria.andApplyStateNotEqualTo(2);//不是已退
	  criteria.andUserIdEqualTo(user_id);
	  
	  if(af7service.selectByExample(example).isEmpty()) {
	   return insertapplyFixedCourt(user_id,today_court_id,day_of_week,0);
	  }else {
	   return -1;
	  }
	  
	 }
	
	
	/*
	 * 审核申请固定场,是否同意
	 * apply_state 是否同意 //1:同意；2：不同意
	 * apply_fixed_id 申请固定场的编号
	 * user_id 申请的用户账号
	 */
	@RequestMapping("/reviewFixedCourtAnswer")
	@ResponseBody
	public int reviewFixedCourtAnswer(int apply_state,int apply_fixed_id,int user_id) {
		UserTable2Example ut2example = new UserTable2Example();
		UserTable2Example.Criteria ut2criteria = ut2example.createCriteria();
		ut2criteria.andUserIdEqualTo(user_id);
		UserTable2 ut2 = ut2service.selectByExample(ut2example).get(0);
		
		if(!ut2.getProperty().equals("球队用户")) {
			return 0;//非球队用户
		}
		
		ApplyFixedTable7 record = new ApplyFixedTable7();
		record.setApplyState(apply_state);
		ApplyFixedTable7Example example = new ApplyFixedTable7Example();
		ApplyFixedTable7Example.Criteria criteria = example.createCriteria();
		criteria.andApplyFixedIdEqualTo(apply_fixed_id);
		af7service.updateByExampleSelective(record, example);
		
		//写入球队表
		if(apply_state == 1) {			
			TeamTable3 tt3record = new TeamTable3();
			tt3record.setApplyFixedId(apply_fixed_id);
			TeamTable3Example tt3example = new TeamTable3Example();
			TeamTable3Example.Criteria tt3criteria = tt3example.createCriteria();
			tt3criteria.andTeamIdEqualTo(ut2.getTeamId());
			return tt3service.updateByExampleSelective(tt3record, tt3example );		
		}
		
		return 1;
	}
	
	/*
	 * 申请更换固定场(更新)
	 * user_id, 用户id
	 * int change_fixedCourt, 要更换的固定场申请表编号
	 * int today_court_id, 要更换固定场的当天场次
	 * int day_of_week, 是星期几
	 * 
	 *return:{
	 *	0:申请失败
	 *	1:申请成功
	 *	-2:请求数据错误
	 *}
	 */
	@RequestMapping("/applyChangeFixedCourt")
	@ResponseBody
	public int applyChangeFixedCourt(int user_id,int change_fixedCourt, int today_court_id,int day_of_week) {
		//弃用//return insertapplyFixedCourt(user_id,today_court_id,day_of_week,3);//3:更换场地未审核
		
		//检查请求数据是否有效		
		ApplyFixedTable7Example example = new ApplyFixedTable7Example();
		ApplyFixedTable7Example.Criteria criteria = example.createCriteria();
		criteria.andApplyFixedIdEqualTo(change_fixedCourt);
		criteria.andUserIdEqualTo(user_id);
		
		if(af7service.selectByExample(example).isEmpty()) {
			//找不到，请求数据出错
			return -2;
		}

		//申请固定场表状态，设置为3.换场未审核
		ApplyFixedTable7Example af7example = new ApplyFixedTable7Example();
		ApplyFixedTable7Example.Criteria af7criteria = af7example.createCriteria();
		af7criteria.andApplyFixedIdEqualTo(change_fixedCourt);
		
		ApplyFixedTable7 af7record = new ApplyFixedTable7();
		af7record.setApplyState(3);//换场未审核
		
		af7service.updateByExampleSelective(af7record , af7example);
		
		//插入申请换场表
		ApplyChangeTable8 record = new ApplyChangeTable8();
		record.setUserId(user_id);
		record.setBookId(change_fixedCourt);//以前固定场申请编号
		record.setCourtId(today_court_id);//当天场次
		record.setTimeId(day_of_week);//周几
		record.setApplyState(3);//球队用户：未审核
		return ac8service.insertSelective(record);
	}

	
	/*
	 * 申请退还固定场
	 * user_id, 用户id
	 * int fixedCourt_id, 要更换的固定场申请表编号
	 * 
	 *return:{
	 *	0:申请失败
	 *	1:申请成功
	 *	-2:请求数据错误
	 *}
	 */
	@RequestMapping("/applyUnsubscribeFixedCourt")
	@ResponseBody
	public int applyUnsubscribeFixedCourt(int user_id,int unsubscribe_fixedCourt,String reason) {
		//检查请求数据是否有效		
		ApplyFixedTable7Example example = new ApplyFixedTable7Example();
		ApplyFixedTable7Example.Criteria criteria = example.createCriteria();
		criteria.andApplyFixedIdEqualTo(unsubscribe_fixedCourt);
		criteria.andUserIdEqualTo(user_id);
		
		if(af7service.selectByExample(example).isEmpty()) {
			//找不到，请求数据出错
			return -2;
		}

		//申请固定场表状态，设置为4.换场未审核
		ApplyFixedTable7 af7record = new ApplyFixedTable7();
		af7record.setApplyState(4);//退场未审核
		
		ApplyFixedTable7Example af7example = new ApplyFixedTable7Example();
		ApplyFixedTable7Example.Criteria af7criteria = af7example.createCriteria();
		af7criteria.andApplyFixedIdEqualTo(unsubscribe_fixedCourt);
		
		af7service.updateByExampleSelective(af7record , af7example);
		
		//插入退场申请表
		ApplyUnsubscribeTable6 record = new ApplyUnsubscribeTable6();
		record.setUserId(user_id);
		record.setBookId(unsubscribe_fixedCourt);
		record.setReason(reason);
		record.setUnsubscribeState(3);
		
		return au6service.insertSelective(record);
	}
	
	//用户自身查表
	 /*
	  *用户根据固定场编号 获取 该申请更换固定场
	  *返回null为无效查找
	  */
	 @RequestMapping("/ApplyChangeFixedCourtByUser")
	 @ResponseBody
	 public Object ApplyChangeFixedCourtByUser(int user_id,int book_id) {
	  ApplyChangeTable8Example example = new ApplyChangeTable8Example(); 
	  ApplyChangeTable8Example.Criteria criteria = example.createCriteria(); 
	  criteria.andUserIdEqualTo(user_id);
	  criteria.andBookIdEqualTo(book_id);
	  
/*	  List<ApplyChangeTable8> list = ac8service.selectByExample(example);
	  if(list.isEmpty()) {
	   return null;
	  }
	  return list; */
	  return ac8service.selectByExample(example);
	 }
	 
	 /*
	  *用户根据固定场编号 获取 该申请退场更换固定场
	  *返回null为无效查找
	  */
	 @RequestMapping("/ApplyUnsubscribeFixedCourtByUser")
	 @ResponseBody
	 public Object ApplyUnsubscribeFixedCourtByUser(int user_id,int book_id) {
	  ApplyUnsubscribeTable6Example example = new ApplyUnsubscribeTable6Example();
	  ApplyUnsubscribeTable6Example.Criteria criteria = example.createCriteria(); 
	  criteria.andUserIdEqualTo(user_id);
	  criteria.andBookIdEqualTo(book_id);
	  
	  List <ApplyUnsubscribeTable6> list = au6service.selectByExample(example); 
	  if(list.isEmpty()) {
	   return null;
	  }
	  return list.get(0); 
	 }
	 //////////////////////////
	////////////////////////////////////////
	
	/*(弃用)
	 * 审核申请更换固定场的列表
	 */
//	@RequestMapping("/reviewChangeFixedCourtList")
//	@ResponseBody
//	public Object reviewChangeFixedCourtList() {
//		ApplyFixedTable7Example example = new ApplyFixedTable7Example();
//		ApplyFixedTable7Example.Criteria criteria = example.createCriteria();
//		criteria.andApplyStateEqualTo(3);//3:换场未审核
//		return af7service.selectByExample(example);
//	}
	/*
	 *根据申请固定场表主键id获取该固定场项 
	 */
	@RequestMapping("/ApplyFixedbyPrimaryId")
	@ResponseBody
	public Object ApplyFixedbyPrimaryId(int PrimaryId) {		
		ApplyFixedTable7Example example = new ApplyFixedTable7Example();
		example.createCriteria().andApplyFixedIdEqualTo(PrimaryId);
		List<ApplyFixedTable7> list = af7service.selectByExample(example);
		if(list.isEmpty()) {
			return null;
		}else {
			return list.get(0);
		}
	}
	
	/*
	 *获取申请固定场的列表
	 *通过状态来查询
	 *-1:则获取全部
	 */
	@RequestMapping("/ApplyFixedCourtList")
	@ResponseBody
	public Object ApplyFixedCourtList(int state) {
		ApplyFixedTable7Example example = new ApplyFixedTable7Example();
		if(state != -1) {
			example.createCriteria().andApplyStateEqualTo(state);
		}
		return af7service.selectByExample(example);
	}
	
	/*
	 *获取申请更换固定场的列表
	 *通过状态来查询
	 *-1:则获取全部
	 */
	@RequestMapping("/ApplyChangeFixedCourtList")
	@ResponseBody
	public Object ApplyChangeFixedCourtList(int state) {
		ApplyChangeTable8Example example = new ApplyChangeTable8Example();	
		if(state != -1) {
			example.createCriteria().andApplyStateEqualTo(state);
		}
		return ac8service.selectByExample(example);
	}
	
	/*
	 * 获取申请退场更换固定场的列表
	 *通过状态来查询
	 *-1:则获取全部
	 */
	@RequestMapping("/ApplyUnsubscribeFixedCourtList")
	@ResponseBody
	public Object ApplyUnsubscribeFixedCourtList(int state) {
		ApplyUnsubscribeTable6Example example = new ApplyUnsubscribeTable6Example();
		if(state != -1) {
			example.createCriteria().andUnsubscribeStateEqualTo(state);
		}
		return au6service.selectByExample(example);
	}
	
	/*
	 * 获取该球队用户的有效固定场申请表项
	 */
	@RequestMapping("/getTeamFixedCourt")
	@ResponseBody
	public Object getTeamFixedCourt(int user_id) {
		return getTeamFixed(user_id);
	}
	
	public int af7service_Update(int record_applyState,int example_applyFixedId) {
		ApplyFixedTable7 record = new ApplyFixedTable7();
		record.setApplyState(record_applyState);
		ApplyFixedTable7Example example = new ApplyFixedTable7Example();
		ApplyFixedTable7Example.Criteria criteria = example.createCriteria();
		criteria.andApplyFixedIdEqualTo(example_applyFixedId);
		return af7service.updateByExampleSelective(record, example);
	}
	
	public int tt3serviceUpdate(int example_TeamId, int example_applyFixedId) {
		TeamTable3 tt3record = new TeamTable3();
		tt3record.setApplyFixedId(example_applyFixedId);
		TeamTable3Example tt3example = new TeamTable3Example();
		TeamTable3Example.Criteria tt3criteria = tt3example.createCriteria();
		tt3criteria.andTeamIdEqualTo(example_TeamId);
		return tt3service.updateByExampleSelective(tt3record, tt3example );		
	}
	
	/*
	 * 审核申请更换固定场,是否同意
	 * apply_state 是否同意 //4:换场同意；5：换场不同意
	 * apply_fixed_id 被更换的申请固定场的编号
	 * apply_change_fixed_id 申请更换的固定场的编号
	 * user_id 申请的用户账号
	 */
	@RequestMapping("/reviewChangeFixedCourtAnswer")
	@ResponseBody
	public int reviewChangeFixedCourtAnswer(int user_id ,int apply_change_fixed_id, int apply_fixed_id, int apply_state) {
		UserTable2Example ut2example = new UserTable2Example();
		UserTable2Example.Criteria ut2criteria = ut2example.createCriteria();
		ut2criteria.andUserIdEqualTo(user_id);
		UserTable2 ut2 = ut2service.selectByExample(ut2example).get(0);
		
		if(!ut2.getProperty().equals("球队用户")) {
			return 0;//非球队用户
		}
		//如果同意
		if(apply_state == 4) {
			af7service_Update(1,apply_change_fixed_id);
			af7service_Update(4,apply_fixed_id);
			
			tt3serviceUpdate(ut2.getTeamId(),apply_change_fixed_id);
		//如果不同意
		}else if(apply_state == 5){
			af7service_Update(5,apply_change_fixed_id);
		}
		
		return 1;
	}
}
