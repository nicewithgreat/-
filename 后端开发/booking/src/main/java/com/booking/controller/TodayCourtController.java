package com.booking.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.booking.pojo.ApplyChangeTable8;
import com.booking.pojo.ApplyUnsubscribeTable6;
import com.booking.pojo.BookHistoryTable4;
import com.booking.pojo.BookHistoryTable4Example;
import com.booking.pojo.ScheduledTimeTable1;
import com.booking.pojo.TodayCourtTable5;
import com.booking.pojo.TodayCourtTable5Example;
import com.booking.service.ApplyChange8Service;
import com.booking.service.ApplyUnsubscribe6Service;
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
	@Autowired
	ApplyUnsubscribe6Service au6service;
	@Autowired
	ApplyChange8Service ac8service;
	
	
	
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
	public int bookTodayFreeCourt(int user_id,/*TodayCourtTable5 item*/
									int todayCourt_id) {
		//��ȡ���쳡�ζ���
		TodayCourtTable5 todayCache = tc5service.getTodayCourtMap().get(todayCourt_id);
		
		//��today_court_id��Ӧ�����и���״̬Ϊ1
		TodayCourtTable5 tc5record = new TodayCourtTable5();
		tc5record.setCourtState(1);
		TodayCourtTable5Example tc5example = new TodayCourtTable5Example();
		
		TodayCourtTable5Example.Criteria tc5Criteria = tc5example.createCriteria();  
		tc5Criteria.andTodayCourtIdEqualTo(todayCourt_id);
		tc5Criteria.andCourtStateEqualTo(0);

		//���µ��충����Ϣ
		if(tc5service.updateByExampleSelective(tc5record, tc5example) < 1) {
			return 0;
		}
		
		//�޸ĳɹ�����1		
		return insertToHistoryTable(user_id,todayCache.getCourtId(),todayCache.getTimeId(),0);//0�Ѷ�
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
	
	private int insertToHistoryTable(int user_id,int court_id,int time_id,int book_state) {
		BookHistoryTable4 bh4record = new BookHistoryTable4();
		bh4record.setUserId(user_id);
		bh4record.setCourtId(court_id);
		bh4record.setTimeId(time_id);
		bh4record.setBookState(book_state);
		//ʱ�䲿�����ݿ��Զ���ȡ
		
		return bh4service.insertSelective(bh4record);
	}
	
	//����������������ѡ����//**************����
	/*user_id:�û�id
	 *back_bookid:���������޸ĵ���ʷ��¼ID
	 *back_todayCourtId:�������쳡��id
	 *change_todayCourtId:Ҫ�ı䵱�쳡��id
	 *
	 *return:{
	 *	0:����ʧ��
	 *	1:����ɹ�
	 *	-1:��Ч����
	 *}
	 */
	
	@RequestMapping("/changeTodayCourt")//**************����
	@ResponseBody
	public int changeTodayCourt(int user_id,
								int back_bookid,
								/*int back_courtid,int back_timeid,
								int change_courtid,int change_timeid*/
								int back_todayCourtId,int change_todayCourtId) {

		BookHistoryTable4Example example = new BookHistoryTable4Example();
		BookHistoryTable4Example.Criteria criteria = example.createCriteria();
		criteria.andBookIdEqualTo(back_bookid);
		criteria.andUserIdEqualTo(user_id);
		criteria.andBookStateEqualTo(0);
		
		//ScheduledTimeTable1 item = st1service.getItem(tc5service.getTodayCourtByID(back_todayCourtId).getTimeId());//back_timeid
		ScheduledTimeTable1 item = st1service.getScheduledMap().get(tc5service.getTodayCourtByID(back_todayCourtId).getTimeId());///??????
		//�ж��Ƿ��ڿ��޸ĵ�ʱ����ڣ�
//		System.out.println("0"+st1service.concatTodayDate(item.getStarttime()));//error
//		System.out.println("0"+st1service.concatTodayDate(item.getEndtime()));//error
//		System.out.println("1"+item.getStarttime());
//		System.out.println("1"+item.getEndtime());
		criteria.andBookDateBetween(st1service.concatTodayDate(item.getStarttime()), st1service.concatTodayDate(item.getEndtime()));//�߼�����
		//�жϸ��û��Ѿ����˵ĳ����Ƿ���Ч
		if(!bh4service.selectByExample(example).isEmpty()) {
			System.out.println("success!");
			//��Ч������޸�
			TodayCourtTable5 tc5record = new TodayCourtTable5();
			tc5record.setCourtState(1);
			
			TodayCourtTable5Example tc5example = new TodayCourtTable5Example();		
			TodayCourtTable5Example.Criteria tc5Criteria = tc5example.createCriteria();  
			tc5Criteria.andTodayCourtIdEqualTo(change_todayCourtId);
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
				tc5Criteria.andTodayCourtIdEqualTo(change_todayCourtId);
				//tc5Criteria.andCourtIdEqualTo(TCMap.get(change_todayCourtId).getCourtId());//change_courtid
				//tc5Criteria.andTimeIdEqualTo(TCMap.get(change_todayCourtId).getTimeId());//change_timeid
				tc5Criteria.andCourtStateEqualTo(1);	
				
				tc5service.updateByExampleSelective(tc5record, tc5example);
				
				//��������¶�������ʷ��¼
				bh4record = new BookHistoryTable4();
				bh4record.setBookState(1);
				
				bh4example = new BookHistoryTable4Example();
				bh4criteria = bh4example.createCriteria();
				bh4criteria.andUserIdEqualTo(user_id);
				bh4criteria.andCourtIdEqualTo(tc5service.getTodayCourtByID(back_todayCourtId).getCourtId());//back_courtid
				
				return insertToHistoryTable(user_id,tc5service.getTodayCourtByID(change_todayCourtId).getCourtId(),tc5service.getTodayCourtByID(change_todayCourtId).getTimeId(),0);
			}else {
				return 0;
			}
			
		}else {
			return -1;
		}
	}
	

	//��ͨ�û����뻻��,�������ڶ�����ʮ�����ڻ����������ϸ���������������뻻����
	/*user_id:�û�id
	 *book_id:��ʷ������¼ID
	 *back_todayCourtId:�������ĳ��α�id
	 *change_todayCourtId:Ҫ�����ĳ��α�id
	 *
	 *return:{
	 *	0:����ʧ��
	 *	1:����ɹ�
	 *	2:����ʧ��
	 *	3:�����ɹ�
	 *	-1:��Ч����
	 *	-2:�������ݴ���
	 *}
	 */	
	@RequestMapping("/applyChangeTodayCourt")
	@ResponseBody
	public int applyChangeTodayCourt(int user_id,int book_id,int back_todayCourtId, int change_todayCourtId) {	//*****����Ҫ�Ĳ���
		BookHistoryTable4Example example = new BookHistoryTable4Example();
		BookHistoryTable4Example.Criteria criteria = example.createCriteria();
		criteria.andBookIdEqualTo(book_id);
		criteria.andUserIdEqualTo(user_id);
		
		List<BookHistoryTable4> bh4List = bh4service.selectByExample(example);
		if(bh4List.isEmpty()) {
			//�Ҳ������������ݳ���
			return -2;
		}
		//�ж��Ƿ��ǵ��충�ĳ�
		if(bh4List.get(0).getBookDate().after(todayzero())) {
			//��ȡ���쳡��map
			ScheduledTimeTable1 item = st1service.getScheduledMap().get(bh4List.get(0).getTimeId());
			//ScheduledTimeTable1 item = st1service.getItem(tc5service.getTodayCourtByID(bh4List.get(0).getTimeId()).getTimeId());//back_timeid
						
			//�ж�����ʱ���Ƿ񳬹���������
			Date now = new Date();
			if(item.getStarttime().after(now)) {
				//�ж��Ƿ����ڶ�����ʮ�����ڻ���,�������ϸ���������������뻻����
				if(bh4List.get(0).getBookDate().after(new Date(now.getTime() - 600000))) {
					return changeCourtOperate(user_id,book_id,back_todayCourtId,change_todayCourtId) + 2;//+2
				}else {
					//������ʷ������¼״̬Ϊ 2(���������)
					setBookHistoryState(book_id,2);
					
					ApplyChangeTable8 record = new ApplyChangeTable8();
					record.setUserId(user_id);
					record.setBookId(book_id);
					record.setCourtId(tc5service.getTodayCourtByID(change_todayCourtId).getCourtId());//court_id
					record.setTimeId(tc5service.getTodayCourtByID(change_todayCourtId).getTimeId());//time_id
					record.setApplyState(0);
					return ac8service.insertSelective(record);
				}
				
			}else {System.out.println("����ʱ�䳬������������ʱ��");return -1;}
		}else {System.out.println("���ǵ��충�ĳ�");return -1;}
	}
	

	//��ͨ�û������˳�,���ó���ʱ���ǰ����Сʱ֮ǰ���ǿ����˳���
	/*user_id:�û�id
	 *book_id:��ʷ������¼ID
	 *reason:�˳�ԭ��
	 *
	 *return:{
	 *	0:����ʧ��
	 *	1:����ɹ�
	 *	-1:��Ч�˳�
	 *	-2:�������ݴ���
	 *}
	 */
	@RequestMapping("/applyUnsubscribeTodayCourt")
	@ResponseBody
	public int applyUnsubscribeTodayCourt(int user_id,int book_id,String reason) {		
		BookHistoryTable4Example example = new BookHistoryTable4Example();
		BookHistoryTable4Example.Criteria criteria = example.createCriteria();
		criteria.andBookIdEqualTo(book_id);
		criteria.andUserIdEqualTo(user_id);
		criteria.andBookStateEqualTo(0);//�Ѷ�
		
		List<BookHistoryTable4> bh4List = bh4service.selectByExample(example);
		if(bh4List.isEmpty()) {
			//�Ҳ������������ݳ���
			return -2;
		}
		
		//�ж��Ƿ��ǵ��충�ĳ�
		if(bh4List.get(0).getBookDate().after(todayzero())) {			
			//��ȡ���쳡��map
			
			ScheduledTimeTable1 item = st1service.getScheduledMap().get(bh4List.get(0).getTimeId());
			//ScheduledTimeTable1 item = st1service.getItem(tc5service.getTodayCourtByID(bh4List.get(0).getTimeId()).getTimeId());//back_timeid
			
			//�ж������Ƿ񳬹��������� ʱ��ε�ǰ����Сʱ
//			System.out.println(st1service.concatTodayDate(item.getStarttime()));
//			System.out.println(new Date(new Date().getTime() + 7200000));
			if(st1service.concatTodayDate(item.getStarttime()).after(new Date(new Date().getTime() + 7200000))) {//7200000����Сʱ
				//������ʷ������¼״̬Ϊ 3(�˳������)
				setBookHistoryState(book_id,3);
				
				//����Чʱ���ھͲ��������˳���				
				ApplyUnsubscribeTable6 record = new ApplyUnsubscribeTable6();
				record.setUserId(user_id);
				record.setBookId(book_id);
				record.setReason(reason);
				record.setUnsubscribeState(0);//��ͨ�û���δ���
				return au6service.insertSelective(record);				
			}else {System.out.println("������������ ʱ��ε�ǰ����Сʱ");return -1;}	
		}else {System.out.println("���ǵ��충�ĳ�");return -1;}
	}
	
	//��ȡ�����賿0���ʱ��
	private Date todayzero() {
		Date today = new Date();
		today.setHours(0);
		today.setMinutes(0);
		today.setSeconds(0);
		return today;
	}
	
	
	//�������쳡�ز���
	/*
	 * return 0:����ʧ��
	 * return 1:�����ɹ�
	 */
	private int changeCourtOperate(int user_id, int back_bookid, int back_todayCourtId, int change_todayCourtId) {
		TodayCourtTable5 tc5record = new TodayCourtTable5();
		tc5record.setCourtState(1);//�Ѷ�
		
		TodayCourtTable5Example tc5example = new TodayCourtTable5Example();		
		TodayCourtTable5Example.Criteria tc5Criteria = tc5example.createCriteria();  
		tc5Criteria.andTodayCourtIdEqualTo(change_todayCourtId);
		tc5Criteria.andCourtStateEqualTo(0);
		//����޸ĳɹ���
		if(tc5service.updateByExampleSelective(tc5record, tc5example)==1) {
			//���޸ĵ����˳��ĳ�����Ϣ
			tc5record = new TodayCourtTable5();
			tc5record.setCourtState(0);
			
			tc5example = new TodayCourtTable5Example();		
			tc5Criteria = tc5example.createCriteria();  
			tc5Criteria.andTodayCourtIdEqualTo(back_todayCourtId);
			tc5Criteria.andCourtStateEqualTo(1);	
			
			tc5service.updateByExampleSelective(tc5record, tc5example);
			
			//�޸���ʷ��¼Ϊ����
			BookHistoryTable4 bh4record = new BookHistoryTable4();
			bh4record.setBookState(1);//����
			BookHistoryTable4Example bh4example = new BookHistoryTable4Example();
			BookHistoryTable4Example.Criteria bh4criteria = bh4example.createCriteria();
			bh4criteria.andBookIdEqualTo(back_bookid);
			
			bh4service.updateByExampleSelective(bh4record, bh4example);
			
			//��������¶�������ʷ��¼	
			return insertToHistoryTable(user_id,tc5service.getTodayCourtByID(change_todayCourtId).getCourtId(),tc5service.getTodayCourtByID(change_todayCourtId).getTimeId(),0);//0:�Ѷ�
		}else {return 0;}
	}
	
	/*
	 * ������ʷ������¼״̬
	 * ����״̬{
	 * 0(�Ѷ�);
	 * 1(����);
	 * 2(���������);
	 * 3(�˳������);
	 * }
	 */
	private int setBookHistoryState(int book_id,int state) {		
		BookHistoryTable4 record = new BookHistoryTable4();
		record.setBookState(state);
		BookHistoryTable4Example example = new BookHistoryTable4Example();
		//example.createCriteria().andUserIdEqualTo(user_id);
		example.createCriteria().andBookIdEqualTo(book_id);
		
		return bh4service.updateByExampleSelective(record, example);
	}
}
