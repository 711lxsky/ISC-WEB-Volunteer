package com.isc.backend.setting;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Activity {
    VolunteerRate1(4,"志愿者等级1"),
    VolunteerRate2(8,"志愿者等级2"),
    VolunteerRate3(16,"志愿者等级3"),
    OrganizerClass1(2,"组织者等级1"),
    OrganizerClass2(4,"组织者等级2"),
    OrganizerClass3(8,"组织者等级3"),
    RegulatorRate1(1,"管理者等级1"),
    RegulatorRate2(2,"管理者等级2"),
    Canceled(0,"被取消"),
    Applying(1,"申请中"),
    Convening(2,"召集中"),
    Conducting(3,"进行中"),
    Finished(4,"已完成");

    private final Integer NUM;
    private final String description;

}
