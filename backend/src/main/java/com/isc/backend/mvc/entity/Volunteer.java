package com/isc/backend.mvc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 志愿者表
 * </p>
 *
 * @author 711lxsky
 * @since 2023-07-04
 */
@ApiModel(value = "Volunteer对象", description = "志愿者表")
public class Volunteer implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private String password;

    private String phone;

    private String email;

    private String avatar;

    private Integer score;

    private Integer activityCount;

    @ApiModelProperty("限制最大参与活动数")
    private Integer activityMax;

    private Integer status;

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

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getActivityCount() {
        return activityCount;
    }

    public void setActivityCount(Integer activityCount) {
        this.activityCount = activityCount;
    }

    public Integer getActivityMax() {
        return activityMax;
    }

    public void setActivityMax(Integer activityMax) {
        this.activityMax = activityMax;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "Volunteer{" +
            "id = " + id +
            ", name = " + name +
            ", password = " + password +
            ", phone = " + phone +
            ", email = " + email +
            ", avatar = " + avatar +
            ", score = " + score +
            ", activityCount = " + activityCount +
            ", activityMax = " + activityMax +
            ", status = " + status +
            ", deleted = " + deleted +
        "}";
    }
}
