package com.isc.backend.mvc.service.impl;

import com.alibaba.fastjson2.JSON;
import com.isc.backend.Util.WebUtil;
import com.isc.backend.setting.Result;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author lxsky711
 * @date 2023-12-15 12:15
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        Result<?> result = new Result<>(HttpStatus.UNAUTHORIZED.value(), "用户认证失败", false);
        String json = JSON.toJSONString(result);
        WebUtil.renderString(response, json);
    }
}
