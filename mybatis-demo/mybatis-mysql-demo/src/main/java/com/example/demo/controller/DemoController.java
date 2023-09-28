package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
    @GetMapping("/instance")
    public void getInstanceTest(){
        System.out.println(SingleInstance.getInstance());
    }
}
