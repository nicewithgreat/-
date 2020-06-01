package com.booking.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.booking.pojo.ApplyUnsubscribeTable6;
import com.booking.pojo.ApplyUnsubscribeTable6Example;

/**
* 项目名称:booking

* 文件名称:ApplyUnsubscribeTable6Service.java
* 创建人员:nicewithgreat
* 功能描述:
*/
public interface ApplyUnsubscribe6Service {
    int insertSelective(ApplyUnsubscribeTable6 record);

    List<ApplyUnsubscribeTable6> selectByExample(ApplyUnsubscribeTable6Example example);

    int updateByExampleSelective(ApplyUnsubscribeTable6 record, ApplyUnsubscribeTable6Example example);
}
