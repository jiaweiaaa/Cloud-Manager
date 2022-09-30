package com.example.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * test
 * @Author jiawei
 * @Date 3/09/2022 0:19
 * @Version 1.0
 */

@RestController
public class HelloController {
    @GetMapping("hello")
    public String hello(){
        return "hello";
    }
}
