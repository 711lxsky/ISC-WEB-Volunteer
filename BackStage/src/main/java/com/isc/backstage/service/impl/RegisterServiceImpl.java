package com.isc.backstage.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.isc.backstage.Exception.DataErrorException;
import com.isc.backstage.Exception.ServeErrorException;
import com.isc.backstage.domain.DTO.RegisterVolunteerDTO;
import com.isc.backstage.domain.Result;
import com.isc.backstage.entity.Account;
import com.isc.backstage.entity.User;
import com.isc.backstage.entity.UserRole;
import com.isc.backstage.mapper.UserMapper;
import com.isc.backstage.service.*;
import com.isc.backstage.setting_enumeration.*;
import com.isc.backstage.utils.SecurityUtil;
import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * @Author: 711lxsky
 * @Date: 2024-01-26
 */

@Log4j2
@Service
public class RegisterServiceImpl extends ServiceImpl<UserMapper, User> implements RegisterService {

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private RoleService roleService;

    @Resource
    private UserRoleService URService;

    @Resource
    private AccountService accountService;

    @Resource
    private SecurityUtil securityUtil;

    @Resource
    private UserService userService;


    @Override
    public Result<?> addVolunteer(RegisterVolunteerDTO volunteerDTO) throws DataErrorException, ServeErrorException {
        if(
//                先做一个空数据判断
                Objects.nonNull(volunteerDTO)
                        && StringUtils.hasText(volunteerDTO.getUsername())
                        && StringUtils.hasText(volunteerDTO.getMobile())
                        && StringUtils.hasText(volunteerDTO.getRegisterPassword())
                        && StringUtils.hasText(volunteerDTO.getConfirmPassword())
        ){
//            前后密码判断，可以增加密码强度校验
            if(! Objects.equals(volunteerDTO.getConfirmPassword(), volunteerDTO.getRegisterPassword())){
                throw new DataErrorException(ExceptionConstant.PasswordDifferent.getMessage_EN());
            }
//            电话号码有效性检验
            if(!PatternConstant.judgeTelephoneNumber(volunteerDTO.getMobile())){
                throw new DataErrorException(ExceptionConstant.TelephoneNumberInvalid.getMessage_EN());
            }
            // 先暂时把限制条件设置为只允许同电话号码、用户名只有一个
//            判重
            LambdaQueryWrapper<User> queryForRepeatUser = new LambdaQueryWrapper<>();
            queryForRepeatUser.eq(User::getName, volunteerDTO.getUsername())
                    .eq(User::getMobile, volunteerDTO.getMobile());
            if(! Objects.isNull(this.baseMapper.selectOne(queryForRepeatUser))){
                throw new DataErrorException(ExceptionConstant.UserRepeat.getMessage_EN());
            }
            User copyFromVolunteer = parseRegisterVolunteerToUser(volunteerDTO);
            // save的时候自增id已经同时更新到了逻辑层
            Long roleIdForVolunteer = roleService.getRoleIdForOneRole(RoleSetting.Volunteer.getRoleNameEN());
            if(Objects.isNull(roleIdForVolunteer)){
                throw new DataErrorException(ExceptionConstant.EnumDateError.getMessage_EN());
            }
            if(! (this.save(copyFromVolunteer)
                    && this.addUserAndRoleInfo(copyFromVolunteer.getId(), roleIdForVolunteer, copyFromVolunteer.getName())
                    && this.addAccountInfo(copyFromVolunteer.getId(), copyFromVolunteer.getMobile(), AccountSetting.CategoryForTelephoneNumber.getCode(), copyFromVolunteer.getName())
            )){
                throw new ServeErrorException(CodeAndMessage.MYSQL_ERROR.getDescription());
            }
            // 当前用户数据已经存储完整，将Details信息存入Redis方便后续直接查询
            userService.saveUserDetailsIntoRedisWithUserId(copyFromVolunteer.getId().toString());
            // 志愿者数据已经存储，返回成功码
            return Result.success(CodeAndMessage.SUCCESS.getCode(), CodeAndMessage.SUCCESS.getDescription());
        }
        log.info(volunteerDTO);
        throw new DataErrorException(ExceptionConstant.DateNull.getMessage_EN());
    }

    private boolean addUserAndRoleInfo(Long userId, Long roleId, String creator){
        UserRole newUserRoleInfo = new UserRole(userId, roleId, DateUtil.date(), creator);
        return URService.addUserAndRoleInfo(newUserRoleInfo);
    }

    private boolean addAccountInfo(Long userId, String account, Integer category, String creator){
        Account newAccountInfo = new Account(userId, account, category, DateUtil.date(), creator);
        return accountService.addAccountInfo(newAccountInfo);
    }

    private User parseRegisterVolunteerToUser(RegisterVolunteerDTO dto){
        User copyFromRegisterVolunteer = new User();
        copyFromRegisterVolunteer.setName(dto.getUsername());
        copyFromRegisterVolunteer.setMobile(dto.getMobile());
        // 简单加密，这里的逻辑是先拿一个随机字符串，作为原始盐值
        String userRawPwdSalt = RandomUtil.randomString(UserSetting.UserSaltLength.getCode());
        // 加密后作为存储盐值，
        String userPwdSalt = securityUtil.haveSimpleAESEncrypt(userRawPwdSalt);
        copyFromRegisterVolunteer.setSalt(userPwdSalt);
        // 原值则跟参数密码混合加密存储为数据库存储的密码
        String encodedPassword = securityUtil.addSaltIntoPassword(userRawPwdSalt, dto.getRegisterPassword());
        copyFromRegisterVolunteer.setPassword(passwordEncoder.encode(encodedPassword));
        copyFromRegisterVolunteer.setCreator(dto.getUsername());
        copyFromRegisterVolunteer.setCreated(DateUtil.date(System.currentTimeMillis()));
        return copyFromRegisterVolunteer;
    }
}
