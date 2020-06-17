package com.tanglei.springbootstudydemo.result;

public class GenerateResponse {
    public static ResponseResult getGenerateResult(ResultCode code,String message,Object object){
        ResponseResult  result = new ResponseResult();
        return result.setCode(code).setMessage(message).setData(object);
    }
    public static ResponseResult getGenerateResult(ResultCode code,String message){
        ResponseResult  result = new ResponseResult();
        return result.setCode(code).setMessage(message);
    }
    public static ResponseResult getGenerateSuccess(){
        ResponseResult  result = new ResponseResult();
        return result.setCode(ResultCode.SUCCESS).setMessage(ResultCode.SUCCESS.desc);
    }
    public static ResponseResult getGenerateSuccess(Object object){
        ResponseResult  result = new ResponseResult();
        return result.setCode(ResultCode.SUCCESS).setMessage(ResultCode.SUCCESS.desc).setData(object);
    }
}
