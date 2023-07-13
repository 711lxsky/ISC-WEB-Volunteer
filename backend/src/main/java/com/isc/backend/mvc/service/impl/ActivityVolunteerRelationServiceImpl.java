package com.isc.backend.mvc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.isc.backend.mvc.entity.Util.JwtUtil;
import com.isc.backend.mvc.entity.Util.RequestUtil;
import com.isc.backend.mvc.entity.ActivityVolunteerRelation;
import com.isc.backend.mvc.entity.Volunteer;
import com.isc.backend.mvc.mapper.ActivityVolunteerRelationMapper;
import com.isc.backend.mvc.service.IActivityVolunteerRelationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 记录志愿者参与活动情况 服务实现类
 * </p>
 *
 * @author 711lxsky
 * @since 2023-07-04
 */
@Service
public class ActivityVolunteerRelationServiceImpl extends ServiceImpl<ActivityVolunteerRelationMapper, ActivityVolunteerRelation> implements IActivityVolunteerRelationService {

    @Resource
    private JwtUtil jwtUtil;

    @Resource
    private RequestUtil requestUtil;

    @Override
    public Boolean addRelation(Integer activityId ,Integer volunteerId) {
        return this.baseMapper.addRelation(activityId,volunteerId);
    }

    @Override
    public List<Integer> getActivityIdsOfVolunteer() {
        Volunteer tokenVolunteer = jwtUtil.parseToken(requestUtil.getToken(),Volunteer.class);
        return this.baseMapper.getActivityIds(tokenVolunteer.getId());
    }

    @Override
    public boolean deleteRelation(Integer activityId, Integer volunteerId) {
        LambdaQueryWrapper<ActivityVolunteerRelation>  wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ActivityVolunteerRelation::getActivityId,activityId)
                .eq(ActivityVolunteerRelation::getVolunteerId,volunteerId);
        return this.remove(wrapper);
    }

    @Override
    public List<Integer> getVolunteersForActivity(Integer activityId) {
        return this.baseMapper.getVolunteersForActivity(activityId);
    }
}
