package com.booking.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.booking.pojo.TodayCourtTable5;
import com.booking.pojo.TodayCourtTable5Example;

/**
* 项目名称:booking

* 文件名称:TodayCourt5Service.java
* 创建人员:nicewithgreat
* 功能描述:
*/
public interface TodayCourt5Service {
	int updateByExampleSelective(TodayCourtTable5 record,TodayCourtTable5Example example);
	List<TodayCourtTable5> selectByExample(TodayCourtTable5Example example);
	List<TodayCourtTable5> selectByExampleWith(TodayCourtTable5Example example);
	
}
