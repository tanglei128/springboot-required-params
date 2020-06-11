package com.tanglei.springbootstudydemo.result;

public enum ResultCode {
    SUCCESS(200,"成功"),
    FAIL(400,"失败"),
    REQUIRED_PARAMS_MISSING(401,"必传参数缺失");

    public int code;
    public String desc;

    ResultCode(int code,String desc){
        this.code = code;
        this.desc = desc;
    }
    ResultCode(int code){
        this.code = code;
    }
}
