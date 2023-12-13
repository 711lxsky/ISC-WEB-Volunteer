package com.isc.backend.mvc.controller;

import com.isc.backend.mvc.entity.Activity;
import com.isc.backend.mvc.entity.ActivityVolunteerRelation;
import com.isc.backend.mvc.service.IActivityService;
import com.isc.backend.setting.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 711lxsky
 * @since 2023-07-04
 */
@RestController
@RequestMapping("/activity")
public class ActivityController {

    @Resource
    private IActivityService activityService;

    @ApiOperation("志愿活动申请接口")
    @PostMapping("/apply")
    public Result<?> applyActivity(@RequestBody Activity activity){
        return activityService.applyActivity(activity);
    }

    @ApiOperation("管理者对处于申请状态的志愿活动查询接口")
    @GetMapping("/regulator-info-apply")
    public Result<Map<String,Object>> infoApplyActivity(@RequestParam(value = "pageNum") Long pageNum,
                                                        @RequestParam(value = "pageSize") Long pageSize){
        return activityService.infoApplyActivity(pageNum,pageSize);
    }

    @ApiOperation("志愿活动驳回/申请不通过接口")
    @PutMapping("/reject")
    public Result<?> rejectActivity(@RequestBody Activity activity){
        return activityService.rejectActivity(activity);
    }

    @ApiOperation("志愿活动通过接口")
    @PutMapping("/pass")
    public Result<?> passActivity(@RequestBody Activity activity){
        return activityService.passActivity(activity);
    }

    @ApiOperation("组织者查询本人所有活动接口")
    @GetMapping("/organizer-info-all")
    public Result<List<Activity>> infoActivityAll(){
        return activityService.infoActivityAll();
    }

    @ApiOperation("组织者活动召集/发布接口")
    @PutMapping("/update-convene")
    public Result<?> updateConveneActivity(@RequestBody Activity activity){
        return activityService.updateConveneActivity(activity.getId());
    }

    @ApiOperation("取消活动接口")
    @PutMapping("/update-cancel")
    public Result<?> updateCancelActivity(@RequestBody Activity activity){
        return activityService.updateCancelActivity(activity);
    }

    @ApiOperation("根据活动主题查询召集中状态活动接口")
    @GetMapping("/info-convene-theme")
    public Result<Map<String,Object>> infoActivityByTheme(@RequestParam(value = "theme") String theme,
                                                          @RequestParam(value = "pageNum") Long pageNum,
                                                          @RequestParam(value = "pageSize") Long pageSize){
        return activityService.infoConveneActivityByTheme(theme,pageNum,pageSize);
    }

    @ApiOperation("志愿者参与活动接口")
    @PostMapping("/participate")
    public Result<?> participateActivity(@RequestBody Activity activity){
        return activityService.participateActivity(activity);
    }

    @ApiOperation("志愿者查询响应召集的所有活动接口")
    @GetMapping("/volunteer-info-participate")
    public Result<List<Activity>> infoParticipateActivity(){
        return activityService.infoParticipateActivity();
    }

    @ApiOperation("志愿者退出响应参加的活动接口")
    @DeleteMapping("/volunteer-secede")
    public Result<?> secedeParticipateActivity(@RequestBody ActivityVolunteerRelation relation){
        return activityService.secedeParticipateActivity(relation);
    }

    @ApiOperation("组织者将活动状态由召集中设置为进行中接口")
    @PutMapping("/proceed")
    public Result<?> proceedActivity(@RequestBody Activity activity){
        return activityService.proceedActivity(activity.getId());
    }

    @ApiOperation("志愿者查询自身进行中的活动接口")
    @GetMapping("/volunteer-info-proceed")
    public Result<List<Activity>> infoProceedActivityForVolunteer(){
        return activityService.infoProceedActivityForVolunteer();
    }

    @ApiOperation("组织者将活动状态由进行中修改为已完成接口")
    @PutMapping("/finish")
    public Result<?> finishActivity(@RequestBody Activity activity){
        return activityService.finishActivity(activity.getId());
    }

    @ApiOperation("志愿者查询自身参与的已完成活动接口")
    @GetMapping("/volunteer-info-finish")
    public Result<List<Activity>> infoFinishActivityForVolunteer(){
        return activityService.infoFinishActivityForVolunteer();
    }

}
