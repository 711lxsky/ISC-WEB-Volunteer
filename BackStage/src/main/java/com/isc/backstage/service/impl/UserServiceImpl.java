package com.isc.backstage.service.impl;

import cn.hutool.jwt.JWTUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.isc.backstage.Util.RedisUtil;
import com.isc.backstage.domain.DTO.LoginUserDTO;
import com.isc.backstage.domain.LoginUser;
import com.isc.backstage.domain.Result;
import com.isc.backstage.domain.VO.TokenVO;
import com.isc.backstage.entity.User;
import com.isc.backstage.service.UserService;
import com.isc.backstage.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
* @author zyy
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2023-12-16 16:43:57
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService, UserDetailsService {

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private AuthenticationManager authenticationManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.getUserByUsername(username);
        return LoginUser.builder()
                .userid(user.getId())
                .username(user.getName())
                .password(user.getPassword())
                .build();
    }

    public User getUserByUsername(String username){
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getName,username);
        Optional<User> optionalUser = Optional.ofNullable(this.baseMapper.selectOne(wrapper));
        return optionalUser.orElseThrow(() -> new UsernameNotFoundException("抱歉，用户不存在"));
    }

    @Override
    public Result<?> login(LoginUserDTO dto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword())
        );
        User user = this.getUserByUsername(dto.getUsername());

    }
}




