package com.booking.dao;

import com.booking.pojo.TeamTable3;
import com.booking.pojo.TeamTable3Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TeamTable3Mapper {
    long countByExample(TeamTable3Example example);

    int deleteByExample(TeamTable3Example example);

    int insert(TeamTable3 record);

    int insertSelective(TeamTable3 record);

    List<TeamTable3> selectByExample(TeamTable3Example example);

    int updateByExampleSelective(@Param("record") TeamTable3 record, @Param("example") TeamTable3Example example);

    int updateByExample(@Param("record") TeamTable3 record, @Param("example") TeamTable3Example example);
}