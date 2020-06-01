package com.booking.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.booking.pojo.CourtTable0;
import com.booking.pojo.CourtTable0Example;
import com.booking.service.CourtTable0Service;

/**
* ��Ŀ����:booking

* �ļ�����:CourtTable0Controller.java
* ������Ա:nicewithgreat
* ��������:���쳡�ζ������
*/

@Controller
public class CourtTable0Controller {
	
	@Autowired
	CourtTable0Service ct0service;
	
	
	/*��ȡ����*/
	@RequestMapping("/getallCourt")
	@ResponseBody
	public List<CourtTable0> getall(){
		List<CourtTable0> ct0list= (ct0service.selectByExample(new CourtTable0Example()));
		for(CourtTable0 ct0 : ct0list) {
			System.out.print(ct0.toString());
		}		
		return ct0service.selectByExample(new CourtTable0Example()); 
	}
}
