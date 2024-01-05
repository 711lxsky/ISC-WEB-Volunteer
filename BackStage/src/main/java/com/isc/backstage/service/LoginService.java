package com.isc.backstage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.isc.backstage.domain.DTO.LoginUserDTO;
import com.isc.backstage.domain.LoginUser;
import com.isc.backstage.domain.Result;

/**
 * @Author: 711lxsky
 * @Date: 2023-12-19
 */

public interface LoginService  {
    Result<?> login(LoginUserDTO dto);
}
