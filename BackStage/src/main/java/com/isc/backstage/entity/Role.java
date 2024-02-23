package com.isc.backstage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 角色
 * @TableName role
 */
@TableName(value ="role")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role implements Serializable {
    /**
     * 角色ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 所属父级角色ID
     */
    @TableField(value = "parent_id")
    private Long parentId;

    /**
     * 角色唯一CODE代码
     */
    @TableField(value = "code")
    private String code;

    /**
     * 角色名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 角色介绍
     */
    @TableField(value = "intro")
    private String intro;

    /**
     * 创建时间
     */
    @TableField(value = "created")
    private Date created;

    /**
     * 创建人
     */
    @TableField(value = "creator")
    private String creator;

    /**
     * 修改时间
     */
    @TableField(value = "edited")
    private Date edited;

    /**
     * 修改人
     */
    @TableField(value = "editor")
    private String editor;

    /**
     * 逻辑删除:0=未删除,1=已删除
     */
    @TableField(value = "deleted")
    private Integer deleted;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}