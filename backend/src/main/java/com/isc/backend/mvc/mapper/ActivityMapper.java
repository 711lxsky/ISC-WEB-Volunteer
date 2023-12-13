package com.isc.backend.mvc.mapper;

import com.isc.backend.mvc.entity.Activity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 711lxsky
 * @since 2023-07-04
 */
public interface ActivityMapper extends BaseMapper<Activity> {
    Integer getActivityNumByNameAndTheme(@Param("activityName") String activityName, @Param("activityTheme") String activityTheme);

    Integer getApplyActivityNumOfOrganizer(@Param("organizerId") Integer organizerId,@Param("activityStatus") Integer activityStatus);

    Integer updateActivityStatusById(@Param("activityId")Integer activityId,@Param("activityStatus")Integer activityStatus);

    Integer addActivityVolunteerNum(@Param("activityId") Integer activityId);

    Integer reduceActivityVolunteerNum(@Param("activityId") Integer activityId);
}
