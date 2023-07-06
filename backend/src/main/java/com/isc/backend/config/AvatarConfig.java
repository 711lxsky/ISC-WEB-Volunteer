package com.isc.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AvatarConfig implements WebMvcConfigurer {
    @Value("${avatar-save-path}")
    private String avatarSavePath;

    @Value("${web-scheme}")
    private String scheme;

    @Value("${web-server-name}")
    private String webServer;

    @Value("${web-port}")
    private String webPort;

    @Value("${avatar-server-path}")
    private String avatarServer;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/avatar/**")
                .addResourceLocations("file:"+avatarSavePath);
    }

    public String setAvatarAccessPath(String avatarName){
        return this.scheme+"://"+this.webServer+":"+this.webPort+"/"+
                this.avatarServer+avatarName;
        //return "http://localhost:8888/avatar/";
    }
}
