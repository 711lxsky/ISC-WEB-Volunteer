package com.isc.backstage.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.isc.backstage.Exception.ServeErrorException;
import com.isc.backstage.setting_enumeration.CodeAndMessage;
import jakarta.annotation.Resource;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: 711lxsky
 * @Date: 2023-12-21
 */
@Log4j2
@Component
public class JacksonUtil {
    private static ObjectMapper objectMapper;

    @Resource
    public void setObjectMapper(ObjectMapper objectMapper) {
        JacksonUtil.objectMapper = objectMapper;
    }

    public <T> String objectToJson(T object) throws ServeErrorException {
        try {
            assert JacksonUtil.objectMapper != null;
            return JacksonUtil.objectMapper.writeValueAsString(object);
        }
        catch (JsonProcessingException e) {
            throw new ServeErrorException(CodeAndMessage.CANT_JSON_PARSE.getCode(), CodeAndMessage.CANT_JSON_PARSE.getDescription());
        }
    }

    public <T> T jsonToObject(String s, Class<T> valueType) throws ServeErrorException{
        if (s == null) {
            return null;
        }
        try {
            return JacksonUtil.objectMapper.readValue(s, valueType);
        }
        catch (JsonProcessingException e) {
            log.info(e);
            throw new ServeErrorException(CodeAndMessage.CANT_JSON_PARSE.getCode(), CodeAndMessage.CANT_JSON_PARSE.getDescription());
        }
    }

    public <T> T jsonToObject(String s, TypeReference<T> typeReference) throws ServeErrorException{
        if (s == null) {
            return null;
        }
        try {
            assert JacksonUtil.objectMapper != null;
            return JacksonUtil.objectMapper.readValue(s, typeReference);
        }
        catch (JsonProcessingException e) {
            log.info(e);
            throw new ServeErrorException(CodeAndMessage.CANT_JSON_PARSE.getCode(), CodeAndMessage.CANT_JSON_PARSE.getDescription());
        }
    }

    public <T> List<T> jsonToList(String s) throws ServeErrorException{
        if (s == null) {
            return null;
        }
        try {
            assert JacksonUtil.objectMapper != null;
            return JacksonUtil.objectMapper.readValue(s, new TypeReference<>() {
            });
        }
        catch (JsonProcessingException e) {
            log.info(e);
            throw new ServeErrorException(CodeAndMessage.CANT_JSON_PARSE.getCode(), CodeAndMessage.CANT_JSON_PARSE.getDescription());
        }
    }
}
