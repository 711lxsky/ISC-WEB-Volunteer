package com.isc.backend.mvc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.isc.backend.mvc.entity.Activity;
import com.isc.backend.mvc.mapper.ActivityMapper;
import com.isc.backend.mvc.service.IActivityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.isc.backend.setting.RCodeMessage;
import com.isc.backend.setting.Result;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 711lxsky
 * @since 2023-07-04
 */
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements IActivityService {

    @Override
    public Result<?> applyActivity(Activity activity) {
        if(this.baseMapper.getActivityNumByNameAndTheme(activity.getName(), activity.getTheme()).equals(com.isc.backend.setting.Activity.RepeatNameAndThemeMax.getCode())){
            return Result.fail(RCodeMessage.ApplyActivityFail.getCode(), RCodeMessage.ApplyActivityFail.getDescription()+":当前主题下活动名重复,请修改活动名称或更换主题");
        }
        activity.setStatus(com.isc.backend.setting.Activity.Applying.getCode());
        if(this.save(activity)){
            return Result.success(RCodeMessage.ApplyActivitySuccess.getCode(), RCodeMessage.ApplyActivitySuccess.getDescription());
        }
        else {
            return Result.fail(RCodeMessage.ApplyActivityFail.getCode(), RCodeMessage.ApplyActivityFail.getDescription()+"后台志愿活动数据新增出错");
        }
    }

    @Override
    public Result<Map<String, Object>> infoApplyActivity(Long pageNum, Long pageSize) {
        LambdaQueryWrapper<Activity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Activity::getStatus,com.isc.backend.setting.Activity.Applying.getCode());
        Page<Activity> pageData = new Page<>(pageNum,pageSize);
        this.page(pageData,wrapper);
        Map<String,Object> data = new HashMap<>();
        data.put("totalRecordNum",pageData.getTotal());
        data.put("applyingActivities",pageData.getRecords());
        return Result.success(RCodeMessage.InfoApplyActivitySuccess.getCode(), RCodeMessage.InfoApplyActivitySuccess.getDescription(),data);
    }

    @Override
    public Result<?> rejectActivity(Activity activity) {
        activity.setStatus(com.isc.backend.setting.Activity.Rejected.getCode());
        UpdateWrapper<Activity> wrapper = new UpdateWrapper<>();
        wrapper.lambda().eq(Activity::getId,activity.getId());
        int updateDataNum = this.baseMapper.update(activity,wrapper);
        if(updateDataNum == 1){
            return Result.success(RCodeMessage.RejectActivitySuccess.getCode(), RCodeMessage.RejectActivitySuccess.getDescription());
        }
        else {
            return Result.success(RCodeMessage.RejectActivityFail.getCode(), RCodeMessage.RejectActivityFail.getDescription()+":后台数据异常");
        }
    }

    @Override
    public Result<?> passActivity(Activity activity) {
        activity.setStatus(com.isc.backend.setting.Activity.Passed.getCode());
        int updateDataNum = this.baseMapper.updateById(activity);
        if(updateDataNum == 1){
            return Result.success(RCodeMessage.PassActivitySuccess.getCode(), RCodeMessage.PassActivitySuccess.getDescription());
        }
        else {
            return Result.fail(RCodeMessage.PassActivityFail.getCode(), RCodeMessage.PassActivityFail.getDescription()+":后台数据异常");
        }
    }
}
