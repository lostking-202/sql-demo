package com.example.demo.mapper;

import com.example.demo.dto.ProductResult;

public interface ProductMapper {
    int queryProductNumByCodeForUpdate(String product_code);

    void updateOrderQuantityPessimistic(String product_code);

    int queryProductNumByCode(String product_code);

    int updateOrderQuantityOptimistic(String product_code);

    ProductResult selectProductDetail(String product_code);

}
