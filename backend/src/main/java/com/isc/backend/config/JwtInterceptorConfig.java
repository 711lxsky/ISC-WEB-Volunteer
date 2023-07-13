package com.isc.backend.config;

import com.isc.backend.mvc.entity.Util.JwtValidateInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class JwtInterceptorConfig implements WebMvcConfigurer {

    @Resource
    private JwtValidateInterceptor jwtValidateInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration registration = registry.addInterceptor(jwtValidateInterceptor);
        registration.addPathPatterns("/**")
                .excludePathPatterns(
                        "/avatar/**",
                        "/volunteer/add",
                        "/volunteer/login",
                        "/volunteer/logout",
                        "/organizer/add",
                        "/organizer/login",
                        "/organizer/logout",
                        "/regulator/add",
                        "/regulator/login",
                        "/regulator/logout"
                );
    }
}
