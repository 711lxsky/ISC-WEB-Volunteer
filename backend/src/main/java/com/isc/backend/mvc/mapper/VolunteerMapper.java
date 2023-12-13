package com.isc.backend.mvc.mapper;

import com.isc.backend.mvc.entity.Volunteer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 志愿者表 Mapper 接口
 * </p>
 *
 * @author 711lxsky
 * @since 2023-07-04
 */
public interface VolunteerMapper extends BaseMapper<Volunteer> {

    Integer getVolunteerNumByName(String volunteerName);

    Integer getVolunteerNumByPhone(String volunteerPhone);

    Integer updateActivityNumOfVolunteer(@Param("activityNum") Integer activityNum, @Param("volunteerId") Integer volunteerId);

    Integer reduceActivityNumOfVolunteer(@Param("volunteerId") Integer volunteerId);

    Integer updateScoreOfVolunteer(@Param("volunteerId") Integer volunteerId);

    Integer updateAvatarOfVolunteer(@Param("volunteerId") Integer volunteerId,@Param("avatar") String avatar);

    String getPassword(@Param("volunteerId")Integer volunteerId);

    Integer updatePassword(@Param("volunteerId") Integer volunteerId, @Param("password") String password);
}
