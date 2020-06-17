package com.tanglei.springbootstudydemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tanglei.springbootstudydemo.entity.User;
import com.tanglei.springbootstudydemo.result.ResponseResult;

import java.util.Map;

public interface UserService extends IService<User> {
    ResponseResult getUserByMap(Map map);

}
