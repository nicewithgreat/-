package com.booking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.dao.ApplyChangeTable8Mapper;
import com.booking.pojo.ApplyChangeTable8;
import com.booking.pojo.ApplyChangeTable8Example;

/**
* 项目名称:booking

* 文件名称:ApplyChange8ServiceImpl.java
* 创建人员:nicewithgreat
* 功能描述:
*/
@Service
public class ApplyChange8ServiceImpl implements ApplyChange8Service {
	
	@Autowired
	ApplyChangeTable8Mapper mapper;
	
	@Override
	public int insertSelective(ApplyChangeTable8 record) {
		// TODO Auto-generated method stub
		return mapper.insertSelective(record);
	}

	@Override
	public List<ApplyChangeTable8> selectByExample(ApplyChangeTable8Example example) {
		// TODO Auto-generated method stub
		return mapper.selectByExample(example);
	}

	@Override
	public int updateByExampleSelective(ApplyChangeTable8 record, ApplyChangeTable8Example example) {
		// TODO Auto-generated method stub
		return mapper.updateByExampleSelective(record, example);
	}

}
