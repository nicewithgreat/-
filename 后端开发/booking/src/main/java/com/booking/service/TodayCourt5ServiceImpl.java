package com.booking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.dao.TodayCourtTable5Mapper;
import com.booking.pojo.TodayCourtTable5;
import com.booking.pojo.TodayCourtTable5Example;

/**
* 项目名称:booking

* 文件名称:TodayCourt5ServiceImpl.java
* 创建人员:nicewithgreat
* 功能描述:
*/
@Service
public class TodayCourt5ServiceImpl implements TodayCourt5Service {
	@Autowired
	private TodayCourtTable5Mapper mapper;
	
	@Override
	public List<TodayCourtTable5> selectByExample(TodayCourtTable5Example example) {
		// TODO Auto-generated method stub
		return mapper.selectByExample(example);
	}

	@Override
	public List<TodayCourtTable5> selectByExampleWith(TodayCourtTable5Example example) {
		// TODO Auto-generated method stub
		return mapper.selectByExampleWith(example);
	}

	@Override
	public int updateByExampleSelective(TodayCourtTable5 record, TodayCourtTable5Example example) {
		// TODO Auto-generated method stub
		return mapper.updateByExampleSelective(record, example);
	}

}
