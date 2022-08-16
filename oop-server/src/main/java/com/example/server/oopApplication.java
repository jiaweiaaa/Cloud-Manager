package com.example.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 *
 * @author Jiawei Li
 * @since 1.0.0
 */
@SpringBootApplication
@MapperScan("com.example.server.mapper")
public class oopApplication {
    public static void main(String[] args){
        SpringApplication.run(oopApplication.class,args);
    }
}
