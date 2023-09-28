package com.example.demo.controller;


import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/hello")
public class HelloController {
    @GetMapping("/sayHello")
    public String sayHello() throws InterruptedException {
        System.out.println(LocalDateTime.now());
        Thread.sleep(5000);
        System.out.println(Thread.currentThread().getName());
        return "hello";
    }
    @Test
    public void test(){
        System.out.println(LocalDateTime.now());
    }
}
