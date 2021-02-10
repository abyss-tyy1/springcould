package com.springcloud.service;


import com.springcloud.dto.UserRequestDto;
import com.springcloud.entity.UserEntity;


import java.util.List;

public interface UserAPIService {
    List<UserEntity> findAllUser(UserRequestDto dto);
}
