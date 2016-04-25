package com.netease.qa.meta.dao;

import com.netease.qa.meta.BlogContent;


public interface BlogContentDao {

	public int insert(BlogContent blogContent);
    
    public int update(BlogContent blogContent);
   
    public int delete(int blogId);
   
    public BlogContent findByBlogId(int blogId);
	
    
}
