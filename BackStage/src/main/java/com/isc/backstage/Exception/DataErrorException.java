package com.isc.backstage.Exception;

import com.isc.backstage.setting_enumeration.CodeAndMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.Serial;

/**
 * @Author: 711lxsky
 * @Date: 2024-01-24
 */
@Getter
public class DataErrorException extends HttpException{

    @Serial
    private static final long serialVersionUID = -222891683232481602L;

    public DataErrorException() {
        super(
                CodeAndMessage.DATA_ERROR.getCode(),
                CodeAndMessage.DATA_ERROR.getDescription(),
                HttpStatus.BAD_REQUEST.value()
        );
    }

    public DataErrorException(String message) {
        super(
                CodeAndMessage.DATA_ERROR.getCode(),
                message,
                HttpStatus.BAD_REQUEST.value()
        );

    }

    public DataErrorException(int code) {
        super(
                code,
                CodeAndMessage.DATA_ERROR.getDescription(),
                HttpStatus.BAD_REQUEST.value()
        );
    }

    public DataErrorException(int code, int httpStatusCode) {
        super(
                code,
                CodeAndMessage.DATA_ERROR.getDescription(),
                httpStatusCode
        );
    }

    public DataErrorException(int code, String message) {
        super(
                code,
                message,
                HttpStatus.BAD_REQUEST.value());
    }

    public DataErrorException(int code, String message, int httpStatusCode) {
        super(code, message, httpStatusCode);

    }

    public DataErrorException(Throwable cause, int code) {
        super(cause, code);
    }

    public DataErrorException(Throwable cause, int code, int httpStatusCode) {
        super(cause, code, httpStatusCode);
    }

    public DataErrorException(Throwable cause) {
        super(cause);
    }

    public DataErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
