package com.isc.backstage.setting_enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: 711lxsky
 * @Date: 2023-12-26
 */
@AllArgsConstructor
@Getter
public enum UserSetting {

    DisEnabled(1, "stop", "禁用状态"),
    Enabled(0, "ok", "正常"),
    UserSaltLength(8, "get a salt length for the user", "拿到一个用户加密盐值长度");

    private final Integer Code;

    private final String description;

    private final String zhDescription;
}
