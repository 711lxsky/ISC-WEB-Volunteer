package com.isc.backend.mvc.service;

import com.isc.backend.mvc.entity.ActivityVolunteerRelation;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 记录志愿者参与活动情况 服务类
 * </p>
 *
 * @author 711lxsky
 * @since 2023-07-04
 */
public interface IActivityVolunteerRelationService extends IService<ActivityVolunteerRelation> {

    Boolean addRelation(Integer volunteerId, Integer activityId);


}
