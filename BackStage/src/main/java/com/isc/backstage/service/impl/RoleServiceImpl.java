package com.isc.backstage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.isc.backstage.Exception.DataErrorException;
import com.isc.backstage.entity.Role;
import com.isc.backstage.mapper.RoleMapper;
import com.isc.backstage.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author zyy
* @description 针对表【role(角色)】的数据库操作Service实现
* @createDate 2023-12-16 16:43:57
*/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
    implements RoleService {



    @Override
    public List<Role> getRolesForUser(Long userid) {
        return this.baseMapper.getRolesForUserById(userid);
    }

    @Override
    public Long getRoleIdForOneRole(String roleName) throws DataErrorException {
        return this.baseMapper.getRoleIdForOneRoleName(roleName);


    }

    @Override
    public Role getRoleByUserId(Long id) {
        return this.baseMapper.getRoleByUserId(id);
    }
}




