package com.isc.backstage.utils;

import com.isc.backstage.domain.Result;
import com.isc.backstage.setting_enumeration.ServletSetting;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * @Author: 711lxsky
 * @Date: 2023-12-21
 */
@Log4j2
@Component
public class ResponseUtil {

    @Resource
    private JacksonUtil jacksonUtil;

    public void responseError(HttpServletResponse response, Integer code, String msg){
        response.setContentType(ServletSetting.getJSONContentType());
        response.setCharacterEncoding(ServletSetting.getUTF8CharacterEncoding());
        Result<?> result = Result.fail(code, msg);
        try {
            OutputStream outputStream = response.getOutputStream();
            outputStream.write(jacksonUtil.objectToJson(result).getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            log.info(e);
            throw new RuntimeException(e);
        }
    }
}
