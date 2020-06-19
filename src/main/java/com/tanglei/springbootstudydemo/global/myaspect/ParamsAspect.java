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

        Object[] objects = joinPoint.getArgs();
        // 参数名
        String[] parameterNames = getParameterNames(joinPoint);

        for (int i = 0; i < parameterNames.length; i++) {
            //paramMap.put(parameterNames[i], objects[i]);
            logger.info("各个参数名称, parameterNames[i]: {}, objects[i]: {}", parameterNames[i],  objects[i]);
        }



        if ("POST".equals(method.toUpperCase())){
            logger.info("POST请求开始, 各个参数, url: {}, method: {}, uri: {}, params: {}", url, method, uri, queryString);
            //重点 这里就是获取@RequestBody参数的关键  调试的情况下 可以看到objects变量已经获取到了请求的参数

            logger.info("objects: {},objectsTypeName: {},",objects,objects.getClass().getName());
            Map<String, Object> allPostParams = getAllPostParams(objects, request);
            validateFields(allPostParams,requiredFields);
        }else if ("GET".equals(method.toUpperCase())){
            //这里可以获取到get请求的参数和其他信息
            logger.info("GET请求开始, 各个参数, url: {}, method: {}, uri: {}, params: {}", url, method, uri, queryString);
            Map<String, String> paramsMap = getAllRequestParam(request);
            validateParams(paramsMap,requiredFields);
        }
        // result的值就是被拦截方法的返回值
        Object result = joinPoint.proceed();
        return result;
    }

//    获取参数列表里参数名
    public  static  String[] getParameterNames(ProceedingJoinPoint joinPoint ) throws Exception{
        String classType = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
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
        Method methods = Class.forName(classType).getMethod(methodName, classes);
        // 参数名
        String[] parameterNames = pnd.getParameterNames(methods);
        return parameterNames;
    }


    public  static  void validateFields( Map<String,Object> map,String [] requiredFields){

        if (requiredFields.length>0){
            for (String field :requiredFields){
                if (StringUtils.isEmpty((String) map.get(field))){
                    throw new ServiceException("必传参数:"+field+",不能为空");
                }
            }
        }
        logger.info("final map: {}",map);
    }



//    如果是post请求，既要考虑body中的传参，还要考虑地址栏的传参，待完善

    public  static  Map<String, Object> getAllPostParams(Object[] objects,HttpServletRequest request){
        Map<String,Object> map = new HashMap();
        for (Object object:objects){
            logger.info("object---->: {},objType:{}", object,object.getClass().getName());
            if (object.getClass().getName().equals("java.lang.String")){
                logger.info("object: {}", object);
            }else {
                map = getMap("",map,object);
            }
        }
        Map<String, String> paramsMap = getAllRequestParam(request);
        //paramsMap 和map合并

        logger.info("final map: {},paramsMap: {}",map,paramsMap);
        return map;

    }
    public static Map getMap (String baseKey,Map<String,Object> currentMap,Object object){
        Map<String,Object> map= JSONObject.parseObject(JSONObject.toJSONString(object), Map.class);
        for (String key:map.keySet()){
            Object value =map.get(key);
            String totalKey = baseKey==""?key:baseKey+"."+key;
            if (value.getClass().getName().equals("java.lang.String")){
                currentMap.put(totalKey,value);
            }else {
                currentMap =getMap(totalKey,currentMap,value);
            }
        }
        return currentMap;
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
        logger.info("GET request：", request );
        if (null != temp) {
            while (temp.hasMoreElements()) {

                String en = (String) temp.nextElement();
                String value = request.getParameter(en);
                logger.info("GET en：{}，value: {}", en,value );
                res.put(en, value);
                // 在报文上送时，如果字段的值为空，则不上送<下面的处理为在获取所有参数数据时，判断若值为空，则删除这个字段>
                if (StringUtils.isEmpty(res.get(en))) {
                    res.remove(en);
                }
            }
        }
        return res;
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
