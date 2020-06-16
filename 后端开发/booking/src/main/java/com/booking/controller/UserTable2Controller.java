package com.booking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.booking.pojo.ApplyChangeTable8;
import com.booking.pojo.ApplyChangeTable8Example;
import com.booking.pojo.ApplyFixedTable7;
import com.booking.pojo.ApplyFixedTable7Example;
import com.booking.pojo.ApplyUnsubscribeTable6;
import com.booking.pojo.ApplyUnsubscribeTable6Example;
import com.booking.pojo.TeamTable3;
import com.booking.pojo.TeamTable3Example;
import com.booking.pojo.UserTable2;
import com.booking.pojo.UserTable2Example;
import com.booking.service.ApplyChange8Service;
import com.booking.service.ApplyFixed7Service;
import com.booking.service.ApplyUnsubscribe6Service;
import com.booking.service.Team3Service;
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
	@Autowired
	Team3Service t3service;
	@Autowired
	ApplyFixed7Service af7service;
	@Autowired
	ApplyUnsubscribe6Service au6service;
	@Autowired
	ApplyChange8Service ac8service;
	
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
	//����-1 ��ѧ��/�����ѱ�ע�ᣬȥ������Ա����
	@RequestMapping("/improvingInfo")
	@ResponseBody
	public int improvingInfo(int user_id,String wechatNO,String identity_num) {
		
		UserTable2Example example = new UserTable2Example();
		UserTable2Example.Criteria criteria = example.createCriteria();
		
		//���Ҹ�ѧ��/�����Ƿ��ѱ�ע��
		criteria.andIdentityNumEqualTo(identity_num);
		criteria.andUserIdNotEqualTo(user_id);
		criteria.andUserNameNotLike("�ο�");
		if(!ut2service.selectByExample(example).isEmpty()) {
			return -1;
		}
		
		//�����Ƿ������Ч����
		example = new UserTable2Example();
		criteria = example.createCriteria();
		
		criteria.andUserNameEqualTo(wechatNO);
		criteria.andUserIdEqualTo(user_id);
		criteria.andPropertyLike("�ο�");
		
		if(!ut2service.selectByExample(example).isEmpty()) {			
			UserTable2 record = new UserTable2();
			record.setIdentityNum(identity_num);
			record.setProperty("�οʹ����");
			return ut2service.updateByExampleSelective(record , example);
		}
		return 0;
	}
	
	//��������:�ύ����Ϊ����û�
	//����1�ύ�ɹ�
	@RequestMapping("/improvingTeamInfo")
	@ResponseBody
	public int improvingTeamInfo(int user_id,String wechatNO,int team_id) {
		UserTable2Example example = new UserTable2Example();
		UserTable2Example.Criteria criteria = example.createCriteria();
		criteria.andUserNameEqualTo(wechatNO);
		criteria.andUserIdEqualTo(user_id);
		criteria.andPropertyLike("��ͨ�û�");//�ж�ǰ׺�Ƿ�Ϊ��ͨ�û�
		
		if(!ut2service.selectByExample(example).isEmpty()) {			
			UserTable2 record = new UserTable2();
			record.setProperty("��ͨ�û������");
			record.setTeamId(team_id);
			return ut2service.updateByExampleSelective(record , example);
		}
		return 0;
	}
	
	//��������������Ա��¼
	//�ɹ����ع���Աid,ʧ�ܷ���-1
	@RequestMapping("/loginAdmin")
	@ResponseBody
	public int getMyInfo(String username,String password) {
		UserTable2Example example = new UserTable2Example();
		UserTable2Example.Criteria criteria = example.createCriteria();
		criteria.andUserNameEqualTo(username);
		criteria.andPasswordEqualTo(password);
		
		List<UserTable2> list = ut2service.selectByExample(example);
		if(list.isEmpty()) {
			return -1;
		}
		return list.get(0).getUserId();
	}
	
	//��������:��ȡ��ǰ�û�������Ϣ
	//ʧ�ܷ���null
	@RequestMapping("/getMyInfo")
	@ResponseBody
	public Object getMyInfo(//int user_id,
							String wechatNO) {
		UserTable2Example example = new UserTable2Example();
		UserTable2Example.Criteria criteria = example.createCriteria();
		criteria.andUserNameEqualTo(wechatNO);
		//criteria.andUserIdEqualTo(user_id);
		
		List<UserTable2> list = ut2service.selectByExample(example);
		if(list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}
	
	//��������:����Ա��ȡ�ο��û�����Ϊ��ͨ�û��ύ�����(δ���)
	@RequestMapping("/getWaitingTourist")
	@ResponseBody
	public Object getWaitingTourist() {
		return getWaitingUserInfo("�οʹ����");
	}
	
	//��������:����Ա��ȡ��ͨ�û�����Ϊ����û��ύ�����(δ���)
	@RequestMapping("/getWaitingOrdinaryUser")
	@ResponseBody
	public Object getWaitingOrdinaryUser() {
		return getWaitingUserInfo("��ͨ�û������");
	}
	
	//��������:����Ա��ȡ����û�����̶������б�(δ���)
	@RequestMapping("/getApplyFixedList")
	@ResponseBody
	public Object getApplyFixedList() {
		ApplyFixedTable7Example example = new ApplyFixedTable7Example();
		ApplyFixedTable7Example.Criteria criteria = example.createCriteria();
		criteria.andApplyStateEqualTo(0);//0:δ���
		
		return af7service.selectByExample(example);
	}
	
	//��������:����Ա��ȡ��ͨ�û����������б�(δ���)
	@RequestMapping("/getChangeCourtList")
	@ResponseBody
	public Object getChangeCourtList() {
		return getApplyChangeList(0);
	}
	
	//��������:����Ա��ȡ��ͨ�û��˳������б�(δ���)
	@RequestMapping("/getUnsubscribeCourtList")
	@ResponseBody
	public Object getUnsubscribeCourtList() {
		return getApplyUnsubscribeList(0);
	}
	
	//��������:����Ա��ȡ����û������̶��������б�(δ���)
	@RequestMapping("/getChangeFixedList")
	@ResponseBody
	public Object getChangeFixedCourtList() {
		return getApplyChangeList(3);
	}
	
	//��������:����Ա��ȡ����û��˻��̶��������б�(δ���)
	@RequestMapping("/getUnsubscribeFixedList")
	@ResponseBody
	public Object getUnsubscribeFixedList() {
		return getApplyUnsubscribeList(3);
	}
	
	private List<UserTable2> getWaitingUserInfo(String Property) {
		UserTable2Example example = new UserTable2Example();
		UserTable2Example.Criteria criteria = example.createCriteria();
		criteria.andPropertyEqualTo(Property);//�û�����
		
		List<UserTable2> list = ut2service.selectByExample(example);
		if(list.isEmpty()) {
			return null;
		}
		return list;
	}
	
	private List<ApplyUnsubscribeTable6> getApplyUnsubscribeList(int state_Property){
		
		ApplyUnsubscribeTable6Example example = new ApplyUnsubscribeTable6Example();
		ApplyUnsubscribeTable6Example.Criteria criteria = example.createCriteria();
		criteria.andUnsubscribeStateEqualTo(state_Property);
		
		return au6service.selectByExample(example);		
	}
	
	private List<ApplyChangeTable8> getApplyChangeList(int state_Property){
		ApplyChangeTable8Example example = new ApplyChangeTable8Example();
		ApplyChangeTable8Example.Criteria criteria = example.createCriteria(); 
		criteria.andApplyStateEqualTo(state_Property);
		
		return ac8service.selectByExample(example );
	}
	
	//��������:����Ա����ο��ύ��������Ϣ�������Ƿ�ͨ��
	//����1�������ɳɹ�
	//����-1���ѧ��/�����ѱ�ע��,�޷�ͨ��
	@RequestMapping("/checkTouristInfo")
	@ResponseBody
	public int checkTouristInfo(int user_id,String identity_num, boolean waitResult) {
		//����ѧ��/�����ѱ�ע��
		UserTable2Example example = new UserTable2Example();
		UserTable2Example.Criteria criteria = example.createCriteria();
		criteria.andIdentityNumEqualTo(identity_num);
		criteria.andUserIdNotEqualTo(user_id);
		criteria.andUserNameNotLike("�ο�");
		
		if(!ut2service.selectByExample(example).isEmpty()) {
			return -1;
		}

		//�ύ�Ƿ�ͨ��
		String result;
		if(waitResult) {
			result = "��ͨ�û�";
		}else {
			result = "�ο���˲�ͨ��";
		}
		
		return checkImprovingInfo(user_id, result, -1);
	}
	
	//��������:����Ա�����ͨ�û��ύ������Ϊ����û��������Ƿ�ͨ��
	//����1�������ɳɹ�
	@RequestMapping("/checkOrdinaryInfo")
	@ResponseBody
	public int checkOrdinaryInfo(int user_id,boolean waitResult,int teamId) {
		String result;
		if(waitResult) {
			result = "����û�";
			//���֮ǰ������Ѿ���������û�����֮ǰ������û�ȡ�������ͨ�û�;
			checkTeamSetUser(teamId, user_id);
		}else {
			result = "��ͨ�û���˲�ͨ��";
		}
		return checkImprovingInfo(user_id, result, teamId);
	}
	
	//��������Ѿ���������û�����֮ǰ������û�ȡ�������ͨ�û�;
	private int checkTeamSetUser(int teamId,int changeUserId) {
		
		//���Ҹ���ӵ�ǰ��ӳ�Ա
		
		UserTable2Example ut2example = new UserTable2Example();
		UserTable2Example.Criteria ut2Criteria = ut2example.createCriteria();
		ut2Criteria.andTeamIdEqualTo(teamId);
		ut2Criteria.andPropertyEqualTo("����û�");
		
		List<UserTable2> lt2List = ut2service.selectByExample(ut2example );
		if(!lt2List.isEmpty()) {
			UserTable2 ut2record = new UserTable2();
			ut2record.setProperty("��ͨ�û�");
			//��ǰ��ӳ�Ա�����ͨ�û�
			ut2service.updateByExampleSelective(ut2record, ut2example);
		}
		
		
		TeamTable3Example example = new TeamTable3Example();
		TeamTable3Example.Criteria criteria = example.createCriteria();
		criteria.andTeamIdEqualTo(teamId);
		
		List<TeamTable3> teamList = t3service.selectByExample(example);
		if(teamList.isEmpty()) {
			return 0;
		}
		TeamTable3 teamItem = teamList.get(0);
		if(teamItem.getApplyFixedId() != null) {//���Ҹ������û�й̶���
			ApplyFixedTable7 af7record = new ApplyFixedTable7();
			af7record.setUserId(changeUserId);
			ApplyFixedTable7Example af7example = new ApplyFixedTable7Example();
			ApplyFixedTable7Example.Criteria af7criteria = af7example.createCriteria();
			af7criteria.andApplyFixedIdEqualTo(teamItem.getApplyFixedId());
			//���Ҹù̶�����ʹ���������ӳ�Ա����
			af7service.updateByExampleSelective(af7record, af7example);
		}
		
		//ʹ���������ͨ�û��������û�
		ut2example = new UserTable2Example();
		ut2Criteria = ut2example.createCriteria();
		ut2Criteria.andUserIdEqualTo(changeUserId);

		UserTable2 ut2record = new UserTable2();
		ut2record.setProperty("����û�");
		//��ͨ�û��������û�
		ut2service.updateByExampleSelective(ut2record, ut2example);

		
		return 0;
	}
	
	public int checkImprovingInfo(int user_id,String waitResult,int teamId) {
		UserTable2Example example = new UserTable2Example();
		UserTable2Example.Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(user_id);
			
		UserTable2 record = new UserTable2();

		if(teamId != -1) {
			record.setTeamId(teamId);
		}
		record.setProperty(waitResult);
		
		return ut2service.updateByExampleSelective(record , example);
		
	}
	
	
}
