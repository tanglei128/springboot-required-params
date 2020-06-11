package com.tanglei.springbootstudydemo.global.myaspect;

import com.alibaba.fastjson.JSON;
import com.tanglei.springbootstudydemo.exception.ServiceException;
import com.tanglei.springbootstudydemo.global.myannotation.Check;
import com.tanglei.springbootstudydemo.utils.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.slf4j.Logger;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;



@Aspect
@Component
public class ParamsAspect {
    private static final Logger logger = LoggerFactory.getLogger(ParamsAspect.class);

    @Pointcut("@annotation(com.tanglei.springbootstudydemo.global.myannotation.Check)")
    public void paramsCheck(){}

    @Around("paramsCheck()")
    public Object around (ProceedingJoinPoint joinPoint) throws Throwable{
        ServletRequestAttributes sAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = sAttributes.getRequest();
        String target = joinPoint.getSignature().getDeclaringTypeName();              // 全路径类名
        String classNm = target.substring(target.lastIndexOf(".") + 1, target.length()); // 类名截取
        String method = joinPoint.getSignature().getName();                          // 获取方法名
        Map<String, String> paramsMap = getAllRequestParam(request);
        logger.info("{}.{} 接收参数: {}", classNm, method, JSON.toJSONString(paramsMap));
        Check check = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(Check.class);
        String[] requiredFields = check.params();
        logger.info("必传参数：{}",JSON.toJSONString(requiredFields));
        validateParams(paramsMap, requiredFields);
        return joinPoint.proceed();
    }

    public  static  void validateParams(Map<String,String> paramsMap,String [] requiredFields){
        if (requiredFields.length>0){
            for (String field :requiredFields){
                if (StringUtils.isEmpty(paramsMap.get(field))){
                    throw new ServiceException("必传参数:"+field+",不能为空");
                }
            }
        }
    }

    /**
     * 获取请求参数
     */
    public static Map<String, String> getAllRequestParam(HttpServletRequest request) {
        Map<String, String> res = new HashMap<>();
        Enumeration<?> temp = request.getParameterNames();
        logger.info("request：", request );
        if (null != temp) {
            while (temp.hasMoreElements()) {
                String en = (String) temp.nextElement();
                String value = request.getParameter(en);
                res.put(en, value);
                // 在报文上送时，如果字段的值为空，则不上送<下面的处理为在获取所有参数数据时，判断若值为空，则删除这个字段>
                if (StringUtils.isEmpty(res.get(en))) {
                    res.remove(en);
                }
            }
        }
        return res;
    }

}
