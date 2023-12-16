package com.isc.backend.mvc.controller;


import com.isc.backend.mvc.entity.User;
import com.isc.backend.mvc.service.LoginService;
import com.isc.backend.setting.Result;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private LoginService loginService;

    @RequestMapping("/hello")
    @PreAuthorize("hasAuthority('test')")
    public String hello(){
        return "hello test";
    }

    @PostMapping("/user/login")
    public Result<?> login(@RequestBody User user){
        return loginService.login(user);
    }

    @RequestMapping("/user/logout")
    public Result<?> logout(){
        return loginService.logout();
    }
}
