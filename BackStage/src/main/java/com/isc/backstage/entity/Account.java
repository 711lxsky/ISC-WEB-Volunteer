package com.isc.backstage.entity;

import cn.hutool.core.date.DateTime;
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
 * 账号
 * @TableName account
 */
@TableName(value ="account")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Account implements Serializable {
    /**
     * 账号ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    @TableField(value = "user_id")
    private Long userId;

    /**
     * 登录账号,如手机号等
     */
    @TableField(value = "open_code")
    private String openCode;

    /**
     * 账号类别
     */
    @TableField(value = "category")
    private Integer category;

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

    public Account(Long userId, String account, Integer category, DateTime created, String creator) {
        this.userId = userId;
        this.openCode = account;
        this.category = category;
        this.created = created;
        this.creator = creator;
    }
}