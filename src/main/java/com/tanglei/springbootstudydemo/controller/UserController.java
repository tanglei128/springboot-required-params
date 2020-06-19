package com.tanglei.springbootstudydemo.controller;

import com.alibaba.fastjson.JSONObject;
import com.tanglei.springbootstudydemo.entity.Course;
import com.tanglei.springbootstudydemo.entity.Student;
import com.tanglei.springbootstudydemo.entity.User;
import com.tanglei.springbootstudydemo.global.myannotation.Check;
import com.tanglei.springbootstudydemo.result.ResponseResult;
import com.tanglei.springbootstudydemo.result.ResultCode;
import com.tanglei.springbootstudydemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    @Check(params = {"size"})
    public ResponseResult getUserByMap(@RequestBody User user) {
        Map map = JSONObject.parseObject(JSONObject.toJSONString(user), Map.class);
        return userService.getUserByMap(map);
    }
    @RequestMapping("/student")
    @Check(params = {
        "course.student.user.name",
        "course.cName"
    })
    public ResponseResult getUserByStudent(@RequestBody Course course, @RequestParam("token") String token) {
        Student student = course.getStudent();
        User user = student.getUser();
        Map map = JSONObject.parseObject(JSONObject.toJSONString(user), Map.class);
        return userService.getUserByMap(map);
    }
}
