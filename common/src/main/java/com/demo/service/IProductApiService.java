package com.demo.service;

import com.demo.entity.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author: bi
 * @date: 2021/11/8 14:00
 */
@FeignClient("product-service")//声明调用提供者的name
public interface IProductApiService {

    @GetMapping("/product/getProduct?orderId={orderId}")
    Product getProductById(@PathVariable("orderId") Long pid);
}
