package com.isc.backstage.domain.VO;

/*
  @Author: 711lxsky
 * @Date: 2023-12-16
 */

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@Schema(description = "Token视图类")
public class TokenVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "授权Token")
    private String AccessToken;

    @Schema(description = "刷新Token")
    private String RefreshToken;
}
