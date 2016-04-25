package com.netease.qa.meta.dao;

import com.netease.qa.meta.User;

public interface UserDao {
	
	public int insert(User user);
    
    public User findByUserId(int userId);

    public User findByEmail(String email);

}
