package com.isc.backend.mvc.service.impl;

import com.isc.backend.config.AvatarConfig;
import com.isc.backend.mvc.entity.Volunteer;
import com.isc.backend.mvc.mapper.VolunteerMapper;
import com.isc.backend.mvc.service.IVolunteerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.isc.backend.setting.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 志愿者表 服务实现类
 * </p>
 *
 * @author 711lxsky
 * @since 2023-07-04
 */
@Service
public class VolunteerServiceImpl extends ServiceImpl<VolunteerMapper, Volunteer> implements IVolunteerService {

    @Resource
    private PasswordEncoder passwordEncoder;

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
        volunteer.setAvatar(AvatarConfig.AvatarAccessPath()+Avatar.VolunteerAvatar.getAvatarName());
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
}
