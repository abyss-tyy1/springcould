package com.springcloud.controller;

import com.springcloud.dto.UserRequestDto;
import com.springcloud.entity.UserEntity;
import com.springcloud.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "springCloud_user_provider_8002")
public class UserController {
    @Autowired
    private UserService userService;
    @Resource
    private DiscoveryClient discoveryClient;
    @Value("${eureka.instance.instance-id}")
    private String serverProvider;

    @RequestMapping(value = "/getUser",method = RequestMethod.POST)
    @ApiOperation(value = "getUser")
    Map<String,Object> getUser(@RequestBody UserRequestDto dto){
        List<UserEntity> allUser = userService.findAllUser(dto);
        Map<String,Object> map = new HashMap<>();
        map.put("data",allUser);

        map.put("serverProvider",serverProvider);
        return map;
    }

    @RequestMapping(method =RequestMethod.POST ,value = "/discovery")
    public Object discovery(){
        //获取微服务列表清单
        List<String> services = discoveryClient.getServices();
        Map<String,Object> map = new HashMap<>();
        map.put("discovery=>services",services);
        //获取服务实例具体信息
        List<ServiceInstance> instances = discoveryClient.getInstances("SPRINGCLOUD-USER-PROVIDER");
        List<String> serviceInstanceList = new ArrayList<>();
        for (ServiceInstance instance : instances) {
           String string = instance.getHost()+"   "+
                   instance.getUri()+"   "+
                   instance.getMetadata()+"   "+
                   instance.getServiceId()+"   "+
                   instance.getScheme()+"   "+
                   instance.getInstanceId()+"   "+
                   instance.getHost()+"   ";
           serviceInstanceList.add(string);
        }

        map.put("serviceInstanceList",serviceInstanceList);
        return map;
    }

}
