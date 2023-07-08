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
    PassActivityFail(2061,"活动通过操作失败");
    private final Integer code;
    private final String description;
}
