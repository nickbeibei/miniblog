package com.netease.qa.ajax;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
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
import com.netease.qa.utils.MD5Utils;
import com.netease.qa.utils.TokenUtils;


@Controller
@RequestMapping(value = "/ajax/user")
public class AjaxReg {

	private static final Logger logger = LoggerFactory.getLogger(AjaxUser.class);
	private static final int EXPIRE_TIME = 24 * 3600 * 1000; //过期时间1天，s

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/reg", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
	public @ResponseBody Map<String, Object> reg(@RequestParam("username") String username,
			@RequestParam("password") String password, @RequestParam("repassword") String repassword,
			@RequestParam("nickname") String nickname, HttpServletResponse response) {

		Map<String, Object> result = new HashMap<String, Object>();

		//还需要对email格式做合法性校验，此处略
		if(StringUtils.isBlank(username) ||
				StringUtils.isBlank(password) ||
				StringUtils.isBlank(repassword) ||
				StringUtils.isBlank(nickname) ||
				!password.equals(repassword)) {
			result.put("recode", 400);
			result.put("msg", "账号密码错误！");
		}
		else{
			logger.info("reg, user:" + username + "nickname:" + nickname);
			User user = new User();
			user.setEmail(username);
			user.setPassword(MD5Utils.getMD5(password));
			user.setUserName(nickname);
			user.setRegisterTime(System.currentTimeMillis());

			int userId = userService.createUser(user);
			logger.info("userId" + String.valueOf(userId));
			if(userId == -1)
			{
				result.put("recode", 409);
				result.put("msg", "该用户已注册");
			}else{
				result.put("recode", 200);
				result.put("msg", "成功登录");
				Cookie cookies = new Cookie("mini_blog_token", TokenUtils.createToken(userId, System.currentTimeMillis() + EXPIRE_TIME));
				cookies.setPath("/");
				cookies.setMaxAge(24 * 3600);
				response.addCookie(cookies);
				response.setContentType("application/json;charset=UTF-8");
			}


		}
		return result;
	}


}
