package com.isc.backstage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.isc.backstage.entity.Account;
import com.isc.backstage.service.AccountService;
import com.isc.backstage.mapper.AccountMapper;
import org.springframework.stereotype.Service;

/**
* @author zyy
* @description 针对表【account(账号)】的数据库操作Service实现
* @createDate 2023-12-16 16:43:57
*/
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account>
    implements AccountService{

    @Override
    public boolean addAccountInfo(Account newAccountInfo) {
        return this.save(newAccountInfo);
    }

    @Override
    public Account getAccountInfoByAccountAndType(Integer accountCategory, String loginAccount) {
        LambdaQueryWrapper<Account> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Account::getCategory, accountCategory);
        wrapper.eq(Account::getOpenCode, loginAccount);
        return this.baseMapper.selectOne(wrapper);
    }
}




