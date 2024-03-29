package com.isc.backend.setting;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserSetting {
    RepeatNameMax(1,"用户名重复最大限制数"),
    RepeatPhoneMax(2,"用户电话重复最大限制数");

    private final Integer Num;
    private final String description;

}
