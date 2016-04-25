package com.netease.qa.web;



import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.netease.qa.meta.Blog;
import com.netease.qa.meta.User;
import com.netease.qa.service.BlogService;
import com.netease.qa.service.UserService;

@Controller
public class ArticleController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BlogService blogService;
	
	private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);
	@RequestMapping(value = "/*", method = RequestMethod.GET)
	public String getList(Model model,HttpServletResponse response,HttpServletRequest request) {
		
		
		
		Cookie[] cookie = request.getCookies();
		User user = null;
		if (cookie != null && cookie.length > 0) {
			for (int i = 0; i < cookie.length; i++) {
				if (cookie[i].getName().equals("mini_blog_token")
						&& !cookie[i].getValue().equals("null")) {
					user = userService.getUserFromTokenString(cookie[i]
							.getValue());
					break;
				}
			}

			if(user != null)
			{
				List<Blog> arts =  blogService.getBlogs(user.getUserId());

				model.addAttribute("articleList", arts);
				model.addAttribute("userid",user.getUserId());
				model.addAttribute("blogname", user.getUserName());
				return "articles";
			}else
			{
				return "login";
			}







		}else{
			return "login";
		}
	}

}
