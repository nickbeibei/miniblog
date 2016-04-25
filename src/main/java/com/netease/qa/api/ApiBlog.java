package com.netease.qa.api;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.netease.qa.api.exception.InvalidRequestException;
import com.netease.qa.meta.Blog;
import com.netease.qa.meta.BlogContent;
import com.netease.qa.meta.User;
import com.netease.qa.service.BlogService;
import com.netease.qa.service.UserService;

@Controller
@RequestMapping(value = "/api/blog")
public class ApiBlog {
	
	private static final Logger logger = LoggerFactory.getLogger(ApiBlog.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BlogService blogService;
	
	
	@RequestMapping(value = "/createblog", method = RequestMethod.POST,
			consumes="application/json", produces = "application/json")
	public @ResponseBody JSONObject createBlog(@RequestHeader HttpHeaders headers, @RequestBody String request) throws UnsupportedEncodingException {
		String title = "";
		String content = "";
		String pictures = "";
		try{
			JSONObject input = JSON.parseObject(request);
			title = input.getString("title");
			content = input.getString("content"); 
			JSONArray jsonArray = input.getJSONArray("pictures");
			for(int i = 0; i < jsonArray.size(); i ++){ 
				if(i == 0)
					pictures =  jsonArray.getString(i);
				else
					pictures =  pictures + "," + jsonArray.getString(i);
			}
		}
		catch(Exception e){
			logger.error("catch Exception when parse input. ", e);
			throw new InvalidRequestException();
		}
		if(StringUtils.isBlank(title) || StringUtils.isBlank(content)){
			throw new InvalidRequestException();
		}
		
		User user = userService.getUserFromToken(headers);
		int userId = user.getUserId();
		
		Blog blog = new Blog();
		blog.setUserId(userId);
		blog.setTitle(title);
		blog.setBlogAbstract(getAbstract(content));
		blog.setPublishTime(System.currentTimeMillis());
		blog.setModifyTime(System.currentTimeMillis());
		
		BlogContent blogContent = new BlogContent();
		blogContent.setContent(content.getBytes("utf-8")); 
		blogContent.setPictures(pictures); 
		
		logger.info("create blog, userId: " + user.getUserId());
		int blogId = blogService.createBlog(blog, blogContent);
		JSONObject json = new JSONObject();
		json.put("blogid", blogId);
		return json;
		
	}
	
	
	@RequestMapping(value = "/deleteblog", method = RequestMethod.GET,
			produces = "application/json")
	public @ResponseBody JSONObject deleteBlog(@RequestHeader HttpHeaders headers, @RequestParam("blogid") int blogId) {
		User user = userService.getUserFromToken(headers);
		logger.info("delete blog, blogId: " + blogId +  ", userId: " + user.getUserId());
		blogService.deleteBlog(blogId);
		return null;
	}
	
	
	@RequestMapping(value = "/updateblog", method = RequestMethod.POST,
			consumes="application/json", produces = "application/json")
	public @ResponseBody JSONObject updateBlog(@RequestHeader HttpHeaders headers, @RequestParam("blogid") int blogId,
			@RequestBody String request) throws UnsupportedEncodingException {
		String title = "";
		String content = "";
		String pictures = ""; 
		try{
			JSONObject input = JSON.parseObject(request);
			title = input.getString("title");
			content = input.getString("content");
			JSONArray jsonArray = input .getJSONArray("pictures");
			for(int i = 0; i < jsonArray.size(); i ++){
				if(i == 0)
					pictures =  jsonArray.getString(i);
				else
					pictures =  pictures + "," + jsonArray.getString(i);
			}
		}
		catch(Exception e){
			logger.error("catch Exception when parse input. ", e);
			throw new InvalidRequestException();
		}
		if(StringUtils.isBlank(title) || StringUtils.isBlank(content)){
			throw new InvalidRequestException();
		}
		User user = userService.getUserFromToken(headers);
		int userId = user.getUserId();
		
		Blog blog = new Blog();
		blog.setBlogId(blogId);
		blog.setUserId(userId);
		blog.setTitle(title);
		blog.setBlogAbstract(getAbstract(content));
		blog.setModifyTime(System.currentTimeMillis());
		
		BlogContent blogContent = new BlogContent();
		blogContent.setBlogId(blogId);
		blogContent.setContent(content.getBytes("utf-8"));
		blogContent.setPictures(pictures); 
		
		logger.info("update blog, userId: " + user.getUserId() + ", blogId: " + blogId);
		blogService.updateBlog(blog, blogContent);
		return null;
	}
	
	
	@RequestMapping(value = "/bloglist", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody JSONObject getBlogList(@RequestHeader HttpHeaders headers) {
		User user = userService.getUserFromToken(headers);
		logger.info("get blog list, userId: " + user.getUserId());
		JSONObject json = blogService.getBlogList(user.getUserId());
		return json;
	}
	
	
	@RequestMapping(value = "/blogdetail", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody JSONObject getBlogDetail(@RequestHeader HttpHeaders headers, @RequestParam("blogid") int blogId) {
		User user = userService.getUserFromToken(headers);
		logger.info("get blog detail, blogId: " + blogId +  ", userId: " + user.getUserId());
		JSONObject json = blogService.getBlogDetail(blogId);
		return json;
	}
	
	
	private String getAbstract(String content){
		return content.length() > 100 ? content.substring(0, 50) : content.substring(0, content.length()/2);
	}
	
}
