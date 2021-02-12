package com.springcloud.feignService;

import com.springcloud.dto.UserRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@FeignClient(name = "springcloud-user-provider")
public interface userService {
    @RequestMapping(value = "/springcloud_user_provider_8001_war/getUser",method = RequestMethod.POST)
    Map<String,Object> getUser_by_feign(@RequestBody UserRequestDto dto);

}
