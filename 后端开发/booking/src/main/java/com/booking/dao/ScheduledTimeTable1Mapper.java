package com.booking.dao;

import com.booking.pojo.ScheduledTimeTable1;
import com.booking.pojo.ScheduledTimeTable1Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ScheduledTimeTable1Mapper {
    long countByExample(ScheduledTimeTable1Example example);

    int deleteByExample(ScheduledTimeTable1Example example);

    int insert(ScheduledTimeTable1 record);

    int insertSelective(ScheduledTimeTable1 record);

    List<ScheduledTimeTable1> selectByExample(ScheduledTimeTable1Example example);

    int updateByExampleSelective(@Param("record") ScheduledTimeTable1 record, @Param("example") ScheduledTimeTable1Example example);

    int updateByExample(@Param("record") ScheduledTimeTable1 record, @Param("example") ScheduledTimeTable1Example example);
}