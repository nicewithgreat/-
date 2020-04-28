package com.booking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.dao.CourtTable0Mapper;
import com.booking.pojo.CourtTable0;
import com.booking.pojo.CourtTable0Example;

@Service
public class CourtTable0ServiceImpl implements CourtTable0Service {
	@Autowired
	private CourtTable0Mapper mapper;
	
	public List<CourtTable0> selectByExample(CourtTable0Example example) {
		// TODO Auto-generated method stub
		return mapper.selectByExample(example);
	}

}
