package com.isc.backstage.service.impl;

import com.isc.backstage.utils.JwtUtil;
import com.isc.backstage.domain.DTO.LoginUserDTO;
import com.isc.backstage.domain.Result;
import com.isc.backstage.domain.VO.TokenVO;
import com.isc.backstage.domain.VO.UserVO;
import com.isc.backstage.entity.User;
import com.isc.backstage.service.LoginService;
import com.isc.backstage.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

/**
 * @Author: 711lxsky
 * @Date: 2023-12-19
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private JwtUtil jwtUtil;

    @Resource
    private UserService userService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Override
    public Result<?> login(LoginUserDTO dto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword())
        );
        User user = userService.getUserByUsername(dto.getUsername());
        UserVO userVO = parseUserToUserVO(user);
        TokenVO tokenVO = jwtUtil.generateTokenVOWithUserInfo(userVO);
        // redis中只放AccessToken,因为后续重新登录需要把之前的token去掉，所以必须有一个AccessToken唯一标识
        jwtUtil.saveAccessTokenWithRefreshToken(tokenVO.getRefreshToken(), tokenVO.getAccessToken());
        return Result.success(200, "登录成功", tokenVO);
    }

    private UserVO parseUserToUserVO(User user){
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }
}
