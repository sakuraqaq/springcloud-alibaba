package com.demo.application.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author: bi
 * @date: 2021/11/8 10:22
 */
@Configuration
public class ApplicationConfig {


    @Bean
    @LoadBalanced //使用Ribbon负载均衡
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}
