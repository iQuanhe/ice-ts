package com.ly.mock.repo.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;

import java.util.List;
import java.util.Map;

public interface QueryMapper {

    List<Map> listAll(@Param("sql") String sql);

    List<Map> listWithPage(@Param("sql") String sql, @Param("current") String current, @Param("size") String size);

    Long getCount(@Param("sql") String sql);
}
