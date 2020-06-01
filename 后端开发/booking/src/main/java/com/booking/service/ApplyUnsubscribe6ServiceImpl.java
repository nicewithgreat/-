package com.booking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.dao.ApplyUnsubscribeTable6Mapper;
import com.booking.pojo.ApplyUnsubscribeTable6;
import com.booking.pojo.ApplyUnsubscribeTable6Example;

/**
* 项目名称:booking

* 文件名称:ApplyUnsubscribeTable6ServiceImpl.java
* 创建人员:nicewithgreat
* 功能描述:
*/
@Service
public class ApplyUnsubscribe6ServiceImpl implements ApplyUnsubscribe6Service {
	
	@Autowired
	private ApplyUnsubscribeTable6Mapper mapper;
	
	@Override
	public int insertSelective(ApplyUnsubscribeTable6 record) {
		// TODO Auto-generated method stub
		return mapper.insertSelective(record);
	}

	@Override
	public List<ApplyUnsubscribeTable6> selectByExample(ApplyUnsubscribeTable6Example example) {
		// TODO Auto-generated method stub
		return mapper.selectByExample(example);
	}

	@Override
	public int updateByExampleSelective(ApplyUnsubscribeTable6 record, ApplyUnsubscribeTable6Example example) {
		// TODO Auto-generated method stub
		return mapper.updateByExampleSelective(record, example);
	}

}
