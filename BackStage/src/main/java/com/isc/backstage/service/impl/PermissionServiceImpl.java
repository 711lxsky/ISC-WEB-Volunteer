package com.isc.backstage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.isc.backstage.entity.Permission;
import com.isc.backstage.service.PermissionService;
import com.isc.backstage.mapper.PermissionMapper;
import org.springframework.stereotype.Service;

/**
* @author zyy
* @description 针对表【permission(权限)】的数据库操作Service实现
* @createDate 2023-12-16 16:43:57
*/
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission>
    implements PermissionService{

}




