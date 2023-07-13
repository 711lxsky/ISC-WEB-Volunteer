package com.isc.backend.mvc.controller;

import com.isc.backend.mvc.entity.Password;
import com.isc.backend.mvc.entity.Regulator;
import com.isc.backend.mvc.service.IRegulatorService;
import com.isc.backend.setting.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 711lxsky
 * @since 2023-07-04
 */
@Controller
@ResponseBody
@RequestMapping("/regulator")
public class RegulatorController {


    @Resource
    private IRegulatorService regulatorService;



    @ApiOperation("管理者注册接口")
    @PostMapping("/add")
    public Result<?> addRegulator(@RequestBody Regulator regulator){
        return regulatorService.addRegulator(regulator);
    }


    @ApiOperation("管理者登录接口")
    @PostMapping("/login")
    public Result<Map<String,Object>> loginRegulator(@RequestBody Regulator regulator){
        return regulatorService.loginRegulator(regulator);
    }

    @ApiOperation("管理者退出登录接口")
    @PostMapping("/logout")
    public Result<?> logoutRegulator(){
        return regulatorService.logoutRegulator();
    }

    @ApiOperation("管理者修改个人基本信息接口")
    @PutMapping("/update-info")
    public Result<?> updateInfo(@RequestBody Regulator regulator){
        return regulatorService.updateInfo(regulator);
    }

    @ApiOperation("管理者修改头像接口")
    @PutMapping("/update-avatar")
    public Result<?> updateAvatar(@RequestParam("avatar")MultipartFile avatar){
        return regulatorService.updateAvatar(avatar);
    }

    @ApiOperation("管理者修改密码接口")
    @PutMapping("/update-password")
    public Result<?> updatePassword(@RequestBody Password password){
        return regulatorService.updatePassword(password);
    }
}
