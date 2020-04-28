package com.booking.dao;

import com.booking.pojo.ApplyFixedTable7;
import com.booking.pojo.ApplyFixedTable7Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ApplyFixedTable7Mapper {
    long countByExample(ApplyFixedTable7Example example);

    int deleteByExample(ApplyFixedTable7Example example);

    int insert(ApplyFixedTable7 record);

    int insertSelective(ApplyFixedTable7 record);

    List<ApplyFixedTable7> selectByExample(ApplyFixedTable7Example example);

    int updateByExampleSelective(@Param("record") ApplyFixedTable7 record, @Param("example") ApplyFixedTable7Example example);

    int updateByExample(@Param("record") ApplyFixedTable7 record, @Param("example") ApplyFixedTable7Example example);
}