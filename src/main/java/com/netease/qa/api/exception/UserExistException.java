package com.netease.qa.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@SuppressWarnings("serial")
@ResponseStatus(value = HttpStatus.CONFLICT, reason = "user exist")  
public class UserExistException extends RuntimeException {  
	public UserExistException() {

    }  
}  