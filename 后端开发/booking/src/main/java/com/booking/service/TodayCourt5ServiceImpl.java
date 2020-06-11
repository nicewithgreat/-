package com.booking.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.dao.TodayCourtTable5Mapper;
import com.booking.pojo.TodayCourtTable5;
import com.booking.pojo.TodayCourtTable5Example;

/**
* ��Ŀ����:booking

* �ļ�����:TodayCourt5ServiceImpl.java
* ������Ա:nicewithgreat
* ��������:
*/
@Service
public class TodayCourt5ServiceImpl implements TodayCourt5Service {
	@Autowired
	private TodayCourtTable5Mapper mapper;
	
	
	/*�������������ʼ�� ȫ�ֱ���ʱ��α�*/
	protected List<TodayCourtTable5> TodayCourtList;
	protected Map<Integer,TodayCourtTable5> TodayCourtMap = new HashMap<>();
	
	@PostConstruct
	public void init() {
		if(TodayCourtList == null) {
			TodayCourtList = mapper.selectByExample(new TodayCourtTable5Example());
		}
		for(TodayCourtTable5 item :TodayCourtList) {
			TodayCourtMap.put(item.getTodayCourtId(), item);
		}
	}
	
	public List<TodayCourtTable5> getTodayCourtList(){
		return TodayCourtList;
	}
	
	public Map<Integer,TodayCourtTable5> getTodayCourtMap(){
		return TodayCourtMap;
	}
	
	public TodayCourtTable5 getTodayCourtByID(int ID) {
		return TodayCourtMap.get(ID);
	}
	
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
