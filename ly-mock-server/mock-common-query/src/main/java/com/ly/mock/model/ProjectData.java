package com.ly.mock.model;

import java.util.Date;
import java.util.List;

public class ProjectData {

    // 成员变量
    private String id;
    private String sysCode;
    private String sysName;
    private String context;
    private Date updateTime;
    private Date createTime;


    public ProjectData(){

    }

    public String getId() {
        return id;
    }

    public String getSysCode() {
        return sysCode;
    }

    public String getSysName() {
        return sysName;
    }

    public String getContext() {
        return context;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public void setSysCode(String sysCode) {
        this.sysCode = sysCode;
    }

    public void setSysName(String sysName) {
        this.sysName = sysName;
    }
}

