package com.netease.qa.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@SuppressWarnings("serial")
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "blog not exist")  
public class BlogNotExistException extends RuntimeException {  
	public BlogNotExistException() {  
    }  
}  