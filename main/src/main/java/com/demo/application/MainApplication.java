package com.demo.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author: bi
 * @date: 2021/11/8 10:03
 */
@ComponentScan(basePackages = {"com.demo.*"})
@EnableDiscoveryClient //开启nacos
@EnableFeignClients(basePackages = "com.demo.service")    //开启fegin
@SpringBootApplication
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class);
    }
}
