package com.tanglei.springbootstudydemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tanglei.springbootstudydemo.entity.User;
import com.tanglei.springbootstudydemo.mapper.UserMapper;
import com.tanglei.springbootstudydemo.result.GenerateResponse;
import com.tanglei.springbootstudydemo.result.ResponseResult;
import com.tanglei.springbootstudydemo.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    UserMapper userMapper;

    @Override
    public ResponseResult getUserByMap(Map map) {
        List list = userMapper.selectByMap(map);
        ResponseResult result = GenerateResponse.getGenerateSuccess(list);
        return result;
    }
}
