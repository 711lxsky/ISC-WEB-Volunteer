package com.isc.backend.setting;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Volunteer {
    ScoreDefault(0,"初始得分"),
    NotFreeLongTime(0,"长时间忙碌中"),
    NotFreeShortTime(1,"短期没空"),
    FreeLongTime(2,"时间充裕"),
    FreeShortTime(3,"近期闲暇");

    private final Integer Code;
    private final String description;
}
