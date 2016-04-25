package com.netease.qa.meta;



public class BlogContent {
	
	private int contentId;
	private int blogId;
	private byte[] content;
	private String pictures; //逗号分隔的url列表
	
	public int getContentId() {
		return contentId;
	}
	
	public void setContentId(int contentId) {
		this.contentId = contentId;
	}
	
	public int getBlogId() {
		return blogId;
	}
	
	public void setBlogId(int blogId) {
		this.blogId = blogId;
	}
	
	public byte[] getContent() {
		return content;
	}
	
	public void setContent(byte[] content) {
		this.content = content;
	}
	
	public String getPictures() {
		return pictures;
	}
	
	public void setPictures(String pictures) {
		this.pictures = pictures;
	}
	
	
}
