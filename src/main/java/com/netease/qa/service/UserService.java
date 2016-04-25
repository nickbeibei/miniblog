package com.netease.qa.service;

import org.springframework.http.HttpHeaders;

import com.netease.qa.meta.User;


public interface UserService {
	
	public int createUser(User user);
    
    public User login(String email, String password);
	
    public User getUserInfo(int userId);
    
    public User getUserFromToken(HttpHeaders headers);
    
    public User getUserFromTokenString(String token);

}
