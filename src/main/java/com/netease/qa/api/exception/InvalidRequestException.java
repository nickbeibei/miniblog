package com.netease.qa.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@SuppressWarnings("serial")
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "invalid input parameter")  
public class InvalidRequestException extends RuntimeException {  
	public InvalidRequestException() {  
    }  
}  
