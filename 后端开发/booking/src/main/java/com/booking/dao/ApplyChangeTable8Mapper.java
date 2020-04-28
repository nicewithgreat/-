package com.booking.dao;

import com.booking.pojo.ApplyChangeTable8;
import com.booking.pojo.ApplyChangeTable8Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ApplyChangeTable8Mapper {
    long countByExample(ApplyChangeTable8Example example);

    int deleteByExample(ApplyChangeTable8Example example);

    int insert(ApplyChangeTable8 record);

    int insertSelective(ApplyChangeTable8 record);

    List<ApplyChangeTable8> selectByExample(ApplyChangeTable8Example example);

    int updateByExampleSelective(@Param("record") ApplyChangeTable8 record, @Param("example") ApplyChangeTable8Example example);

    int updateByExample(@Param("record") ApplyChangeTable8 record, @Param("example") ApplyChangeTable8Example example);
}