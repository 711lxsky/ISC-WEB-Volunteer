package com.isc.backend.mvc.controller;

import com.isc.backend.mvc.entity.Organizer;
import com.isc.backend.mvc.entity.Password;
import com.isc.backend.mvc.service.IOrganizerService;
import com.isc.backend.setting.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 * 组织者表 前端控制器
 * </p>
 *
 * @author 711lxsky
 * @since 2023-07-04
 */
@RestController
@RequestMapping("/organizer")
public class OrganizerController {

    @Resource
    private IOrganizerService organizerService;

    @ApiOperation("组织者注册接口")
    @PostMapping("/add")
    public Result<?> addOrganizer(@RequestBody Organizer organizer){
        return organizerService.addOrganizer(organizer);
    }


    @ApiOperation("组织者登录接口")
    @PostMapping("/login")
    public Result<Map<String,Object>> loginOrganizer(@RequestBody Organizer organizer){
        return organizerService.loginOrganizer(organizer);
    }

    @ApiOperation("组织者退出登录接口")
    @PostMapping("/logout")
    public Result<?> logoutOrganizer(){
        return organizerService.logoutOrganizer();
    }

    @ApiOperation("组织者修改个人基本信息接口")
    @PutMapping("/update-info")
    public Result<?> updateInfo(@RequestBody Organizer organizer){
        return organizerService.updateInfo(organizer);
    }

    @ApiOperation("组织者修改头像接口")
    @PutMapping("/update-avatar")
    public Result<?> updateAvatar(@RequestParam("avatar")MultipartFile avatar){
        return organizerService.updateAvatar(avatar);
    }

    @ApiOperation("组织者修改密码接口")
    @PutMapping("/update-password")
    public Result<?> updatePassword(@RequestBody Password password){
        return organizerService.updatePassword(password);
    }
}
