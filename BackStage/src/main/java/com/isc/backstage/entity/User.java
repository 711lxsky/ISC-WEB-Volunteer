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
 * 用户
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * 用户ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户状态:0=正常,1=禁用
     */
    @TableField(value = "state")
    private Integer state;

    /**
     * 姓名
     */
    @TableField(value = "name")
    private String name;

    /**
     * 头像图片地址
     */
    @TableField(value = "head_img_url")
    private String head_img_url;

    /**
     * 手机号码
     */
    @TableField(value = "mobile")
    private String mobile;

    /**
     * 密码加盐
     */
    @TableField(value = "salt")
    private String salt;

    /**
     * 登录密码
     */
    @TableField(value = "password")
    private String password;

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