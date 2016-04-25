package com.netease.qa.ajax;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.netease.qa.meta.User;
import com.netease.qa.service.UserService;


@Controller
@RequestMapping(value = "/ajax/user")
public class AjaxUser {

	private static final Logger logger = LoggerFactory.getLogger(AjaxUser.class);
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/userinfo", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
	public @ResponseBody Map<String, Object> getInfo(HttpServletRequest request,HttpServletResponse respons) {
		Cookie[] cookie = request.getCookies();
		String token = null;
		for (int i = 0; i < cookie.length; i++) {
			if (cookie[i].getName().equals("mini_blog_token")) {
				token = cookie[i].getValue();
			}
		}
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			User user = userService.getUserFromTokenString(token);
			result.put("email", user.getEmail());
			result.put("nickname", user.getUserName());
		} catch (Exception e) {
			result.put("recode", 400);
			result.put("msg", "token错误！");
		}

		return result;
	}


//	@RequestMapping(value = "/user", method = RequestMethod.POST, produces = {"text/html;charset=UTF-8"})
//	public @ResponseBody String updateInfo(@RequestParam("id") int id,@RequestParam("name") String name) {
//		logger.info("hello word~");
//		logger.info("username:" + name);
//		JSONObject json = new JSONObject();
//		json.put("userid",id);
//		json.put("username", name);
//		json.put("haha", "hehe");
//
//
//		String result = json.toString();
//		return result;
//	}

}
