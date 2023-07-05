package com.isc.backend.mvc.mapper;

import com.isc.backend.mvc.entity.Volunteer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 志愿者表 Mapper 接口
 * </p>
 *
 * @author 711lxsky
 * @since 2023-07-04
 */
public interface VolunteerMapper extends BaseMapper<Volunteer> {

    Integer getVolunteerNumByName(String volunteerName);

    Integer getVolunteerNumByPhone(String volunteerPhone);
}
