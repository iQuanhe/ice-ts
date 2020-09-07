package service;

import frame.http.HttpCmd;
import org.json.JSONObject;

import java.util.*;

public class HttpCmdQuery extends HttpCmd {//http接口需要继承 HttpCmd
    static {
        //注册接口 "/service/query"
        HttpCmd.register("/lyapi/systems",HttpCmdQuery.class);
    }

    @Override
    public void execute() {  //接口业务逻辑处理方法
        JSONObject params = getJSONObject();//读取post请求json参数并创建JSONObject
        Map<String,String> param = getParams();
        String test = params.optString("service","");//

        result.put("ret","0");
        result.put("msg","this is a service,param service=:"+test);
        response(result);  //响应请求
    }
}
