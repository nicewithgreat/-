package com.booking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.dao.UserTable2Mapper;
import com.booking.pojo.UserTable2;
import com.booking.pojo.UserTable2Example;

/**
* 项目名称:booking

* 文件名称:UserTable2ServiceImpl.java
* 创建人员:nicewithgreat
* 功能描述:
*/
@Service
public class UserTable2ServiceImpl implements UserTable2Service {
	@Autowired
	UserTable2Mapper mapper;
		
	@Override
	public int insertSelective(UserTable2 record) {
		// TODO Auto-generated method stub
		return mapper.insertSelective(record);
	}

	@Override
	public List<UserTable2> selectByExample(UserTable2Example example) {
		// TODO Auto-generated method stub
		return mapper.selectByExample(example);
	}

	@Override
	public int updateByExampleSelective(UserTable2 record, UserTable2Example example) {
		// TODO Auto-generated method stub
		return mapper.updateByExampleSelective(record, example);
	}

	@Override
	public int deleteByExample(UserTable2Example example) {
		// TODO Auto-generated method stub
		return mapper.deleteByExample(example);
	}

}
