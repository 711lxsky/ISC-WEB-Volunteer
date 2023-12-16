package com.isc.backend.mvc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.isc.backend.mvc.entity.LoginUser;
import com.isc.backend.mvc.entity.User;
import com.isc.backend.mvc.mapper.MenuMapper;
import com.isc.backend.mvc.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author lxsky711
 * @date 2023-12-14 21:26
 */
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private MenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //查询用户信息
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,username);
        User user = userMapper.selectOne(queryWrapper);
        //如果没有查询到用户就抛出异常
        if(Objects.isNull(user)){
            throw new RuntimeException("用户名或者密码错误");
        }

//        List<String> list = new ArrayList<>(Arrays.asList("test","admin"));
        List<String> permissonKeyList = menuMapper.selectPermsByUserId(user.getId());
        //把数据封装成UserDetails返回
        return new LoginUser(user,permissonKeyList);
    }
}
