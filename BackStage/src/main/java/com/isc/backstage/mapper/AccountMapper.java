package com.isc.backstage.mapper;

import com.isc.backstage.entity.Account;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
* @author zyy
* @description 针对表【account(账号)】的数据库操作Mapper
* @createDate 2023-12-16 16:43:57
* @Entity generator.entity.Account
*/
@Mapper
@Repository
public interface AccountMapper extends BaseMapper<Account> {

}




