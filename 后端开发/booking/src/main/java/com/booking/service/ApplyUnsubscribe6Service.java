package com.booking.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.booking.pojo.ApplyUnsubscribeTable6;
import com.booking.pojo.ApplyUnsubscribeTable6Example;

/**
* ��Ŀ����:booking

* �ļ�����:ApplyUnsubscribeTable6Service.java
* ������Ա:nicewithgreat
* ��������:
*/
public interface ApplyUnsubscribe6Service {
    int insertSelective(ApplyUnsubscribeTable6 record);

    List<ApplyUnsubscribeTable6> selectByExample(ApplyUnsubscribeTable6Example example);

    int updateByExampleSelective(ApplyUnsubscribeTable6 record, ApplyUnsubscribeTable6Example example);
}
