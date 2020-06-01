package com.booking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.booking.pojo.UserTable2;
import com.booking.pojo.UserTable2Example;
import com.booking.service.UserTable2Service;

/**
* ��Ŀ����:booking

* �ļ�����:UserTable2Controller.java
* ������Ա:nicewithgreat
* ��������:
*/
@Controller
public class UserTable2Controller {
	@Autowired
	UserTable2Service ut2service;
	/* ��������
	 * String wechatNO ΢�ź�
	 * int user_id �û���������UserTable2Controller.user_id
	 * String identity_num �����ѧ�Ż򹤺�
	 * boolean waitResult ����Ա��� ���Ƹ�����Ϣ�� ���
	 * */
	//��������:�����û���һ�ε�¼����΢�źŵ��û����Ʋ����û���
	//����1���ɹ�
	@RequestMapping("/addUser")
	@ResponseBody
	public int addUser(String wechatNO) {
		UserTable2Example example = new UserTable2Example();
		example.createCriteria().andUserNameEqualTo(wechatNO);
		
		if(ut2service.selectByExample(example).isEmpty()) {
			UserTable2 record = new UserTable2();
			record.setUserName(wechatNO);
			record.setIdentityNum("0");
			record.setPassword("0");
			record.setLevel((double)0);
			record.setProperty("�ο�");
			
			return ut2service.insertSelective(record);
		}
		
		return 0;
	}
	
	//��������:�ύ���Ƶĸ�����Ϣ
	//����1�ύ�ɹ�
	@RequestMapping("/improvingInfo")
	@ResponseBody
	public int improvingInfo(int user_id,String wechatNO,String identity_num) {
		UserTable2Example example = new UserTable2Example();
		UserTable2Example.Criteria criteria = example.createCriteria();
		criteria.andUserNameEqualTo(wechatNO);
		criteria.andUserIdEqualTo(user_id);
		
		if(!ut2service.selectByExample(example).isEmpty()) {			
			UserTable2 record = new UserTable2();
			record.setIdentityNum(identity_num);
			record.setProperty("�����");
			return ut2service.updateByExampleSelective(record , example);
		}
		return 1;
	}
	
	//��������:��ȡ��ǰ�û�������Ϣ
	//ʧ�ܷ���null
	@RequestMapping("/getMyInfo")
	@ResponseBody
	public Object getMyInfo(int user_id,String wechatNO) {
		UserTable2Example example = new UserTable2Example();
		UserTable2Example.Criteria criteria = example.createCriteria();
		criteria.andUserNameEqualTo(wechatNO);
		criteria.andUserIdEqualTo(user_id);
		
		List<UserTable2> list = ut2service.selectByExample(example);
		if(list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}
	
	//��������:����Ա��ȡ�û��ύ�����
	@RequestMapping("/getWaitingUserInfo")
	@ResponseBody
	public Object getWaitingUserInfo() {
		UserTable2Example example = new UserTable2Example();
		UserTable2Example.Criteria criteria = example.createCriteria();
		criteria.andPropertyEqualTo("�����");
		
		List<UserTable2> list = ut2service.selectByExample(example);
		if(list.isEmpty()) {
			return null;
		}
		return list;
	}
	
	//��������:����Ա����ο��ύ��������Ϣ�������Ƿ�ͨ��
	//����1�������ɳɹ�
	@RequestMapping("/checkImprovingInfo")
	@ResponseBody
	public int checkImprovingInfo(int user_id,boolean waitResult) {
		UserTable2Example example = new UserTable2Example();
		UserTable2Example.Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(user_id);
			
		UserTable2 record = new UserTable2();
		String result;
		if(waitResult) {
			result = "��ͨ�û�";
		}else {
			result = "��˲�ͨ��";
		}
		record.setProperty(result);
		
		return ut2service.updateByExampleSelective(record , example);
		

	}
}
