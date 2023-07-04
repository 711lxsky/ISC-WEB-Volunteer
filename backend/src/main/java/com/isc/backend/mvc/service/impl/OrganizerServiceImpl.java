package com.isc.backend.mvc.service.impl;

import com.isc.backend.mvc.entity.Organizer;
import com.isc.backend.mvc.mapper.OrganizerMapper;
import com.isc.backend.mvc.service.IOrganizerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 组织者表 服务实现类
 * </p>
 *
 * @author 711lxsky
 * @since 2023-07-04
 */
@Service
public class OrganizerServiceImpl extends ServiceImpl<OrganizerMapper, Organizer> implements IOrganizerService {

}
