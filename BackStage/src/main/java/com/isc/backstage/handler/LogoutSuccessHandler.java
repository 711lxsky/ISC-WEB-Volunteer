package com.isc.backstage.handler;

import com.isc.backstage.utils.JacksonUtil;
import com.isc.backstage.domain.Result;
import com.isc.backstage.setting_enumeration.CodeAndMessage;
import com.isc.backstage.setting_enumeration.WebSetting;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @Author: 711lxsky
 * @Date: 2024-01-04
 */
@Service
public class LogoutSuccessHandler implements org.springframework.security.web.authentication.logout.LogoutSuccessHandler {

    @Resource
    private JacksonUtil jacksonUtil;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        SecurityContextHolder.clearContext();
        response.setHeader(WebSetting.getA_C_A_O(), WebSetting.getAll());
        response.setHeader(WebSetting.getC_C(), WebSetting.getN_C());
        response.setContentType(WebSetting.getResponseContentType());
        response.setContentType(WebSetting.getResponseCharacterEncoding());
        response.setStatus(HttpStatus.OK.value());
        response.getWriter().println(jacksonUtil.objectToJson(
                Result.success(CodeAndMessage.SUCCESS.getCode(), CodeAndMessage.SUCCESS.getZhDescription())));
        response.getWriter().flush();
    }
}
