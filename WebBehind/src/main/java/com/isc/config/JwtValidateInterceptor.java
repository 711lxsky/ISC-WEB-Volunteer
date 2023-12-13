package com.isc.config;

import com.alibaba.fastjson2.JSON;
import com.isc.common.utils.JwtUtil;
import com.isc.common.viewObj.Result;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class JwtValidateInterceptor implements HandlerInterceptor {
// JWT校验拦截器
    @Resource
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) throws Exception {
        String token = request.getHeader("X-Token");
        if(token != null){
            try {
                jwtUtil.parseToken(token);
                log.debug(request.getRequestURI()+"验证通过");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        log.debug(request.getRequestURI()+"验证失败，禁止访问");
        response.setContentType("application/json;charset=utf-8");
        Result<Object> fail = Result.fail(2003,"jwt无效,请重新登录");
        response.getWriter().write(JSON.toJSONString(fail));
        return false;
    }
}
