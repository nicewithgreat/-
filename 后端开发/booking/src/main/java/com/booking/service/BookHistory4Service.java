package com.booking.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.booking.pojo.BookHistoryTable4;
import com.booking.pojo.BookHistoryTable4Example;

/**
* ��Ŀ����:booking

* �ļ�����:BookHistoryTable4Service.java
* ������Ա:nicewithgreat
* ��������:
*/
public interface BookHistory4Service {
	int insertSelective(BookHistoryTable4 record);
	
	List<BookHistoryTable4> selectByExample(BookHistoryTable4Example example);
	
	int updateByExampleSelective(@Param("record") BookHistoryTable4 record, @Param("example") BookHistoryTable4Example example);
}
