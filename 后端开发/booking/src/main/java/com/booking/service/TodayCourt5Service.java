package com.booking.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.booking.pojo.TodayCourtTable5;
import com.booking.pojo.TodayCourtTable5Example;

/**
* ��Ŀ����:booking

* �ļ�����:TodayCourt5Service.java
* ������Ա:nicewithgreat
* ��������:
*/
public interface TodayCourt5Service {
	int updateByExampleSelective(TodayCourtTable5 record,TodayCourtTable5Example example);
	
	List<TodayCourtTable5> selectByExample(TodayCourtTable5Example example);
	
	List<TodayCourtTable5> selectByExampleWith(TodayCourtTable5Example example);	
	
	public List<TodayCourtTable5> getTodayCourtList();
	
	public Map<Integer,TodayCourtTable5> getTodayCourtMap();
	
	public TodayCourtTable5 getTodayCourtByID(int ID);
}
