package com.isc.backstage.service;

import com.isc.backstage.entity.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author zyy
* @description 针对表【permission(权限)】的数据库操作Service
* @createDate 2023-12-16 16:43:57
*/
public interface PermissionService extends IService<Permission> {

    List<Permission> getPermissionsByRoleIds(List<Long> roleIds);

    List<Permission> getPermissionsByRoleId(Long roleId);
}
