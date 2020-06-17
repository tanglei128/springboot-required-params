package com.tanglei.springbootstudydemo.mapper;

import com.tanglei.springbootstudydemo.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper {
    public User getUserById(String id);


    @SelectKey(keyProperty = "user.id",resultType = String.class, before = true,
            statement = "select replace(uuid(), '-', '')")
    @Options(keyProperty = "user.id", useGeneratedKeys = true)
    @Insert("insert into user(id,name,pwd) values(#{user.id,jdbcType=VARCHAR},#{user.name,jdbcType=VARCHAR},#{user.pwd,jdbcType=VARCHAR})")
    public int add(@Param("user") User user);
}
