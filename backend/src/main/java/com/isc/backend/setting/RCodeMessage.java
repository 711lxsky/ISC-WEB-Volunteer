package com.isc.backend.setting;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RCodeMessage {

    AddSuccess(2000,"账号注册成功"),
    AddFail(2001,"注册失败");

    private final Integer code;
    private final String description;
}
