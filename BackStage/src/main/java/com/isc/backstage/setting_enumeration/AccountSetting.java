package com.isc.backstage.setting_enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: 711lxsky
 * @Date: 2024-01-26
 */
@AllArgsConstructor
@Getter
public enum AccountSetting {

    CategoryForTelephoneNumber(1, "TelephoneNumber","手机号码类账号");

    private final Integer Code;
    private final String Description_EN;
    private final String Description_ZH;
}
