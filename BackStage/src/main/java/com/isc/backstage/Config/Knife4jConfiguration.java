package com.isc.backstage.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Knife4jConfiguration {

    @Bean
    public OpenAPI getAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("doc-isc")
                        .description("knife4j文档")
                        .version("v1"));
    }

}