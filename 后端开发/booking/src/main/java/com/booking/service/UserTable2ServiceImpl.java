package com.booking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.dao.UserTable2Mapper;
import com.booking.pojo.UserTable2;
import com.booking.pojo.UserTable2Example;

/**
* ��Ŀ����:booking

* �ļ�����:UserTable2ServiceImpl.java
* ������Ա:nicewithgreat
* ��������:
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
