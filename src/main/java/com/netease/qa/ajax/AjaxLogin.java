package com.netease.qa.ajax;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.netease.qa.meta.User;
import com.netease.qa.service.UserService;
import com.netease.qa.utils.TokenUtils;


@Controller
@RequestMapping(value = "/ajax/user")
public class AjaxLogin {
	
	private static final Logger logger = LoggerFactory.getLogger(AjaxUser.class);
	private static final long EXPIRE_TIME = 7 * 24 * 3600 * 1000; //杩囨湡鏃堕棿7澶╋紝ms
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
	public @ResponseBody Map<String, Object> login(@RequestParam("username") String username,@RequestParam("password") String password, HttpServletResponse response) {
		logger.info("login");
		logger.info("username:" + username);
		logger.info("password:" + password);
		
		Long loginTime = System.currentTimeMillis();
		Map<String, Object> result = new HashMap<String,Object>();

		if(StringUtils.isBlank(username) || StringUtils.isBlank(password)){
			result.put("recode",400);
			result.put("msg", "璐﹀彿瀵嗙爜閿欒锛�");
		} else {
			try {
				User user = userService.login(username, password);
				int userId = user.getUserId();		
				result.put("recode",200);
				result.put("msg", "鎴愬姛鐧诲綍");
				Cookie cookies = new Cookie("mini_blog_token", TokenUtils.createToken(userId, loginTime + EXPIRE_TIME));
				cookies.setPath("/");
				cookies.setMaxAge(24*60*60); 
				response.addCookie(cookies);
				response.setContentType("application/json;charset=UTF-8");
				
			} catch (Exception e) {
				result.put("recode",400);
				result.put("msg", "璐﹀彿瀵嗙爜閿欒锛�");
			}		
		}
		return result;
	}

	
	@RequestMapping(value = "/logout", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
	public @ResponseBody Map<String, Object> logout(HttpServletRequest request,HttpServletResponse response) {
		logger.info("logout");
	
		Cookie[] cookies = request.getCookies();
		Map<String, Object> result = new HashMap<String,Object>();
		
		String token = null;
		for (int i = 0; i < cookies.length; i++) {
			if (cookies[i].getName().equals("mini_blog_token")) {
				token = cookies[i].getValue();
			}
		}
		
		try {
			User user = userService.getUserFromTokenString(token);
			Cookie cookie = new Cookie("mini_blog_token",null);
			cookie.setMaxAge(0);
			response.addCookie(cookie);
			result.put("recode",200);
			result.put("msg", "閫�鍑虹櫥褰�!");
		} catch (Exception e) {
			result.put("recode", 400);
			result.put("msg", "token閿欒锛�");
		}
		
		return result;
	}
	
	
}
