package com.isc.backend.mvc.service;

import com.isc.backend.mvc.entity.Volunteer;
import com.baomidou.mybatisplus.extension.service.IService;
import com.isc.backend.setting.Result;

import java.util.Map;

/**
 * <p>
 * 志愿者表 服务类
 * </p>
 *
 * @author 711lxsky
 * @since 2023-07-04
 */
public interface IVolunteerService extends IService<Volunteer> {

    Result<?> addVolunteer(Volunteer volunteer);

    Result<Map<String, Object>> loginVolunteer(Volunteer volunteer);

    Integer updateVolunteerActivityNum(Volunteer volunteer);
}
