package com.example.demo.service;

import com.example.demo.SqlApplication;
import com.example.demo.mapper.ProductMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= SqlApplication.class)
@Service
public class TranstationalDemoService {
    @Autowired
    ProductMapper mapper;

    @Test
    @Transactional(rollbackFor =Exception.class)
    public void test1(){
        //mapper.selectProductDetail("8001");
        //methodA();
        mapper.updateOrderQuantityPessimistic("8001");
    }
    @Transactional(propagation = Propagation.MANDATORY)
    private void methodA(){
        mapper.updateOrderQuantityPessimistic("8001");
    }

}
