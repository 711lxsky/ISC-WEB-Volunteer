package com.isc.backstage.utils;

import com.isc.backstage.Exception.AuthenticationException;
import com.isc.backstage.setting_enumeration.CodeAndMessage;
import com.isc.backstage.setting_enumeration.JwtSetting;
import com.isc.backstage.setting_enumeration.PatternConstant;
import com.isc.backstage.setting_enumeration.StringConstant;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

/**
 * @Author: 711lxsky
 * @Date: 2023-12-21
 */
@Component
@Log4j2
public class RequestUtil {

    public String getTokenFromRequest(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException{
        String header = request.getHeader(JwtSetting.getAuthorization_header());
        if(! StringUtils.hasText(header)){
            throw new AuthenticationException(CodeAndMessage.NOT_FOUND_TOKEN.getDescription());
        }
        String[] splits = header.split(StringConstant.SPACE);
        log.info("splits :{}", (Object) splits);
        if (splits.length != 2 || !Pattern.matches(PatternConstant.BEARER_PATTERN, splits[0])) {
            throw new AuthenticationException(CodeAndMessage.CANT_PARSE.getDescription());
        }
        return splits[1];
    }
}
