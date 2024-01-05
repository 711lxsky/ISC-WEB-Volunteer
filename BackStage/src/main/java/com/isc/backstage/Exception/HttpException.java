package com.isc.backstage.Exception;

import com.isc.backstage.setting_enumeration.CodeAndMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @Author: 711lxsky
 * @Date: 2023-12-21
 */
@Getter
public class HttpException extends RuntimeException{

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误消息
     */
    private String message;

    /**
     * HTTP状态码
     */
    private int httpStatusCode;

    public HttpException() {
        this.message = CodeAndMessage.INTERNAL_SERVER_ERROR.getDescription();
        this.code = CodeAndMessage.INTERNAL_SERVER_ERROR.getCode();
        this.httpStatusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
    }

    public HttpException(String message) {
        this.message = message;
        this.code = CodeAndMessage.INTERNAL_SERVER_ERROR.getCode();
        this.httpStatusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
    }

    public HttpException(int code) {
        this.message = CodeAndMessage.INTERNAL_SERVER_ERROR.getDescription();
        this.code = code;
        this.httpStatusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
    }

    public HttpException(int code, int httpStatusCode) {
        this.message = CodeAndMessage.INTERNAL_SERVER_ERROR.getDescription();
        this.code = code;
        this.httpStatusCode = httpStatusCode;
    }

    public HttpException(int code, String message) {
        this.message = message;
        this.code = code;
        this.httpStatusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
    }

    public HttpException(int code, String message, int httpStatusCode) {
        this.message = message;
        this.code = code;
        this.httpStatusCode = httpStatusCode;
    }

    public HttpException(Throwable cause, int code) {
        super(cause);
        this.code = code;
        this.httpStatusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
    }

    public HttpException(Throwable cause, int code, int httpStatusCode) {
        super(cause);
        this.code = code;
        this.httpStatusCode = httpStatusCode;
    }

    public HttpException(Throwable cause){
        super(cause);
    }

    public HttpException(String message, Throwable cause) {
        super(message, cause);
    }

    public Throwable doFillInStackTrace() {
        return super.fillInStackTrace();
    }
}
