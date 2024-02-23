package com.isc.backstage.domain;

import com.isc.backstage.domain.VO.TokenVO;
import com.isc.backstage.domain.VO.UserVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * @Author: 711lxsky
 * @Date: 2024-01-29
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "在线用户信息类")
public class OnlineUserInformation implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "用户信息")
    private UserVO userInfo;

    @Schema(description = "Token数据信息")
    private TokenVO tokenInfo;
}
