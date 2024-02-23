package com.isc.backstage.mapper;

import com.isc.backstage.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author zyy
* @description 针对表【role(角色)】的数据库操作Mapper
* @createDate 2023-12-16 16:43:57
* @Entity generator.entity.Role
*/
@Mapper
@Repository
public interface RoleMapper extends BaseMapper<Role> {

    List<Role> getRolesForUserById(@Param("userid") Long userid);

    Long getRoleIdForOneRoleName(@Param("roleName") String roleName);

    Role getRoleByUserId(@Param("userId") Long userId);
}




