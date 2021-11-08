package com.demo.entity;

import lombok.Data;

/**
 * @author: bi
 * @date: 2021/11/8 10:27
 */
@Data
public class Order {

    private Long orderId;

    private Long userId;

    private Long productId;
}
