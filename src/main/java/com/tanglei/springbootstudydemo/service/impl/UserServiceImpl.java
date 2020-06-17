package com.tanglei.springbootstudydemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tanglei.springbootstudydemo.entity.User;
import com.tanglei.springbootstudydemo.mapper.UserMapper;
import com.tanglei.springbootstudydemo.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
