package com.tanglei.springbootstudydemo.controller;

import com.tanglei.springbootstudydemo.global.myannotation.Check;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/v1")
public class HelloController {

    @RequestMapping("/user")
    @Check(params = {"userName", "pwd"})
    @ResponseBody
    public Map getUser(){
        Map map = new HashMap();
        map.put("userName","userName");
        map.put("pwd","111222333");
        return  map;

    }
}
