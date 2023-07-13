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
    ParticipateActivityFail(2111,"志愿活动参与失败"),
    InfoParticipateActivitySuccess(2120,"查询处于召集状态中我参与的活动成功"),
    InfoParticipateActivityFail(2121,"查询处于召集状态中我参与的活动失败"),
    SecedeActivitySuccess(2130,"退出响应参加的活动成功"),
    SecedeActivityFail(2131,"退出响应参加的活动失败"),
    ProceedActivitySuccess(2140,"修改活动状态为进行中成功"),
    ProceedActivityFail(2141,"修改活动状态为进行中失败"),
    InfoProceedActivitySuccess(2150,"志愿者查询进行中活动成功"),
    InfoProceedActivityFail(2151,"志愿者查询进行中活动失败"),
    FinishActivitySuccess(2160,"修改活动状态为已完成成功"),
    FinishActivityFail(2161,"修改活动状态为已完成失败"),
    InfoFinishActivitySuccess(2170,"志愿者查询已完成活动成功"),
    InfoFinishActivityFail(2171,"志愿者查询已完成活动失败"),
    UpdateInfoSuccess(2180,"账号信息修改成功"),
    UpdateInfoFail(2181,"账号信息修改失败"),
    UpdateAvatarSuccess(2190,"修改头像成功"),
    UpdateAvatarFail(2191,"修改头像失败"),
    LogoutSuccess(2200,"账号退出登录成功"),
    LogoutFail(2201,"账号退出登录失败"),
    UpdatePasswordSuccess(2210,"修改密码成功"),
    UpdatePasswordFail(2211,"修改密码失败");


    private final Integer code;
    private final String description;
}
