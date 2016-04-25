package com.netease.qa.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@SuppressWarnings("serial")
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "something wrong~")  
public class InnerServerException extends RuntimeException {  
	public InnerServerException() {
		
    }  
	
}  