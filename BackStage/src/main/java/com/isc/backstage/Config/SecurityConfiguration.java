package com.isc.backstage.Config;

import com.isc.backstage.filter.JwtAuthenticationTokenFilter;
import com.isc.backstage.handler.LogoutHandler;
import com.isc.backstage.handler.LogoutSuccessHandler;
import com.isc.backstage.handler.RestAccessDeniedHandler;
import com.isc.backstage.handler.RestAuthenticationEntryPoint;
import com.isc.backstage.setting_enumeration.JwtSetting;
import com.isc.backstage.setting_enumeration.WebSetting;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @Author: 711lxsky
 * @Date: 2023-12-16
 */

// Security安全策略配置了类
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfiguration {

    @Resource
    private LogoutHandler logoutHandler;

    @Resource
    private LogoutSuccessHandler logoutSuccessHandler;

    @Resource
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Resource
    private RestAccessDeniedHandler restAccessDeniedHandler;

   @Resource
   private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Bean
    public DefaultSecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // 过滤请求
                .authorizeHttpRequests()
                // 静态资源放行
                .requestMatchers(CommonSecurityConfiguration.STATIC_RESOURCE_WHITE_LIST).permitAll()
                // 白名单接口放行
                .requestMatchers(JwtSetting.getWhiteList()).permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // 认证失败与授权失败处理类
                .exceptionHandling()
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                .accessDeniedHandler(restAccessDeniedHandler)
                .and()
                // 拦截过滤器
                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .logout().logoutUrl(WebSetting.getLogoutUrl())
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler(logoutSuccessHandler)
                .and()
                // 跨域
                .cors()
                .and()
                .headers().frameOptions().disable();
        return httpSecurity.build();

    }
}
