package com.booking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.booking.service.Team3Service;



/**
* ��Ŀ����:booking

* �ļ�����:TeamsController.java
* ������Ա:nicewithgreat
* ��������:
*/
@Controller
public class TeamsController {
	@Autowired
	Team3Service t3service;
	
	@RequestMapping("/getTeamsList")
	@ResponseBody
	public Object getTeamsList() {		
		return t3service.getCollegeList();
	}
}

