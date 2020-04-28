package com.booking.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.booking.pojo.CourtTable0;
import com.booking.pojo.CourtTable0Example;
import com.booking.service.CourtTable0Service;

@Controller
public class CourtTable0Controller {
	
	@Autowired
	CourtTable0Service ct0service;
	
	
	/*获取场地*/
	@RequestMapping("/getallCourt")
	public List<CourtTable0> getall(){
		List<CourtTable0> ct0list= (ct0service.selectByExample(new CourtTable0Example())) .stream().collect(Collectors.toList());
		for(CourtTable0 ct0 : ct0list) {
			System.out.print(ct0.toString());
		}
		
		return ct0list; 
	}
}
