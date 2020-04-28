package com.booking.dao;

import com.booking.pojo.CourtTable0;
import com.booking.pojo.CourtTable0Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CourtTable0Mapper {
    long countByExample(CourtTable0Example example);

    int deleteByExample(CourtTable0Example example);

    int insert(CourtTable0 record);

    int insertSelective(CourtTable0 record);

    List<CourtTable0> selectByExample(CourtTable0Example example);

    int updateByExampleSelective(@Param("record") CourtTable0 record, @Param("example") CourtTable0Example example);

    int updateByExample(@Param("record") CourtTable0 record, @Param("example") CourtTable0Example example);
}