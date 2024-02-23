package com.isc.backstage.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Author: 711lxsky
 * @Date: 2023-12-19
 * @Description: 对于安全配置的一般类，避免在Service层中重复引入，依赖循环
 */
@Configuration
public class CommonSecurityConfiguration {

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager getAuthenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }

    /**
     * 静态资源白名单
     */
    public static final String[] STATIC_RESOURCE_WHITE_LIST = {
            // swagger
            "/doc.html", "/webjars/**", "/v3/api-docs/**", "/favicon.*",
            "/swagger-resources/**", "/v2/api-docs/**", "/swagger-ui/**",

            // 静态资源文件
            "/", "/**.js", "/**.css", "/**.html", "/**.svg", "/**.pdf", "/**.jpg", "/**.png", "/**.gif",
            "/**.ico",
            // 排除字体格式后缀
            "/**.ttf", "/**.woff", "/**.woff2",
            // 排除Druid、H2
            "/druid/**", "/h2-console/**" };
}
