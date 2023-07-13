package com.isc.backend.mvc.service;

import com.isc.backend.mvc.entity.Organizer;
import com.baomidou.mybatisplus.extension.service.IService;
import com.isc.backend.mvc.entity.Password;
import com.isc.backend.setting.Result;
import org.springframework.web.multipart.MultipartFile;

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

    boolean addActivityNumOfOrganizer(Integer organizerId);

    boolean reduceActivityNumOfOrganizer(Integer organizerId);

    Result<?> logoutOrganizer();

    Result<?> updateInfo(Organizer organizer);

    Result<?> updateAvatar(MultipartFile avatar);

    Result<?> updatePassword(Password password);
}
