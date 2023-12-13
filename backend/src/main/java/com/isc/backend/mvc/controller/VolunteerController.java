package com.isc.backend.mvc.controller;

import com.isc.backend.mvc.entity.Password;
import com.isc.backend.mvc.entity.Volunteer;
import com.isc.backend.mvc.service.IVolunteerService;
import com.isc.backend.setting.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 * 志愿者表 前端控制器
 * </p>
 *
 * @author 711lxsky
 * @since 2023-07-04
 */
@Controller
@ResponseBody
@RequestMapping("/volunteer")
public class VolunteerController {

    @Resource
    private IVolunteerService volunteerService;

    @ApiOperation("志愿者用户注册接口")
    @PostMapping("/add")
    public Result<?> addVolunteer(@RequestBody Volunteer volunteer){
        return volunteerService.addVolunteer(volunteer);
    }

    @ApiOperation("志愿者登录接口")
    @PostMapping("/login")
    public Result<Map<String,Object>> loginVolunteer(@RequestBody Volunteer volunteer){
        return volunteerService.loginVolunteer(volunteer);
    }

    @ApiOperation("志愿者退出登录接口")
    @PostMapping("/logout")
    public Result<?> logoutVolunteer(){
        return volunteerService.logoutVolunteer();
    }

    // 这个接口不包括修改头像和密码
    @ApiOperation("志愿者修改个人基本信息接口")
    @PutMapping("/update-info")
    public Result<?> updateInfo(@RequestBody Volunteer volunteer){
        return volunteerService.updateInfo(volunteer);
    }

    @ApiOperation("志愿者修改头像接口")
    @PutMapping("/update-avatar")
    public Result<?> updateAvatar(@RequestParam("avatar")MultipartFile avatar){
        return volunteerService.updateAvatar(avatar);
    }

    @ApiOperation("志愿者修改密码接口")
    @PutMapping("/update-password")
    public Result<?> updatePassword(@RequestBody Password password){
        return volunteerService.updatePassword(password);
    }
}
