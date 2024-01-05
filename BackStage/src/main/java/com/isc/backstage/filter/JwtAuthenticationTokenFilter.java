package com.isc.backstage.filter;

import com.isc.backstage.Config.CommonSecurityConfiguration;
import com.isc.backstage.Exception.AuthenticationException;
import com.isc.backstage.Exception.ServeErrorException;
import com.isc.backstage.utils.JwtUtil;
import com.isc.backstage.utils.RequestUtil;
import com.isc.backstage.utils.ResponseUtil;
import com.isc.backstage.domain.VO.UserVO;
import com.isc.backstage.setting_enumeration.CodeAndMessage;
import com.isc.backstage.setting_enumeration.JwtSetting;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

/**
 * @Author: 711lxsky
 * @Date: 2023-12-20
 */
@Log4j2
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Resource
    private RequestUtil requestUtil;

    @Resource
    private JwtUtil jwtUtil;

    @Resource
    private ResponseUtil responseUtil;

    @Resource
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        // 静态资源放行
        if(Arrays.stream(CommonSecurityConfiguration.STATIC_RESOURCE_WHITE_LIST)
                .anyMatch(uri -> uri.equals(request.getServletPath()))) {
            filterChain.doFilter(request, response);
            return;
        }
        // 白名单放行
        if(Arrays.stream(JwtSetting.getWhiteList())
                .anyMatch(uri -> uri.equals(request.getServletPath()))) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            String accessToken = requestUtil.getTokenFromRequest(request, response);
            String accessTokenId = jwtUtil.getJwtIdFromToken(accessToken);

            // 判断黑名单
            if(jwtUtil.judgeAccessTokenInBlackList(accessTokenId)){
                responseUtil.responseError(response, CodeAndMessage.TOKEN_INVALID.getCode(), CodeAndMessage.TOKEN_INVALID.getZhDescription());
            }
            // 从AccessToken中获取user的必要信息
            UserVO userFromAccessToken = jwtUtil.parseAccessTokenToClass(accessToken, UserVO.class);
            if(Objects.isNull(userFromAccessToken)){
                responseUtil.responseError(response,
                        CodeAndMessage.UN_AUTHORIZATION.getCode(),
                        CodeAndMessage.UN_AUTHENTICATION.getZhDescription());
            }
            String useridStr = userFromAccessToken.getId().toString();
            if(StringUtils.hasText(useridStr) && Objects.isNull(SecurityContextHolder.getContext().getAuthentication())){
                if(! jwtUtil.judgeAccessTokenInRedis(useridStr, accessTokenId)){
                    responseUtil.responseError(response,
                            CodeAndMessage.ACCESS_TOKEN_EXPIRED.getCode(),
                            CodeAndMessage.ACCESS_TOKEN_EXPIRED.getZhDescription());
                }
                //从数据库加载获取user详细信息，包括权限等
                UserDetails userDetails = userDetailsService.loadUserByUsername(userFromAccessToken.getName());
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
            filterChain.doFilter(request, response);
        } catch (AuthenticationException | ServeErrorException exception){
            log.info(exception.toString()+exception.getHttpStatusCode()+ exception.getMessage()+exception.getCode());
            responseUtil.responseError(response, exception.getHttpStatusCode(), exception.getMessage());
        }
    }
}
