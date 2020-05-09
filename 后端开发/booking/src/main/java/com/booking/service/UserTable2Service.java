package com.booking.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.booking.pojo.UserTable2;
import com.booking.pojo.UserTable2Example;

/**
* 项目名称:booking

* 文件名称:UserTable2Service.java
* 创建人员:nicewithgreat
* 功能描述:
*/
public interface UserTable2Service {
	int insertSelective(UserTable2 record);
	
	List<UserTable2> selectByExample(UserTable2Example example);
	
	int updateByExampleSelective(UserTable2 record, UserTable2Example example);
	
	int deleteByExample(UserTable2Example example);
}
