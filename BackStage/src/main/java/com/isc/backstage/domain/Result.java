package com.isc.backstage.domain;

import com.isc.backstage.Exception.HttpException;
import com.isc.backstage.setting_enumeration.CodeAndMessage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

/**
 * @Author: 711lxsky
 * @Date: 2023-12-16
 */
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "请求结果类")
public class Result <T> {

    @Schema(description = "响应码")
    private Integer code;

    @Schema(description = "响应消息")
    private String message;

    @Schema(description = "返回数据")
    private T data;


    public static <T> Result<T> success(Integer code, String message){
        return new Result<>(code,message,null);
    }

    public static <T> Result<T> success(Integer code,String message,T data){
        return new Result<>(code,message,data);
    }

    public static <T> Result<T> fail(Integer code,String message){
        return new Result<>(code,message,null);
    }

    public static <T> Result<T> fail(Integer code,String message,T data){
        return new Result<>(code,message,data);
    }

    public static <T> Result<T> fail(HttpException e){
        return new Result<>(e.getHttpStatusCode(), e.getMessage(), null);
    }

    public static <T> Result<T> fail(AuthenticationException e){
        return new Result<>(HttpStatus.UNAUTHORIZED.value(), CodeAndMessage.UN_AUTHENTICATION.getDescription(), null);
    }
}
