package com.tanglei.springbootstudydemo.global.myaspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tanglei.springbootstudydemo.exception.ServiceException;
import com.tanglei.springbootstudydemo.global.myannotation.Check;
import com.tanglei.springbootstudydemo.utils.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.LoggerFactory;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.slf4j.Logger;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.*;


@Aspect
@Component
public class ParamsAspect {
    private static final Logger logger = LoggerFactory.getLogger(ParamsAspect.class);

    @Pointcut("@annotation(com.tanglei.springbootstudydemo.global.myannotation.Check)")
    public void paramsCheck(){}

    @Around("paramsCheck()")
    public Object around (ProceedingJoinPoint joinPoint) throws Throwable{
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        Check check = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(Check.class);
        String[] requiredFields = check.params();

        String url = request.getRequestURL().toString();
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String queryString = request.getQueryString();
        //这里可以获取到get请求的参数和其他信息
        logger.info("请求开始, 各个参数, url: {}, method: {}, uri: {}, params: {}", url, method, uri, queryString);
        //重点 这里就是获取@RequestBody参数的关键  调试的情况下 可以看到o变量已经获取到了请求的参数
        Object[] objs = joinPoint.getArgs();

        logger.info("objs: {}",objs);
        // result的值就是被拦截方法的返回值
        Object result = joinPoint.proceed();
        validateFields(objs,requiredFields);
//        Map fieldsName = getFieldsName(joinPoint);
//        logger.info("fieldsName: {}",fieldsName);

        return result;
//        ServletRequestAttributes sAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = sAttributes.getRequest();
//        String target = joinPoint.getSignature().getDeclaringTypeName();              // 全路径类名
//        String classNm = target.substring(target.lastIndexOf(".") + 1, target.length()); // 类名截取
//        String method = joinPoint.getSignature().getName();                          // 获取方法名
//        Map<String, String> paramsMap = getAllRequestParam(request);
//        logger.info("{}.{} 接收参数: {}", classNm, method, JSON.toJSONString(paramsMap));
//        Check check = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(Check.class);
//        String[] requiredFields = check.params();
//        logger.info("必传参数：{}",JSON.toJSONString(requiredFields));
//        validateParams(paramsMap, requiredFields);
//        return joinPoint.proceed();
    }

    public  static  void validateFields(Object[] objs,String [] requiredFields){
            Map map = new HashMap();
                for (Object object:objs){
                    if (object.getClass().isPrimitive()){
                        logger.info("object: {}", object);
                    }else {
                        Map mapin = JSONObject.parseObject(JSONObject.toJSONString(object), Map.class);
                        logger.info("objectzzz: {}", object);
                        logger.info("mapin: {}", mapin);
                        for (Object key:mapin.keySet()){
//                            String k = (String)key;
                            Object value = mapin.get(key);
                            logger.info("key: {},value:{}", key,value);
                            logger.info("key: {},value:{}", key.getClass().getName(),value.getClass().getName());
                            if(value.getClass().getName().equals("java.lang.String")){
                                map.put(key,value);
                            }else {
                                Map mapin2 = JSONObject.parseObject(JSONObject.toJSONString(value), Map.class);
                                for (Object o:mapin2.keySet()){
                                    map.put(key.toString()+"."+o.toString(),mapin2.get(o));
                                }
                                //map.put(key+"",value);
                            }
                           // map.put(key,value);
                        }
                        //map.put()
                    }
                    //logger.info("各个参数ss, object: {}, field: {}", object, field);
                }
        logger.info("final map: {}",map);
//        if (requiredFields.length>0){
//            for (String field :requiredFields){
//                for (Object object:objs){
//                    logger.info("各个参数ss, object: {}, field: {}", object, field);
//                }
////                if (StringUtils.isEmpty(paramsMap.get(field))){
////                    throw new ServiceException("必传参数:"+field+",不能为空");
////                }
//            }
//        }
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

    private static Map getFieldsName(ProceedingJoinPoint joinPoint) throws ClassNotFoundException, NoSuchMethodException {
        String classType = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        // 参数值
        Object[] args = joinPoint.getArgs();
        Class<?>[] classes = new Class[args.length];
        for (int k = 0; k < args.length; k++) {
            if (!args[k].getClass().isPrimitive()) {
                // 获取的是封装类型而不是基础类型
                String result = args[k].getClass().getName();
                Class s = map.get(result);
                classes[k] = s == null ? args[k].getClass() : s;
            }
        }
        ParameterNameDiscoverer pnd = new DefaultParameterNameDiscoverer();
        // 获取指定的方法，第二个参数可以不传，但是为了防止有重载的现象，还是需要传入参数的类型
        Method method = Class.forName(classType).getMethod(methodName, classes);
        // 参数名
        String[] parameterNames = pnd.getParameterNames(method);
        // 通过map封装参数和参数值
        HashMap<String, Object> paramMap = new HashMap();
        for (int i = 0; i < parameterNames.length; i++) {
            paramMap.put(parameterNames[i], args[i]);
        }
        return paramMap;
    }

    private static HashMap<String, Class> map = new HashMap<String, Class>() {
        {
            put("java.lang.Integer", int.class);
            put("java.lang.Double", double.class);
            put("java.lang.Float", float.class);
            put("java.lang.Long", long.class);
            put("java.lang.Short", short.class);
            put("java.lang.Boolean", boolean.class);
            put("java.lang.Char", char.class);
        }
    };

}
