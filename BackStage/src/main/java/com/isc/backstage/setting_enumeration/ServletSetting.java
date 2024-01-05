package com.isc.backstage.setting_enumeration;

import lombok.Data;
import lombok.Getter;

/**
 * @Author: 711lxsky
 * @Date: 2023-12-21
 */
@Data
public class ServletSetting {

    @Getter
    private static final String JSONContentType = "application/json";

    @Getter
    private static final String UTF8CharacterEncoding = "UTF-8";
}
