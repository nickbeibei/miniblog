package com.netease.qa.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@SuppressWarnings("serial")
@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "invalid password")  
public class InvalidPasswordException extends RuntimeException {  
	public InvalidPasswordException() {  
    }  
}  