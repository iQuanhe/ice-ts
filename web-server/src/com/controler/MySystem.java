package com.controler;

public class MySystem {

    // 成员变量
    private String sysCode;
    private String sysName;

    public MySystem(String code, String name){
        this.sysCode = code;
        this.sysName = name;
    }

    String getSysCode(){
        return sysCode;
    }
    String getSysName(){
        return sysName;
    }

    void setSysCode(String sysCode){
        this.sysCode = sysCode;
    }
    void setSysName(String sysName) {
        this.sysName = sysName;
    }

}
