package com.netease.qa.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.netease.qa.meta.Blog;
import com.netease.qa.meta.BlogContent;


public interface BlogService {
	
	public int createBlog(Blog blog, BlogContent blogContent);

	public int deleteBlog(int blogId);

	public int updateBlog(Blog blog, BlogContent blogContent);
	
	public JSONObject getBlogList(int userId);
	
	public List<Blog> getBlogs(int userId);
	
	public JSONObject getBlogDetail(int blogId);

	
}
