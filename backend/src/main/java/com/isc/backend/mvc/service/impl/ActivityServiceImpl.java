package com.isc.backend.mvc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.isc.backend.Util.JwtUtil;
import com.isc.backend.Util.RequestUtil;
import com.isc.backend.mvc.entity.Activity;
import com.isc.backend.mvc.entity.ActivityVolunteerRelation;
import com.isc.backend.mvc.entity.Organizer;
import com.isc.backend.mvc.entity.Volunteer;
import com.isc.backend.mvc.mapper.ActivityMapper;
import com.isc.backend.mvc.service.IActivityService;
import com.isc.backend.setting.ActivitySetting;
import com.isc.backend.setting.RCodeMessage;
import com.isc.backend.setting.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 711lxsky
 * @since 2023-07-04
 */
@Slf4j
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements IActivityService {

    @Resource
    private RequestUtil requestUtil;

    @Resource
    private JwtUtil jwtUtil;

    @Resource
    private ActivityVolunteerRelationServiceImpl activityVolunteerRelationService;

    @Resource
    private VolunteerServiceImpl volunteerService;

    @Resource
    private OrganizerServiceImpl organizerService;

    @Override
    public Result<?> applyActivity(Activity activity) {
        Organizer tokenOrganizer = jwtUtil.parseToken(requestUtil.getToken(), Organizer.class);
        if (this.baseMapper.getApplyActivityNumOfOrganizer(activity.getOrganizerId(),ActivitySetting.Applying.getCode()).equals(tokenOrganizer.getActivityMax())) {
            return Result.fail(RCodeMessage.ApplyActivityFail.getCode(), RCodeMessage.ApplyActivityFail.getDescription() + ":已达到组织者最大活动限制数");
        }

        if (this.baseMapper.getActivityNumByNameAndTheme(activity.getName(), activity.getTheme()).equals(ActivitySetting.RepeatNameAndThemeMax.getCode())) {
            return Result.fail(RCodeMessage.ApplyActivityFail.getCode(), RCodeMessage.ApplyActivityFail.getDescription() + ":当前主题下活动名重复,请修改活动名称或更换主题");
        }
        activity.setStatus(ActivitySetting.Applying.getCode());
        if (this.save(activity)) {
            if(this.organizerService.addActivityNumOfOrganizer(activity.getOrganizerId())){
                return Result.success(RCodeMessage.ApplyActivitySuccess.getCode(), RCodeMessage.ApplyActivitySuccess.getDescription());
            }
            else{
                return Result.fail(RCodeMessage.ApplyActivityFail.getCode(), RCodeMessage.AddFail.getDescription()+"后台组织者数据错误");
            }
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
            if(organizerService.reduceActivityNumOfOrganizer(activity.getOrganizerId())){
                return Result.success(RCodeMessage.RejectActivitySuccess.getCode(), RCodeMessage.RejectActivitySuccess.getDescription());
            }
            else {
                return Result.fail(RCodeMessage.RejectActivityFail.getCode(), RCodeMessage.RejectActivityFail.getDescription()+":组织者数据异常");
            }
        }
        return Result.fail(RCodeMessage.RejectActivityFail.getCode(), RCodeMessage.RejectActivityFail.getDescription() + ":后台数据异常");
    }

    @Override
    public Result<?> passActivity(Activity activity) {
        activity.setStatus(ActivitySetting.Passed.getCode());
        int updateDataNum = this.baseMapper.updateById(activity);
        if (updateDataNum == 1) {
            return Result.success(RCodeMessage.PassActivitySuccess.getCode(), RCodeMessage.PassActivitySuccess.getDescription());
        }
        return Result.fail(RCodeMessage.PassActivityFail.getCode(), RCodeMessage.PassActivityFail.getDescription() + ":后台数据异常");
    }

    @Override
    public Result<List<Activity>> infoActivityAll() {
        Organizer tokenOrganizer = jwtUtil.parseToken(requestUtil.getToken(),Organizer.class);
        LambdaQueryWrapper<Activity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Activity::getOrganizerId,tokenOrganizer.getId());
        List<Activity> data = this.baseMapper.selectList(wrapper);
        return Result.success(RCodeMessage.InfoActivityAllSuccess.getCode(), RCodeMessage.InfoActivityAllSuccess.getDescription(),data);
    }

    @Override
    public Result<?> updateConveneActivity(Integer activityId) {
        Activity activityGetFromDB = this.baseMapper.selectById(activityId);
        if(!activityGetFromDB.getStatus().equals(ActivitySetting.Passed.getCode())){
            return Result.fail(RCodeMessage.ConveneActivityFail.getCode(), RCodeMessage.ConveneActivityFail.getDescription()+":当前活动非通过状态");
        }
        int updateDataNum = this.baseMapper.updateActivityStatusById(activityId,ActivitySetting.Convening.getCode());
        if(updateDataNum == 1){
            return Result.success(RCodeMessage.ConveneActivitySuccess.getCode(), RCodeMessage.ConveneActivitySuccess.getDescription());
        }
        return Result.fail(RCodeMessage.ConveneActivityFail.getCode(), RCodeMessage.ConveneActivityFail.getDescription()+":数据异常");
    }

    @Override
    public Result<?> updateCancelActivity(Activity activity) {
        int cancelActivityNum = this.baseMapper.updateActivityStatusById(activity.getId(),ActivitySetting.Canceled.getCode());
        if(cancelActivityNum == 1){
            if(organizerService.reduceActivityNumOfOrganizer(activity.getOrganizerId())){
                return Result.success(RCodeMessage.CancelActivitySuccess.getCode(), RCodeMessage.CancelActivitySuccess.getDescription());
            }
            else {
                return Result.fail(RCodeMessage.CancelActivityFail.getCode(), RCodeMessage.CancelActivityFail.getDescription()+":组织者数据异常");
            }
        }
        return Result.fail(RCodeMessage.CancelActivityFail.getCode(), RCodeMessage.CancelActivityFail.getDescription()+":数据异常");
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
            return Result.fail(RCodeMessage.ParticipateActivityFail.getCode(), RCodeMessage.ParticipateActivityFail.getDescription()+":活动人员已满");
        }
        int updateActivityNum = this.baseMapper.addActivityVolunteerNum(activity.getId());
        if(updateActivityNum != 1){
            return Result.fail(RCodeMessage.ParticipateActivityFail.getCode(), RCodeMessage.ParticipateActivityFail.getDescription()+":后台活动数据错误");
        }

        Volunteer tokenVolunteer = jwtUtil.parseToken(requestUtil.getToken(),Volunteer.class);

        if(tokenVolunteer.getActivityCount().equals(tokenVolunteer.getActivityMax())){
            return Result.fail(RCodeMessage.ParticipateActivityFail.getCode(),RCodeMessage.ParticipateActivityFail.getDescription()+":参与活动数已达最大上限");
        }
        tokenVolunteer.setActivityCount(tokenVolunteer.getActivityCount()+1);
        int updateVolunteerNum = volunteerService.updateVolunteerActivityNum(tokenVolunteer);

        if(updateVolunteerNum != 1){
            return Result.fail(RCodeMessage.ParticipateActivityFail.getCode(), RCodeMessage.ParticipateActivityFail.getDescription()+":后台志愿者数据错误");
        }
        if(activityVolunteerRelationService.addRelation(activity.getId(),tokenVolunteer.getId())){
                return Result.success(RCodeMessage.ParticipateActivitySuccess.getCode(), RCodeMessage.ParticipateActivitySuccess.getDescription());
            }
        else {
                return Result.fail(RCodeMessage.ParticipateActivityFail.getCode(), RCodeMessage.ParticipateActivityFail.getDescription()+":后台志愿者与活动关系数据错误");
            }
    }

    @Override
    public Result<List<Activity>> infoParticipateActivity() {
        List<Integer> activityIds = activityVolunteerRelationService.getActivityIdsOfVolunteer();
        List<Activity> activities = new ArrayList<>();
        for(Integer id : activityIds){
            System.out.println(id);
            LambdaQueryWrapper<Activity> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Activity::getId,id)
                            .eq(Activity::getStatus,ActivitySetting.Convening.getCode());
            Activity activity = this.baseMapper.selectOne(wrapper);
            if(activity != null){
                activities.add(activity);
            }
        }
        return Result.success(RCodeMessage.InfoParticipateActivitySuccess.getCode(), RCodeMessage.InfoParticipateActivitySuccess.getDescription(),activities);
    }

    @Override
    public Result<?> secedeParticipateActivity(ActivityVolunteerRelation relation) {
        int updateActivityNum = this.baseMapper.reduceActivityVolunteerNum(relation.getActivityId());
        if(updateActivityNum != 1){
            return Result.fail(RCodeMessage.SecedeActivityFail.getCode(), RCodeMessage.SecedeActivityFail.getDescription()+":活动数据异常");
        }
        if(! this.volunteerService.reduceActivityNum(relation.getVolunteerId())){
            return Result.fail(RCodeMessage.SecedeActivityFail.getCode(), RCodeMessage.SecedeActivityFail.getDescription()+":志愿者数据异常");
        }
        if(! activityVolunteerRelationService.deleteRelation(relation.getActivityId(),relation.getVolunteerId())){
            return Result.fail(RCodeMessage.SecedeActivityFail.getCode(), RCodeMessage.SecedeActivityFail.getDescription()+":活动志愿者关系异常");
        }
        return Result.success(RCodeMessage.SecedeActivitySuccess.getCode(), RCodeMessage.SecedeActivitySuccess.getDescription());
    }

    @Override
    public Result<?> proceedActivity(Integer activityId) {
        Activity activityGetFromDB = this.baseMapper.selectById(activityId);
        if(!activityGetFromDB.getStatus().equals(ActivitySetting.Convening.getCode())){
            return Result.fail(RCodeMessage.ProceedActivityFail.getCode(), RCodeMessage.ProceedActivityFail.getDescription()+":当前活动状态非召集中");
        }
        if(activityGetFromDB.getVolunteerCurrentNumber() < (activityGetFromDB.getVolunteerMin())){
            return Result.fail(RCodeMessage.ProceedActivityFail.getCode(), RCodeMessage.ProceedActivityFail.getDescription()+":未达到最低目标人数");
        }
        if(this.baseMapper.updateActivityStatusById(activityId,ActivitySetting.Conducting.getCode()) != 1){
            return Result.fail(RCodeMessage.ProceedActivityFail.getCode(), RCodeMessage.ProceedActivityFail.getDescription()+":活动数据异常");
        }
        return Result.success(RCodeMessage.ProceedActivitySuccess.getCode(), RCodeMessage.ProceedActivitySuccess.getDescription());
    }

    @Override
    public Result<List<Activity>> infoProceedActivityForVolunteer() {
        List<Integer> activityIds = activityVolunteerRelationService.getActivityIdsOfVolunteer();
        List<Activity> activities = new ArrayList<>();
        for(Integer activityId : activityIds){
            LambdaQueryWrapper<Activity> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Activity::getId,activityId)
                    .eq(Activity::getStatus,ActivitySetting.Conducting.getCode());
            Activity activity = this.baseMapper.selectOne(wrapper);
            if(activity != null){
                activities.add(activity);
            }
        }
        return Result.success(RCodeMessage.InfoProceedActivitySuccess.getCode(), RCodeMessage.InfoProceedActivitySuccess.getDescription(),activities);
    }

    @Override
    public Result<?> finishActivity(Integer activityId) {
        Activity activity = this.baseMapper.selectById(activityId);
        if(!activity.getStatus().equals(ActivitySetting.Conducting.getCode())){
            return Result.fail(RCodeMessage.FinishActivityFail.getCode(), RCodeMessage.FinishActivityFail.getDescription()+":当前活动状态非进行中");
        }
        if(this.baseMapper.updateActivityStatusById(activityId,ActivitySetting.Finished.getCode()) != 1){
            return Result.fail(RCodeMessage.FinishActivityFail.getCode(), RCodeMessage.FinishActivityFail.getDescription()+":志愿活动数据异常");
        }
        List<Integer> volunteers = activityVolunteerRelationService.getVolunteersForActivity(activityId);
        if(volunteers == null){
            return Result.fail(RCodeMessage.FinishActivityFail.getCode(), RCodeMessage.FinishActivityFail.getDescription()+":活动相关志愿者数据异常");
        }
        if(! volunteerService.updateScoreOfVolunteers(volunteers)){
            return Result.fail(RCodeMessage.FinishActivityFail.getCode(), RCodeMessage.FinishActivityFail.getDescription()+":志愿者数据异常");
        }
        return Result.success(RCodeMessage.FinishActivitySuccess.getCode(), RCodeMessage.FinishActivitySuccess.getDescription());
    }

    @Override
    public Result<List<Activity>> infoFinishActivityForVolunteer() {
        List<Integer> activityIds = activityVolunteerRelationService.getActivityIdsOfVolunteer();
        List<Activity> activities = new ArrayList<>();
        for(Integer activityId : activityIds){
            LambdaQueryWrapper<Activity> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(Activity::getId,activityId)
                    .eq(Activity::getStatus,ActivitySetting.Finished.getCode());
            Activity activity = this.baseMapper.selectOne(wrapper);
            if(activity != null){
                activities.add(activity);
            }
        }
        return Result.success(RCodeMessage.InfoFinishActivitySuccess.getCode(), RCodeMessage.InfoFinishActivitySuccess.getDescription(),activities);
    }
}
