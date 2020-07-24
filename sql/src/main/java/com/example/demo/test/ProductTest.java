package com.example.demo.test;

import com.example.demo.SqlApplication;
import com.example.demo.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=SqlApplication.class)
public class ProductTest {
    @Autowired
    ProductService service;
    @Test
    public void test1() throws JsonProcessingException {
        ObjectMapper objectMapper=new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(service.getProdtctDetail()));
    }

    @Test
    public void test2() throws JsonProcessingException {
        for(int i=0;i<11;i++){
            ((Runnable) () -> {
                service.orderProductWithoutLock();
            }).run();
        }
        ObjectMapper objectMapper=new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(service.getProdtctDetail()));
    }

    @Test
    public void test3(){
        service.methodB();
    }
}
