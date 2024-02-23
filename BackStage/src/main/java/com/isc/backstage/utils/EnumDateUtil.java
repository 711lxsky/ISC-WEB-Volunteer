package com.isc.backstage.utils;

import com.isc.backstage.Exception.DataErrorException;
import com.isc.backstage.setting_enumeration.AccountSetting;
import com.isc.backstage.setting_enumeration.ExceptionConstant;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @Author: 711lxsky
 * @Date: 2024-01-28
 */
@Component
public class EnumDateUtil {

    public Integer swapAccountTypeFromStringToInteger(String accountTypeStr) throws DataErrorException{
        for(AccountSetting cmp : AccountSetting.values()){
            if(Objects.equals(cmp.getDescription_EN(), accountTypeStr)){
                return cmp.getCode();
            }
        }
        throw new DataErrorException(ExceptionConstant.EnumDateError.getMessage_EN());
    }
}
