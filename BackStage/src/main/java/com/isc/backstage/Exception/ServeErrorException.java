package com.isc.backstage.Exception;

import com.isc.backstage.setting_enumeration.CodeAndMessage;
import org.springframework.http.HttpStatus;

/**
 * @Author: 711lxsky
 * @Date: 2023-12-21
 */

public class ServeErrorException extends  HttpException{

    public ServeErrorException(){
        super(
                CodeAndMessage.INTERNAL_SERVER_ERROR.getCode(),
                CodeAndMessage.INTERNAL_SERVER_ERROR.getDescription(),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
    }

    public ServeErrorException(Integer code) {
        super(
                code,
                CodeAndMessage.INTERNAL_SERVER_ERROR.getDescription(),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
    }

    public ServeErrorException(String message) {
        super(
                CodeAndMessage.INTERNAL_SERVER_ERROR.getCode(),
                message,
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
    }

    public ServeErrorException(Integer code, String message){
        super(
                code,
                message,
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
    }

}
