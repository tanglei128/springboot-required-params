package com.tanglei.springbootstudydemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.tanglei.springbootstudydemo.mapper")
@SpringBootApplication
public class SpringbootstudydemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootstudydemoApplication.class, args);
    }

}
