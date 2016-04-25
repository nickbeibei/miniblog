package com.netease.qa.meta.dao;

import java.util.List;

import com.netease.qa.meta.Blog;


public interface BlogDao {

	public int insert(Blog blog);
    
    public int update(Blog blog);
   
    public int delete(int blogId);
   
    public List<Blog> selectAllByUserId(int userId);
   
    public Blog findByBlogId(int blogId);

}
