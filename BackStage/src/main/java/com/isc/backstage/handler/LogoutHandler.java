package com.isc.backstage.handler;

import com.isc.backstage.utils.JwtUtil;
import com.isc.backstage.utils.RequestUtil;
import com.isc.backstage.setting_enumeration.CodeAndMessage;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * @Author: 711lxsky
 * @Date: 2024-01-04
 */
@Service
@RequiredArgsConstructor
public class LogoutHandler implements org.springframework.security.web.authentication.logout.LogoutHandler {

    @Resource
    private RequestUtil requestUtil;

    @Resource
    private JwtUtil jwtUtil;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String accessToken = requestUtil.getTokenFromRequest(request, response);
        if(! ( jwtUtil.removeAccessTokenInRedis(accessToken) && jwtUtil.putAccessTokenIntoBlackList(accessToken))){
            throw new RuntimeException(CodeAndMessage.INTERNAL_SERVER_ERROR.getZhDescription());
        }
        SecurityContextHolder.clearContext();
    }
}
