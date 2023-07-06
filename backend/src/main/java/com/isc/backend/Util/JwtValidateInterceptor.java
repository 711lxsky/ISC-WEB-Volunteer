package com.isc.backend.Util;

import com.alibaba.fastjson2.JSON;
import com.isc.backend.setting.RCodeMessage;
import com.isc.backend.setting.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class JwtValidateInterceptor implements HandlerInterceptor {
    @Resource
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
      String token = request.getHeader(jwtUtil.getTokenName());
      log.debug(request.getRequestURI()+"jwt验证");
      if(token != null){
          try {
              jwtUtil.parseToken(token);
              log.debug(request.getRequestURI()+"jwt验证通过");
              return true;
          } catch (Exception e){
              e.printStackTrace();
          }
      }
        log.debug(request.getRequestURI()+"Jwt验证失败，访问禁止");
        response.setContentType("application/json;charset=utf-8");
        Result<Object> jwtValidateFail = Result.fail(RCodeMessage.JwtValidateFail.getCode(), RCodeMessage.JwtValidateFail.getDescription()+",请重新登录");
        response.getWriter().write(JSON.toJSONString(jwtValidateFail));
        return false;
    }
}
