package com.isc.backstage.domain.VO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * @Author: 711lxsky
 * @Date: 2023-12-21
 */
@Data
@Schema(description = "用户视图类")
@AllArgsConstructor
@NoArgsConstructor
public class UserVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "用户ID")
    private Long id;

    @Schema(description = "用户名")
    private String name;

    @Schema(description = "用户头像")
    private String headImgUrl;

    @Schema(description = "用户手机号")
    private String mobile;

}
