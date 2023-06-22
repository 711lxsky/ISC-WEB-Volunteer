package com.isc.sys.controller;

import com.isc.common.viewObj.Result;
import com.isc.sys.entity.User;
import com.isc.sys.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author 711lxsky
 * @since 2023-04-08
 */
@RestController
@RequestMapping("/user")
// @CrossOrigin 处理跨域的注解标签
public class UserController {
    @Autowired
    private IUserService userService;

    @GetMapping("/all")
    public Result<List<User>> getAllUser(){
       List<User> list = userService.list();
       return Result.success(list,"查询");
    }

    @PostMapping("/logout")
    public Result<?> logout(@RequestHeader("X-Token") String token){
        if(userService.logout(token) == true){
        return Result.success();
    }
        return Result.fail("退出失败");
    }

}
