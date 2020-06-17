package com.tanglei.springbootstudydemo.mapper;

import com.tanglei.springbootstudydemo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper {
    public User getUserById(String id);
}
