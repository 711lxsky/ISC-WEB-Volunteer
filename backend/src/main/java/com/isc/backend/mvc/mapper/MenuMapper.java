package com.isc.backend.mvc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.isc.backend.mvc.entity.Menu;

import java.util.List;

/**
 * @author lxsky711
 * @date 2023-12-15 11:53
 */
public interface MenuMapper extends BaseMapper<Menu> {

    List<String> selectPermsByUserId(Long id);
}
