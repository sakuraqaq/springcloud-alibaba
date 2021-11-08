package com.demo.controller;

import com.demo.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: bi
 * @date: 2021/11/8 10:32
 */
@Slf4j
@RestController
@RequestMapping("/product")
public class ProductController {

    @GetMapping("/getProduct")
    public Product getProduct(@RequestParam Long orderId) {
        Product product = new Product();
        product.setProductId(orderId + 1);
        log.info("product");
        return product;
    }
}
