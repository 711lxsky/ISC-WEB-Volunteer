package com.isc.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.isc.common.viewObj.Result;
import com.isc.sys.entity.User;
import com.isc.sys.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
        if(userService.logout(token)){
        return Result.success();
    }
        return Result.fail("退出失败");
    }

    @GetMapping("/list")
    public Result<Map<String,Object>> getUserList(@RequestParam(value = "username",required = false) String username,
                                                  @RequestParam(value = "phone",required = false) String phone,
                                                    @RequestParam(value = "pageNum") Long pageNum,
                                                    @RequestParam(value = "pageSize") Long pageSize){
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.hasLength(username),User::getUsername,username);
        wrapper.eq(StringUtils.hasLength(phone),User::getPhone,phone);
        Map<String,Object> data = userService.getUserList(wrapper,pageNum,pageSize);
        if(data .isEmpty()){
            return Result.fail(20002);
        }
        return Result.success(data);
    }

    @PostMapping("/adduser")
    public Result<?> addUser(@RequestBody User user){
        userService.save(user);
        return Result.success("新增用户成功");
    }

}
