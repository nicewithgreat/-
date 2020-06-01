package com.booking.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.booking.pojo.ApplyFixedTable7;
import com.booking.pojo.ApplyFixedTable7Example;

/**
* 项目名称:booking

* 文件名称:ApplyFixed7Service.java
* 创建人员:nicewithgreat
* 功能描述:
*/
public interface ApplyFixed7Service {
    int insertSelective(ApplyFixedTable7 record);

    List<ApplyFixedTable7> selectByExample(ApplyFixedTable7Example example);

    int updateByExampleSelective(ApplyFixedTable7 record, ApplyFixedTable7Example example);

}
