package com.netease.qa.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@SuppressWarnings("serial")
@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "invalid token")  
public class InvalidTokenException extends RuntimeException {  
	public InvalidTokenException() {  
    }  
}  