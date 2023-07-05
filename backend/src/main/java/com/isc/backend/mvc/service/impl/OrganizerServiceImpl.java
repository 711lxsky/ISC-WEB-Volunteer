package com.isc.backend.mvc.service.impl;

import com.isc.backend.config.AvatarConfig;
import com.isc.backend.mvc.entity.Organizer;
import com.isc.backend.mvc.mapper.OrganizerMapper;
import com.isc.backend.mvc.service.IOrganizerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.isc.backend.setting.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 组织者表 服务实现类
 * </p>
 *
 * @author 711lxsky
 * @since 2023-07-04
 */
@Service
public class OrganizerServiceImpl extends ServiceImpl<OrganizerMapper, Organizer> implements IOrganizerService {

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public Result<?> addOrganizer(Organizer organizer) {
        if(this.baseMapper.getOrganizerNumByName(organizer.getName()) == User.RepeatNumMax.getNum()){
            return Result.fail(RCodeMessage.AddFail.getCode(), RCodeMessage.AddFail.getDescription()+":组织者名重复");
        }
        if(this.baseMapper.getOrganizerNumByPhone(organizer.getPhone()) == User.RepeatPhoneMax.getNum()){
            return Result.fail(RCodeMessage.AddFail.getCode(), RCodeMessage.AddFail.getDescription()+
                    ":同一电话只能申请"+User.RepeatPhoneMax.getNum()+"个组织者账号");
        }
        organizer.setPassword(passwordEncoder.encode(organizer.getPassword()));
        organizer.setActivityMax(Activity.OrganizerClass1.getNUM());
        organizer.setAvatar(AvatarConfig.AvatarAccessPath()+ Avatar.OrganizerAvatar.getAvatarName());
        if(this.save(organizer)){
            return Result.success(RCodeMessage.AddSuccess.getCode(), "组织者"+RCodeMessage.AddSuccess.getDescription());
        }
        else {
            return Result.fail(RCodeMessage.AddFail.getCode(), RCodeMessage.AddFail.getDescription()+":后台组织者数据新增错误");
        }
    }
}
