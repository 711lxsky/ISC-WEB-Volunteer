package com.isc.backstage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.isc.backstage.entity.RolePermission;
import com.isc.backstage.service.RolePermissionService;
import com.isc.backstage.mapper.RolePermissionMapper;
import org.springframework.stereotype.Service;

/**
* @author zyy
* @description 针对表【role_permission(角色权限)】的数据库操作Service实现
* @createDate 2023-12-16 16:43:57
*/
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission>
    implements RolePermissionService{

}




