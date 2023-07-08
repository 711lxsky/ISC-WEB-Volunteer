package com.isc.backend.mvc.service;

import com.isc.backend.mvc.entity.Activity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.isc.backend.setting.Result;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 711lxsky
 * @since 2023-07-04
 */
public interface IActivityService extends IService<Activity> {

    Result<?> applyActivity(Activity activity);

    Result<Map<String, Object>> infoApplyActivity(Long pageNum, Long pageSize);

    Result<?> rejectActivity(Activity activity);

    Result<?> passActivity(Activity activity);
}
