package com.booking.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.booking.pojo.ApplyChangeTable8;
import com.booking.pojo.ApplyChangeTable8Example;

/**
* ��Ŀ����:booking

* �ļ�����:ApplyChange8Service.java
* ������Ա:nicewithgreat
* ��������:
*/
public interface ApplyChange8Service {
    int insertSelective(ApplyChangeTable8 record);

    List<ApplyChangeTable8> selectByExample(ApplyChangeTable8Example example);

    int updateByExampleSelective(ApplyChangeTable8 record, ApplyChangeTable8Example example);
}
