package com.booking.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.booking.pojo.BookHistoryTable4;
import com.booking.pojo.BookHistoryTable4Example;
import com.booking.pojo.ScheduledTimeTable1;
import com.booking.pojo.TodayCourtTable5;
import com.booking.pojo.TodayCourtTable5Example;
import com.booking.service.BookHistory4Service;
import com.booking.service.ScheduledTime1Service;
import com.booking.service.TodayCourt5Service;

/**
* ��Ŀ����:booking

* �ļ�����:TodayCourtController.java
* ������Ա:nicewithgreat
* ��������:���쳡�ζ������
*/

@Controller
public class TodayCourtController {
	@Autowired
	TodayCourt5Service tc5service;
	@Autowired
	BookHistory4Service bh4service;
	@Autowired
	ScheduledTime1Service st1service;
	
	
	/*��ȡĿǰ�Ľ��충�������,�����Ϊ�����ѯ,���齫ʱ��α���ë�򳡱���뵽����*/
	@RequestMapping("/getTodayCourtWithOtherInfo")
	@ResponseBody
	public Object getTodayCourtWithOtherInfo(){
//		List<TodayCourtTable5> t5list= (tc5service.selectByExampleWith(new TodayCourtTable5Example())) .stream().collect(Collectors.toList());
//		for(TodayCourtTable5 l5 : t5list) {
//			System.out.println(l5.toString());
//		}
		
		return tc5service.selectByExampleWith(new TodayCourtTable5Example());
	}
	
	
	/*��ȡĿǰ�Ľ��충�������,�������ѯ*/
	@RequestMapping("/getTodayCourt")
	@ResponseBody
	public Object getTodayCourt(){
		return tc5service.selectByExample(new TodayCourtTable5Example());
	}
	
	/*Ԥ���������Ч���г��أ���Ҫǰ���ж��Ƿ�����Ч�ڼ�*/
	@RequestMapping("/bookTodayFreeCourt")
	@ResponseBody
	public int bookTodayFreeCourt(int user_id,TodayCourtTable5 item) {
		
		//��today_court_id��Ӧ�����и���״̬Ϊ1
		TodayCourtTable5 tc5record = new TodayCourtTable5();
		tc5record.setCourtState(1);
		TodayCourtTable5Example tc5example = new TodayCourtTable5Example();
		
		TodayCourtTable5Example.Criteria tc5Criteria = tc5example.createCriteria();  
		tc5Criteria.andTodayCourtIdEqualTo(item.getTodayCourtId());
		tc5Criteria.andCourtStateEqualTo(0);

		//���µ��충����Ϣ
		if(tc5service.updateByExampleSelective(tc5record, tc5example) < 1) {
			return 0;
		}
				
		insertToHistoryTable(user_id,item.getCourtId(),item.getTimeId(),1);

		//�޸ĳɹ�����1
		return 1;
	}
	
	//��������:��ѯ������ʷ������Ϣ
	@RequestMapping("/getMyHistoryInfo")
	@ResponseBody
	public Object getMyHistoryInfo(int user_id) {		
		BookHistoryTable4Example example = new BookHistoryTable4Example();
		BookHistoryTable4Example.Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(user_id);
		
		return bh4service.selectByExample(example);
	}
	
	//��������:���붩����Ϣ ����ʷ������Ϣ��
	@RequestMapping("/insertInfoToHistory")
	@ResponseBody
	public int insertInfoToHistory(int user_id,int court_id,int time_id,int book_state) {
		return insertToHistoryTable(user_id,court_id,time_id,book_state);
	}
	
	public int insertToHistoryTable(int user_id,int court_id,int time_id,int book_state) {
		BookHistoryTable4 bh4record = new BookHistoryTable4();
		bh4record.setUserId(user_id);
		bh4record.setCourtId(court_id);
		bh4record.setTimeId(time_id);
		bh4record.setBookState(book_state);
		//ʱ�䲿�����ݿ��Զ���ȡ
		
		return bh4service.insertSelective(bh4record);
	}
	
	//����������������ѡ����
	/*user_id:�û�id
	 *back_bookid:���������޸ĵ���ʷ��¼ID
	 *int back_courtid,int back_timeid,
	 * ��������id,����ʱ��id
	 *int change_courtid,int change_timeid
	 *Ҫ�ı�ĳ���id,Ҫ�ı��ʱ��id
	 *
	 *return:{
	 *	0:����ʧ��
	 *	1:����ɹ�
	 *	-1:��Ч����
	 *}
	 */
	
	@RequestMapping("/changeTodayCourt")
	@ResponseBody
	public int changeTodayCourt(int user_id,
								int back_bookid,int back_courtid,int back_timeid,
								int change_courtid,int change_timeid) {
		
		BookHistoryTable4Example example = new BookHistoryTable4Example();
		BookHistoryTable4Example.Criteria criteria = example.createCriteria();
		criteria.andBookIdEqualTo(back_bookid);
		criteria.andUserIdEqualTo(user_id);
		criteria.andBookStateEqualTo(0);
		
		ScheduledTimeTable1 item = st1service.getItem(back_courtid);
		criteria.andBookDateBetween(st1service.concatTodayDate(item.getStarttime()), st1service.concatTodayDate(item.getEndtime()));
		//�жϸ��û��Ѿ����˵ĳ����Ƿ���Ч
		if(bh4service.selectByExample(example)!=null) {
			//��Ч������޸�
			TodayCourtTable5 tc5record = new TodayCourtTable5();
			tc5record.setCourtState(1);
			
			TodayCourtTable5Example tc5example = new TodayCourtTable5Example();		
			TodayCourtTable5Example.Criteria tc5Criteria = tc5example.createCriteria();  
			tc5Criteria.andTodayCourtIdEqualTo(change_courtid);
			tc5Criteria.andCourtStateEqualTo(0);
			//����޸ĳɹ���
			if(tc5service.updateByExampleSelective(tc5record, tc5example)==1) {
				
				//�޸���ʷ��¼Ϊ����
				BookHistoryTable4 bh4record = new BookHistoryTable4();
				bh4record.setBookState(1);
				BookHistoryTable4Example bh4example = new BookHistoryTable4Example();
				BookHistoryTable4Example.Criteria bh4criteria = bh4example.createCriteria();
				bh4criteria.andBookIdEqualTo(back_bookid);
				bh4service.updateByExampleSelective(bh4record, bh4example);
				
				//���޸ĵ����˳��ĳ�����Ϣ
				tc5record = new TodayCourtTable5();
				tc5record.setCourtState(0);
				
				tc5example = new TodayCourtTable5Example();		
				tc5Criteria = tc5example.createCriteria();  
				tc5Criteria.andCourtIdEqualTo(change_courtid);
				tc5Criteria.andTimeIdEqualTo(change_timeid);
				tc5Criteria.andCourtStateEqualTo(1);	
				tc5service.updateByExampleSelective(tc5record, tc5example);
				
				
				//��������¶�������ʷ��¼
				return insertToHistoryTable(user_id,change_courtid,change_timeid,0);
			}
			return -1;
		}
		
		BookHistoryTable4 bh4record = new BookHistoryTable4();
		bh4record.setBookState(1);
		
		BookHistoryTable4Example bh4example = new BookHistoryTable4Example();
		BookHistoryTable4Example.Criteria bh4criteria = bh4example.createCriteria();
		bh4criteria.andUserIdEqualTo(user_id);
		bh4criteria.andCourtIdEqualTo(back_courtid);
		
		return 0;
	}
	

}
