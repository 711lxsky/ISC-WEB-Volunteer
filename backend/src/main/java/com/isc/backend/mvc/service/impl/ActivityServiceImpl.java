package com.isc.backend.mvc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.isc.backend.Util.JwtUtil;
import com.isc.backend.mvc.entity.Activity;
import com.isc.backend.mvc.entity.Organizer;
import com.isc.backend.mvc.entity.Volunteer;
import com.isc.backend.mvc.mapper.ActivityMapper;
import com.isc.backend.mvc.service.IActivityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.isc.backend.setting.ActivitySetting;
import com.isc.backend.setting.RCodeMessage;
import com.isc.backend.setting.Result;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 711lxsky
 * @since 2023-07-04
 */
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements IActivityService {

    @Resource
    private JwtUtil jwtUtil;

    @Resource
    private ActivityVolunteerRelationServiceImpl activityVolunteerRelationService;

    @Resource
    private VolunteerServiceImpl volunteerService;

    @Override
    public Result<?> applyActivity(Activity activity) {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String token = request.getHeader(jwtUtil.getTokenName());
        Organizer tokenOrganizer = jwtUtil.parseToken(token, Organizer.class);
        if (this.baseMapper.getApplyActivityNumOfOrganizer(activity.getOrganizerId(),ActivitySetting.Applying.getCode()).equals(tokenOrganizer.getActivityMax())) {
            return Result.fail(RCodeMessage.ApplyActivityFail.getCode(), RCodeMessage.ApplyActivityFail.getDescription() + ":已达到组织者最大活动限制数");
        }

        if (this.baseMapper.getActivityNumByNameAndTheme(activity.getName(), activity.getTheme()).equals(ActivitySetting.RepeatNameAndThemeMax.getCode())) {
            return Result.fail(RCodeMessage.ApplyActivityFail.getCode(), RCodeMessage.ApplyActivityFail.getDescription() + ":当前主题下活动名重复,请修改活动名称或更换主题");
        }
        activity.setStatus(ActivitySetting.Applying.getCode());
        if (this.save(activity)) {
            return Result.success(RCodeMessage.ApplyActivitySuccess.getCode(), RCodeMessage.ApplyActivitySuccess.getDescription());
        } else {
            return Result.fail(RCodeMessage.ApplyActivityFail.getCode(), RCodeMessage.ApplyActivityFail.getDescription() + "后台志愿活动数据新增出错");
        }
    }

    @Override
    public Result<Map<String, Object>> infoApplyActivity(Long pageNum, Long pageSize) {
        LambdaQueryWrapper<Activity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Activity::getStatus, ActivitySetting.Applying.getCode());
        Page<Activity> pageData = new Page<>(pageNum, pageSize);
        this.page(pageData, wrapper);
        Map<String, Object> data = new HashMap<>();
        data.put("totalRecordNum", pageData.getTotal());
        data.put("applyingActivities", pageData.getRecords());
        return Result.success(RCodeMessage.InfoApplyActivitySuccess.getCode(), RCodeMessage.InfoApplyActivitySuccess.getDescription(), data);
    }

    @Override
    public Result<?> rejectActivity(Activity activity) {
        activity.setStatus(ActivitySetting.Rejected.getCode());
        UpdateWrapper<Activity> wrapper = new UpdateWrapper<>();
        wrapper.lambda().eq(Activity::getId, activity.getId());
        int updateDataNum = this.baseMapper.update(activity, wrapper);
        if (updateDataNum == 1) {
            return Result.success(RCodeMessage.RejectActivitySuccess.getCode(), RCodeMessage.RejectActivitySuccess.getDescription());
        } else {
            return Result.success(RCodeMessage.RejectActivityFail.getCode(), RCodeMessage.RejectActivityFail.getDescription() + ":后台数据异常");
        }
    }

    @Override
    public Result<?> passActivity(Activity activity) {
        activity.setStatus(ActivitySetting.Passed.getCode());
        int updateDataNum = this.baseMapper.updateById(activity);
        if (updateDataNum == 1) {
            return Result.success(RCodeMessage.PassActivitySuccess.getCode(), RCodeMessage.PassActivitySuccess.getDescription());
        } else {
            return Result.fail(RCodeMessage.PassActivityFail.getCode(), RCodeMessage.PassActivityFail.getDescription() + ":后台数据异常");
        }
    }

    @Override
    public Result<List<Activity>> infoActivityAll() {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String token = request.getHeader(jwtUtil.getTokenName());
        Organizer tokenOrganizer = jwtUtil.parseToken(token,Organizer.class);
        LambdaQueryWrapper<Activity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Activity::getOrganizerId,tokenOrganizer.getId());
        List<Activity> data = this.baseMapper.selectList(wrapper);
        return Result.success(RCodeMessage.InfoActivityAllSuccess.getCode(), RCodeMessage.InfoActivityAllSuccess.getDescription(),data);
    }

    @Override
    public Result<?> updateConveneActivity(Activity activity) {
        activity.setStatus(ActivitySetting.Convening.getCode());
        int updateDataNum = this.baseMapper.updateById(activity);
        if(updateDataNum == 1){
            return Result.success(RCodeMessage.ConveneActivitySuccess.getCode(), RCodeMessage.ConveneActivitySuccess.getDescription());
        }
        else {
            return Result.fail(RCodeMessage.ConveneActivityFail.getCode(), RCodeMessage.ConveneActivityFail.getDescription()+":数据异常");
        }
    }

    @Override
    public Result<?> updateCancelActivity(Integer id) {
        int cancelActivityNum = this.baseMapper.updateActivityStatusById(id,ActivitySetting.Canceled.getCode());
        if(cancelActivityNum == 1){
            return Result.success(RCodeMessage.CancelActivitySuccess.getCode(), RCodeMessage.CancelActivitySuccess.getDescription());
        }
        else {
            return Result.fail(RCodeMessage.CancelActivityFail.getCode(), RCodeMessage.CancelActivityFail.getDescription()+":数据异常");
        }
    }

    @Override
    public Result<Map<String, Object>> infoConveneActivityByTheme(String theme, Long pageNum, Long pageSize) {
        LambdaQueryWrapper<Activity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Activity::getTheme, theme);
        wrapper.eq(Activity::getStatus, ActivitySetting.Convening.getCode());
        Page<Activity> pageData = new Page<>(pageNum, pageSize);
        this.page(pageData, wrapper);
        Map<String, Object> data = new HashMap<>();
        data.put("theme", theme);
        data.put("totalRecordNum", pageData.getTotal());
        data.put("activities", pageData.getRecords());
        return Result.success(RCodeMessage.InfoActivityByThemeSuccess.getCode(), RCodeMessage.InfoActivityByThemeSuccess.getDescription(), data);
    }

    @Override
    public Result<?> participateActivity(Activity activity) {
        if(activity.getVolunteerCurrentNumber().equals(activity.getVolunteerMax())){
            return Result.fail(RCodeMessage.ParticipateActivityFail.getCode(), RCodeMessage.ParticipateActivityFail.getDescription()+":已达最大限制人数");
        }
        activity.setVolunteerCurrentNumber(activity.getVolunteerCurrentNumber()+1);
        int updateActivityNum = this.baseMapper.updateActivityVolunteerNum(activity.getId(),activity.getVolunteerCurrentNumber());
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String token = request.getHeader(jwtUtil.getTokenName());
        Volunteer tokenVolunteer = jwtUtil.parseToken(token,Volunteer.class);
        tokenVolunteer.setActivityCount(tokenVolunteer.getActivityCount()+1);
        int updateVolunteerNum = this.volunteerService.updateVolunteerActivityNum(tokenVolunteer);
        if(updateActivityNum != 1){
            return Result.fail(RCodeMessage.ParticipateActivityFail.getCode(), RCodeMessage.ParticipateActivityFail.getDescription()+":后台活动数据错误");
        }
        else if(updateVolunteerNum != 1){
            return Result.fail(RCodeMessage.ParticipateActivityFail.getCode(), RCodeMessage.ParticipateActivityFail.getDescription()+":后台志愿者数据错误");
        }
        else {
            if(activityVolunteerRelationService.addRelation(activity.getId(),tokenVolunteer.getId())){
                return Result.success(RCodeMessage.ParticipateActivitySuccess.getCode(), RCodeMessage.ParticipateActivitySuccess.getDescription());
            }
            else {
                return Result.fail(RCodeMessage.ParticipateActivityFail.getCode(), RCodeMessage.ParticipateActivityFail.getDescription()+":后台志愿者与活动关系数据错误");
            }
        }
    }
}
