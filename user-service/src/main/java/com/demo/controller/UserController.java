package com.demo.controller;

import com.demo.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: bi
 * @date: 2021/11/8 10:35
 */
@RestController
@RequestMapping("/user")
public class UserController {


    @GetMapping("/getUserInfo")
    public User getUserInfo(@RequestParam Long orderId){
        User user = new User();
        user.setUserId(orderId + 2);
        return user;
    }
}
