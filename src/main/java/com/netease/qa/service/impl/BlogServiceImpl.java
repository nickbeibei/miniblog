package com.netease.qa.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.netease.qa.api.exception.BlogNotExistException;
import com.netease.qa.api.exception.InnerServerException;
import com.netease.qa.meta.Blog;
import com.netease.qa.meta.BlogContent;
import com.netease.qa.meta.dao.BlogContentDao;
import com.netease.qa.meta.dao.BlogDao;
import com.netease.qa.service.BlogService;


@Service
public class BlogServiceImpl implements BlogService {
	
	private static final Logger logger = LoggerFactory.getLogger(BlogServiceImpl.class);

	@Autowired
	private BlogDao blogDao;
	
	@Autowired
	private BlogContentDao blogContentDao;
	
	
	@Override
	public int createBlog(Blog blog, BlogContent blogContent) {
		logger.info("blog.title",""+blog.getTitle());
		logger.info("blog.content",""+blog.getBlogAbstract());
		blogDao.insert(blog);
		
		int blogId = blog.getBlogId();
		blogContent.setBlogId(blogId);
		blogContentDao.insert(blogContent);
		
		return blogId;
	}

	
	@Override
	public int deleteBlog(int blogId) {
		Blog blog = blogDao.findByBlogId(blogId);
		BlogContent blogContent = blogContentDao.findByBlogId(blogId);
		if(blog == null || blogContent == null){
			throw new BlogNotExistException();
		}
		blogDao.delete(blogId);
		blogContentDao.delete(blogId);
		return 0;
	}
	

	@Override
	public int updateBlog(Blog blog, BlogContent blogContent) {

		int blogId = blog.getBlogId();
		if(blogDao.findByBlogId(blogId) == null || blogContentDao.findByBlogId(blogId) == null){
			throw new BlogNotExistException();
		}
		
		blogDao.update(blog);
		blogContentDao.update(blogContent);
		
		return 0;
	}
	
	@Override
	public List<Blog> getBlogs(int userId){
		List<Blog> blogs = blogDao.selectAllByUserId(userId);
		return blogs;
	}
	
	@Override
	public JSONObject getBlogList(int userId) {
		
		List<Blog> blogs = blogDao.selectAllByUserId(userId);
		
		JSONArray jsonArray = new JSONArray();
		for(Blog blog : blogs){
			JSONObject tmp = new JSONObject();
			tmp.put("blogid", blog.getBlogId());
			tmp.put("title", blog.getTitle());
			tmp.put("summary", blog.getBlogAbstract());
			tmp.put("createTime", blog.getPublishTime());
			tmp.put("modifyTime", blog.getModifyTime());
			jsonArray.add(tmp);
		}
		
		JSONObject result = new JSONObject();
		result.put("userid", userId);
		result.put("blogs", jsonArray);
		
		return result;
	}

	
	@Override
	public JSONObject getBlogDetail(int blogId) {
		
		Blog blog = blogDao.findByBlogId(blogId);
		BlogContent blogContent = blogContentDao.findByBlogId(blogId);
		if(blog == null || blogContent == null){
			throw new BlogNotExistException();
		}
		
		JSONArray jsonArray = new JSONArray();
		String pictures = blogContent.getPictures();
		for(String str : pictures.split(","))
			jsonArray.add(str);
		
		JSONObject json = new JSONObject();
		try {
			json.put("content", new String(blogContent.getContent(), "utf-8"));
			json.put("createTime", blog.getPublishTime());
			json.put("modifyTime", blog.getModifyTime());
			json.put("pictures", jsonArray);
			json.put("title", blog.getTitle());
		}
		catch (Exception e) {
			logger.error("error in getBlogDetail: ", e);
			throw new InnerServerException();
		}
		return json;
	}

}
