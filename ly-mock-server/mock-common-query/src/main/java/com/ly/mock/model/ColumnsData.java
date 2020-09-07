package com.ly.mock.model;

import java.util.Date;

public class ColumnsData {


    // 成员变量
    private String id;
    private String path;
    private String cols;
    private int serialNumber;
    private Date updateTime;
    private Date createTime;


    public ColumnsData(){

    }

    public String getId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    public String getCols() {
        return cols;
    }

    public int getSerialNumber() {
        return serialNumber;
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

    public void setPath(String path) {
        this.path = path;
    }

    public void setCols(String cols) {
        this.cols = cols;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }
}
