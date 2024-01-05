package com.isc.backstage.Exception;

import com.isc.backstage.setting_enumeration.CodeAndMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.Serial;

/**
 * 认证异常
 */
@Getter
public class AuthenticationException extends HttpException {

	@Serial
	private static final long serialVersionUID = -222891683232481602L;

	public AuthenticationException() {
		super(CodeAndMessage.UN_AUTHENTICATION.getCode(), CodeAndMessage.UN_AUTHENTICATION.getDescription(), HttpStatus.UNAUTHORIZED.value());
	}

	public AuthenticationException(String message) {
		super(CodeAndMessage.UN_AUTHENTICATION.getCode(), message, HttpStatus.UNAUTHORIZED.value());
	}

	public AuthenticationException(int code) {
		super(code, CodeAndMessage.UN_AUTHENTICATION.getDescription(), HttpStatus.UNAUTHORIZED.value());
	}

	public AuthenticationException(int code, String message) {
		super(code, message, HttpStatus.UNAUTHORIZED.value());
	}


}
