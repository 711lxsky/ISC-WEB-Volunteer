package com.isc.backend.mvc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.isc.backend.mvc.entity.Util.JwtUtil;
import com.isc.backend.mvc.entity.Util.RequestUtil;
import com.isc.backend.config.AvatarConfig;
import com.isc.backend.mvc.entity.Password;
import com.isc.backend.mvc.entity.Volunteer;
import com.isc.backend.mvc.mapper.VolunteerMapper;
import com.isc.backend.mvc.service.IVolunteerService;
import com.isc.backend.setting.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 * 志愿者表 服务实现类
 * </p>
 *
 * @author 711lxsky
 * @since 2023-07-04
 */
@Slf4j
@Service
public class VolunteerServiceImpl extends ServiceImpl<VolunteerMapper, Volunteer> implements IVolunteerService {

    @Resource
    private AvatarConfig avatarConfig;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private JwtUtil jwtUtil;

    @Resource
    private RequestUtil requestUtil;

    @Override
    public Result<?> addVolunteer(Volunteer volunteer) {
        if(this.baseMapper.getVolunteerNumByName(volunteer.getName()).equals(UserSetting.RepeatNameMax.getNum())){
            return Result.fail(RCodeMessage.AddFail.getCode(), RCodeMessage.AddFail.getDescription()+":志愿者名重复");
        }
        if(this.baseMapper.getVolunteerNumByPhone(volunteer.getPhone()).equals(UserSetting.RepeatPhoneMax.getNum())) {
            return Result.fail(RCodeMessage.AddFail.getCode(), RCodeMessage.AddFail.getDescription() +
                    ":同一电话只能申请"+ UserSetting.RepeatPhoneMax.getNum()+"个志愿者账号");
        }
        volunteer.setPassword(passwordEncoder.encode(volunteer.getPassword()));
        volunteer.setActivityMax(VolunteerSetting.VolunteerRate1.getCode());
        volunteer.setAvatar(AvatarSetting.VolunteerAvatar.getAvatarName());
        if(volunteer.getStatus() == null){
            volunteer.setStatus(VolunteerSetting.FreeShortTime.getCode());
        }
        if(this.save(volunteer)){
            return Result.success(RCodeMessage.AddSuccess.getCode(), "志愿者"+RCodeMessage.AddSuccess.getDescription());
        }
        else {
            return Result.fail(RCodeMessage.AddFail.getCode(), RCodeMessage.AddFail.getDescription()+":后台志愿者数据新增错误");
        }
    }

    @Override
    public Result<Map<String, Object>> loginVolunteer(Volunteer volunteer) {
        LambdaQueryWrapper<Volunteer> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Volunteer::getName,volunteer.getName());
        Volunteer loginVolunteer = this.baseMapper.selectOne(wrapper);
        if(loginVolunteer == null){
            return Result.fail(RCodeMessage.LoginFail.getCode(), RCodeMessage.LoginFail.getDescription()+":志愿者名错误");
        }
        else if(! passwordEncoder.matches(volunteer.getPassword(),loginVolunteer.getPassword())){
            return Result.fail(RCodeMessage.LoginFail.getCode(), RCodeMessage.LoginFail.getDescription()+":密码错误");
        }
        else {
            loginVolunteer.setPassword(null);
            String token = jwtUtil.createToken(loginVolunteer);
            loginVolunteer.setAvatar(avatarConfig.setAvatarAccessPath(loginVolunteer.getAvatar()));
            Map<String,Object> data = new HashMap<>();
            data.put("id",loginVolunteer.getId());
            data.put("name",loginVolunteer.getName());
            data.put("phoneNumber",loginVolunteer.getPhone());
            data.put("email",loginVolunteer.getEmail());
            data.put("avatar",loginVolunteer.getAvatar());
            data.put("score",loginVolunteer.getScore());
            data.put("activityCount",loginVolunteer.getActivityCount());
            data.put("activityRestrictMax",loginVolunteer.getActivityMax());
            data.put("status",loginVolunteer.getStatus());
            data.put(jwtUtil.getTokenName(),token);
            return Result.success(RCodeMessage.LoginSuccess.getCode(), "志愿者"+RCodeMessage.LoginSuccess.getDescription(),data);
        }
    }

    @Override
    public Integer updateVolunteerActivityNum(Volunteer volunteer) {
        return this.baseMapper.updateActivityNumOfVolunteer(volunteer.getActivityCount(),volunteer.getId());
    }

    @Override
    public boolean reduceActivityNum(Integer volunteerId) {
        int updateVolunteerNum = this.baseMapper.reduceActivityNumOfVolunteer(volunteerId);
        return updateVolunteerNum == 1;
    }

    @Override
    public boolean updateScoreOfVolunteers(List<Integer> volunteerIds) {
        for(Integer volunteerId : volunteerIds){
            if (this.baseMapper.updateScoreOfVolunteer(volunteerId) != 1){
                return false;
            }
        }
        return true;
    }

    @Override
    public Result<?> logoutVolunteer() {
        return Result.success(RCodeMessage.LogoutSuccess.getCode(), "志愿者"+RCodeMessage.LogoutSuccess.getDescription());
    }

    @Override
    public Result<?> updateInfo(Volunteer volunteer) {
        if(volunteer.getName() != null && this.baseMapper.getVolunteerNumByName(volunteer.getName()).equals(UserSetting.RepeatNameMax.getNum())){
            return Result.fail(RCodeMessage.UpdateInfoFail.getCode(), RCodeMessage.UpdateInfoFail.getDescription()+":志愿者名重复");
        }
        if(volunteer.getPhone() != null && this.baseMapper.getVolunteerNumByPhone(volunteer.getPhone()).equals(UserSetting.RepeatPhoneMax.getNum())){
            return Result.fail(RCodeMessage.UpdateInfoFail.getCode(), RCodeMessage.UpdateInfoFail.getDescription()+":同一电话只能绑定两个账户");
        }
        UpdateWrapper<Volunteer> wrapper = new UpdateWrapper<>();
        wrapper.lambda().eq(Volunteer::getId,volunteer.getId());
        int updateVolunteerNum = this.baseMapper.update(volunteer,wrapper);
        if(updateVolunteerNum != 1){
            return Result.fail(RCodeMessage.UpdateInfoFail.getCode(), RCodeMessage.UpdateInfoFail.getDescription()+":志愿者数据异常");
        }
        return Result.success(RCodeMessage.UpdateInfoSuccess.getCode(), ""+RCodeMessage.UpdateInfoSuccess.getDescription());
    }

    @Override
    public Result<?> updateAvatar(MultipartFile avatar) {
        String token = requestUtil.getToken();
        Volunteer tokenVolunteer = jwtUtil.parseToken(token,Volunteer.class);
        String avatarNewName = UUID.randomUUID()+avatarConfig.getAvatarSuffix();
        File newAvatar = new File(avatarConfig.getAvatarSavePath()+avatarNewName);
        try {
            avatar.transferTo(newAvatar);
            if(this.baseMapper.updateAvatarOfVolunteer(tokenVolunteer.getId(),avatarNewName) != 1){
                return Result.fail(RCodeMessage.UpdateAvatarFail.getCode(), RCodeMessage.UpdateAvatarFail.getDescription()+":志愿者数据异常");
            }
            return Result.success(RCodeMessage.UpdateAvatarSuccess.getCode(), "志愿者"+RCodeMessage.UpdateAvatarSuccess.getDescription());
        }
        catch (IOException e){
            log.info("文件写入磁盘错误");
            return Result.fail(RCodeMessage.UpdateAvatarFail.getCode(), RCodeMessage.UpdateAvatarFail.getDescription()+"文件写入错误");
        }
    }

    @Override
    public Result<?> updatePassword(Password password) {
        String token = requestUtil.getToken();
        Volunteer tokenVolunteer = jwtUtil.parseToken(token,Volunteer.class);
        if(! passwordEncoder.matches(password.getOldPassword(),this.baseMapper.getPassword(tokenVolunteer.getId()))){
            return Result.fail(RCodeMessage.UpdatePasswordFail.getCode(),RCodeMessage.UpdatePasswordFail.getDescription()+":旧密码错误,身份校验未通过");
        }
        if(this.baseMapper.updatePassword(tokenVolunteer.getId(),passwordEncoder.encode(password.getNewPassword())) != 1){
            return Result.fail(RCodeMessage.UpdatePasswordFail.getCode(), RCodeMessage.UpdatePasswordFail.getDescription()+":志愿者数据异常");
        }
        return Result.success(RCodeMessage.UpdatePasswordSuccess.getCode(), RCodeMessage.UpdatePasswordSuccess.getDescription());
    }
}
