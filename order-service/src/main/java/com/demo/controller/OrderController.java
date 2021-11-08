package com.demo.controller;

import com.alibaba.fastjson.JSON;
import com.demo.entity.Order;
import com.demo.entity.Product;
import com.demo.entity.User;
import com.demo.service.IProductApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author: bi
 * @date: 2021/11/8 10:22
 */
@RestController
@RequestMapping("/order")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderController {

    private static final String PRODUCT_URL = "http://product-service/product/";
    private static final String PRODUCT_SERVER = "product-service";

    private static final String USER_URL = "http://user-service/user/";
    private static final String USER_SERVER = "user-service";

    private final RestTemplate restTemplate;


    //专门负责服务注册和发现的，我们可以通过它获取到注册到注册中心的所有服务
    private final DiscoveryClient discoveryClient;

    private final IProductApiService iProductApiService;

    /**
     * 启动Ribbon负载均衡
     */
    @GetMapping("/prod")
    public Object order3(@RequestParam Long orderId) {

        log.info(">>>客户下单，调用商品微服务查询商品信息<<<");
        // 采用restTemplate调用
        Product product = restTemplate.getForObject(PRODUCT_URL + "getProduct?orderId=" + orderId, Product.class);
        User user = restTemplate.getForObject(USER_URL + "getUserInfo?orderId=" + orderId, User.class);
        log.info(">>商品信息,查询结果:" + JSON.toJSONString(product));
        log.info(">>用户信息,查询结果:" + JSON.toJSONString(user));
        Order order = new Order();
        order.setOrderId(orderId);
        order.setProductId(product.getProductId());
        order.setUserId(user.getUserId());
        return order;

    }


    /**
     * 使用nacos方式范问相关接口信息
     * 不可以启动Ribbon负载均衡
     */
    @GetMapping("/orders")
    public Order prodNacos(@RequestParam Long orderId) {
        log.info(">>>客户下单，调用商品微服务查询商品信息<<<");
        // 采用restTemplate调用
        //从nacos中获取服务地址 获取的是个list集群信息


        ServiceInstance userInstance = discoveryClient.getInstances(USER_SERVER).get(0);
        String userUrl = userInstance.getHost() + ":" + userInstance.getPort();
        log.info(">>从nacos中获取到的用户的微服务地址为:" + userUrl);
        User user = restTemplate.getForObject("http://" + userUrl + "/user/getUserInfo?orderId=" + orderId, User.class);

        ServiceInstance productInstance = discoveryClient.getInstances(PRODUCT_SERVER).get(0);
        String productUrl = productInstance.getHost() + ":" + productInstance.getPort();
        log.info(">>从nacos中获取到的商品的微服务地址为:" + productUrl);
        Product product = restTemplate.getForObject("http://" + productUrl + "/product/getProduct?orderId=" + orderId, Product.class);

        log.info(">>商品信息,查询结果:" + JSON.toJSONString(product));
        log.info(">>用户信息,查询结果:" + JSON.toJSONString(user));
        Order order = new Order();
        order.setOrderId(orderId);
        order.setUserId(user.getUserId());
        order.setProductId(product.getProductId());
        return order;
    }




    /**
     * 使用Feign 调用远程服务
     * */
    @GetMapping("/orders/orders")
    public Object order(@RequestParam Long orderId) {

        log.info(">>>客户下单，基于fegin调用商品微服务查询商品信息<<<");
        Product product = iProductApiService.getProductById(orderId);
        log.info(">>商品信息,查询结果:" + JSON.toJSONString(product));
        Order order = new Order();
        order.setOrderId(orderId);
        order.setProductId(product.getProductId());
        return order;

    }



    @RequestMapping("/mesg1")
    public String message1(){
        return "范问sertinel第一信息";
    }

    @RequestMapping("/mesg2")
    public String message2(){
        return "范问sertinel第二信息";
    }

}
