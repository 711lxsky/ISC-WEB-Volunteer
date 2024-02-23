package com.isc.backstage.domain.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @Author: 711lxsky
 * @Date: 2023-12-16
 */
@Data
@Schema(description = "用户登录数据类")
public class LoginUserDTO implements Serializable {


    @Serial
    private static final long serialVersionUID = 1L;

//    按道理来讲是不会也不能出现一个账户多个用户的情况的，所以登录之后要从后台把用户角色类型查出来，而不是用户提交
//    @Schema(description = "登录用户角色类型", requiredMode = Schema.RequiredMode.REQUIRED)
//    private String loginRoleType;

    //这里可以考虑之后换成枚举形式的整数之类

    @Schema(description = "登录账户类型，类型英文字符串，首字母大写", requiredMode = Schema.RequiredMode.REQUIRED)
    private String loginAccountType;

    @Schema(description = "登录账号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String loginAccount;

    @Schema(description = "登录密码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

    // 这里之后可以考虑加入新的校验逻辑，比方说随即字符串等等
}
