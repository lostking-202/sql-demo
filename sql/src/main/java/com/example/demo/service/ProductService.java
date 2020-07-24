package com.example.demo.service;

import com.example.demo.dto.ProductResult;
import com.example.demo.mapper.ProductMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ProductService {
    @Resource
    private ProductMapper productMapper;

    private static final String product_code1="8001";

    private static final String product_code2="8002";

    private final AtomicInteger optimisticSuccess=new AtomicInteger();

    private final AtomicInteger optimisticFailure=new AtomicInteger();

    private final AtomicInteger pessimisticSuccess=new AtomicInteger();

    private final AtomicInteger pessimisticFailure=new AtomicInteger();

    @Transactional
    public void orderProductOptimistic()throws NullPointerException{
        int num=productMapper.queryProductNumByCode(product_code1);
        if(num<=0){
            optimisticFailure.incrementAndGet();
            return;
        }
        int result=productMapper.updateOrderQuantityOptimistic(product_code1);
        if(result==0){
            optimisticFailure.incrementAndGet();
            throw new NullPointerException("商品已经卖完");
        }
        optimisticSuccess.incrementAndGet();
    }


    public String getStatistics(){
        return "optimisticSuccess:"+optimisticSuccess+",optimisticFailure:"+optimisticFailure+",pessimisticSuccess:"+pessimisticSuccess+",pessimisticSuccess"+pessimisticSuccess;
    }

    @Transactional(rollbackFor =Exception.class)
    public void orderProductPessimistic(){
        int num=productMapper.queryProductNumByCodeForUpdate(product_code2);
        if(num<=0){
            pessimisticFailure.incrementAndGet();
            return;
        }
        productMapper.updateOrderQuantityPessimistic(product_code2);
        pessimisticSuccess.incrementAndGet();
    }

    @Transactional
    public ProductResult getProdtctDetail(){
        Random random=new Random();
        return productMapper.selectProductDetail(random.nextInt()%2==0?product_code1:product_code2);
    }

    public void clearStatistics(){
        optimisticSuccess.set(0);
        optimisticFailure.set(0);
        pessimisticSuccess.set(0);
        pessimisticFailure.set(0);
    }

    public void orderProductWithoutLock(){
        productMapper.updateOrderQuantityPessimistic(product_code2);
    }

    @Transactional(propagation = Propagation.NEVER)
    public void methodA(){
        methodB();
    }
    @Transactional(propagation = Propagation.NEVER)
    public void methodB(){
        //productMapper.selectProductDetail("8001");
        productMapper.updateOrderQuantityPessimistic("8001");
    }
}
