package com.isc.backend.mvc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author 711lxsky
 * @since 2023-07-04
 */
public class Activity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer organizerId;

    private String name;

    private String theme;

    private LocalDateTime dataTime;

    private String location;

    /**
     * 所需最小的志愿者数量
     */
    private Integer volunteerMin;

    /**
     * 所需志愿者最大数
     */
    private Integer volunteerMax;

    private Integer volunteerCurrentNumber;

    /**
     * 活动状态
     */
    private Integer status;

    /**
     * 活动描述
     */
    private String description;

    private Integer deleted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrganizerId() {
        return organizerId;
    }

    public void setOrganizerId(Integer organizerId) {
        this.organizerId = organizerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public LocalDateTime getDataTime() {
        return dataTime;
    }

    public void setDataTime(LocalDateTime dataTime) {
        this.dataTime = dataTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getVolunteerMin() {
        return volunteerMin;
    }

    public void setVolunteerMin(Integer volunteerMin) {
        this.volunteerMin = volunteerMin;
    }

    public Integer getVolunteerMax() {
        return volunteerMax;
    }

    public void setVolunteerMax(Integer volunteerMax) {
        this.volunteerMax = volunteerMax;
    }

    public Integer getVolunteerCurrentNumber() {
        return volunteerCurrentNumber;
    }

    public void setVolunteerCurrentNumber(Integer volunteerCurrentNumber) {
        this.volunteerCurrentNumber = volunteerCurrentNumber;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "Activity{" +
            "id = " + id +
            ", organizerId = " + organizerId +
            ", name = " + name +
            ", theme = " + theme +
            ", dataTime = " + dataTime +
            ", location = " + location +
            ", volunteerMin = " + volunteerMin +
            ", volunteerMax = " + volunteerMax +
            ", volunteerCurrentNumber = " + volunteerCurrentNumber +
            ", status = " + status +
            ", description = " + description +
            ", deleted = " + deleted +
        "}";
    }
}
