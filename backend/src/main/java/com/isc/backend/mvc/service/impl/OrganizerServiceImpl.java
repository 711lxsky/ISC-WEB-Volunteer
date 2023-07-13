package com.isc.backend.mvc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.isc.backend.mvc.entity.Password;
import com.isc.backend.mvc.entity.Util.JwtUtil;
import com.isc.backend.mvc.entity.Util.RequestUtil;
import com.isc.backend.config.AvatarConfig;
import com.isc.backend.mvc.entity.Organizer;
import com.isc.backend.mvc.mapper.OrganizerMapper;
import com.isc.backend.mvc.service.IOrganizerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.isc.backend.setting.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
    private RequestUtil requestUtil;

    @Resource
    private JwtUtil jwtUtil;

    @Resource
    private AvatarConfig avatarConfig;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public Result<?> addOrganizer(Organizer organizer) {
        if(this.baseMapper.getOrganizerNumByName(organizer.getName()).equals(UserSetting.RepeatNameMax.getNum())){
            return Result.fail(RCodeMessage.AddFail.getCode(), RCodeMessage.AddFail.getDescription()+":组织者名重复");
        }
        if(this.baseMapper.getOrganizerNumByPhone(organizer.getPhone()).equals(UserSetting.RepeatPhoneMax.getNum())){
            return Result.fail(RCodeMessage.AddFail.getCode(), RCodeMessage.AddFail.getDescription()+
                    ":同一电话只能申请"+ UserSetting.RepeatPhoneMax.getNum()+"个组织者账号");
        }
        organizer.setPassword(passwordEncoder.encode(organizer.getPassword()));
        organizer.setActivityMax(OrganizerSetting.OrganizerClass1.getCode());
        organizer.setAvatar(AvatarSetting.OrganizerAvatar.getAvatarName());
        if(this.save(organizer)){
            return Result.success(RCodeMessage.AddSuccess.getCode(), "组织者"+RCodeMessage.AddSuccess.getDescription());
        }
        else {
            return Result.fail(RCodeMessage.AddFail.getCode(), RCodeMessage.AddFail.getDescription()+":后台组织者数据新增错误");
        }
    }

    @Override
    public Result<Map<String, Object>> loginOrganizer(Organizer organizer) {
        LambdaQueryWrapper<Organizer> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Organizer::getName,organizer.getName());
        Organizer loginOrganizer = this.baseMapper.selectOne(wrapper);
        if(loginOrganizer == null){
            return Result.fail(RCodeMessage.LoginFail.getCode(), RCodeMessage.LoginFail.getDescription()+":组织者名错误");
        }
        else if(! passwordEncoder.matches(organizer.getPassword(),loginOrganizer.getPassword())){
            return Result.fail(RCodeMessage.LoginFail.getCode(), RCodeMessage.LoginFail.getDescription()+":密码错误");
        }
        else {
            loginOrganizer.setPassword(null);
            String token = jwtUtil.createToken(loginOrganizer);
            loginOrganizer.setAvatar(avatarConfig.setAvatarAccessPath(loginOrganizer.getAvatar()));
            Map<String,Object> data = new HashMap<>();
            data.put("id",loginOrganizer.getId());
            data.put("name",loginOrganizer.getName());
            data.put("phoneNumber",loginOrganizer.getPhone());
            data.put("email",loginOrganizer.getEmail());
            data.put("avatar",loginOrganizer.getAvatar());
            data.put("activityOrganizeMax",loginOrganizer.getActivityMax());
            data.put(jwtUtil.getTokenName(),token);
            return Result.success(RCodeMessage.LoginSuccess.getCode(), "组织者"+RCodeMessage.LoginSuccess.getDescription(),data);
        }
    }


    @Override
    public boolean addActivityNumOfOrganizer(Integer organizerId) {
        int updateDataNum = this.baseMapper.addActivityNum(organizerId);
        return updateDataNum == 1;
    }

    @Override
    public boolean reduceActivityNumOfOrganizer(Integer organizerId) {
        int updateDataNum = this.baseMapper.reduceActivityNum(organizerId);
        return updateDataNum == 1;
    }

    @Override
    public Result<?> logoutOrganizer() {
        return Result.success(RCodeMessage.LogoutSuccess.getCode(), "组织者"+RCodeMessage.LogoutSuccess.getDescription());
    }

    @Override
    public Result<?> updateInfo(Organizer organizer) {
        if(organizer.getName() != null && this.baseMapper.getOrganizerNumByName(organizer.getName()).equals(UserSetting.RepeatNameMax.getNum())){
            return Result.fail(RCodeMessage.UpdateInfoFail.getCode(), RCodeMessage.UpdateInfoFail.getDescription()+"：组织者名重复");
        }
        if(organizer.getPhone() != null && this.baseMapper.getOrganizerNumByPhone(organizer.getPhone()).equals(UserSetting.RepeatPhoneMax.getNum())){
            return Result.fail(RCodeMessage.UpdateInfoFail.getCode(), RCodeMessage.UpdateInfoFail.getDescription()+":电话号码已达绑定账户上限");
        }
        UpdateWrapper<Organizer> wrapper = new UpdateWrapper<>();
        wrapper.lambda().eq(Organizer::getId,organizer.getId());
        if(this.baseMapper.update(organizer,wrapper) != 1){
            return Result.fail(RCodeMessage.UpdateInfoFail.getCode(), RCodeMessage.UpdateInfoFail.getDescription()+":组织者数据异常");
        }
        return Result.success(RCodeMessage.UpdateInfoSuccess.getCode(), "组织者"+RCodeMessage.UpdateInfoSuccess.getDescription());
    }

    @Override
    public Result<?> updateAvatar(MultipartFile avatar) {
        String token = requestUtil.getToken();
        Organizer tokenOrganizer = jwtUtil.parseToken(token,Organizer.class);
        String avatarNewName = UUID.randomUUID() + avatarConfig.getAvatarSuffix();
        File avatarNew = new File(avatarConfig.getAvatarSavePath()+avatarNewName);
        try {
            avatar.transferTo(avatarNew);
            if(this.baseMapper.updateAvatar(tokenOrganizer.getId(),avatarNewName) != 1){
                return Result.fail(RCodeMessage.UpdateAvatarFail.getCode(), RCodeMessage.UpdateAvatarFail.getDescription()+":组织者数据错误");
            }
            return Result.success(RCodeMessage.UpdateAvatarSuccess.getCode(), RCodeMessage.UpdateAvatarSuccess.getDescription());
        }
        catch (IOException ioException){
            log.debug("头像写入目标磁盘错误");
            return Result.fail(RCodeMessage.UpdateAvatarFail.getCode(), RCodeMessage.UpdateAvatarFail.getDescription()+":文件写入错误");
        }
    }

    @Override
    public Result<?> updatePassword(Password password) {
        String token = requestUtil.getToken();
        Organizer tokenOrganizer = jwtUtil.parseToken(token, Organizer.class);
        if(! passwordEncoder.matches(password.getOldPassword(),this.baseMapper.getPassword(tokenOrganizer.getId()))){
            return Result.fail(RCodeMessage.UpdatePasswordFail.getCode(),RCodeMessage.UpdatePasswordFail.getDescription()+":旧密码错误,身份校验未通过");
        }
        if(this.baseMapper.updatePassword(tokenOrganizer.getId(),passwordEncoder.encode(password.getNewPassword())) != 1){
            return Result.fail(RCodeMessage.UpdatePasswordFail.getCode(), RCodeMessage.UpdatePasswordFail.getDescription()+":组织者数据异常");
        }
        return Result.success(RCodeMessage.UpdatePasswordSuccess.getCode(), RCodeMessage.UpdatePasswordSuccess.getDescription());
    }
}
