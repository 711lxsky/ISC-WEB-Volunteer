package com.isc.backend.mvc.service.impl;

import com.isc.backend.mvc.entity.Activity;
import com.isc.backend.mvc.mapper.ActivityMapper;
import com.isc.backend.mvc.service.IActivityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
