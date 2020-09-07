package com.ly.mock.controller;

import com.alibaba.fastjson.JSONObject;
import com.ly.mock.common.IsNumeric;
import com.ly.mock.model.ApiData;
import com.ly.mock.model.ColumnsData;
import com.ly.mock.model.Result;

import com.ly.mock.repo.mapper.ApiDataMapper;
//import com.ly.mock.repo.mapper.ColumnsDataMapper;
import com.ly.mock.repo.mapper.QueryMapper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RefreshScope
@Configuration
@Component
public class QueryController {

    public QueryController() {

    }


    @Autowired
    private QueryMapper queryMapper;

    @Autowired
    private ApiDataMapper apiDataMapper;

//    @Autowired
//    private ColumnsDataMapper columnsDataMapper;

    //查询
    @PostMapping("/lyapi/apilist")
    public Result search(@RequestBody Map map,HttpServletRequest request) {
        List<ApiData> list = apiDataMapper.getApiDataDetail(map);
        Map res = new HashMap();
        res.put("list",list);
        return Result.ok(res);
    }

    //删除
    @PostMapping("/lyapi/deleteapi")
    public Result delete(@RequestBody ApiData api,HttpServletRequest request) {
        ApiData old = apiDataMapper.getApiDataById(api);
        if(old!= null){
            apiDataMapper.deleteApiData(old);
            return Result.ok("删除成功");
        }
        return Result.error(304,"数据不存在");
    }

    //新增接口数据
    @PostMapping("/lyapi/addapi")
    public Result addapi(@RequestBody ApiData api, HttpServletRequest request, HttpServletResponse response){
        int order = apiDataMapper.getApiDataOrder()+1;
        String id = UUID.randomUUID().toString();
        String path = api.getPath();
        String[] paths =path.split("/");
        System.out.println("path: "+path);
        String newPath ="";
        for (int i = 0 ; i <paths.length ; i++ ) {
            Boolean isNum = IsNumeric.is(paths[i]);
            String temp = i==0?"":"/";
            if(isNum == true){
                newPath += temp+"$";
            }else{
                newPath += temp + paths[i];
            }
        }
        api.setOrder(order);
        api.setId(id);
        api.setPath(newPath);
        api.setTimes(0);
        System.out.println("id: "+id);
        int length = apiDataMapper.checkApiData(api);
        System.out.println("length: "+length);
        if(length>0){
            return Result.error(300,"接口已存在");
        }else{
            apiDataMapper.insertApiData(api);
            return Result.ok("新建成功");
        }

    }

    //修改接口数据
    @PostMapping("/lyapi/updateapi")
    public Result updateapi(@RequestBody ApiData api, HttpServletRequest request, HttpServletResponse response){
        int length = apiDataMapper.checkApiData(api);
        System.out.println("length: "+length);
        if(length > 0){
            return Result.error(301,"接口数据重复");
        }
        ApiData old = apiDataMapper.getApiDataById(api);
        if(old!= null){
            ApiData newApi = checkApiData(api,old);
            apiDataMapper.updateApiData(newApi);
            return Result.ok("修改成功");
        }
        return Result.error(304,"数据不存在");

    }

    @RequestMapping("/lyapi/today")
    public Result today(){
        List<Map> list = apiDataMapper.getTodayFlows();
        List<Map> list_b = apiDataMapper.getBeforeTodayFlows();
        if(list_b.size()>0){
            apiDataMapper.insertDateFlows(list_b);
            apiDataMapper.deleteFlows();
        }
        Map res = new HashMap();
        int total =0;
        for(int i = 0; i < list.size(); i++){
            total += Integer.parseInt(list.get(i).get("value").toString());
        }
        res.put("list",list);
        res.put("total",total);
        return Result.ok(res);
    }


    @RequestMapping("/lyapi/month")
    public Result month(){
        List<Map> list = apiDataMapper.getMonthFlows();
        Map res = new HashMap();
        int total =0;
        for(int i = 0; i < list.size(); i++){
            total += Integer.parseInt(list.get(i).get("value").toString());
        }
        res.put("list",list);
        res.put("total",total);
        return Result.ok(res);
    }

    @RequestMapping("/lyapi/recent")
    public Result recent(){
        List<Map> list = apiDataMapper.getRecentFlows();
        Map res = new HashMap();
        int total =0;
        for(int i = 0; i < list.size(); i++){
            total += Integer.parseInt(list.get(i).get("value").toString());
        }
        res.put("list",list);
        res.put("total",total);
        return Result.ok(res);
    }


    @RequestMapping("/lyapi/**")
    public Result lyapi(HttpServletRequest request) {
        String url = request.getRequestURI();
        String method = request.getMethod();
        String queryString =request.getQueryString();
        String sql="";
        if(url.equals("/lyapi/tops")){
            sql="SELECT syscode ,path as xdata,times as ydata FROM apis  ORDER BY times DESC LIMIT 20";
        }
        if(url.equals("/lyapi/system")){
            sql="SELECT syscode as xdata ,sum(times) as ydata FROM apis GROUP BY syscode";
        }
        if(url.equals("/lyapi/systems")){
            sql="SELECT * FROM system  ORDER BY updateTime DESC";
        }
        if(url.equals("/lyapi/getsys")){
            sql="SELECT DISTINCT sysName as label,sysCode as value  FROM system";
        }
        if(url.equals("/lyapi/apilist")){
            sql="SELECT * FROM apis";
        }

        Map<String, Object> res = new HashMap();
        //ListData obj =new ListData(list,0);

        if(!sql.equals("")){
            List list = queryMapper.listAll(sql);
            res.put("list",list);
            res.put("total",0);
            return Result.ok(res);
        }
        return Result.error(305,"接口未实现");
    }

//    @PostMapping("/kpi-management/getMyCols")
//    public JSONObject getMyCols(@RequestBody ColumnsData columns) {
//        ColumnsData fields = columnsDataMapper.getColumnsData(columns);
//        JSONObject jsStr = new JSONObject();
//        jsStr.put("success", true);
//        jsStr.put("code", 200);
//        jsStr.put("data", null);
//        if(fields != null){
//            jsStr = JSONObject.parseObject(fields.getCols());
//        }
//        return jsStr;
//    }
//
//    @PostMapping("/kpi-management/saveMyCols")
//    public JSONObject saveMyCols(@RequestBody ColumnsData columns){
//        JSONObject jsStr = new JSONObject();
//        jsStr.put("success", true);
//        jsStr.put("code", 200);
//        jsStr.put("data", null);
//        jsStr.put("message", "保存成功");
//        ColumnsData fields = columnsDataMapper.getColumnsData(columns);
//        if(fields == null){
//            columnsDataMapper.insertColumnsData(fields);
//        }else{
//            columnsDataMapper.updateColumnsData(fields);
//        }
//        return jsStr;
//    }

    @RequestMapping("/**")
    public JSONObject common( HttpServletRequest request) {
        String url = request.getRequestURI();
        String method = request.getMethod();
        String[] arr = url.split("/");
        String path ="" ,context="";
        for (int i = 0; i < arr.length; i++) {
            if(i==1){
                context=arr[i];
            }else if(i==2){
                path += arr[i];
            }else if(i>2){
                path += "/" + arr[i];
            }
        }
        Map fliters =new HashMap();
        fliters.put("method",method);
        fliters.put("path",path);
        fliters.put("context",context);

        JSONObject jsStr = new JSONObject();
        jsStr.put("success", true);
        jsStr.put("code", 200);
        jsStr.put("data", null);
        jsStr.put("message", "无此接口数据");

        ApiData api  =apiDataMapper.getApiDataByContext(fliters);
        if(api != null){
            api.setTimes(api.getTimes()+1);
            apiDataMapper.updateApiData(api);

            Map flow =new HashMap();
            flow.put("id",UUID.randomUUID().toString());
            flow.put("apiId",api.getId());
            flow.put("syscode",api.getSyscode());
            flow.put("apiPath",api.getPath());
            apiDataMapper.insertFlows(flow);

            jsStr = JSONObject.parseObject(api.getResult());
        }
        return jsStr  ;

    }



    private ApiData checkApiData(ApiData _new,ApiData _old) {
        if (StringUtils.isBlank(_new.getPath())) {
            _new.setPath(_old.getPath());
        }
        if (StringUtils.isBlank(_new.getDesc())) {
            _new.setDesc(_old.getDesc());
        }
        if (StringUtils.isBlank(_new.getStrict()+"")) {
            _new.setIsStrict(_old.getStrict());
        }
        if (StringUtils.isBlank(_new.getRandom()+"")) {
            _new.setIsRandom(_old.getRandom());
        }
        if (StringUtils.isBlank(_new.getExtend()+"")) {
            _new.setIsExtend(_old.getExtend());
        }
        if (StringUtils.isBlank(_new.getMethod())) {
            _new.setMethod(_old.getMethod());
        }
        if (StringUtils.isBlank(_new.getResult())) {
            _new.setResult(_old.getResult());
        }
        if (StringUtils.isBlank(_new.getSyscode())) {
            _new.setSyscode(_old.getSyscode());
        }
        if (StringUtils.isBlank(_new.getNums()+"")) {
            _new.setNums(_old.getNums());
        }
        if (StringUtils.isBlank(_new.getTimes()+"")) {
            _new.setTimes(_old.getTimes());
        }
        return _new;
    }


}
