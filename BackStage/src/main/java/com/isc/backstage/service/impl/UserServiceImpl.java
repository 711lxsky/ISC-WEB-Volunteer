package com.isc.backstage.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.isc.backstage.Exception.AuthenticationException;
import com.isc.backstage.Exception.DataErrorException;
import com.isc.backstage.Exception.ServeErrorException;
import com.isc.backstage.domain.LoginUser;
import com.isc.backstage.domain.Result;
import com.isc.backstage.domain.VO.UserVO;
import com.isc.backstage.entity.Permission;
import com.isc.backstage.entity.Role;
import com.isc.backstage.entity.User;
import com.isc.backstage.mapper.UserMapper;
import com.isc.backstage.service.PermissionService;
import com.isc.backstage.service.RoleService;
import com.isc.backstage.service.UserService;
import com.isc.backstage.setting_enumeration.CodeAndMessage;
import com.isc.backstage.setting_enumeration.ExceptionConstant;
import com.isc.backstage.setting_enumeration.FileSetting;
import com.isc.backstage.setting_enumeration.StringConstant;
import com.isc.backstage.utils.*;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
* @author zyy
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2023-12-16 16:43:57
*/
@Log4j2
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService, UserDetailsService {

    @Resource
    private RequestUtil requestUtil;

    @Resource
    private RoleService roleService;

    @Resource
    private PermissionService permissionService;

    @Resource
    private AliyunOSSUtil aliyunOSSUtil;

    @Resource
    OldAliyunOSSUtil oldAliyunOSSUtil;

    @Resource
    private JwtUtil jwtUtil;

    @Resource
    private RedisUtil redisUtil;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        // 全局使用此函数时记得改成userId为入参
        Long userid = Long.valueOf(userId);
        User user = this.baseMapper.selectById(userid);
        if(Objects.isNull(user)){
            throw new UsernameNotFoundException(ExceptionConstant.UserNotFound.getMessage_EN());
        }
        Role roleGetFromDataBase = roleService.getRoleByUserId(user.getId());
        if(Objects.isNull(roleGetFromDataBase)){
            throw new DataErrorException();
        }
        List<Permission> permissionList = permissionService.getPermissionsByRoleId(roleGetFromDataBase.getId());
        return new LoginUser(user, roleGetFromDataBase, permissionList);
    }

    public void saveUserDetailsIntoRedisWithUserId(String userId) throws UsernameNotFoundException{
        UserDetails userInfo = this.loadUserByUsername(userId);
        redisUtil.set(userId, userInfo);
        if(! redisUtil.exists(userId)){
            throw new ServeErrorException(ExceptionConstant.CannotSaveDateIntoRedis.getMessage_EN());
        }
    }

    public UserDetails getUserDetailsFromRedisWithUserId(String userId) {
        UserDetails userInfo = (UserDetails) redisUtil.get(userId);
        if(Objects.nonNull(userInfo)){
            return userInfo;
        }
        throw new ServeErrorException(ExceptionConstant.CannotGetDateFromRedis.getMessage_EN());
    }

    @Override
    public User getUserInfoById(Long userId) {
        return this.baseMapper.selectById(userId);
    }

    public User getUserByUsername(String username){
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getName,username);
        Optional<User> optionalUser = Optional.ofNullable(this.baseMapper.selectOne(wrapper));
        return optionalUser.orElseThrow(() -> new UsernameNotFoundException("抱歉，用户不存在"));
    }

    @Override
    public Result<?> updateUserAvatar(MultipartFile newAvatarFile) throws DataErrorException, AuthenticationException, ServeErrorException {
        if(Objects.isNull(newAvatarFile)){
            throw new DataErrorException(ExceptionConstant.DateNull.getMessage_EN());
        }
        HttpServletRequest request = requestUtil.getRequest();
        log.info("need requestId: {}",request.getRequestId());
        String accessToken = requestUtil.getTokenFromRequest(request);
        UserVO userFromAccessToken = jwtUtil.parseAccessTokenToClass(accessToken, UserVO.class);
        String userInfo = userFromAccessToken.getName() + StringConstant.LINE_SEPARATOR + userFromAccessToken.getId();
        String newAvatarURL = aliyunOSSUtil.uploadDateToOSSForFragment(userInfo, newAvatarFile, FileSetting.getAvatarFileType());
        log.info(oldAliyunOSSUtil.uploadUserAvatar(userInfo, newAvatarFile));
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda()
                .eq(User::getId, userFromAccessToken.getId())
                .set(User::getHeadImgUrl, newAvatarURL)
                .set(User::getEdited, DateUtil.date())
                .set(User::getEditor, userFromAccessToken.getName());
        if(this.update(updateWrapper)){
            return Result.success(CodeAndMessage.SUCCESS.getCode(), CodeAndMessage.SUCCESS.getDescription(), newAvatarURL);
        }
        throw new ServeErrorException(CodeAndMessage.MYSQL_ERROR.getDescription());
    }

}
