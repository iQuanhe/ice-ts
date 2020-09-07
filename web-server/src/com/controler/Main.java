package com.controler;

import com.controler.MySystem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class Main {

    // JDBC 驱动名及数据库 URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/admin-template";

    // 数据库的用户名与密码，需要根据自己的设置
    static final String USER = "root";
    static final String PASS = "123456";


    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        try{
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);

            // 打开链接
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            // 执行查询
            System.out.println(" 实例化Statement对象...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT sysCode, sysName FROM system";
            ResultSet rs = stmt.executeQuery(sql);

            //创建数组
            List<MySystem> listsys = new ArrayList();

            // 展开结果集数据库
            while(rs.next()){
                // 通过字段检索
                String sysCode  = rs.getString("sysCode");
                String sysName = rs.getString("sysName");

                MySystem obj = new MySystem(sysCode,sysName) ;
                listsys.add(obj);

            }

            for(MySystem tmp:listsys){
                System.out.println(tmp.getSysCode()+":" +tmp.getSysName());
            }

            // 完成后关闭
            rs.close();
            stmt.close();
            conn.close();
        }catch(SQLException se){
            // 处理 JDBC 错误
            se.printStackTrace();
        }catch(Exception e){
            // 处理 Class.forName 错误
            e.printStackTrace();
        }finally{
            // 关闭资源
            try{
                if(stmt!=null) stmt.close();
            }catch(SQLException se2){
            }// 什么都不做
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }

    }


}

