package com.tanglei.springbootstudydemo.global.myaspect;

import com.alibaba.fastjson.JSON;
import com.tanglei.springbootstudydemo.entity.OperationLogPersistenceDTE;
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
import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

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
        Object result =null;
        ServletRequestAttributes sAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = sAttributes.getRequest();
        String target = joinPoint.getSignature().getDeclaringTypeName();              // 全路径类名
        String className = target.substring(target.lastIndexOf(".") + 1, target.length()); // 类名截取
        String method = joinPoint.getSignature().getName();                          // 获取方法名
        //String queryString = request.getQueryString();
        Map<String, String> requestParams = getRequestParams(request);
        String source = requestParams.get("source");
        String clientIp = getRemoteHost(request); // clientIp地址-请求源IP
        String requestUrl = request.getRequestURL().toString();  // 请求路径
        String serverIp = InetAddress.getLocalHost().getHostAddress(); //服务器ip
        Object[] args = joinPoint.getArgs();//请求
        result = joinPoint.proceed(); //响应
        String reqContent = JSON.toJSONString(args);
        String resContent = JSON.toJSONString(result);
        OperationLogPersistenceDTE dte = new OperationLogPersistenceDTE();
        dte.setClassName(className);
        dte.setClientIp(clientIp);
        dte.setId("");
        dte.setLogType("info");
        dte.setMethod(method);
        dte.setReqContent(reqContent);
        dte.setResContent(resContent);
        dte.setServerIp(serverIp);
        dte.setTitle(requestUrl);
        dte.setSource(source);
        dte.setReservedText("保留字段");
        logger.info("日志持久化内容: {}",dte);
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

    /**
     * 获取请求参数
     */
    public  Map<String, String> getRequestParams(HttpServletRequest request) {
        Map<String, String> res = new HashMap<>();
        Enumeration<?> temp = request.getParameterNames();
        if (null != temp) {
            while (temp.hasMoreElements()) {
                String en = (String) temp.nextElement();
                String value = request.getParameter(en);
                res.put(en, value);
                // 在报文上送时，如果字段的值为空，则不上送<下面的处理为在获取所有参数数据时，判断若值为空，则删除这个字段>
                if (res.get(en)==null||"".equals(res.get(en))) {
                    res.remove(en);
                }
            }
        }
        return res;
    }

}
