package com.isc.backstage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.isc.backstage.domain.LoginUser;
import com.isc.backstage.entity.Permission;
import com.isc.backstage.entity.Role;
import com.isc.backstage.entity.User;
import com.isc.backstage.mapper.UserMapper;
import com.isc.backstage.service.PermissionService;
import com.isc.backstage.service.RoleService;
import com.isc.backstage.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
* @author zyy
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2023-12-16 16:43:57
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService, UserDetailsService {

    @Resource
    private RoleService roleService;

    @Resource
    private PermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.getUserByUsername(username);
        List<Role> roles = roleService.getRolesForUser(user.getId());
        List<Long> roleIds = new ArrayList<>();
        roles.forEach(i -> roleIds.add(i.getId()));
        List<Permission> permissions = permissionService.getPermissionsByRoleIds(roleIds);
        return new LoginUser(user, roles, permissions);
    }

    public User getUserByUsername(String username){
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getName,username);
        Optional<User> optionalUser = Optional.ofNullable(this.baseMapper.selectOne(wrapper));
        return optionalUser.orElseThrow(() -> new UsernameNotFoundException("抱歉，用户不存在"));
    }

}




