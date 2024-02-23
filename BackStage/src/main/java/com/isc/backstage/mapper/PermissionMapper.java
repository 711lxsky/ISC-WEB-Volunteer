package com.isc.backstage.mapper;

import com.isc.backstage.entity.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author zyy
* @description 针对表【permission(权限)】的数据库操作Mapper
* @createDate 2023-12-16 16:43:57
* @Entity generator.entity.Permission
*/
@Mapper
@Repository
public interface PermissionMapper extends BaseMapper<Permission> {

    List<Permission> getPermissionsByRoleIds(@Param("roleIds") List<Long> roleIds);

    List<Permission> getPermissionsByRoleId(@Param("roleId") Long roleId);
}




