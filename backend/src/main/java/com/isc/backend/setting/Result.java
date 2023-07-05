package com.isc.backend.setting;

// 这里我没用lombok注解
public class Result<T> {
    private Integer code;
    private String message;
    private T data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

      public Result(Integer code, String message, T data) {
        this.setCode(code);
        this.setMessage(message);
        this.setData(data);
    }

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
