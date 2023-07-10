package com.isc.backend.setting;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RCodeMessage {

    AddSuccess(2000,"账号注册成功"),
    AddFail(2001,"注册失败"),
    LoginSuccess(2010,"登录成功"),
    LoginFail(2011,"登录失败"),
    JwtValidateFail(2021,"jwt校验失败"),
    ApplyActivitySuccess(2030,"志愿活动申请成功"),
    ApplyActivityFail(2031,"志愿活动申请失败"),
    InfoApplyActivitySuccess(2040,"查询申请中活动成功"),
    InfoApplyActivityFail(2041,"查询申请中活动失败"),
    RejectActivitySuccess(2050,"活动申请拒绝/驳回操作成功"),
    RejectActivityFail(2051,"活动申请拒绝/驳回操作失败"),
    PassActivitySuccess(2060,"活动通过操作成功"),
    PassActivityFail(2061,"活动通过操作失败"),
    InfoActivityByThemeSuccess(2070,"根据主题查询活动成功"),
    InfoActivityByThemeFail(2071,"根据主题查询活动失败"),
    InfoActivityAllSuccess(2080,"组织者查询自身相关所有活动成功"),
    InfoActivityAllFail(2081,"组织者查询自身相关所有活动失败"),
    ConveneActivitySuccess(2090,"志愿活动召集/发布成功"),
    ConveneActivityFail(2091,"志愿活动召集/发布失败"),
    CancelActivitySuccess(2100,"志愿活动取消成功"),
    CancelActivityFail(2101,"志愿活动取消失败"),
    ParticipateActivitySuccess(2110,"志愿活动参与成功"),
    ParticipateActivityFail(2111,"志愿活动参与失败");
    private final Integer code;
    private final String description;
}
