package com.booking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.dao.BookHistoryTable4Mapper;
import com.booking.pojo.BookHistoryTable4;
import com.booking.pojo.BookHistoryTable4Example;

/**
* ��Ŀ����:booking

* �ļ�����:BookHistoryTable4ServiceImpl.java
* ������Ա:nicewithgreat
* ��������:
*/
@Service
public class BookHistory4ServiceImpl implements BookHistory4Service {
	@Autowired
	private BookHistoryTable4Mapper mapper;
	
	@Override
	public int insertSelective(BookHistoryTable4 record) {
		// TODO Auto-generated method stub
		return mapper.insertSelective(record);
	}

	@Override
	public List<BookHistoryTable4> selectByExample(BookHistoryTable4Example example) {
		// TODO Auto-generated method stub
		return mapper.selectByExample(example);
	}

	@Override
	public int updateByExampleSelective(BookHistoryTable4 record, BookHistoryTable4Example example) {
		// TODO Auto-generated method stub
		return mapper.updateByExampleSelective(record, example);
	}

}
