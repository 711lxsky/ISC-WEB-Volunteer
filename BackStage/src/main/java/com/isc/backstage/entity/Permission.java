package com.isc.backstage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 权限
 * @TableName permission
 */
@TableName(value ="permission")
@Data
public class Permission implements Serializable {
    /**
     * 权限ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 所属父级权限ID
     */
    @TableField(value = "parent_id")
    private Long parent_id;

    /**
     * 权限唯一CODE代码
     */
    @TableField(value = "code")
    private String code;

    /**
     * 权限名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 权限介绍
     */
    @TableField(value = "intro")
    private String intro;

    /**
     * 权限类别
     */
    @TableField(value = "category")
    private Integer category;

    /**
     * URL规则
     */
    @TableField(value = "uri")
    private Long uri;

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