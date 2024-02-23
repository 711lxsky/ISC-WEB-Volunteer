package com.isc.backstage.setting_enumeration;

import java.util.regex.Pattern;

/**
 * 常用正则表达式常量
 *
 * @Author: 711lxsky
 * @Date: 2023-12-21
 */

public class PatternConstant {

    /**
     * 手机号正则表达式
     */
    public final static Pattern TELEPHONE_PATTERN = Pattern
            .compile("^(13\\d|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18\\d|19[0-35-9])\\d{8}$");

    /**
     * 常规自动日期格式识别正则表达式
     */
    public final static Pattern DATE_PATTERN = Pattern.compile("^[-+]?\\d*$");

    /**
     * 驼峰转下划线正则表达式
     */
    public final static Pattern UNDERLINE_PATTERN = Pattern.compile("_(\\w)");

    /**
     * 令牌前缀正则表达式
     */
    public final static String BEARER_PATTERN = "^Bearer$";

    public static Boolean judgeTelephoneNumber(String mobile){
        return TELEPHONE_PATTERN.matcher(mobile).matches();
    }
}