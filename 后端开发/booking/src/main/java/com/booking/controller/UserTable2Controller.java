package com.booking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.booking.pojo.UserTable2;
import com.booking.pojo.UserTable2Example;
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
	@RequestMapping("/improvingInfo")
	@ResponseBody
	public int improvingInfo(int user_id,String wechatNO,String identity_num) {
		UserTable2Example example = new UserTable2Example();
		UserTable2Example.Criteria criteria = example.createCriteria();
		criteria.andUserNameEqualTo(wechatNO);
		criteria.andUserIdEqualTo(user_id);
		
		if(!ut2service.selectByExample(example).isEmpty()) {			
			UserTable2 record = new UserTable2();
			record.setIdentityNum(identity_num);
			record.setProperty("待审核");
			return ut2service.updateByExampleSelective(record , example);
		}
		return 1;
	}
	
	//功能描述:获取当前用户个人信息
	//失败返回null
	@RequestMapping("/getMyInfo")
	@ResponseBody
	public Object getMyInfo(int user_id,String wechatNO) {
		UserTable2Example example = new UserTable2Example();
		UserTable2Example.Criteria criteria = example.createCriteria();
		criteria.andUserNameEqualTo(wechatNO);
		criteria.andUserIdEqualTo(user_id);
		
		List<UserTable2> list = ut2service.selectByExample(example);
		if(list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}
	
	//功能描述:管理员获取用户提交的审核
	@RequestMapping("/getWaitingUserInfo")
	@ResponseBody
	public Object getWaitingUserInfo() {
		UserTable2Example example = new UserTable2Example();
		UserTable2Example.Criteria criteria = example.createCriteria();
		criteria.andPropertyEqualTo("待审核");
		
		List<UserTable2> list = ut2service.selectByExample(example);
		if(list.isEmpty()) {
			return null;
		}
		return list;
	}
	
	//功能描述:管理员审核游客提交的完善信息，给予是否通过
	//返回1则审核完成成功
	@RequestMapping("/checkImprovingInfo")
	@ResponseBody
	public int checkImprovingInfo(int user_id,boolean waitResult) {
		UserTable2Example example = new UserTable2Example();
		UserTable2Example.Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(user_id);
			
		UserTable2 record = new UserTable2();
		String result;
		if(waitResult) {
			result = "普通用户";
		}else {
			result = "审核不通过";
		}
		record.setProperty(result);
		
		return ut2service.updateByExampleSelective(record , example);
		

	}
}
