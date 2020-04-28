package com.booking.dao;

import com.booking.pojo.UserTable2;
import com.booking.pojo.UserTable2Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserTable2Mapper {
    long countByExample(UserTable2Example example);

    int deleteByExample(UserTable2Example example);

    int insert(UserTable2 record);

    int insertSelective(UserTable2 record);

    List<UserTable2> selectByExample(UserTable2Example example);

    int updateByExampleSelective(@Param("record") UserTable2 record, @Param("example") UserTable2Example example);

    int updateByExample(@Param("record") UserTable2 record, @Param("example") UserTable2Example example);
}