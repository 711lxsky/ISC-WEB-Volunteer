package com.isc.backend.mvc.controller;

import com.isc.backend.mvc.entity.Activity;
import com.isc.backend.mvc.service.IActivityService;
import com.isc.backend.setting.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
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

    @ApiOperation("对处于申请状态的志愿活动查询接口")
    @GetMapping("/info-apply")
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

}
