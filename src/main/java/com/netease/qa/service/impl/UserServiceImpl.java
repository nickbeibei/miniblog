package com.netease.qa.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.netease.qa.api.exception.InvalidPasswordException;
import com.netease.qa.api.exception.InvalidTokenException;
import com.netease.qa.api.exception.UserExistException;
import com.netease.qa.api.exception.UserNotExistException;
import com.netease.qa.meta.User;
import com.netease.qa.meta.dao.UserDao;
import com.netease.qa.service.UserService;
import com.netease.qa.utils.MD5Utils;
import com.netease.qa.utils.TokenUtils;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao userDao;
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


	@Override
	@Transactional
	public int createUser(User user) {
		String email = user.getEmail();
		User u = userDao.findByEmail(email); 
		if(u != null){
			return -1;
		}else
		{

			userDao.insert(user);
			return user.getUserId();
		}

	}
	
	
	@Override
	public User login(String email, String password) {
		User u = userDao.findByEmail(email); 
		if (u == null) {
			throw new UserNotExistException();
		}
		String actualPWD = u.getPassword();
		if (MD5Utils.getMD5(password).equals(actualPWD)) {
			return u;
		} else {
			throw new InvalidPasswordException();
		}
	}

	
	@Override
	public User getUserInfo(int userId) {
		User u = userDao.findByUserId(userId);
		if (u == null) {
			throw new UserNotExistException();
		} else {
			return u;
		}
	}

	
	@Override
	public User getUserFromToken(HttpHeaders headers){
		//校验token
		List<String> h = headers.get("authorization");
		if(h == null || h.size() != 1){
			throw new InvalidTokenException();
		}

		int userId = TokenUtils.verifyToken(h.get(0));	
		if (userId == -1) {
			throw new InvalidTokenException();
		}
		
		User user = getUserInfo(userId);
		return user;
	}
	
	@Override
	public User getUserFromTokenString(String token){
		int userId = TokenUtils.verifyToken(token);	
		if (userId == -1) {
			throw new InvalidTokenException();
		}
		
		User user = getUserInfo(userId);
		return user;
	}

}
