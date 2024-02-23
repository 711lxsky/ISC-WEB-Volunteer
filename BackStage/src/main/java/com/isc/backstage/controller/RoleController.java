package com.isc.backstage.controller;

import com.isc.backstage.domain.DTO.RoleDTO;
import com.isc.backstage.domain.Result;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: 711lxsky
 * @Date: 2024-01-05
 */
@RestController
@RequestMapping("/role")
public class RoleController {
    
    @Operation(summary = "新增角色")
    @PostMapping("/add-role")
    public Result<?> addRole(RoleDTO dto){
        return null;
    }


}
