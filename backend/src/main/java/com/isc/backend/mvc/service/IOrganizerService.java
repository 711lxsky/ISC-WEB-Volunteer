package com.isc.backend.mvc.service;

import com.isc.backend.mvc.entity.Organizer;
import com.baomidou.mybatisplus.extension.service.IService;
import com.isc.backend.setting.Result;

import java.util.Map;

/**
 * <p>
 * 组织者表 服务类
 * </p>
 *
 * @author 711lxsky
 * @since 2023-07-04
 */
public interface IOrganizerService extends IService<Organizer> {

    Result<?> addOrganizer(Organizer organizer);

    Result<Map<String, Object>> loginOrganizer(Organizer organizer);
}
