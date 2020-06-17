package com.tanglei.springbootstudydemo.controller;

import com.tanglei.springbootstudydemo.entity.User;
import com.tanglei.springbootstudydemo.result.ResponseResult;
import com.tanglei.springbootstudydemo.result.ResultCode;
import com.tanglei.springbootstudydemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
