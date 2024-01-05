package com.isc.backstage.domain;

import lombok.*;

/**
 * @Author: 711lxsky
 * @Date: 2023-12-16
 */
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Result <T> {

    private Integer code;

    private String message;

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
}
