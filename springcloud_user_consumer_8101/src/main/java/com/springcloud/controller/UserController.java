package com.springcloud.controller;

import com.springcloud.dto.UserRequestDto;
import com.springcloud.feignService.userService;
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
import java.util.Map;


@RestController
@Api(tags = "")
@RequestMapping("user_consumer_8001")
public class UserController {
    //直接再启动时注册一个有负载均衡注解的bean，但是目前为止我不知道发布到tomcat上面的war包的服务
    //如何使用，因为war包的原因后面我要自动补全路径，就不能使用注解有LodeBalance的Bean了，因为
    //两次负载均衡可能会找到的服务有可能不是同一个造成服务调用时好时坏！！解决办法就是使用LoadBalancerClient
    //在调用的使用先使用了负载均衡的算法选中服务实例后再进行确定路径的访问就可以实现负载均衡了。发布war包确实会麻烦很多
    @Autowired
    private RestTemplate restTemplate;
    // private static final String REST_URL_PREFIX="http://localhost:8001/springcould_user_provider_8001_war/";
    private static final String REST_URL_PREFIX="http://localhost:8002/springcloud_user_provider_8002_war";
    @Autowired
    private LoadBalancerClient loadBalancer;
    //feign的实现方式，war包的我也是需要在requestMapping里面补全路径，但是问题是我怎么知道它选中了哪个服务实例
    //唉，淡淡的忧伤
    @Autowired
    private userService userServiceFeign;
    @RequestMapping(value = "/consumerGetInfo",method = RequestMethod.POST)
    public Object consumerGetInfo(@RequestBody UserRequestDto dto){
        //新创建的bean是没有一开始的LodeBalance的  即没有负载均衡
        RestTemplate template = new RestTemplate();
        return template.postForObject(REST_URL_PREFIX+"/getUser",dto,Object.class);

    }

    /**
     * 目前war包形式的实现成功的负载均衡方式！
     * @param dto
     * @return
     */
    @RequestMapping(value = "/consumerGetInfo_ribbonClient",method = RequestMethod.POST)
    public Object consumerGetInfo_ribbonClient(@RequestBody UserRequestDto dto){
        /* String infra = "{ \"userId\": 1}";//这是http的访问方式，貌似使用ribbon不用这样操作。
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("application/json;UTF-8"));
        HttpEntity<String> strEntity = new HttpEntity<String>(infra,headers);*/
        ServiceInstance instance = loadBalancer.choose("springcloud-user-provider");
        URI url = URI.create(String.format("http://%s:%s/%s/getUser", instance.getHost(), instance.getPort(),"springcloud_user_provider_"+instance.getPort()+"_war"));
        String path = url.toString();
        RestTemplate template = new RestTemplate();
        return template.postForObject(path, dto, Object.class);
    }

    /**
     * 不能确定访问的是哪个实例，所以不知道如何补全mapping路径导致时好时坏的访问；
     * @param dto
     * @return
     */
    @RequestMapping(value = "/consumerGetInfo_feign",method = RequestMethod.POST)
    public Object consumerGetInfo_feign(@RequestBody UserRequestDto dto){
      return userServiceFeign.getUser_by_feign(dto);
    }

}
