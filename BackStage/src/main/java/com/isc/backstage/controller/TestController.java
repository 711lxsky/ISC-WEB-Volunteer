package com.isc.backstage.controller;

import com.isc.backstage.Exception.AuthenticationException;
import com.isc.backstage.Exception.DataErrorException;
import com.isc.backstage.Exception.ServeErrorException;
import com.isc.backstage.domain.DTO.LoginUserDTO;
import com.isc.backstage.domain.DTO.RegisterVolunteerDTO;
import com.isc.backstage.domain.Result;
import com.isc.backstage.service.LoginService;
import com.isc.backstage.service.RegisterService;
import com.isc.backstage.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Log4j2
@RestController
@RequestMapping("/test")
public class TestController {


    @Resource
    private LoginService loginService;

    @Resource
    private UserService userService;

    @Resource
    private RegisterService registerService;

    public String hello(){
        return "hello";
    }

    @Operation(summary = "登录")
    @PostMapping("/login")
    public Result<?> login(@Parameter(name = "loginUserDTO", description = "用户登录数据类")
            @RequestBody @Validated LoginUserDTO dto){
        try {
            return loginService.login(dto);
        }
        catch (DataErrorException | AuthenticationException | ServeErrorException e){
            log.info(e);
            return Result.fail(e);
        }
        catch (org.springframework.security.core.AuthenticationException e){
            log.info(e);
            return Result.fail(e);
        }
    }


    @Operation(summary = "志愿者注册")
    @PostMapping("/register")
    public Result<?> registerForVolunteer(@Parameter(name = "registerVolunteerDTO", description = "注册志愿者数据类", required = true)
                                              @RequestBody RegisterVolunteerDTO volunteerDTO){
        try {
            return registerService.addVolunteer(volunteerDTO);
        }catch (DataErrorException | ServeErrorException exception){
            log.info(exception);
            return Result.fail(exception);
        }
    }

    @Operation(summary = "修改用户头像")
    @PostMapping("/update-user-avatar")
    public Result<?> updateUserAvatar(@Parameter(name = "newAvatar", description = "新头像", required = true)
                                      @RequestBody MultipartFile newAvatarFile){
        try {
            return userService.updateUserAvatar(newAvatarFile);
        }catch (ServeErrorException | DataErrorException | AuthenticationException e){
            log.info(e);
            e.printStackTrace();
            return Result.fail(e);
        }
    }

    // 刷新AccessToken令牌策略：直接传一个RefreshToken进去，解析之后再从Redis取出过期的AccessToken,最后删除掉原AccessToken
    // 也可以据此调整Redis存储策略
//    public Result<?> refreshAccessTokenWithRefreshToken()


    @PostMapping("/add-permission")
    public Result<?> addPermission(){
        return null;
    }

}
