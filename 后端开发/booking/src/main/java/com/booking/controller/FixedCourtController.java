package com.booking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.booking.dao.ApplyFixedTable7Mapper;
import com.booking.dao.TeamTable3Mapper;
import com.booking.pojo.ApplyFixedTable7;
import com.booking.pojo.ApplyFixedTable7Example;
import com.booking.pojo.TeamTable3;
import com.booking.pojo.TeamTable3Example;
import com.booking.pojo.UserTable2;
import com.booking.pojo.UserTable2Example;
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
	 * 申请固定场
	 * 
	 */
	@RequestMapping("/applyFixedCourt")
	@ResponseBody
	public int applyFixedCourt(int user_id,int today_court_id,int day_of_week) {
		return insertapplyFixedCourt(user_id,today_court_id,day_of_week,0);
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
	 * 申请更换固定场
	 */
	@RequestMapping("/applyChangeFixedCourt")
	@ResponseBody
	public int applyChangeFixedCourt(int user_id,int today_court_id,int day_of_week) {
		return insertapplyFixedCourt(user_id,today_court_id,day_of_week,3);//3:更换场地未审核
	}
	
	/*
	 * 审核申请更换固定场的列表
	 */
	@RequestMapping("/reviewChangeFixedCourtList")
	@ResponseBody
	public Object reviewChangeFixedCourtList() {
		ApplyFixedTable7Example example = new ApplyFixedTable7Example();
		ApplyFixedTable7Example.Criteria criteria = example.createCriteria();
		criteria.andApplyStateEqualTo(3);//3:换场未审核
		return af7service.selectByExample(example);
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
