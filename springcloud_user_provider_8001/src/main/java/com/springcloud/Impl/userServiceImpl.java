package com.springcloud.Impl;

import com.springcloud.dto.UserRequestDto;
import com.springcloud.entity.UserEntity;
import com.springcloud.service.UserService;

import com.springcloud.mapper.UserMapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Service;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.List;
@Service
public class userServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @ApiOperation(value = "找到所有用户")
    @RequestMapping(value = "/findAllUser",method = RequestMethod.POST)
    public List<UserEntity> findAllUser(UserRequestDto dto) {
        return userMapper.findAllUser(dto);
    }
}
