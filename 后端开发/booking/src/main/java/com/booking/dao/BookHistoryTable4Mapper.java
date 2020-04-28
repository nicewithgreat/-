package com.booking.dao;

import com.booking.pojo.BookHistoryTable4;
import com.booking.pojo.BookHistoryTable4Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BookHistoryTable4Mapper {
    long countByExample(BookHistoryTable4Example example);

    int deleteByExample(BookHistoryTable4Example example);

    int insert(BookHistoryTable4 record);

    int insertSelective(BookHistoryTable4 record);

    List<BookHistoryTable4> selectByExample(BookHistoryTable4Example example);

    int updateByExampleSelective(@Param("record") BookHistoryTable4 record, @Param("example") BookHistoryTable4Example example);

    int updateByExample(@Param("record") BookHistoryTable4 record, @Param("example") BookHistoryTable4Example example);
}