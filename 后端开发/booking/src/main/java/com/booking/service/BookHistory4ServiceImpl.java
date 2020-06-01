package com.booking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.dao.BookHistoryTable4Mapper;
import com.booking.pojo.BookHistoryTable4;
import com.booking.pojo.BookHistoryTable4Example;

/**
* 项目名称:booking

* 文件名称:BookHistoryTable4ServiceImpl.java
* 创建人员:nicewithgreat
* 功能描述:
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
