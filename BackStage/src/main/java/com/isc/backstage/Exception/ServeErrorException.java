package com.isc.backstage.Exception;

/**
 * @Author: 711lxsky
 * @Date: 2023-12-21
 */

public class ServeErrorException extends  HttpException{

    public ServeErrorException(){
        super();
    }

    public ServeErrorException(Integer code) {
        super(code);
    }

    public ServeErrorException(String message){
        super(message);
    }

    public ServeErrorException(Integer code, String message){
        super(code, message);
    }

}
