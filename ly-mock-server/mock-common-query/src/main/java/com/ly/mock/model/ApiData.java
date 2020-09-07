package com.ly.mock.model;
import java.util.Date;
import java.util.List;

public class ApiData {

    // 成员变量

    private String id;
    private String desc;
    private int isExtend;
    private int isRandom;
    private int isStrict;
    private String method;
    private int nums;
    private int times;
    private String path;
    private String  result;
    private String  syscode;
    private String  firstPath;
    private String  secondPath;
    private String  thirdPath;
    private String  forthPath;
    private int  order;
    private Date updateTime;


    public ApiData(){

    }

    public String getPath() {
        return path;
    }

    public int getExtend() {
        return isExtend;
    }

    public int getRandom() {
        return isRandom;
    }

    public int getStrict() {
        return isStrict;
    }

    public int getNums() {
        return nums;
    }

    public int getOrder() {
        return order;
    }

    public String getDesc() {
        return desc;
    }

    public int getTimes() {
        return times;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public void setIsExtend(int isExtend) {
        this.isExtend = isExtend;
    }

    public void setIsRandom(int isRandom) {
        this.isRandom = isRandom;
    }

    public void setIsStrict(int isStrict) {
        this.isStrict = isStrict;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setNums(int nums) {
        this.nums = nums;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setSyscode(String syscode) {
        this.syscode = syscode;
    }

    public String getId() {
        return id;
    }

    public String getFirstPath() {
        return firstPath;
    }

    public String getSecondPath() {
        return secondPath;
    }

    public String getThirdPath() {
        return thirdPath;
    }

    public String getForthPath() {
        return forthPath;
    }

    public String getMethod() {
        return method;
    }

    public String getResult() {
        return result;
    }

    public String getSyscode() {
        return syscode;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setFirstPath(String firstPath) {
        this.firstPath = firstPath;
    }

    public void setSecondPath(String secondPath) {
        this.secondPath = secondPath;
    }

    public void setThirdPath(String thirdPath) {
        this.thirdPath = thirdPath;
    }

    public void setForthPath(String forthPath) {
        this.forthPath = forthPath;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
