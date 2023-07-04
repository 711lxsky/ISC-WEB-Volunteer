package com.isc.backend.mvc.service.impl;

import com.isc.backend.mvc.entity.Volunteer;
import com.isc.backend.mvc.mapper.VolunteerMapper;
import com.isc.backend.mvc.service.IVolunteerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 志愿者表 服务实现类
 * </p>
 *
 * @author 711lxsky
 * @since 2023-07-04
 */
@Service
public class VolunteerServiceImpl extends ServiceImpl<VolunteerMapper, Volunteer> implements IVolunteerService {

}
