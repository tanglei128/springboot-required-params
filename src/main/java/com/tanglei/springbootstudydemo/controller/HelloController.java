package com.tanglei.springbootstudydemo.controller;

import com.tanglei.springbootstudydemo.global.myannotation.Check;
import com.tanglei.springbootstudydemo.result.ResponseResult;
import com.tanglei.springbootstudydemo.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/v1")
public class HelloController {

    @Autowired
    public HelloService helloService;

    @RequestMapping("/user")
    @Check(params = {"userName", "pwd"})
    @ResponseBody
    public Map getUser(){
        Map map = new HashMap();
        map.put("userName","userName");
        map.put("pwd","111222333");
        return  map;

    }

    @RequestMapping(value="/users",method = RequestMethod.POST,produces ="application/json; charset=utf-8")
    @Check(params = {"size"})
    @ResponseBody
    public ResponseResult getUsers(){
        return  helloService.getUsers();
    }


}
