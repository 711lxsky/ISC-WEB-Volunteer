package com.isc.backend.setting;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrganizerSetting {
    OrganizerClass1(2,"组织者等级1,对应活动限制数"),
    OrganizerClass2(4,"组织者等级2，对应活动限制数"),
    OrganizerClass3(8,"组织者等级3，对应活动限制数");

    private final Integer Code;
    private final String description;
}
