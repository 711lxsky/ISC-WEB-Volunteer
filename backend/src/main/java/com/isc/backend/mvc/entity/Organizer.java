package com.isc.backend.mvc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 组织者表
 * </p>
 *
 * @author 711lxsky
 * @since 2023-07-04
 */
public class Organizer implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private String password;

    private String phone;

    private String email;

    private String avatar;

    private Integer activityMax;

    private Integer deleted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getActivityMax() {
        return activityMax;
    }

    public void setActivityMax(Integer activityMax) {
        this.activityMax = activityMax;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "Organizer{" +
            "id = " + id +
            ", name = " + name +
            ", password = " + password +
            ", phone = " + phone +
            ", eamil = " + email +
            ", avatar = " + avatar +
            ", activityMax = " + activityMax +
            ", deleted = " + deleted +
        "}";
    }
}
