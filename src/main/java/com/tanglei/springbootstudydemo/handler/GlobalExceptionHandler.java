package com.tanglei.springbootstudydemo.handler;
import com.tanglei.springbootstudydemo.exception.ServiceException;
import com.tanglei.springbootstudydemo.global.myaspect.ParamsAspect;
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
    public Map allExceptionHandler(HttpServletRequest request, Exception exception,HttpServletResponse response) {
        String exNm = exception.getClass().getName();
        logger.error("请求出现异常:"+ request.getRequestURI()+","+exception.getMessage());
        Map map = new HashMap();
        if (exception instanceof ServiceException){
            map.put("code",response.getStatus());
            map.put("message", exception.getMessage());
            return map;
        }else {
            map.put("code",response.getStatus());
            map.put("message", "其他异常");
            return map;
        }
    }

}
