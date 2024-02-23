package com.isc.backstage.domain.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: 711lxsky
 * @Date: 2024-01-21
 */
@AllArgsConstructor
@Data
@Schema(description = "角色数据类")
public class RoleDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "所属父角色Id，可以为本身")
    private Long parentId;

    @Schema(description = "角色唯一标识码")
    private String code;

    @Schema(description = "角色名称")
    private String name;

    @Schema(description = "角色介绍，描述")
    private String intro;

    @Schema(description = "角色创建时间")
    private Date created;

    @Schema(description = "角色创建者")
    private String creator;

    @Schema(description = "角色编辑时间")
    private Date edited;

    @Schema(description = "角色编辑者")
    private String editor;
}
