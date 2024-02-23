package com.isc.backstage.domain.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * @Author: 711lxsky
 * @Date: 2024-01-24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "注册志愿者数据类")
public class RegisterVolunteerDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 114514L;

    @Schema(description = "用户名", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;

    @Schema(description = "手机号码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String mobile;

    @Schema(description = "登陆密码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String registerPassword;

    @Schema(description = "确认密码，后端二次校验用", requiredMode = Schema.RequiredMode.REQUIRED)
    private String confirmPassword;
}
