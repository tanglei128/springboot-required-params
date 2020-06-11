package com.tanglei.springbootstudydemo.handler;
import com.tanglei.springbootstudydemo.exception.ServiceException;
import com.tanglei.springbootstudydemo.global.myaspect.ParamsAspect;
import com.tanglei.springbootstudydemo.result.ResponseResult;
import com.tanglei.springbootstudydemo.result.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;



@RestControllerAdvice
public class GlobalExceptionHandler  {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler(value = Exception.class)
    public ResponseResult allExceptionHandler(HttpServletRequest request, Exception exception,HttpServletResponse response) {
        String exNm = exception.getClass().getName();
        logger.error("请求出现异常:"+ request.getRequestURI()+","+exception.getMessage());
        ResponseResult responseResult = new ResponseResult();
        if (exception instanceof ServiceException){
            responseResult.setCode(ResultCode.REQUIRED_PARAMS_MISSING).setMessage(exception.getMessage());
        }else {
            responseResult.setCode(ResultCode.FAIL).setMessage(exception.getMessage());
        }
        return responseResult;
    }

}
