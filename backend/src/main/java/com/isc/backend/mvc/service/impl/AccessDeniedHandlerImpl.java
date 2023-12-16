package com.isc.backend.mvc.service.impl;

import com.alibaba.fastjson2.JSON;
import com.isc.backend.Util.WebUtil;
import com.isc.backend.setting.Result;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author lxsky711
 * @date 2023-12-15 12:11
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        Result<?> result = new Result<>(HttpStatus.FORBIDDEN.value(), "权限不够", false);
        String json = JSON.toJSONString(request);
        WebUtil.renderString(response, json);

    }
}
