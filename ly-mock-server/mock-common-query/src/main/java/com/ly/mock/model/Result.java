package com.ly.mock.model;

import lombok.Data;

/**
     * @author 冯赵杨
     * @date 2018/7/18 上午10:19
     */
    @Data
    public class Result {

        private boolean success = true;

        private String status;

        private Integer code;

        private Integer errorCode;

        private String message;

        private Object data;

        public static Result build(String status, Integer code, String message, Object data) {
            return new Result(status, code, message, data);
        }

        public static Result ok(Object data) {
            return new Result(data);
        }

        public static Result ok() {
            return new Result(null);
        }

        public static Result error(Integer code, String msg) {
            Result result = new Result();
            result.setStatus("fail");
            result.setCode(code);
            result.setErrorCode(code);
            result.setMessage(msg);
            result.setSuccess(false);
            return result;
        }


        public Result() {

        }

        public static Result build(String status, Integer code, String message) {
            return new Result(status, code, message, null);
        }

        public Result(String status, Integer code, String message, Object data) {
            this.code = code;
            this.errorCode = code;
            this.status = status;
            this.message = message;
            this.data = data;
        }

        public Result(Object data) {
            this.code = 200;
            this.errorCode = 0;
            this.status = "success";
            this.message = "";
            this.data = data;
        }

        public static Result fail() {
            return new Result("fail", 500, "服务器接口异常", null);
        }

        public static Result fail(String msg) {
            return new Result("fail", 300, msg, null);
        }


        public void setStatus(String status) {
            this.status = status;
        }

        public String getStatus() {
            return status;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public Integer getCode() {
            return code;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setData(Object data) {
            this.data = data;
        }

        public Object getData() {
            return data;
        }

        public Integer getErrorCode (){return errorCode;}

        public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    }

