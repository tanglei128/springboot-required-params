package com.tanglei.springbootstudydemo.controller;

import com.alibaba.fastjson.JSONObject;
import com.tanglei.springbootstudydemo.entity.User;
import com.tanglei.springbootstudydemo.result.ResponseResult;
import com.tanglei.springbootstudydemo.result.ResultCode;
import com.tanglei.springbootstudydemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("/getAll")
    public ResponseResult getAll() {
        ResponseResult result = new ResponseResult();
        List<User> list = userService.list();
        return result.setCode(ResultCode.SUCCESS).setMessage("成功").setData(list);
    }
    @RequestMapping("/map")
    public ResponseResult getUserByMap(@RequestBody User user) {
        Map map = JSONObject.parseObject(JSONObject.toJSONString(user), Map.class);
        return userService.getUserByMap(map);
    }
}
