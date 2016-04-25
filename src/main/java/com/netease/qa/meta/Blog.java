package com.netease.qa.meta;


public class Blog {
	
	private int blogId;
	private int userId;
	private String title;
	private String blogAbstract;
	private Long publishTime;
	private Long modifyTime;
	
	public int getBlogId() {
		return blogId;
	}
	
	public void setBlogId(int blogId) {
		this.blogId = blogId;
	}
	
	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getBlogAbstract() {
		return blogAbstract;
	}
	
	public void setBlogAbstract(String blogAbstract) {
		this.blogAbstract = blogAbstract;
	}
	
	public Long getPublishTime() {
		return publishTime;
	}
	
	public void setPublishTime(Long publishTime) {
		this.publishTime = publishTime;
	}
	
	public Long getModifyTime() {
		return modifyTime;
	}
	
	public void setModifyTime(Long modifyTime) {
		this.modifyTime = modifyTime;
	}


}
