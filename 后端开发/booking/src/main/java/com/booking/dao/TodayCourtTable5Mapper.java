package com.booking.dao;

import com.booking.pojo.TodayCourtTable5;
import com.booking.pojo.TodayCourtTable5Example;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TodayCourtTable5Mapper {
    long countByExample(TodayCourtTable5Example example);

    int deleteByExample(TodayCourtTable5Example example);

    int insert(TodayCourtTable5 record);

    int insertSelective(TodayCourtTable5 record);

    List<TodayCourtTable5> selectByExample(TodayCourtTable5Example example);

    int updateByExampleSelective(@Param("record") TodayCourtTable5 record, @Param("example") TodayCourtTable5Example example);

    int updateByExample(@Param("record") TodayCourtTable5 record, @Param("example") TodayCourtTable5Example example);
    
    //暂定连表查询，后续可能将部分表，从启动服务器就读取为静态类；
    List<TodayCourtTable5> selectByExampleWith(TodayCourtTable5Example example);

}