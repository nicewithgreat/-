package com.booking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.dao.ApplyFixedTable7Mapper;
import com.booking.pojo.ApplyFixedTable7;
import com.booking.pojo.ApplyFixedTable7Example;

/**
* 项目名称:booking

* 文件名称:ApplyFixed7ServiceImpl.java
* 创建人员:nicewithgreat
* 功能描述:
*/
@Service
public class ApplyFixed7ServiceImpl implements ApplyFixed7Service {
	
	@Autowired
	ApplyFixedTable7Mapper mapper;
	
	@Override
	public int insertSelective(ApplyFixedTable7 record) {
		// TODO Auto-generated method stub
		return mapper.insertSelective(record);
	}

	@Override
	public List<ApplyFixedTable7> selectByExample(ApplyFixedTable7Example example) {
		// TODO Auto-generated method stub
		return mapper.selectByExample(example);
	}

	@Override
	public int updateByExampleSelective(ApplyFixedTable7 record, ApplyFixedTable7Example example) {
		// TODO Auto-generated method stub
		return mapper.updateByExampleSelective(record, example);
	}

}
