package com.isc.backend.setting;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum VolunteerSetting {
    VolunteerRate1(4,"志愿者等级1"),
    VolunteerRate2(8,"志愿者等级2"),
    VolunteerRate3(16,"志愿者等级3"),
    ScoreDefault(0,"初始得分"),
    NotFreeLongTime(0,"长时间忙碌中"),
    NotFreeShortTime(1,"短期没空"),
    FreeLongTime(2,"时间充裕"),
    FreeShortTime(3,"近期闲暇");

    private final Integer Code;
    private final String description;
}
