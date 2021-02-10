package com.springcloud.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "RegisterCenter")
@RestController
@RequestMapping("/RegisterCenter_7001")
public class RegisterCenter {
    @RequestMapping(value ="/eureka" ,method = RequestMethod.POST)
    public Object Hello(){
        String str = "hello eureka";
        return str;
    }
}
