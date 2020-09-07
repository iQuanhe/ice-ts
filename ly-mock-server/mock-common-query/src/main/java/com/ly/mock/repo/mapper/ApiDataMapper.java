package com.ly.mock.repo.mapper;

import com.ly.mock.model.ApiData;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface ApiDataMapper  {


    List<ApiData> getApiDataDetail(@Param("filters")Map filters);

    ApiData getApiDataById(@Param("apiData") ApiData apiData);

    int getApiDataOrder();

    int checkApiData(@Param("apiData") ApiData apiData);

    void insertApiData (@Param("apiData") ApiData apiData);

    void updateApiData (@Param("apiData") ApiData apiData);

    void deleteApiData (@Param("apiData") ApiData apiData);

    ApiData getApiDataByContext (@Param("filters")Map filters);

    List<Map> getTodayFlows();
    List<Map> getMonthFlows();
    List<Map> getRecentFlows();
    List<Map> getBeforeTodayFlows();
    void insertDateFlows(@Param("flows") List<Map> flows);
    void insertFlows(@Param("item") Map item);
    void deleteFlows();

}
