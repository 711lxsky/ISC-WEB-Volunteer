package com.isc.backstage.Exception;


import com.isc.backstage.setting_enumeration.CodeAndMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.Serial;

/**
 * 授权异常
 */
@Getter
public class AuthorizationException extends HttpException {

	@Serial
	private static final long serialVersionUID = -432605618235404747L;

	public AuthorizationException() {
		super(CodeAndMessage.UN_AUTHORIZATION.getCode(), CodeAndMessage.UN_AUTHORIZATION.getDescription(), HttpStatus.UNAUTHORIZED.value());
	}

	public AuthorizationException(String message) {
		super(CodeAndMessage.UN_AUTHORIZATION.getCode(), message, HttpStatus.UNAUTHORIZED.value());
	}

	public AuthorizationException(int code) {
		super(code, CodeAndMessage.UN_AUTHORIZATION.getDescription(), HttpStatus.UNAUTHORIZED.value());
	}

	public AuthorizationException(int code, String message) {
		super(code, message, HttpStatus.UNAUTHORIZED.value());
	}
}
