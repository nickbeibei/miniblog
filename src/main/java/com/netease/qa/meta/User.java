package com.netease.qa.meta;

public class User {
	
	private int userId;
	private String email;
	private String userName;
	private String password;
	private Long registerTime;
	
	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Long getRegisterTime() {
		return registerTime;
	}
	
	public void setRegisterTime(Long registerTime) {
		this.registerTime = registerTime;
	}

	

}
