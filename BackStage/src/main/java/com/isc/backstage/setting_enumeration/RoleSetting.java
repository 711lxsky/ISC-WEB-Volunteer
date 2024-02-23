package com.isc.backstage.setting_enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: 711lxsky
 * @Date: 2024-01-25
 */
@AllArgsConstructor
@Getter
public enum RoleSetting {

    Volunteer("volunteer", "志愿者");

    private final String RoleNameEN;

    private final String RoleNameZH;
}
