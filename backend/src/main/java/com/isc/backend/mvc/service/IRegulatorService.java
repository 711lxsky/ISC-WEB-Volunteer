package com.isc.backend.mvc.service;

import com.isc.backend.mvc.entity.Password;
import com.isc.backend.mvc.entity.Regulator;
import com.baomidou.mybatisplus.extension.service.IService;
import com.isc.backend.setting.Result;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 711lxsky
 * @since 2023-07-04
 */
public interface IRegulatorService extends IService<Regulator> {

    Result<?> addRegulator(Regulator regulator);

    Result<Map<String, Object>> loginRegulator(Regulator regulator);

    Result<?> logoutRegulator();

    Result<?> updateInfo(Regulator regulator);

    Result<?> updateAvatar(MultipartFile avatar);

    Result<?> updatePassword(Password password);

}
