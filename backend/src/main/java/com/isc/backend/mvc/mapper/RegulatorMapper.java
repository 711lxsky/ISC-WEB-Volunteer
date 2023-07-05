package com.isc.backend.mvc.mapper;

import com.isc.backend.mvc.entity.Regulator;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 711lxsky
 * @since 2023-07-04
 */
public interface RegulatorMapper extends BaseMapper<Regulator> {

    Integer getRegulatorNumByName(String regulatorName);
    Integer getRegulatorNumByPhone(String regulatorPhone);
}
