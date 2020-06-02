package com.booking.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.booking.pojo.TeamTable3;
import com.booking.pojo.TeamTable3Example;

/**
* 项目名称:booking

* 文件名称:Team3Service.java
* 创建人员:nicewithgreat
* 功能描述:
*/
public interface Team3Service {
    int insertSelective(TeamTable3 record);

    List<TeamTable3> selectByExample(TeamTable3Example example);

    int updateByExampleSelective(TeamTable3 record, TeamTable3Example example);
    
    public List<TeamTable3> getCollegeList();
}
