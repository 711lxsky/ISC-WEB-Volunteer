package com.isc.backend.mvc.service.impl;

import com.isc.backend.mvc.entity.Regulator;
import com.isc.backend.mvc.mapper.RegulatorMapper;
import com.isc.backend.mvc.service.IRegulatorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.isc.backend.setting.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 711lxsky
 * @since 2023-07-04
 */
@Service
public class RegulatorServiceImpl extends ServiceImpl<RegulatorMapper, Regulator> implements IRegulatorService {

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public Result<?> addRegulator(Regulator regulator) {
        if(this.baseMapper.getRegulatorNumByName(regulator.getName()) == User.RepeatNumMax.getNum()){
            return Result.fail((RCodeMessage.AddFail.getCode()),RCodeMessage.AddFail.getDescription()+":管理者名重复");
        }
        if(this.baseMapper.getRegulatorNumByPhone(regulator.getPhone()) == User.RepeatPhoneMax.getNum()){
            return Result.fail(RCodeMessage.AddFail.getCode(), RCodeMessage.AddFail.getDescription()+
                    ":同一个电话只能注册"+User.RepeatPhoneMax.getNum()+"个管理者账号");
        }
        regulator.setPassword(passwordEncoder.encode(regulator.getPassword()));
        regulator.setRate(Activity.RegulatorRate1.getNUM());
        regulator.setAvatar(Avatar.RegulatorAvatar.getAvatarName());
        if(this.save(regulator)){
            return Result.success(RCodeMessage.AddSuccess.getCode(), "管理者"+RCodeMessage.AddSuccess.getDescription());
        }
        else {
            return Result.fail(RCodeMessage.AddFail.getCode(), RCodeMessage.AddFail.getDescription()+":后台管理者数据新增错误");
        }
    }
}
