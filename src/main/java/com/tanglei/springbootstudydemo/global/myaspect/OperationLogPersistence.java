package com.tanglei.springbootstudydemo.global.myaspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName OperationLogPersistence
 * @Description TODO
 * @Author shs-xxaqbzytl
 * @Date 2020/8/26 9:12
 * @Version 1.0
 */
@Aspect
@Component
public class OperationLogPersistence {
    private static final Logger logger = LoggerFactory.getLogger(OperationLogPersistence.class);

    @Pointcut("@annotation(com.tanglei.springbootstudydemo.global.myannotation.OperationLogPersistence)")
    public void opLogSave(){}  //别名

    @Around("opLogSave()") //别名引用
    public Object around (ProceedingJoinPoint joinPoint) throws Throwable{
        ServletRequestAttributes sAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = sAttributes.getRequest();
        String target = joinPoint.getSignature().getDeclaringTypeName();              // 全路径类名
        String classNm = target.substring(target.lastIndexOf(".") + 1, target.length()); // 类名截取
        String method = joinPoint.getSignature().getName();                          // 获取方法名
        String clientIp = getRemoteHost(request); // clientIp地址-请求源IP
        String requestUrl = request.getRequestURL().toString();  // 请求路径
        Object result = joinPoint.proceed(); //响应


        return result;
    }

    /**
     * 获取目标主机的ip (clientIp)
     * @param request
     * @return
     */
    private String getRemoteHost(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip.contains("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
    }

}
