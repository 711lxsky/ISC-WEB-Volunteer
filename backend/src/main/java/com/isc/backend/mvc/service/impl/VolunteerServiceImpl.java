package com.isc.backend.mvc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.isc.backend.Util.JwtUtil;
import com.isc.backend.config.AvatarConfig;
import com.isc.backend.mvc.entity.Volunteer;
import com.isc.backend.mvc.mapper.VolunteerMapper;
import com.isc.backend.mvc.service.IVolunteerService;
import com.isc.backend.setting.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

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

    @Override
    public Result<?> addVolunteer(Volunteer volunteer) {
        if(this.baseMapper.getVolunteerNumByName(volunteer.getName()) == User.RepeatNumMax.getNum()){
            return Result.fail(RCodeMessage.AddFail.getCode(), RCodeMessage.AddFail.getDescription()+":志愿者名重复");
        }
        if(this.baseMapper.getVolunteerNumByPhone(volunteer.getPhone()) == User.RepeatPhoneMax.getNum()) {
            return Result.fail(RCodeMessage.AddFail.getCode(), RCodeMessage.AddFail.getDescription() +
                    ":同一电话只能申请"+User.RepeatPhoneMax.getNum()+"个志愿者账号");
        }
        volunteer.setPassword(passwordEncoder.encode(volunteer.getPassword()));
        volunteer.setActivityMax(Activity.VolunteerRate1.getNUM());
        volunteer.setAvatar(Avatar.VolunteerAvatar.getAvatarName());
        if(volunteer.getStatus() == null){
            volunteer.setStatus(com.isc.backend.setting.Volunteer.FreeShortTime.getCode());
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
}
