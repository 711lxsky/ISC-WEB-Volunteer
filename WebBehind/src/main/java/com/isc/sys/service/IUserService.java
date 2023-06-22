package com.isc.sys.service;

import com.isc.sys.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author 711lxsky
 * @since 2023-04-08
 */

public interface IUserService extends IService<User> {

    Map<String, Object> login(User user);

    Map<String, Object> getUserInfo(String token);

    boolean logout(String token);
}
