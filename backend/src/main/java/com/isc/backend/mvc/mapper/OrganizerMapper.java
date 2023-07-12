package com.isc.backend.mvc.mapper;

import com.isc.backend.mvc.entity.Organizer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 组织者表 Mapper 接口
 * </p>
 *
 * @author 711lxsky
 * @since 2023-07-04
 */
public interface OrganizerMapper extends BaseMapper<Organizer> {

     Integer getOrganizerNumByName(String organizerName);

     Integer getOrganizerNumByPhone(String organizerPhone);

     Integer addActivityNum(@Param("organizerId") Integer organizerId);

     Integer reduceActivityNum(Integer organizerId);
}
