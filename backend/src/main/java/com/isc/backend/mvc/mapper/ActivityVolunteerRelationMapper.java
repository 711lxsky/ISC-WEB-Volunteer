package com.isc.backend.mvc.mapper;

import com.isc.backend.mvc.entity.ActivityVolunteerRelation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 记录志愿者参与活动情况 Mapper 接口
 * </p>
 *
 * @author 711lxsky
 * @since 2023-07-04
 */
public interface ActivityVolunteerRelationMapper extends BaseMapper<ActivityVolunteerRelation> {

    Boolean addRelation(@Param("activityId") Integer activityId,@Param("VolunteerId") Integer volunteerId);

    List<Integer> getActivityIds(@Param("volunteerId") Integer volunteerId);
}
