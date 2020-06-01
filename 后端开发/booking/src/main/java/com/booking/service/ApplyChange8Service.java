package com.booking.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.booking.pojo.ApplyChangeTable8;
import com.booking.pojo.ApplyChangeTable8Example;

/**
* 项目名称:booking

* 文件名称:ApplyChange8Service.java
* 创建人员:nicewithgreat
* 功能描述:
*/
public interface ApplyChange8Service {
    int insertSelective(ApplyChangeTable8 record);

    List<ApplyChangeTable8> selectByExample(ApplyChangeTable8Example example);

    int updateByExampleSelective(ApplyChangeTable8 record, ApplyChangeTable8Example example);
}
