package com.tanglei.springbootstudydemo.result;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ResponseResult {
    private String message;
    private int code;
    private Object data;
//
//    ResponseResult(String message,int code){
//        this.message = message;
//        this.code = code;
//    }
//    ResponseResult(String message,int code,Object data){
//        this.message = message;
//        this.code = code;
//        this.data = data;
//    }


    public String getMessage() {
        return message;
    }

    public ResponseResult setMessage(String message) {
        this.message = message;
        return this;
    }

    public int getCode() {
        return code;
    }

    public ResponseResult setCode(ResultCode resultCode) {
        this.code = resultCode.code;
        return this;
    }

    public Object getData() {
        return data;
    }

    public ResponseResult setData(Object data) {
        this.data = data;
        return this;
    }
}
