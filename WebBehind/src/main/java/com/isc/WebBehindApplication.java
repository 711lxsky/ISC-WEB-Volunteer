package com.isc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.isc.*.mapper")
public class WebBehindApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebBehindApplication.class, args);
    }

}
