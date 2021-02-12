package com.springcloud.controller;

import com.springcloud.dto.UserRequestDto;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;


@RestController
@Api(tags = "")
@RequestMapping("user_consumer_8001")
public class UserController {
    @Autowired
    private RestTemplate restTemplate;
    // private static final String REST_URL_PREFIX="http://localhost:8001/springcould_user_provider_8001_war/";
    private static final String REST_URL_PREFIX="http://SPRINGCLOUD_USER_PROVIDER";
    @Autowired
    private LoadBalancerClient loadBalancer;
    @RequestMapping(value = "/consumerGetInfo",method = RequestMethod.POST)
    public Object consumerGetInfo(@RequestBody UserRequestDto dto){

        return restTemplate.postForObject(REST_URL_PREFIX+"/getUser",dto,Object.class);

    }

    @RequestMapping(value = "/consumerGetInfo_ribbonClient",method = RequestMethod.POST)
    public Object consumerGetInfo_ribbonClient(@RequestBody UserRequestDto dto){
        ServiceInstance instance = loadBalancer.choose("SPRINGCLOUD_USER_PROVIDER");
       /* String infra = "{ \"userId\": 1}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("application/json;UTF-8"));
        HttpEntity<String> strEntity = new HttpEntity<String>(infra,headers);*/
        URI url = URI.create(String.format("http://%s:%s/%s/getUser", instance.getHost(), instance.getPort(),instance.getServiceId().toLowerCase()+"_"+instance.getPort()+"_war"));
        return restTemplate.postForObject(url, dto, HashMap.class);
    }


}
