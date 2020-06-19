package com.tanglei.springbootstudydemo.controller;

import com.tanglei.springbootstudydemo.entity.User;
import com.tanglei.springbootstudydemo.global.myannotation.Check;
import com.tanglei.springbootstudydemo.global.myaspect.ParamsAspect;
import com.tanglei.springbootstudydemo.result.ResponseResult;
import com.tanglei.springbootstudydemo.service.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/v1")
public class HelloController {
    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);
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
    @Check(params = {"name"})
    @ResponseBody
    public ResponseResult getUsers(@RequestParam(value="name", required = false) String name, @RequestBody User user){
        return  helloService.getUsers();
    }


}
