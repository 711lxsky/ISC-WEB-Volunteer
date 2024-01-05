package com.isc.backstage.handler;

import com.isc.backstage.utils.JacksonUtil;
import com.isc.backstage.domain.Result;
import com.isc.backstage.setting_enumeration.CodeAndMessage;
import com.isc.backstage.setting_enumeration.WebSetting;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @Author: 711lxsky
 * @Date: 2024-01-05
 */

@Service
public class RestAccessDeniedHandler implements AccessDeniedHandler {

    @Resource
    private JacksonUtil jacksonUtil;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setHeader(WebSetting.getA_C_A_O(), WebSetting.getAll());
        response.setHeader(WebSetting.getC_C(), WebSetting.getN_C());
        response.setContentType(WebSetting.getResponseContentType());
        response.setCharacterEncoding(WebSetting.getResponseCharacterEncoding());
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.getWriter()
                .println(jacksonUtil.objectToJson(Result.fail(CodeAndMessage.UN_AUTHORIZATION.getCode(),
                        CodeAndMessage.UN_AUTHENTICATION.getZhDescription()+accessDeniedException.getMessage())));
        response.getWriter().flush();
    }
}
