package com.tanglei.springbootstudydemo.service.impl;

import com.tanglei.springbootstudydemo.result.ResponseResult;
import com.tanglei.springbootstudydemo.result.ResultCode;
import com.tanglei.springbootstudydemo.service.HelloService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HelloServiceImpl implements HelloService {
    @Override
    public ResponseResult getUsers() {
        ResponseResult result = new ResponseResult();
        List list = new ArrayList();
        Map map = new HashMap();
        map.put("userName","Tom");
        map.put("age",10);
        Map map2 = new HashMap();
        map2.put("userName","Leo");
        map2.put("age",18);
        list.add(map);
        list.add(map2);
        return result.setCode(ResultCode.SUCCESS).setMessage("成功").setData(list);
    }
}
