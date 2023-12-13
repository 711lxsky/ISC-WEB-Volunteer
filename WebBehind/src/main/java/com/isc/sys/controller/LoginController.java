package com.isc.sys.controller;

import com.isc.common.viewObj.Result;
import com.isc.sys.entity.User;
import com.isc.sys.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Api(tags = {"登陆接口列表"})
@RestController
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private IUserService userService;

    @ApiOperation("用户登录接口")
    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody User user) {
        Map<String,Object>  data =  userService.login(user);
        if(! data.isEmpty()){
            return Result.success(data);
        }
        return Result.fail(20002,"用户名或密码错误");
    }

    @GetMapping("/info")
    public Result<Map<String,Object>> getUerInfo(@RequestParam("token") String token){
        // 根据token获取用户信息，存入了redis的那串数据
        Map<String,Object> data = userService.getUserInfo(token);
        if(! data.isEmpty()){
            return Result.success(data);
        }
        return Result.fail(20003,"登录信息无效，请重新登录");
    }
}
