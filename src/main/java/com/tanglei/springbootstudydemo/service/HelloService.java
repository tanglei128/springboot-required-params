package com.tanglei.springbootstudydemo.service;

import com.tanglei.springbootstudydemo.entity.User;
import com.tanglei.springbootstudydemo.result.ResponseResult;

public interface HelloService {
    ResponseResult getUsers();
    ResponseResult getUserById(String id);
}
