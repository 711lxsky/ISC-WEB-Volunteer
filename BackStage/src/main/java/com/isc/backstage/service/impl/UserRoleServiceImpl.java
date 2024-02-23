package com.isc.backstage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.isc.backstage.entity.UserRole;
import com.isc.backstage.service.UserRoleService;
import com.isc.backstage.mapper.UserRoleMapper;
import org.springframework.stereotype.Service;

/**
* @author zyy
* @description 针对表【user_role(用户角色)】的数据库操作Service实现
* @createDate 2023-12-16 16:43:57
*/
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole>
    implements UserRoleService{

    @Override
    public boolean addUserAndRoleInfo(UserRole newUserRoleInfo) {
        return this.save(newUserRoleInfo);
    }
}




