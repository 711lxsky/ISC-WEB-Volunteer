package com.isc.backend.Util;

//ess_token可能会被发送到多个服务器，到处抛头露面。refresh_token仅仅是客户端和认证服务器进行交付。客户端不会将refresh_token发送给其它服务器。

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Slf4j
@Component
public class RequestUtil {

    @Resource
    private JwtUtil jwtUtil;

    public String getToken(){
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        return request.getHeader(jwtUtil.getTokenName());
    }

    public HttpServletRequest getRequest(){
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }
}
