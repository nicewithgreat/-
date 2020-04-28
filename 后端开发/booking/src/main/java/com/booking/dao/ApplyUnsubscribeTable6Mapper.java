package com.booking.dao;

import com.booking.pojo.ApplyUnsubscribeTable6;
import com.booking.pojo.ApplyUnsubscribeTable6Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ApplyUnsubscribeTable6Mapper {
    long countByExample(ApplyUnsubscribeTable6Example example);

    int deleteByExample(ApplyUnsubscribeTable6Example example);

    int insert(ApplyUnsubscribeTable6 record);

    int insertSelective(ApplyUnsubscribeTable6 record);

    List<ApplyUnsubscribeTable6> selectByExample(ApplyUnsubscribeTable6Example example);

    int updateByExampleSelective(@Param("record") ApplyUnsubscribeTable6 record, @Param("example") ApplyUnsubscribeTable6Example example);

    int updateByExample(@Param("record") ApplyUnsubscribeTable6 record, @Param("example") ApplyUnsubscribeTable6Example example);
}