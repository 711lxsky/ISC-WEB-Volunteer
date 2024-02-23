package com.isc.backstage.service;

import com.isc.backstage.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author zyy
* @description 针对表【role(角色)】的数据库操作Service
* @createDate 2023-12-16 16:43:57
*/
public interface RoleService extends IService<Role> {

    List<Role> getRolesForUser(Long userid);

    Long getRoleIdForOneRole(String roleName);

    Role getRoleByUserId(Long id);
}