package com.isc.backstage.service;

import com.isc.backstage.domain.DTO.LoginUserDTO;
import com.isc.backstage.domain.Result;
import com.isc.backstage.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author zyy
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2023-12-16 16:43:57
*/
public interface UserService extends IService<User> {

    Result<?> login(LoginUserDTO dto);
}
