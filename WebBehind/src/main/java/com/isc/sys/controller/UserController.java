package com.isc.sys.controller;

import com.isc.common.viewObj.Result;
import com.isc.sys.entity.User;
import com.isc.sys.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

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
public class UserController {
    @Autowired
    private IUserService userService;

    @GetMapping("all")
    public Result<List<User>> getAllUser(){
       List<User> list = userService.list();
       return Result.success(list,"查询");
    }

}
