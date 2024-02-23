package com.isc.backstage.domain.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @Author: 711lxsky
 * @Date: 2023-12-21
 */
@AllArgsConstructor
@Data
@Schema(description = "Token数据类")
public class TokenDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "授权Token的ID")
    private String AccessTokenId;

    @Schema(description = "授权Token")
    private String AccessToken;

    @Schema(description = "刷新Token")
    private String RefreshToken;
}
