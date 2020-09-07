package com.ly.mock.logic;

import com.ly.mock.common.IsNumeric;
import com.ly.mock.model.ApiData;
import com.ly.mock.model.Result;
import com.ly.mock.repo.mapper.QueryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@Component
public class ProjectApi {

    @Autowired
    private QueryMapper queryMapper;

    public Result ProjectAddApi(@RequestBody ApiData Api, HttpServletRequest request, HttpServletResponse response) {
        String path = Api.getPath();
        String[] paths =path.split("/");
        System.out.println("start=="+path);
        String newPath ="";
        for (int i = 0 ; i <paths.length ; i++ ) {
            Boolean isNum = IsNumeric.is(paths[i]);
            String temp = i==0?"":"/";
            if(isNum == true){
                newPath += temp+"$";
            }else{
                newPath += temp + paths[i];
            }
            if(i==0){
                Api.setFirstPath(isNum?"$":paths[i]);
            }
            if(i==1){
                Api.setSecondPath(isNum?"$":paths[i]);
            }
            if(i==2){
                Api.setThirdPath(isNum?"$":paths[i]);
            }
            if(i==3){
                Api.setForthPath(isNum?"$":paths[i]);
            }
        }

        String id = UUID.randomUUID().toString();
        System.out.println("id=="+id);
        List arr =  queryMapper.listAll("select * from apis order by `order` desc");
        Object obj =arr.get(0);


        String sql="INSERT INTO apis(id,path,firstPath,secondPath,thirdPath,forthPath,result,`desc`,method,nums, syscode) SELECT '";
        sql += id +"','";
        sql += newPath +"','";
        sql += Api.getFirstPath() +"','";
        sql += Api.getSecondPath() +"','";
        sql += Api.getThirdPath() +"','";
        sql += Api.getForthPath() +"','";
        sql += Api.getResult() +"','";
        sql += Api.getDesc() +"','";
        sql += Api.getMethod() +"','";
        sql += Api.getNums() +"','";
        sql += Api.getSyscode() +"'";
        sql += " FROM DUAL WHERE NOT EXISTS (SELECT path FROM apis WHERE path ='" ;
        sql +=  newPath;
        sql +=  "' AND syscode ='";
        sql +=  Api.getSyscode();
        sql +=  "' AND method ='";
        sql +=  Api.getMethod();
        sql +=  "')";

        System.out.println("sql=="+sql);
//        List list = queryMapper.listAll(sql);

        return Result.ok("9");
    }
}
