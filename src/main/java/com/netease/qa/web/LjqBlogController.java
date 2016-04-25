package com.netease.qa.web;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.netease.qa.api.exception.InvalidRequestException;
import com.netease.qa.api.exception.InvalidTokenException;
import com.netease.qa.meta.Blog;
import com.netease.qa.meta.BlogContent;
import com.netease.qa.meta.User;
import com.netease.qa.service.BlogService;
import com.netease.qa.service.UserService;

/**
 * 处理所有和博文有关的请求，/blogljq开头
 */
@Controller
@RequestMapping(value = "/ljqblog")
public class LjqBlogController {

	private static final Logger logger = LoggerFactory
			.getLogger(LjqBlogController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private BlogService blogService;

	/**
	 * 获取编辑博客页面 或者 新建博客页面
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET, produces = { "text/html;charset=UTF-8" })
	public String editshow(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		String blogId = request.getParameter("id");
		logger.info("blogid=" + blogId);
		Cookie[] cookie = request.getCookies();
		if(cookie != null && cookie.length>0 && !cookie[0].getValue().equals("null"))
		{
			User user =  userService.getUserFromTokenString(cookie[0].getValue());
			logger.info("the user is" + user.getUserId() );
			if(user!=null)
			{
				model.addAttribute("blogname", user.getUserName());
				model.addAttribute("username", user.getUserName());
			}
		}
		model.addAttribute("id", blogId);
		model.addAttribute("content", "啊哈哈哈哈哈哈！");
		return "pages/ljqblog/edit";
	}

	/**
	 * 获取博文详情预览页
	 */
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public String detail(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		String blogId = request.getParameter("id");
		logger.info("blogid=" + blogId);
		response.setContentType("text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		Cookie[] cookie = request.getCookies();
		if(cookie != null && cookie.length>0 && !cookie[0].getValue().equals("null"))
		{
			User user =  userService.getUserFromTokenString(cookie[0].getValue());
			logger.info("the user is" + user.getUserId() );
			if(user!=null)
			{
				model.addAttribute("blogname", user.getUserName());
				model.addAttribute("username", user.getUserName());
			}
		}
		model.addAttribute("id", blogId);
		return "pages/ljqblog/show";
	}

	/**
	 * 获取博文详情
	 */
	@RequestMapping(value = "/content", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	public @ResponseBody JSONObject content(Model model,
			HttpServletRequest request, HttpServletResponse response,
			@RequestParam("id") int blogId) {
		// 验证用户信息是否有效，无效则403
		User user = null;
		Cookie[] cookie = request.getCookies();
		if (cookie != null && cookie.length > 0) {
			for (int i = 0; i < cookie.length; i++) {
				if (cookie[i].getName().equals("mini_blog_token")
						&& !cookie[i].getValue().equals("null")) {
					user = userService.getUserFromTokenString(cookie[i]
							.getValue());
					if(user!=null)
					{
						model.addAttribute("blogname", user.getUserName());
						model.addAttribute("username", user.getUserName());
					}

					break;
				}
			}
		}
		if (user == null)
			throw new InvalidTokenException();
		int userId = user.getUserId();
		logger.info("get blog detail, blogId: " + blogId + ", userId: "
				+ userId);
		JSONObject json = blogService.getBlogDetail(blogId);
		return json;
	}

	/**
	 * 创建博文
	 */
	@RequestMapping(value = "/createblog", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public @ResponseBody JSONObject createBlog(Model model,HttpServletRequest request,
			@RequestHeader HttpHeaders headers, @RequestBody String requestb)
			throws UnsupportedEncodingException {
		// 验证用户信息是否有效，无效则403
		User user = null;
		Cookie[] cookie = request.getCookies();
		if (cookie != null && cookie.length > 0) {
			for (int i = 0; i < cookie.length; i++) {
				if (cookie[i].getName().equals("mini_blog_token")
						&& !cookie[i].getValue().equals("null")) {
					user = userService.getUserFromTokenString(cookie[i]
							.getValue());
					if(user!=null)
					{
						model.addAttribute("blogname", user.getUserName());
						model.addAttribute("username", user.getUserName());
					}
					break;
				}
			}
		}
		if (user == null)
			throw new InvalidTokenException();
		int userId = user.getUserId();

		// 有效用户
		String title = "";
		String content = "";
		String pictures = "";
		try {
			JSONObject input = JSON.parseObject(requestb);
			title = input.getString("title");
			logger.info("title"+title);
			content = input.getString("content");
			logger.info("content"+content);
			JSONArray jsonArray = input.getJSONArray("pictures");
			for (int i = 0; i < jsonArray.size(); i++) {
				if (i == 0)
					pictures = jsonArray.getString(i);
				else
					pictures = pictures + "," + jsonArray.getString(i);
			}
		} catch (Exception e) {
			logger.error("catch Exception when parse input. ", e);
			throw new InvalidRequestException();
		}
		if (StringUtils.isBlank(title) || StringUtils.isBlank(content)) {
			throw new InvalidRequestException();
		}

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
		logger.info("blog--------: " + user.getUserId());
		int blogId = blogService.createBlog(blog, blogContent);
		JSONObject json = new JSONObject();
		json.put("blogid", blogId);

		return json;

	}

	/**
	 * 创建博文
	 */
	@RequestMapping(value = "/updateblog", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	public @ResponseBody String updateBlog(Model model,HttpServletRequest request,
			@RequestHeader HttpHeaders headers, @RequestBody String requestb,@RequestParam("blogid") int blogId)
			throws UnsupportedEncodingException {

		// 验证用户信息是否有效，无效则403
		User user = null;
		Cookie[] cookie = request.getCookies();
		if (cookie != null && cookie.length > 0) {
			for (int i = 0; i < cookie.length; i++) {
				if (cookie[i].getName().equals("mini_blog_token")
						&& !cookie[i].getValue().equals("null")) {
					user = userService.getUserFromTokenString(cookie[i]
							.getValue());
					if(user!=null)
					{
						model.addAttribute("blogname", user.getUserName());
						model.addAttribute("username", user.getUserName());
					}
					break;
				}
			}
		}
		if (user == null)
			throw new InvalidTokenException();
		int userId = user.getUserId();

		// 有效用户
		String title = "";
		String content = "";
		String pictures = "";
		try {
			JSONObject input = JSON.parseObject(requestb);
			title = input.getString("title");
			content = input.getString("content");
			JSONArray jsonArray = input.getJSONArray("pictures");
			for (int i = 0; i < jsonArray.size(); i++) {
				if (i == 0)
					pictures = jsonArray.getString(i);
				else
					pictures = pictures + "," + jsonArray.getString(i);
			}
		} catch (Exception e) {
			logger.error("catch Exception when parse input. ", e);
			throw new InvalidRequestException();
		}
		if (StringUtils.isBlank(title) || StringUtils.isBlank(content)) {
			throw new InvalidRequestException();
		}

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

		logger.info("update blog, userId: " + user.getUserId() + ", blogId: "
				+ blogId);
		blogService.updateBlog(blog, blogContent);
		return "{}";

	}

	private String getAbstract(String content) {
		return content.length() > 100 ? content.substring(0, 50) : content
				.substring(0, content.length() / 2);
	}

	@ResponseStatus(value = org.springframework.http.HttpStatus.NOT_FOUND)
	public final class ResourceNotFoundException extends RuntimeException {
		// class definition
	}

}
