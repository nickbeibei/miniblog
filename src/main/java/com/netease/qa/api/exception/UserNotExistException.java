package com.netease.qa.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@SuppressWarnings("serial")
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "user not exist")  
public class UserNotExistException extends RuntimeException {  
	public UserNotExistException() {  
    }  
}  