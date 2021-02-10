package com.springcloud.controller;

import com.springcloud.dto.UserRequestDto;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
@Api(tags = "")
@RequestMapping("user_consumer_8001")
public class UserController {
    @Autowired
    private RestTemplate restTemplate;
    private static final String REST_URL_PREFIX="http://localhost:8001/springcould_user_provider_8001_war/";


    @RequestMapping(value = "/consumerGetInfo",method = RequestMethod.POST)
    public Object consumerGetInfo(@RequestBody UserRequestDto dto){

        return restTemplate.postForObject(REST_URL_PREFIX+"springCloud_user_provider_8001/getUser",dto, Object.class);
    }


}
