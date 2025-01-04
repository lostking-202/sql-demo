package com.example.demo.service;

import com.example.demo.entity.Person;
import com.example.demo.mapper.PersonMapper;
import org.apache.ibatis.transaction.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.concurrent.TransferQueue;

@Service
public class PersonService {
    @Autowired
    PersonMapper personMapper;
    @Autowired
    TransactionTemplate transactionTemplate;

    public void batchDo(){
        new Thread(()->{
            transactionTemplate.executeWithoutResult((__->{
                List<Person> personList=personMapper.getPersonsInBatchForUpdate();
                // 可以跳过锁住的数据
                System.out.println("线程："+Thread.currentThread().getName()+"拿到"+personList.size()+"个person");
                try {
                    Thread.sleep(1000*60);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }));
        }).start();
        new Thread(()->{
            transactionTemplate.executeWithoutResult((__->{
                List<Person> personList=personMapper.getPersonsInBatchForUpdate();
                System.out.println("线程："+Thread.currentThread().getName()+"拿到"+personList.size()+"个person");
                try {
                    Thread.sleep(1000*60);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }));
        }).start();
    }

    public void doOne(){

    }
}
