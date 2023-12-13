package com.isc.backend.setting;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RegulatorSetting {
    RegulatorRate1(1,"管理者等级1"),
    RegulatorRate2(2,"管理者等级2");

    private final Integer Code;
    private final String description;

}
