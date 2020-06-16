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

* 文件名称:UserTable2Controller.java
* 创建人员:nicewithgreat
* 功能描述:
*/
@Controller
public class UserTable2Controller {
	@Autowired
	UserTable2Service ut2service;
	@Autowired
	Team3Service t3service;
	@Autowired
	ApplyFixed7Service af7service;
	@Autowired
	ApplyUnsubscribe6Service au6service;
	@Autowired
	ApplyChange8Service ac8service;
	
	/* 函数参数
	 * String wechatNO 微信号
	 * int user_id 用户表主键：UserTable2Controller.user_id
	 * String identity_num 申请的学号或工号
	 * boolean waitResult 管理员审查 完善个人信息的 结果
	 * */
	//功能描述:当新用户第一次登录，则将微信号当用户名称插入用户表
	//返回1即成功
	@RequestMapping("/addUser")
	@ResponseBody
	public int addUser(String wechatNO) {
		UserTable2Example example = new UserTable2Example();
		example.createCriteria().andUserNameEqualTo(wechatNO);
		
		if(ut2service.selectByExample(example).isEmpty()) {
			UserTable2 record = new UserTable2();
			record.setUserName(wechatNO);
			record.setIdentityNum("0");
			record.setPassword("0");
			record.setLevel((double)0);
			record.setProperty("游客");
			
			return ut2service.insertSelective(record);
		}
		
		return 0;
	}
	
	//功能描述:提交完善的个人信息
	//返回1提交成功
	//返回-1 该学号/工号已被注册，去跟管理员申请
	@RequestMapping("/improvingInfo")
	@ResponseBody
	public int improvingInfo(int user_id,String wechatNO,String identity_num) {
		
		UserTable2Example example = new UserTable2Example();
		UserTable2Example.Criteria criteria = example.createCriteria();
		
		//查找该学号/工号是否已被注册
		criteria.andIdentityNumEqualTo(identity_num);
		criteria.andUserIdNotEqualTo(user_id);
		criteria.andUserNameNotLike("游客");
		if(!ut2service.selectByExample(example).isEmpty()) {
			return -1;
		}
		
		//查找是否符合有效条件
		example = new UserTable2Example();
		criteria = example.createCriteria();
		
		criteria.andUserNameEqualTo(wechatNO);
		criteria.andUserIdEqualTo(user_id);
		criteria.andPropertyLike("游客");
		
		if(!ut2service.selectByExample(example).isEmpty()) {			
			UserTable2 record = new UserTable2();
			record.setIdentityNum(identity_num);
			record.setProperty("游客待审核");
			return ut2service.updateByExampleSelective(record , example);
		}
		return 0;
	}
	
	//功能描述:提交申请为球队用户
	//返回1提交成功
	@RequestMapping("/improvingTeamInfo")
	@ResponseBody
	public int improvingTeamInfo(int user_id,String wechatNO,int team_id) {
		UserTable2Example example = new UserTable2Example();
		UserTable2Example.Criteria criteria = example.createCriteria();
		criteria.andUserNameEqualTo(wechatNO);
		criteria.andUserIdEqualTo(user_id);
		criteria.andPropertyLike("普通用户");//判断前缀是否为普通用户
		
		if(!ut2service.selectByExample(example).isEmpty()) {			
			UserTable2 record = new UserTable2();
			record.setProperty("普通用户待审核");
			record.setTeamId(team_id);
			return ut2service.updateByExampleSelective(record , example);
		}
		return 0;
	}
	
	//功能描述：管理员登录
	//成功返回管理员id,失败返回-1
	@RequestMapping("/loginAdmin")
	@ResponseBody
	public int getMyInfo(String username,String password) {
		UserTable2Example example = new UserTable2Example();
		UserTable2Example.Criteria criteria = example.createCriteria();
		criteria.andUserNameEqualTo(username);
		criteria.andPasswordEqualTo(password);
		
		List<UserTable2> list = ut2service.selectByExample(example);
		if(list.isEmpty()) {
			return -1;
		}
		return list.get(0).getUserId();
	}
	
	//功能描述:获取当前用户个人信息
	//失败返回null
	@RequestMapping("/getMyInfo")
	@ResponseBody
	public Object getMyInfo(//int user_id,
							String wechatNO) {
		UserTable2Example example = new UserTable2Example();
		UserTable2Example.Criteria criteria = example.createCriteria();
		criteria.andUserNameEqualTo(wechatNO);
		//criteria.andUserIdEqualTo(user_id);
		
		List<UserTable2> list = ut2service.selectByExample(example);
		if(list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}
	
	//功能描述:管理员获取游客用户申请为普通用户提交的审核(未审核)
	@RequestMapping("/getWaitingTourist")
	@ResponseBody
	public Object getWaitingTourist() {
		return getWaitingUserInfo("游客待审核");
	}
	
	//功能描述:管理员获取普通用户申请为球队用户提交的审核(未审核)
	@RequestMapping("/getWaitingOrdinaryUser")
	@ResponseBody
	public Object getWaitingOrdinaryUser() {
		return getWaitingUserInfo("普通用户待审核");
	}
	
	//功能描述:管理员获取球队用户申请固定场的列表(未审核)
	@RequestMapping("/getApplyFixedList")
	@ResponseBody
	public Object getApplyFixedList() {
		ApplyFixedTable7Example example = new ApplyFixedTable7Example();
		ApplyFixedTable7Example.Criteria criteria = example.createCriteria();
		criteria.andApplyStateEqualTo(0);//0:未审核
		
		return af7service.selectByExample(example);
	}
	
	//功能描述:管理员获取普通用户换场申请列表(未审核)
	@RequestMapping("/getChangeCourtList")
	@ResponseBody
	public Object getChangeCourtList() {
		return getApplyChangeList(0);
	}
	
	//功能描述:管理员获取普通用户退场申请列表(未审核)
	@RequestMapping("/getUnsubscribeCourtList")
	@ResponseBody
	public Object getUnsubscribeCourtList() {
		return getApplyUnsubscribeList(0);
	}
	
	//功能描述:管理员获取球队用户更换固定场申请列表(未审核)
	@RequestMapping("/getChangeFixedList")
	@ResponseBody
	public Object getChangeFixedCourtList() {
		return getApplyChangeList(3);
	}
	
	//功能描述:管理员获取球队用户退还固定场申请列表(未审核)
	@RequestMapping("/getUnsubscribeFixedList")
	@ResponseBody
	public Object getUnsubscribeFixedList() {
		return getApplyUnsubscribeList(3);
	}
	
	private List<UserTable2> getWaitingUserInfo(String Property) {
		UserTable2Example example = new UserTable2Example();
		UserTable2Example.Criteria criteria = example.createCriteria();
		criteria.andPropertyEqualTo(Property);//用户性质
		
		List<UserTable2> list = ut2service.selectByExample(example);
		if(list.isEmpty()) {
			return null;
		}
		return list;
	}
	
	private List<ApplyUnsubscribeTable6> getApplyUnsubscribeList(int state_Property){
		
		ApplyUnsubscribeTable6Example example = new ApplyUnsubscribeTable6Example();
		ApplyUnsubscribeTable6Example.Criteria criteria = example.createCriteria();
		criteria.andUnsubscribeStateEqualTo(state_Property);
		
		return au6service.selectByExample(example);		
	}
	
	private List<ApplyChangeTable8> getApplyChangeList(int state_Property){
		ApplyChangeTable8Example example = new ApplyChangeTable8Example();
		ApplyChangeTable8Example.Criteria criteria = example.createCriteria(); 
		criteria.andApplyStateEqualTo(state_Property);
		
		return ac8service.selectByExample(example );
	}
	
	//功能描述:管理员审核游客提交的完善信息，给予是否通过
	//返回1则审核完成成功
	//返回-1则该学号/工号已被注册,无法通过
	@RequestMapping("/checkTouristInfo")
	@ResponseBody
	public int checkTouristInfo(int user_id,String identity_num, boolean waitResult) {
		//检查该学号/工号已被注册
		UserTable2Example example = new UserTable2Example();
		UserTable2Example.Criteria criteria = example.createCriteria();
		criteria.andIdentityNumEqualTo(identity_num);
		criteria.andUserIdNotEqualTo(user_id);
		criteria.andUserNameNotLike("游客");
		
		if(!ut2service.selectByExample(example).isEmpty()) {
			return -1;
		}

		//提交是否通过
		String result;
		if(waitResult) {
			result = "普通用户";
		}else {
			result = "游客审核不通过";
		}
		
		return checkImprovingInfo(user_id, result, -1);
	}
	
	//功能描述:管理员审核普通用户提交的申请为球队用户，给予是否通过
	//返回1则审核完成成功
	@RequestMapping("/checkOrdinaryInfo")
	@ResponseBody
	public int checkOrdinaryInfo(int user_id,boolean waitResult,int teamId) {
		String result;
		if(waitResult) {
			result = "球队用户";
			//如果之前该球队已经有了球队用户，则将之前的球队用户取消变成普通用户;
			checkTeamSetUser(teamId, user_id);
		}else {
			result = "普通用户审核不通过";
		}
		return checkImprovingInfo(user_id, result, teamId);
	}
	
	//检查该球队已经有了球队用户，则将之前的球队用户取消变成普通用户;
	private int checkTeamSetUser(int teamId,int changeUserId) {
		
		//查找改球队当前球队成员
		
		UserTable2Example ut2example = new UserTable2Example();
		UserTable2Example.Criteria ut2Criteria = ut2example.createCriteria();
		ut2Criteria.andTeamIdEqualTo(teamId);
		ut2Criteria.andPropertyEqualTo("球队用户");
		
		List<UserTable2> lt2List = ut2service.selectByExample(ut2example );
		if(!lt2List.isEmpty()) {
			UserTable2 ut2record = new UserTable2();
			ut2record.setProperty("普通用户");
			//当前球队成员变成普通用户
			ut2service.updateByExampleSelective(ut2record, ut2example);
		}
		
		
		TeamTable3Example example = new TeamTable3Example();
		TeamTable3Example.Criteria criteria = example.createCriteria();
		criteria.andTeamIdEqualTo(teamId);
		
		List<TeamTable3> teamList = t3service.selectByExample(example);
		if(teamList.isEmpty()) {
			return 0;
		}
		TeamTable3 teamItem = teamList.get(0);
		if(teamItem.getApplyFixedId() != null) {//查找给球队有没有固定场
			ApplyFixedTable7 af7record = new ApplyFixedTable7();
			af7record.setUserId(changeUserId);
			ApplyFixedTable7Example af7example = new ApplyFixedTable7Example();
			ApplyFixedTable7Example.Criteria af7criteria = af7example.createCriteria();
			af7criteria.andApplyFixedIdEqualTo(teamItem.getApplyFixedId());
			//查找该固定场，使他变成新球队成员名下
			af7service.updateByExampleSelective(af7record, af7example);
		}
		
		//使得申请的普通用户变成球队用户
		ut2example = new UserTable2Example();
		ut2Criteria = ut2example.createCriteria();
		ut2Criteria.andUserIdEqualTo(changeUserId);

		UserTable2 ut2record = new UserTable2();
		ut2record.setProperty("球队用户");
		//普通用户变成球队用户
		ut2service.updateByExampleSelective(ut2record, ut2example);

		
		return 0;
	}
	
	public int checkImprovingInfo(int user_id,String waitResult,int teamId) {
		UserTable2Example example = new UserTable2Example();
		UserTable2Example.Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(user_id);
			
		UserTable2 record = new UserTable2();

		if(teamId != -1) {
			record.setTeamId(teamId);
		}
		record.setProperty(waitResult);
		
		return ut2service.updateByExampleSelective(record , example);
		
	}
	
	
}
