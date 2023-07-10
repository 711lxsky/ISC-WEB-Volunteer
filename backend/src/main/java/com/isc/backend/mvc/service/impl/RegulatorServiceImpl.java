package com.isc.backend.mvc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.isc.backend.Util.JwtUtil;
import com.isc.backend.config.AvatarConfig;
import com.isc.backend.mvc.entity.Regulator;
import com.isc.backend.mvc.mapper.RegulatorMapper;
import com.isc.backend.mvc.service.IRegulatorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.isc.backend.setting.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

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

    @Resource
    private JwtUtil jwtUtil;

    @Resource
    private AvatarConfig avatarConfig;

    @Override
    public Result<?> addRegulator(Regulator regulator) {
        if(this.baseMapper.getRegulatorNumByName(regulator.getName()).equals(UserSetting.RepeatNameMax.getNum())){
            return Result.fail((RCodeMessage.AddFail.getCode()),RCodeMessage.AddFail.getDescription()+":管理者名重复");
        }
        if(this.baseMapper.getRegulatorNumByPhone(regulator.getPhone()).equals(UserSetting.RepeatPhoneMax.getNum())){
            return Result.fail(RCodeMessage.AddFail.getCode(), RCodeMessage.AddFail.getDescription()+
                    ":同一个电话只能注册"+ UserSetting.RepeatPhoneMax.getNum()+"个管理者账号");
        }
        regulator.setPassword(passwordEncoder.encode(regulator.getPassword()));
        regulator.setRate(RegulatorSetting.RegulatorRate1.getCode());
        regulator.setAvatar(AvatarSetting.RegulatorAvatar.getAvatarName());
        if(this.save(regulator)){
            return Result.success(RCodeMessage.AddSuccess.getCode(), "管理者"+RCodeMessage.AddSuccess.getDescription());
        }
        else {
            return Result.fail(RCodeMessage.AddFail.getCode(), RCodeMessage.AddFail.getDescription()+":后台管理者数据新增错误");
        }
    }

    @Override
    public Result<Map<String, Object>> loginRegulator(Regulator regulator) {
        LambdaQueryWrapper<Regulator> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Regulator::getName,regulator.getName());
        Regulator loginRegulator = this.baseMapper.selectOne(wrapper);
        if(loginRegulator == null){
            return Result.fail(RCodeMessage.LoginFail.getCode(), RCodeMessage.LoginFail.getDescription()+":管理者名错误");
        }
        else if( passwordEncoder.matches(regulator.getPassword(), loginRegulator.getPassword())){
            loginRegulator.setPassword(null);
            String token = jwtUtil.createToken(loginRegulator);
            loginRegulator.setAvatar(avatarConfig.setAvatarAccessPath(loginRegulator.getAvatar()));
            Map<String,Object> data = new HashMap<>();
            data.put("id",loginRegulator.getId());
            data.put("name",loginRegulator.getName());
            data.put("phoneNumber",loginRegulator.getPhone());
            data.put("email",loginRegulator.getEmail());
            data.put("avatar",loginRegulator.getAvatar());
            data.put("rate",loginRegulator.getRate());
            data.put(jwtUtil.getTokenName(),token);
            return Result.success(RCodeMessage.LoginSuccess.getCode(), "管理者"+RCodeMessage.LoginSuccess.getDescription(),data);
        }
        else {
            return Result.fail(RCodeMessage.LoginFail.getCode(), RCodeMessage.LoginFail.getDescription()+":密码错误");
        }
    }
}
