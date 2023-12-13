package com.isc.sys.mapper;

import com.isc.sys.entity.Test;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 711lxsky
 * @since 2023-04-08
 */
@Mapper
@Repository
public interface TestMapper extends BaseMapper<Test> {

}
