package io.github.shenyaoguan.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class Controllers {
    @RequestMapping("/hello")
    public String hello(){
        return "hello world!";
    }
}
