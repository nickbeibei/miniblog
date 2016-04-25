package com.netease.qa.api;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.netease.qa.api.exception.InvalidRequestException;
import com.netease.qa.meta.User;
import com.netease.qa.service.UserService;
import com.netease.qa.utils.MD5Utils;
import com.netease.qa.utils.TokenUtils;

@Controller
@RequestMapping(value = "/api/user")
public class ApiUser {

	private static final Logger logger = LoggerFactory.getLogger(ApiUser.class);
	private static final long EXPIRE_TIME = 7 * 24 * 3600 * 1000; //过期时间7天，ms

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/createuser", method = RequestMethod.POST,
			consumes = "application/json", produces = "application/json")
	public @ResponseBody JSONObject createUser(@RequestBody String request) {
		String email = "";
		String nickname = "";
		String password = "";
		Long registerTime = System.currentTimeMillis();
		try{
			JSONObject input = JSON.parseObject(request);
			email = input.getString("email1");
			nickname = input.getString("nickname");
			password = input.getString("password");
		}
		catch(Exception e){
			logger.error("catch Exception when parse input. ", e);
			throw new InvalidRequestException();
		}
		//还需要对email格式做合法性校验，此处略
		if(StringUtils.isBlank(email) || StringUtils.isBlank(nickname) || StringUtils.isBlank(password)){
			throw new InvalidRequestException();
		}

		User user = new User();
		user.setEmail(email);
		user.setPassword(MD5Utils.getMD5(password));
		user.setUserName(nickname);
		user.setRegisterTime(registerTime);

		int userId = userService.createUser(user);
		JSONObject json = new JSONObject();
		json.put("userid", userId);
		json.put("mini_blog_token", TokenUtils.createToken(userId, registerTime + EXPIRE_TIME));
		json.put("expire", registerTime + EXPIRE_TIME);
		return json;

	}


	@RequestMapping(value = "/login", method = RequestMethod.POST,
			consumes="application/json", produces = "application/json")
	public @ResponseBody JSONObject login(@RequestBody String request) {
		String email = "";
		String password = "";
		Long loginTime = System.currentTimeMillis();
		try{
			JSONObject input = JSON.parseObject(request);
			email = input.getString("email");
			password = input.getString("password");
		}
		catch(Exception e){
			logger.error("catch Exception when parse input. ", e);
			throw new InvalidRequestException();
		}
		if(StringUtils.isBlank(email) || StringUtils.isBlank(password)){
			throw new InvalidRequestException();
		}

		User user = userService.login(email, password);
		int userId = user.getUserId();
		JSONObject json = new JSONObject();
		json.put("userid", userId);
		json.put("mini_blog_token", TokenUtils.createToken(userId, loginTime + EXPIRE_TIME));
		json.put("expire", loginTime + EXPIRE_TIME);
		return json;
	}


	@RequestMapping(value = "/userinfo", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody JSONObject getUserInfo(@RequestHeader HttpHeaders headers) {

		User user = userService.getUserFromToken(headers);
		JSONObject json = new JSONObject();
		json.put("email", user.getEmail());
		json.put("nickname", user.getUserName());
		return json;
	}

}
